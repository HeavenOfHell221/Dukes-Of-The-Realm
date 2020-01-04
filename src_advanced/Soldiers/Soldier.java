package Soldiers;

import java.io.Serializable;
import java.util.ArrayList;

import DukesOfTheRealm.Caserne;
import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Miller;
import DukesOfTheRealm.Ost;
import DukesOfTheRealm.ReserveOfSoldiers;
import DukesOfTheRealm.Sprite;
import Enums.BuildingEnum;
import Enums.CollisionEnum;
import Enums.SoldierEnum;
import Interface.IProduction;
import Interface.IUpdate;
import Utility.Collision;
import Utility.Point2D;
import Utility.Settings;
import Utility.SoldierPack;
import Utility.Time;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe abstraite représentant un Soldat.
 */
public abstract class Soldier extends Sprite implements Serializable, IUpdate, IProduction
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Le type du soldat : Piquier / Chevalier / Catapulte.
	 */
	protected SoldierEnum type;

	/**
	 * Référence vers l'ost à laquelle le soldat appartient.
	 */
	protected Ost itsOst = null;

	/**
	 * Booléen indiquant si le soldat est présent sur le terrain.
	 */
	public boolean onField = false;

	/**
	 * Booléen indiquant si le soldat est arrivé au point de séparation autours du château adverse.
	 */
	protected boolean isArrived = false;

	/**
	 * Booléen indiquant si le soldat est correctement positionné autours du château adverse pour
	 * l'attaquer.
	 */
	protected boolean isInPosition = false;

	/**
	 * Booléen indiquant si le soldat est mort.
	 */
	public boolean isDead = false;

	/**
	 * Le point autours du château adverse sur lequel le soldat se positionne pour attaquer
	 */
	protected Point2D attackLocation = null;

	/**
	 * Booléen indiquant si le soldat est en attante d'un point d'attaque
	 */
	protected boolean isWaitingForAttackLocation = false;

	/**
	 * Indique le statut actuel de collision du soldat.
	 *
	 * @see CollisionEnum
	 */
	private CollisionEnum collisionState = CollisionEnum.None;

	/**
	 * Indique la dernière collision rencontrée par le soldat, utilisé pour traiter la collision entre
	 * le soldat et le coin d'un château.
	 */
	private CollisionEnum lastCollision = CollisionEnum.None;

	/**
	 * Dégâts restant avant que l'unité meurt.
	 */
	private int remainingDamage;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur Soldier
	 *
	 * @param layer  Le Pane pour afficher la réprésentation graphique de ce soldat.
	 * @param coord  Les coordonnées du soldat à son déploiement.
	 * @param itsOst La référence vers l'ost du soldat.
	 */
	public Soldier(final Pane layer, final Point2D coord, final Ost itsOst)
	{
		this.canvas = layer;
		this.coordinate = coord;
		this.itsOst = itsOst;
	}

	/**
	 * Constructeur par défaut
	 */
	protected Soldier()
	{

	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	/**
	 * Affiche le soldat à l'écran.
	 *
	 * @param color La couleur représentant le soldat sur le terrain.
	 */
	public void Awake(final Color color)
	{
		getShape().setFill(color);
		getLayer().getChildren().add(getShape());
		this.onField = true;
		this.remainingDamage = this.type.damage;
	}

	/**
	 * Initialise les composants transients du soldat.
	 *
	 * @param pane Le Pane pour afficher la réprésentation graphique de ce soldat.
	 */
	public void startTransient(final Pane pane)
	{
		this.canvas = pane;
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		if (this.isDead)
		{
			return;
		}

		if (!this.isArrived)
		{
			Move(getSeparationPoint());
		}

		if (this.isArrived && !this.isInPosition) // Arrived to castle but not in position to attack
		{
			if (this.attackLocation != null)
			{
				Move(this.attackLocation);
			}
			else
			{
				Move(getWaitingPoint());
			}
		}

		if (this.isWaitingForAttackLocation)
		{
			SetAttackLocation();
		}

		if (this.isInPosition && !this.isWaitingForAttackLocation)
		{
			if (!this.itsOst.isBackup())
			{
				attack();
			}
			else
			{
				addInReserve(getDestination().getReserveOfSoldiers());
				this.isDead = true;
			}
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * Ajoute une unité en renfort dans la réserve en paramètre.
	 *
	 * @param reserve La réserve dans laquelle l'unité va.
	 */
	public void addInReserve(final ReserveOfSoldiers reserve)
	{
		reserve.getSoldierPack().replace(this.type, reserve.getSoldierPack().get(this.type) + 1);
	}

	@Override
	public void productionFinished(final Castle castle, final boolean cancel)
	{
		if (!cancel)
		{
			SoldierPack<Integer> s = castle.getReserveOfSoldiers().getSoldierPack();
			s.replace(this.type, s.get(this.type) + 1);
		}
		castle.getCaserne().getSoldierPack().replace(this.type, castle.getCaserne().getSoldierPack().get(this.type) - 1);
	}

	@Override
	public void productionStart(final Caserne caserne)
	{
		caserne.getSoldierPack().replace(this.type, caserne.getSoldierPack().get(this.type) + 1);
		((Miller)caserne.getCastle().getBuilding(BuildingEnum.Miller)).removeVillager(this.type.villager);
	}

	/**
	 * Donne un point d'attaque au soldat s'il y en a un disponible, sinon le soldat passe en attente
	 * d'un point d'attaque
	 */
	private final void SetAttackLocation()
	{
		if (getDestination().isAvailableAttackLocation())
		{
			this.attackLocation = getDestination().getNextAttackLocation();
			this.isWaitingForAttackLocation = false;
		}
		else
		{
			this.isWaitingForAttackLocation = true;
		}
	}

	/**
	 * Déplace le soldat d'une case vers sa destination, tout en évitant une collision avec un château,
	 * puis vérifie s'il est arrivé à destination.
	 *
	 * @param dst Les coordonnée du point de destination du soldat.
	 */
	private void Move(final Point2D dst)
	{
		if (this.isWaitingForAttackLocation || this.isDead)
		{
			return;
		}

		isOutOfScreen();

		if (dst.delta(this.coordinate).getX() <= 0.5d)
		{
			this.coordinate.setX(dst.getX());
		}

		if (dst.delta(this.coordinate).getY() <= 0.5d)
		{
			this.coordinate.setY(dst.getY());
		}

		int directionX = getX() < dst.getX() ? 1 : getX() == (int) dst.getX() ? 0 : -1;
		int directionY = getY() < dst.getY() ? 1 : getY() == (int) dst.getY() ? 0 : -1;

		double offsetX = getMotion(directionX);
		double offsetY = getMotion(directionY);

		this.collisionState = Collision
				.testCollisionWithAllCastlesNearby(new Point2D(this.coordinate.getX() + offsetX, this.coordinate.getY() + offsetY));

		switch (this.collisionState)
		{
			case LeftTop:

				switch (this.lastCollision)
				{
					case Left:
						addMotion(0, getMotion(-1));
						break;
					case None:
						if (AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() > this.coordinate.getY(), 1, 0))
						{
						}
						else if (AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() < this.coordinate.getY(), directionX,
								directionY))
						{
						}
						else if (AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() > this.coordinate.getY(), 1, 0))
						{
						}
						else if (AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() < this.coordinate.getY(), directionX,
								directionY))
						{
						}
						else if (AIMoveHandle(dst.getX() == this.coordinate.getX(), 0, directionY))
						{
						}
						else if (AIMoveHandle(dst.getY() == this.coordinate.getY(), directionX, 0))
						{
						}
						break;
					default:
						break;
				}

				break;
			case TopRight:
				switch (this.lastCollision)
				{
					case Top:
						addMotion(getMotion(1), 0);
						break;
					case None:
						if (AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() > this.coordinate.getY(), directionX,
								directionY))
						{
						}
						else if (AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() < this.coordinate.getY(), directionX,
								directionY))
						{
						}
						else if (AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() > this.coordinate.getY(), 0, 1))
						{
						}
						else if (AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() < this.coordinate.getY(), 0, 1))
						{
						}
						else if (AIMoveHandle(dst.getX() == this.coordinate.getX(), 0, directionY))
						{
						}
						else if (AIMoveHandle(dst.getY() == this.coordinate.getY(), directionX, 0))
						{
						}
						break;
					default:
						break;
				}

				break;
			case RightBottom:

				switch (this.lastCollision)
				{
					case Right:
						addMotion(0, getMotion(1));
						break;
					case None:
						if (AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() > this.coordinate.getY(), directionX,
								directionY))
						{
						}
						else if (AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() < this.coordinate.getY(), -1, 0))
						{
						}
						else if (AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() > this.coordinate.getY(), directionX,
								directionY))
						{
						}
						else if (AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() < this.coordinate.getY(), -1, 0))
						{
						}
						else if (AIMoveHandle(dst.getX() == this.coordinate.getX(), 0, directionY))
						{
						}
						else if (AIMoveHandle(dst.getY() == this.coordinate.getY(), directionX, 0))
						{
						}
						break;
					default:
						break;
				}
				break;
			case BottomLeft:
				switch (this.lastCollision)
				{
					case Bottom:
						addMotion(getMotion(-1), 0);
						break;
					case None:
						if (AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() > this.coordinate.getY(), 0, -1))
						{
						}
						else if (AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() < this.coordinate.getY(), 0, -1))
						{
						}
						else if (AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() > this.coordinate.getY(), directionX,
								directionY))
						{
						}
						else if (AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() < this.coordinate.getY(), directionX,
								directionY))
						{
						}
						else if (AIMoveHandle(dst.getX() == this.coordinate.getX(), 0, directionY))
						{
						}
						else if (AIMoveHandle(dst.getY() == this.coordinate.getY(), directionX, 0))
						{
						}
						break;
					default:
						break;
				}
				break;
			case Right:
				this.lastCollision = this.collisionState;
				addMotion(0, getMotion(1));
				break;
			case Left:
				this.lastCollision = this.collisionState;
				addMotion(0, getMotion(-1));
				break;
			case Bottom:
				this.lastCollision = this.collisionState;
				addMotion(getMotion(-1), 0);
				break;
			case Top:
				this.lastCollision = this.collisionState;
				addMotion(getMotion(1), 0);
				break;
			case Inside:
				switch (this.lastCollision)
				{
					case Left:
						addMotion(getMotion(-1), 0);
						break;
					case Right:
						addMotion(getMotion(1), 0);
						break;
					case Bottom:
						addMotion(0, getMotion(1));
						break;
					case Top:
						addMotion(0, getMotion(-1));
						break;
					case None:
						addMotion(-offsetX, -offsetY);
						break;
					default:
						break;
				}
				break;
			case None:
				this.lastCollision = this.collisionState;
				addMotion(offsetX, offsetY);
				break;
			default:
				break;

		}

		if (!this.isArrived)
		{
			isArrived();
		}
		else if (!this.isInPosition)
		{
			isInPosition();
		}
	}

	/**
	 * Autorise ou non le déplacement du soldat selon qu'un prédicat donné soit respecté. Si le prédicat
	 * est bien respecté alors le déplacement est effectué.
	 *
	 * @param  predicat   Le prédicat à vérifier.
	 * @param  directionX La direction selon l'axe des abscisses (1 ou -1).
	 * @param  directionY La direction selon l'axe des ordonnées (1 ou -1).
	 * @return            true si le déplacement a été effectué.
	 */
	private boolean AIMoveHandle(final boolean predicat, final int directionX, final int directionY)
	{
		if (predicat)
		{
			addMotion(getMotion(directionX), getMotion(directionY));
		}
		return predicat;
	}

	/**
	 * Calcule la déplacement réalisé par le soldat en une fois.
	 *
	 * @param  direction La direction du déplacement. Il peut être positif (1) ou négatif (-1).
	 * @return           Le déplacement du soldat.
	 */
	private double getMotion(final int direction)
	{
		return this.itsOst.getSpeed() * Time.deltaTime * direction;
	}

	/**
	 * Vérifie si le soldat est sorti de l'écran, auquel cas il est tué.
	 */
	private void isOutOfScreen()
	{
		if (getX() > Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.04) || getY() > Settings.SCENE_HEIGHT || getX() <= 0
				|| getY() <= 0)
		{
			this.isDead = true;
		}
	}

	/**
	 * Vérifie si le soldat est arrivé au point de séparation de l'ost autours du château adverse et
	 * modifie les données du soldats en conséquence.
	 */
	private void isArrived()
	{
		Point2D p = this.coordinate.delta(getSeparationPoint());

		if (p.getX() < 1 && p.getY() < 1)
		{
			this.isArrived = true;
			SetAttackLocation();
		}
	}

	/**
	 * Vérifie si le soldat est arrivé à son point d'attaque autours du château adverse et modifie les
	 * données du soldats en conséquence.
	 */
	private void isInPosition()
	{
		if (!this.isWaitingForAttackLocation)
		{
			Point2D p = this.coordinate.delta(this.attackLocation);

			if (p.getX() < 1 && p.getY() < 1)
			{
				this.isInPosition = true;
			}
		}
	}

	/**
	 * Réalise une attaque sur le château adverse. Si le soldat attaquant dépense son dernier point
	 * d'attaque il est tué.
	 */
	private void attack()
	{
		if (!isStopAttack())
		{
			// Il va vouloir faire 1 point de dégâts
			getDestination().randomRemoveHP();

			// Si le point de damage n'a pas reussi, la reserve bloque et stop l'attaque
			if (!getDestination().isStopAttack())
			{
				this.isDead = --this.remainingDamage <= 0 ? true : false;
			}
			else
			{
				this.itsOst.win();
				this.isDead = true;
			}
		}
		else
		{
			this.isDead = true;
		}
	}

	/*************************************************/
	/*************** DELEGATES METHODS ***************/
	/*************************************************/

	/**
	 * Récupère le point de séparation de l'ost à laquelle le soldat appartient.
	 *
	 * @return Retourne le point de séparation de l'ost.
	 * @see    DukesOfTheRealm.Ost#getSeparationPoint()
	 */
	public Point2D getSeparationPoint()
	{
		return this.itsOst.getSeparationPoint();
	}

	/**
	 * Récupère le point d'attente du soldat autours du château.
	 *
	 * @return Retourne le point d'attente du soldat.
	 * @see    DukesOfTheRealm.Ost#getWaitingPoint()
	 */
	public Point2D getWaitingPoint()
	{
		return this.itsOst.getWaitingPoint();
	}

	/**
	 * Récupère la liste des soldats composants l'ost.
	 *
	 * @return Retourne la liste des soldats.
	 * @see    DukesOfTheRealm.Ost#getSoldiers()
	 */
	public ArrayList<Soldier> getSoldiers()
	{
		return this.itsOst.getSoldiers();
	}

	/**
	 * Appelle la méthode win de l'ost lorsque le château adverse est vaincu.
	 *
	 * @see DukesOfTheRealm.Ost#win()
	 */
	public void win()
	{
		this.itsOst.win();
	}

	/**
	 * Récupère l'état de l'attaque, si celle-ci est en cours ou stoppée.
	 *
	 * @return Retourne l'état de l'attaque.
	 * @see    DukesOfTheRealm.Ost#isStopAttack()
	 */
	public boolean isStopAttack()
	{
		return this.itsOst.isStopAttack();
	}

	/**
	 * Récupère le château de destination.
	 *
	 * @return Retourne la référence vers le château de destination.
	 * @see    DukesOfTheRealm.Ost#getDestination()
	 */
	public Castle getDestination()
	{
		return this.itsOst.getDestination();
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	@Override
	public double getProductionTime(Castle castle,int level)
	{
		return getType().productionTime;
	}

	@Override
	public int getProductionCost(int level)
	{
		return getType().cost;
	}

	/**
	 * @return the type
	 */
	public final SoldierEnum getType()
	{
		return this.type;
	}

	/**
	 * @return the onField
	 */
	public final boolean isOnField()
	{
		return this.onField;
	}

	/**
	 * @return the attackLocation
	 */
	public final Point2D getAttackLocation()
	{
		return this.attackLocation;
	}

	@Override
	public int getLevel()
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
