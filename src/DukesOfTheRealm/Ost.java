package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

import DukesOfTheRealm.Castle.Orientation;
import Enums.SoldierEnum;
import Interface.IUpdate;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Soldiers.Soldier;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Repr�sente une ost. Contient tous les soldats qui la compose.
 */
public class Ost implements IUpdate, Serializable
{
	private static final long serialVersionUID = 1L;

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * R�f�rence sur le ch�teau d'origine de l'ost.
	 */
	private final Castle origin;

	/**
	 * R�f�rence sur le ch�teau de destination de l'ost.
	 */
	private final Castle destination;

	/**
	 * La direction g�n�rale vers laquelle l'ost se dirige : Nord Ouest / Nord Est / Sud Ouest / Sud
	 * Est.
	 */
	private Orientation destinationArea;

	/**
	 * Le point autours du ch�teau de destination vers lequel toutes les unit�s de l'ost se dirigent
	 * dans un premier temps.
	 */
	private Point2D separationPoint = null;

	/**
	 * Le point d'attente autours du ch�teau de destination o� une unit� se positionne si toutes les
	 * positions d'attaque du ch�teau sont occup�es.
	 */
	private Point2D waitingPoint = null;

	/**
	 * Le nombre total de soldats qui composent l'ost, tous types confondus.
	 */
	private final int nbSoldiers;

	/**
	 * Le nombre de soldats de type Piquiers qui composent l'ost.
	 */
	private final int nbPikers;

	/**
	 * Le nombre de soldats de type Chevaliers qui composent l'ost.
	 */
	private final int nbKnights;

	/**
	 * Le nombre de soldats de type Catapulte qui composent l'ost.
	 */
	private final int nbOnagers;

	/**
	 * La vitesse de d�placement de l'ost.
	 */
	private int speed;

	/**
	 * La liste contenant toutes les r�f�rences vers les soldats de l'ost.
	 */
	private final ArrayList<Soldier> soldiers;

	/**
	 * Le nombre de soldats qui ont �t� d�ploy�s sur le terrain.
	 */
	private int nbSoldiersSpawned;

	/**
	 * Un bool�en indiquant si l'ost est enti�rement d�ploy�e sur le terrain.
	 */
	private boolean fullyDeployed = false;

	/**
	 * La couleur qui repr�sente l'ost � l'�cran.
	 */
	private transient Color color;

	/**
	 * Donne la date du dernier d�ploiement d'unit�.
	 */
	private transient long lastTime;

	/**
	 * Bool�en indiquant si l'ost correspond � un envoi de renforts d'un ch�teau � un autre d'un m�me
	 * acteur.
	 */
	private boolean isBackup;

	/**
	 * Un bool�en indiquant si l'attaque est actuellement stop�e ou non.
	 */
	private boolean stopAttack = false;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur Ost.
	 * 
	 * @param origin      la r�f�rence vers le ch�teau d'origine.
	 * @param destination la r�f�rence vers le ch�teau de destination.
	 * @param nbPikers    le nombre de soldats de type Piquier.
	 * @param nbKnights   le nombre de soldats de type Chevalier.
	 * @param nbOnagers   le nombre de soldats de type Catapulte.
	 * @param color       la couleur permettant de repr�senter l'ost � l'�cran.
	 * @param isBackup    le bool�en indiquant si l'ost correspond � un transport de renforts vers un
	 *                    ch�teau alli�.
	 */
	public Ost(final Castle origin, final Castle destination, final int nbPikers, final int nbKnights, final int nbOnagers,
			final Color color, final boolean isBackup)
	{
		this.origin = origin;
		this.destination = destination;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
		this.nbSoldiers = this.nbPikers + this.nbKnights + this.nbOnagers;
		this.soldiers = new ArrayList<>();
		this.color = color;
		this.isBackup = isBackup;
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		SetDestinationArea();
		this.speed = SetOstSpeed();
		this.separationPoint = SetSeparationPoint();
		this.waitingPoint = SetWaitingPoint();
	}

