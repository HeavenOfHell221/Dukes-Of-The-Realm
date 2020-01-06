package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import DukesOfTheRealm.Castle.Orientation;
import Enums.SoldierEnum;
import Interface.IUpdate;
import Soldiers.Archer;
import Soldiers.Berserker;
import Soldiers.Conveyors;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Soldiers.Soldier;
import Soldiers.Spy;
import Utility.Point2D;
import Utility.Settings;
import Utility.SoldierPack;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Représente une ost. Contient tous les soldats qui la compose.
 */
public class Ost implements IUpdate, Serializable
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Référence sur le château d'origine de l'ost.
	 */
	private final Castle origin;

	/**
	 * Référence sur le château de destination de l'ost.
	 */
	private final Castle destination;

	/**
	 * La direction générale vers laquelle l'ost se dirige : Nord Ouest / Nord Est / Sud Ouest / Sud
	 * Est.
	 */
	private Orientation destinationArea;

	/**
	 * Le point autours du château de destination vers lequel toutes les unités de l'ost se dirigent
	 * dans un premier temps.
	 */
	private Point2D separationPoint = null;

	/**
	 * Le point d'attente autours du château de destination où une unité se positionne si toutes les
	 * positions d'attaque du château sont occupées.
	 */
	private Point2D waitingPoint = null;

	/**
	 * Nombre d'unité total de l'ost.
	 */
	private final int nbSoldiers;

	/**
	 * Nombre d'unité de l'ost.
	 *
	 * @see Utility.SoldierPack
	 */
	private final SoldierPack<Integer> soldierPack;

	/**
	 * La vitesse de déplacement de l'ost.
	 */
	private int speed;

	/**
	 * La liste contenant toutes les références vers les soldats de l'ost.
	 */
	private final ArrayList<Soldier> soldiers;

	/**
	 * Le nombre de soldats qui ont été déployés sur le terrain.
	 */
	private int nbSoldiersSpawned;

	/**
	 * Un booléen indiquant si l'ost est entièrement déployée sur le terrain.
	 */
	private boolean fullyDeployed = false;

	/**
	 * La couleur qui représente l'ost à l'écran.
	 */
	private transient Color color;

	/**
	 * Donne la date du dernier déploiement d'unité.
	 */
	private transient long lastTime;

	/**
	 * Booléen indiquant si l'ost correspond à un envoi de renforts d'un château à un autre d'un même
	 * acteur.
	 */
	private boolean isBackup;

	/**
	 * Un booléen indiquant si l'attaque est actuellement stopée ou non.
	 */
	private boolean stopAttack = false;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur Ost.
	 *
	 * @param origin      la référence vers le château d'origine.
	 * @param destination la référence vers le château de destination.
	 * @param soldierPack Le nombre d'unité dans l'ost.
	 * @param color       la couleur permettant de représenter l'ost à l'écran.
	 */
	public Ost(final Castle origin, final Castle destination, final SoldierPack<Integer> soldierPack, final Color color)
	{
		this.origin = origin;
		this.destination = destination;
		this.soldierPack = soldierPack;
		this.soldiers = new ArrayList<>();
		this.color = color;

		int count = 0;
		for (int i : soldierPack.values())
		{
			count += i;
		}

		this.nbSoldiers = count;
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
	 * @param color la couleur permettant de représenter l'ost à l'écran.
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

						this.origin.getMiller().addVillager(s.getType().villager);

						if (s.getType() == SoldierEnum.Conveyors)
						{
							this.origin.getMarket().removeConveyors();
							this.destination.addFlorin(Settings.CONVEYORS_FLORIN);
						}
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
	 * Calcule la vitesse de déplacement de l'ost selon les unités qui la compose.
	 *
	 * @return la vitesse de déplacement de l'ost.
	 */
	private int SetOstSpeed()
	{
		int minimalSpeed = Integer.MAX_VALUE;

		for (SoldierEnum s : SoldierEnum.values())
		{
			minimalSpeed = this.soldierPack.get(s) > 0 && s.speed < minimalSpeed ? s.speed : minimalSpeed;
		}
		return minimalSpeed;
	}

	/**
	 * Détermine le point de séparation de l'ost autours du château adverse en fonction de la position
	 * de celui-ci par rapport au château d'origine de l'ost.
	 * <p>
	 * Par exemple pour un château adverse situé au Nord-Est du château de départ le point de séparation
	 * sera au Sud-Ouest du château adverse.
	 * </p>
	 *
	 * @return le point de séparation de l'ost.
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
	 * Détermine le point d'attente d'une unité autours du château adverse en fonction de la position de
	 * celui-ci par rapport au château d'origine de l'ost.
	 *
	 * @return le point d'attente d'une unité.
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
	 * Détermine et met en place la direction générale vers laquelle l'ost se dirige : Nord-Ouest /
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
	 * Déploie une vague de soldat sur le terrain. Le nombre de soldats déployés en une vague correspond
	 * au nombre par défaut ou au nombre d'unités restantes à déployer s'il en reste moins.
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
					spawnSoldier(this.origin.getX() + Settings.THIRD_OF_CASTLE * i, this.origin.getY());
				}
				break;
			case West:
			case East:
				for (int i = 0; i < nbSpawn; i++)
				{
					spawnSoldier(this.origin.getX(), this.origin.getY() + Settings.THIRD_OF_CASTLE * i);
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
	 * Fais apparaître un soldat sur le terrain.
	 *
	 * @param x l'abscisse du soldat à créer.
	 * @param y l'ordonnée du soldat à créer.
	 */
	private void spawnSoldier(final int x, final int y)
	{
		final SoldierEnum soldierType = getNextAvailableSoldier();
		final Pane layer = this.origin.getLayer();
		Soldier soldier = null;
		switch (this.origin.getOrientation())
		{
			case North:
				switch (soldierType)
				{
					case Piker:
						soldier = new Piker(layer, new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.PIKER_REPRESENTATION_RADIUS * 2),
								this, this.speed);
						break;
					case Knight:
						soldier = new Knight(layer, new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.KNIGHT_REPRESENTATION_SIZE),
								this, this.speed);
						break;
					case Onager:
						soldier = new Onager(layer, new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE),
								this, this.speed);
						break;
					case Archer:
						soldier = new Archer(layer, new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE),
								this, this.speed);
						break;
					case Berserker:
						soldier = new Berserker(layer, new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE),
								this, this.speed);
						break;
					case Spy:
						soldier = new Spy(layer, new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE), this,
								this.speed);
						break;
					case Conveyors:
						soldier = new Conveyors(layer, new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE),
								this, this.speed);
						break;
					default:
						break;
				}

				break;
			case South:
				switch (soldierType)
				{
					case Piker:
						soldier = new Piker(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this, this.speed);
						break;
					case Knight:
						soldier = new Knight(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this, this.speed);
						break;
					case Onager:
						soldier = new Onager(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this, this.speed);
						break;
					case Archer:
						soldier = new Archer(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this, this.speed);
						break;
					case Berserker:
						soldier = new Berserker(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this,
								this.speed);
						break;
					case Spy:
						soldier = new Spy(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this, this.speed);
						break;
					case Conveyors:
						soldier = new Conveyors(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this,
								this.speed);
						break;
					default:
						break;
				}
				break;
			case West:
				switch (soldierType)
				{
					case Piker:
						soldier = new Piker(layer, new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.PIKER_REPRESENTATION_RADIUS * 2, y),
								this, this.speed);
						break;
					case Knight:
						soldier = new Knight(layer, new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.KNIGHT_REPRESENTATION_SIZE, y),
								this, this.speed);
						break;
					case Onager:
						soldier = new Onager(layer, new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE, y),
								this, this.speed);
						break;
					case Archer:
						soldier = new Archer(layer, new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE, y),
								this, this.speed);
						break;
					case Berserker:
						soldier = new Berserker(layer, new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE, y),
								this, this.speed);
						break;
					case Spy:
						soldier = new Spy(layer, new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE, y), this,
								this.speed);
						break;
					case Conveyors:
						soldier = new Conveyors(layer, new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE, y),
								this, this.speed);
						break;
					default:
						break;
				}
				break;
			case East:
				switch (soldierType)
				{
					case Piker:
						soldier = new Piker(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this, this.speed);
						break;
					case Knight:
						soldier = new Knight(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this, this.speed);
						break;
					case Onager:
						soldier = new Onager(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this, this.speed);
						break;
					case Archer:
						soldier = new Archer(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this, this.speed);
						break;
					case Berserker:
						soldier = new Berserker(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this,
								this.speed);
						break;
					case Spy:
						soldier = new Spy(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this, this.speed);
						break;
					case Conveyors:
						soldier = new Conveyors(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this,
								this.speed);
						break;
					default:
						break;
				}
				break;

			default:
				break;
		}

		if (soldier != null)
		{
			this.soldiers.add(soldier);
			soldier.Awake(this.color);
			this.nbSoldiersSpawned++;
			this.soldierPack.replace(soldierType, this.soldierPack.get(soldierType) - 1);
		}
	}

	/**
	 * Détermine le prochain type de soldat à déployer parmi ceux qui ne l'ont pas encore été. La
	 * priorité est prédéfinie.
	 *
	 * @return le type du prochain soldat à déployer.
	 */
	private SoldierEnum getNextAvailableSoldier()
	{
		SoldierEnum nextType;

		if (this.soldierPack.get(SoldierEnum.Onager) > 0)
		{
			nextType = SoldierEnum.Onager;
		}
		else if (this.soldierPack.get(SoldierEnum.Spy) > 0)
		{
			nextType = SoldierEnum.Spy;
		}
		else if (this.soldierPack.get(SoldierEnum.Archer) > 0)
		{
			nextType = SoldierEnum.Archer;
		}
		else if (this.soldierPack.get(SoldierEnum.Berserker) > 0)
		{
			nextType = SoldierEnum.Berserker;
		}
		else if (this.soldierPack.get(SoldierEnum.Piker) > 0)
		{
			nextType = SoldierEnum.Piker;
		}
		else if (this.soldierPack.get(SoldierEnum.Knight) > 0)
		{
			nextType = SoldierEnum.Knight;
		}
		else
		{
			nextType = SoldierEnum.Conveyors;
		}

		return nextType;
	}

	/**
	 * Vérifie si le temps écoulé depuis le dernier déploiement de soldats est suffisant pour pouvoir en
	 * déployer à nouveau.
	 *
	 * @param  now   la date actuelle.
	 * @param  pause le booléen indiquant si le jeu est en pause.
	 * @return       true si le delta entre now et lastTime est supérieur au temps fixé par défaut entre
	 *               deux déploiement de soldats.
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
	 * Réalise les traitements nécéssaires lorsque l'ost à vaincu le château adverse : modifier le
	 * propriétaire du château et sa couleur, et réinitialiser sa file de production.
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
				this.destination.getCaserne().resetQueue(false);
				this.destination.getReserveOfSoldiers().reactivateAttack();
			}
		}
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return la référence vers le château d'origine.
	 */
	public final Castle getOrigin()
	{
		return this.origin;
	}

	/**
	 * @return la référence vers le château de destination.
	 */
	public final Castle getDestination()
	{
		return this.destination;
	}

	/**
	 * @return la position du château adverse par rapport au château d'origine.
	 */
	public Orientation getDestinationArea()
	{
		return this.destinationArea;
	}

	/**
	 * @return le point de séparation de l'ost autours du château adverse.
	 */
	public Point2D getSeparationPoint()
	{
		return this.separationPoint;
	}

	/**
	 * @return le point d'attente d'une unité autours du château adverse.
	 */
	public final Point2D getWaitingPoint()
	{
		return this.waitingPoint;
	}

	/**
	 * @return le booléen indiquant si l'ost est entièrement déployée ou non.
	 */
	public final boolean isFullyDeployed()
	{
		return this.fullyDeployed;
	}

	/**
	 * @return le booléen indiquant si l'ost correspond à un envoi de renforts vers un château allié.
	 */
	public final boolean isBackup()
	{
		return this.isBackup;
	}

	/**
	 * @return le booléen indiquant si l'attaque est stoppée.
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

	/**
	 * @return the speed
	 */
	public final int getSpeed()
	{
		return this.speed;
	}
}