	/**
	 * Initialise les composants transients d'une ost.
	 * 
	 * @param color la couleur permettant de repr�senter l'ost � l'�cran.
	 */
	public void startTransient(final Color color)
	{
		this.color = color;
		this.soldiers.forEach(soldier ->
		{
			soldier.startTransient(this.origin.getLayer());
			soldier.Awake(this.color);
		});
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		this.isBackup = this.origin.getActor() == this.destination.getActor();

		if (!this.fullyDeployed && Time(now, pause))
		{
			DeployOneSoldiersWave();
		}

		if (!pause)
		{
			if (this.soldiers.size() > 0)
			{
				final Iterator<Soldier> it = this.soldiers.iterator();

				while (it.hasNext())
				{
					Soldier s = it.next();

					if (s.isDead)
					{
						s.RemoveShapeToLayer();
						this.destination.freeAttackLocation(s.getAttackLocation());
						it.remove();
						Main.nbSoldier--;
					}
					else
					{
						s.update(now, pause);
					}
				}
			}
			else
			{
				this.origin.removeOst();
			}
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * Calcule la vitesse de d�placement de l'ost selon les unit�s qui la compose.
	 * 
	 * @return la vitesse de d�placement de l'ost.
	 */
	private int SetOstSpeed()
	{
		int minimalSpeed = Settings.KNIGHT_SPEED;
		minimalSpeed = this.nbPikers > 0 ? Settings.PIKER_SPEED : minimalSpeed;
		minimalSpeed = this.nbOnagers > 0 ? Settings.ONAGER_SPEED : minimalSpeed;
		return minimalSpeed;
	}

	/**
	 * D�termine le point de s�paration de l'ost autours du ch�teau adverse en fonction de la position
	 * de celui-ci par rapport au ch�teau d'origine de l'ost.
	 * <p>
	 * Par exemple pour un ch�teau adverse situ� au Nord-Est du ch�teau de d�part le point de s�paration
	 * sera au Sud-Ouest du ch�teau adverse.
	 * </p>
	 * 
	 * @return le point de s�paration de l'ost.
	 */
	private Point2D SetSeparationPoint()
	{
		final Orientation area = getDestinationArea();
		final int offsetX = area == Orientation.NE || area == Orientation.SE ? -Settings.GAP_WITH_SOLDIER - Settings.SOLDIER_SIZE
				: Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER;
		final int offsetY = area == Orientation.SE || area == Orientation.SW ? -Settings.GAP_WITH_SOLDIER - Settings.SOLDIER_SIZE
				: Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER;
		final Point2D separationPoint = new Point2D(this.destination.getX() + offsetX, this.destination.getY() + offsetY);
		return separationPoint;
	}

	/**
	 * D�termine le point d'attente d'une unit� autours du ch�teau adverse en fonction de la position de
	 * celui-ci par rapport au ch�teau d'origine de l'ost.
	 * 
	 * @return le point d'attente d'une unit�.
	 */
	private Point2D SetWaitingPoint()
	{
		int offsetX = 0;
		int offsetY = 0;
		switch (getDestinationArea())
		{
			case NW:
				offsetX = 2 * Settings.THIRD_OF_CASTLE;
				offsetY = Settings.CASTLE_SIZE + 2 * Settings.GAP_WITH_SOLDIER + Settings.SOLDIER_SIZE;
				break;
			case NE:
				offsetX = -2 * (Settings.GAP_WITH_SOLDIER + Settings.SOLDIER_SIZE);
				offsetY = 2 * Settings.THIRD_OF_CASTLE;
				break;
			case SE:
				offsetY = -2 * (Settings.GAP_WITH_SOLDIER + Settings.SOLDIER_SIZE);
				break;
			case SW:
				offsetX = Settings.CASTLE_SIZE + 2 * Settings.GAP_WITH_SOLDIER + Settings.SOLDIER_SIZE;
				break;
			default:
				break;
		}
		final Point2D waitingPoint = new Point2D(this.destination.getX() + offsetX, this.destination.getY() + offsetY);
		return waitingPoint;
	}

	/**
	 * D�termine et met en place la direction g�n�rale vers laquelle l'ost se dirige : Nord-Ouest /
	 * Nord-Est / Sud-Ouest / Sud-Est.
	 */
	private void SetDestinationArea()
	{
		if (this.origin.getX() <= this.destination.getX())
		{
			this.destinationArea = this.origin.getY() <= this.destination.getY() ? Orientation.SE : Orientation.NE;
		}
		else
		{
			this.destinationArea = this.origin.getY() <= this.destination.getY() ? Orientation.SW : Orientation.NW;
		}
	}

	/**
	 * D�ploie une vague de soldat sur le terrain. Le nombre de soldats d�ploy�s en une vague correspond
	 * au nombre par d�faut ou au nombre d'unit�s restantes � d�ployer s'il en reste moins.
	 * 
	 * @see Settings
	 */
	private void DeployOneSoldiersWave()
	{
		final int nbSpawn = this.nbSoldiersSpawned <= this.nbSoldiers - Settings.SIMULTANEOUS_SPAWNS ? Settings.SIMULTANEOUS_SPAWNS
				: this.nbSoldiers - this.nbSoldiersSpawned;
		Main.nbSoldier += nbSpawn;
		switch (this.origin.getOrientation())
		{
			case North:
			case South:
				for (int i = 0; i < nbSpawn; i++)
				{
					SpawnSoldier(this.origin.getX() + Settings.THIRD_OF_CASTLE * i, this.origin.getY());
				}
				break;
			case West:
			case East:
				for (int i = 0; i < nbSpawn; i++)
				{
					SpawnSoldier(this.origin.getX(), this.origin.getY() + Settings.THIRD_OF_CASTLE * i);
				}
				break;
			default:
				break;
		}

		if (this.nbSoldiersSpawned == this.nbSoldiers)
		{
			this.fullyDeployed = true;
		}
	}

	/**
	 * Fais appara�tre un soldat sur le terrain.
	 * 
	 * @param x l'abscisse du soldat � cr�er.
	 * @param y l'ordonn�e du soldat � cr�er.
	 */
	private void SpawnSoldier(final int x, final int y)
	{
		final SoldierEnum soldierType = getNextAvailableSoldier();
		final Pane layer = this.origin.getLayer();

		switch (this.origin.getOrientation())
		{
			case North:
				switch (soldierType)
				{
					case Piker:
						final Piker piker = new Piker(layer,
								new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.PIKER_REPRESENTATION_RADIUS * 2), this, this.speed);
						this.soldiers.add(piker);
						piker.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Knight:
						final Knight knight = new Knight(layer,
								new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.KNIGHT_REPRESENTATION_SIZE), this, this.speed);
						this.soldiers.add(knight);
						knight.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Onager:
						final Onager onager = new Onager(layer,
								new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE), this, this.speed);
						this.soldiers.add(onager);
						onager.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					default:
						break;
				}
				break;
			case South:
				switch (soldierType)
				{
					case Piker:
						final Piker piker = new Piker(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this,
								this.speed);
						this.soldiers.add(piker);
						piker.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Knight:
						final Knight knight = new Knight(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this,
								this.speed);
						this.soldiers.add(knight);
						knight.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Onager:
						final Onager onager = new Onager(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this,
								this.speed);
						this.soldiers.add(onager);
						onager.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					default:
						break;
				}
				break;
			case West:
				switch (soldierType)
				{
					case Piker:
						final Piker piker = new Piker(layer,
								new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.PIKER_REPRESENTATION_RADIUS * 2, y), this, this.speed);
						this.soldiers.add(piker);
						piker.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Knight:
						final Knight knight = new Knight(layer,
								new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.KNIGHT_REPRESENTATION_SIZE, y), this, this.speed);
						this.soldiers.add(knight);
						knight.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Onager:
						final Onager onager = new Onager(layer,
								new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE, y), this, this.speed);
						this.soldiers.add(onager);
						onager.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					default:
						break;
				}
				break;
			case East:
				switch (soldierType)
				{
					case Piker:
						final Piker piker = new Piker(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this,
								this.speed);
						this.soldiers.add(piker);
						piker.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Knight:
						final Knight knight = new Knight(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this,
								this.speed);
						this.soldiers.add(knight);
						knight.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Onager:
						final Onager onager = new Onager(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this,
								this.speed);
						this.soldiers.add(onager);
						onager.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					default:
						break;
				}
				break;

			default:
				break;
		}

	}

	/**
	 * D�termine le prochain type de soldat � d�ployer parmi ceux qui ne l'ont pas encore �t�. La
	 * priorit� est donn�e aux unit�s les plus lentes.
	 * 
	 * @return le type du prochain soldat � d�ployer.
	 */
	private SoldierEnum getNextAvailableSoldier()
	{
		final AtomicReference<SoldierEnum> slowestType = new AtomicReference<>();
		final long nbCreatedPikers = this.soldiers.stream().filter(soldier -> soldier.getType() == SoldierEnum.Piker).count();
		final long nbCreatedOnagers = this.soldiers.stream().filter(soldier -> soldier.getType() == SoldierEnum.Onager).count();

		if (nbCreatedOnagers < this.nbOnagers)
		{
			slowestType.set(SoldierEnum.Onager);
		}
		else if (nbCreatedPikers < this.nbPikers)
		{
			slowestType.set(SoldierEnum.Piker);
		}
		else
		{
			slowestType.set(SoldierEnum.Knight);
		}
		return slowestType.get();
	}

	/**
	 * V�rifie si le temps �coul� depuis le dernier d�ploiement de soldats est suffisant pour pouvoir en
	 * d�ployer � nouveau.
	 * 
	 * @param  now   la date actuelle.
	 * @param  pause le bool�en indiquant si le jeu est en pause.
	 * @return       true si le delta entre now et lastTime est sup�rieur au temps fix� par d�faut entre
	 *               deux d�ploiement de soldats.
	 * @see          Settings
	 */
	private boolean Time(final long now, final boolean pause)
	{
		if (pause)
		{
			this.lastTime = now;
		}
		if (now - this.lastTime > Settings.GAME_FREQUENCY_OST)
		{
			this.lastTime = now;
			return true;
		}
		return false;
	}

	/**
	 * R�alise les traitements n�c�ssaires lorsque l'ost � vaincu le ch�teau adverse : modifier le
	 * propri�taire du ch�teau et sa couleur, et r�initialiser sa file de production.
	 */
	public void win()
	{
		if (!this.stopAttack && !this.origin.getActor().isDead)
		{
			this.stopAttack = true;
			if (!this.isBackup)
			{
				this.origin.getActor().castlesWaitForAdding.add(this.destination);
				this.destination.getActor().castlesWaitForDelete.add(this.destination);

				this.destination.setActor(this.origin.getActor());

				this.destination.switchColor(this.origin.getActor().getColor());
				this.destination.resetQueue(false);
				this.destination.reactivateAttack();
			}
		}
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return la r�f�rence vers le ch�teau d'origine.
	 */
	public final Castle getOrigin()
	{
		return this.origin;
	}

	/**
	 * @return la r�f�rence vers le ch�teau de destination.
	 */
	public final Castle getDestination()
	{
		return this.destination;
	}

	/**
	 * @return la position du ch�teau adverse par rapport au ch�teau d'origine.
	 */
	public Orientation getDestinationArea()
	{
		return this.destinationArea;
	}

	/**
	 * @return le point de s�paration de l'ost autours du ch�teau adverse.
	 */
	public Point2D getSeparationPoint()
	{
		return this.separationPoint;
	}

	/**
	 * @return le point d'attente d'une unit� autours du ch�teau adverse.
	 */
	public final Point2D getWaitingPoint()
	{
		return this.waitingPoint;
	}

	/**
	 * @return le bool�en indiquant si l'ost est enti�rement d�ploy�e ou non.
	 */
	public final boolean isFullyDeployed()
	{
		return this.fullyDeployed;
	}

	/**
	 * @return le bool�en indiquant si l'ost correspond � un envoi de renforts vers un ch�teau alli�.
	 */
	public final boolean isBackup()
	{
		return this.isBackup;
	}

	/**
	 * @return le bool�en indiquant si l'attaque est stopp�e.
	 */
	public final boolean isStopAttack()
	{
		return this.stopAttack;
	}

	/**
	 * @return la liste de soldats composant l'ost.
	 */
	public final ArrayList<Soldier> getSoldiers()
	{
		return this.soldiers;
	}

}
