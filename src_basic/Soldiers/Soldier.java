package Soldiers;

import java.io.Serializable;
import java.util.ArrayList;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Ost;
import DukesOfTheRealm.ReserveOfSoldiers;
import DukesOfTheRealm.Sprite;
import Enums.CollisionEnum;
import Enums.SoldierEnum;
import Interface.IUpdate;
import Utility.Collision;
import Utility.Point2D;
import Utility.Settings;
import Utility.Time;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe abstraite repr�sentant un Soldat.
 */
public abstract class Soldier extends Sprite implements Serializable, IUpdate
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Le type du soldat : Piquier / Chevalier / Catapulte.
	 */
	protected SoldierEnum type;

	/**
	 * Une structure contenant les donn�es d'un soldat : vitesse de d�placement, point de vie et d�g�ts
	 * � infliger.
	 */
	protected Stats stats;

	/**
	 * R�f�rence vers l'ost � laquelle le soldat appartient.
	 */
	protected Ost itsOst = null;

	/**
	 * Bool�en indiquant si le soldat est pr�sent sur le terrain.
	 */
	public boolean onField = false;

	/**
	 * Bool�en indiquant si le soldat est arriv� au point de s�paration autours du ch�teau adverse.
	 */
	protected boolean isArrived = false;

	/**
	 * Bool�en indiquant si le soldat est correctement positionn� autours du ch�teau adverse pour
	 * l'attaquer.
	 */
	protected boolean isInPosition = false;

	/**
	 * Bool�en indiquant si le soldat est mort.
	 */
	public boolean isDead = false;

	/**
	 * Le point autours du ch�teau adverse sur lequel le soldat se positionne pour attaquer
	 */
	protected Point2D attackLocation = null;

	/**
	 * Bool�en indiquant si le soldat est en attante d'un point d'attaque
	 */
	protected boolean isWaitingForAttackLocation = false;

	/**
	 * Indique le statut actuel de collision du soldat.
	 * 
	 * @see CollisionEnum
	 */
	private CollisionEnum collisionState = CollisionEnum.None;

	/**
	 * Indique la derni�re collision rencontr�e par le soldat, utilis� pour traiter la collision entre
	 * le soldat et le coin d'un ch�teau.
	 */
	private CollisionEnum lastCollision = CollisionEnum.None;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur Soldier
	 * 
	 * @param layer  Le Pane pour afficher la r�pr�sentation graphique de ce soldat.
	 * @param coord  Les coordonn�es du soldat � son d�ploiement.
	 * @param itsOst La r�f�rence vers l'ost du soldat.
	 */
	public Soldier(final Pane layer, final Point2D coord, final Ost itsOst)
	{
		this.canvas = layer;
		this.coordinate = coord;
		this.itsOst = itsOst;
	}

	/**
	 * Constructeur par d�faut
	 */
	protected Soldier()
	{

	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	/**
	 * Affiche le soldat � l'�cran.
	 * 
	 * @param color La couleur repr�sentant le soldat sur le terrain.
	 */
	public void Awake(final Color color)
	{
		getShape().setFill(color);
		getLayer().getChildren().add(getShape());
		this.onField = true;
	}

	/**
	 * Initialise les composants transients du soldat.
	 * 
	 * @param pane Le Pane pour afficher la r�pr�sentation graphique de ce soldat.
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
			if (!this.itsOst.isBackup() || isStopAttack())
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
	 * Ajoute une unit� en renfort dans la r�serve en param�tre.
	 * 
	 * @param reserve La r�serve dans laquelle l'unit� va.
	 */
	public abstract void addInReserve(ReserveOfSoldiers reserve);

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
	 * D�place le soldat d'une case vers sa destination, tout en �vitant une collision avec un ch�teau,
	 * puis v�rifie s'il est arriv� � destination.
	 * 
	 * @param dst Les coordonn�e du point de destination du soldat.
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
	 * Autorise ou non le d�placement du soldat selon qu'un pr�dicat donn� soit respect�. Si le pr�dicat
	 * est bien respect� alors le d�placement est effectu�.
	 * 
	 * @param  predicat   Le pr�dicat � v�rifier.
	 * @param  directionX La direction selon l'axe des abscisses (1 ou -1).
	 * @param  directionY La direction selon l'axe des ordonn�es (1 ou -1).
	 * @return            true si le d�placement a �t� effectu�.
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
	 * Calcule la d�placement r�alis� par le soldat en une fois.
	 * 
	 * @param  direction La direction du d�placement. Il peut �tre positif (1) ou n�gatif (-1).
	 * @return           Le d�placement du soldat.
	 */
	private double getMotion(final int direction)
	{
		return this.stats.speed * Time.deltaTime * direction;
	}

	/**
	 * V�rifie si le soldat est sorti de l'�cran, auquel cas il est tu�.
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
	 * V�rifie si le soldat est arriv� au point de s�paration de l'ost autours du ch�teau adverse et
	 * modifie les donn�es du soldats en cons�quence.
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
	 * V�rifie si le soldat est arriv� � son point d'attaque autours du ch�teau adverse et modifie les
	 * donn�es du soldats en cons�quence.
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
	 * R�alise une attaque sur le ch�teau adverse. Si le soldat attaquant d�pense son dernier point
	 * d'attaque il est tu�.
	 */
	private void attack()
	{
		if (!isStopAttack())
		{
			// Il va vouloir faire 1 point de d�g�ts
			getDestination().randomRemoveHP();

			// Si le point de damage n'a pas reussi, la reserve bloque et stop l'attaque
			if (!getDestination().isStopAttack())
			{
				this.isDead = --this.stats.damage <= 0 ? true : false;
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
	 * R�cup�re le point de s�paration de l'ost � laquelle le soldat appartient.
	 * 
	 * @return Retourne le point de s�paration de l'ost.
	 * @see    DukesOfTheRealm.Ost#getSeparationPoint()
	 */
	public Point2D getSeparationPoint()
	{
		return this.itsOst.getSeparationPoint();
	}

	/**
	 * R�cup�re le point d'attente du soldat autours du ch�teau.
	 * 
	 * @return Retourne le point d'attente du soldat.
	 * @see    DukesOfTheRealm.Ost#getWaitingPoint()
	 */
	public Point2D getWaitingPoint()
	{
		return this.itsOst.getWaitingPoint();
	}

	/**
	 * R�cup�re la liste des soldats composants l'ost.
	 * 
	 * @return Retourne la liste des soldats.
	 * @see    DukesOfTheRealm.Ost#getSoldiers()
	 */
	public ArrayList<Soldier> getSoldiers()
	{
		return this.itsOst.getSoldiers();
	}

	/**
	 * Appelle la m�thode win de l'ost lorsque le ch�teau adverse est vaincu.
	 * 
	 * @see DukesOfTheRealm.Ost#win()
	 */
	public void win()
	{
		this.itsOst.win();
	}

	/**
	 * R�cup�re l'�tat de l'attaque, si celle-ci est en cours ou stopp�e.
	 * 
	 * @return Retourne l'�tat de l'attaque.
	 * @see    DukesOfTheRealm.Ost#isStopAttack()
	 */
	public boolean isStopAttack()
	{
		return this.itsOst.isStopAttack();
	}

	/**
	 * R�cup�re le ch�teau de destination.
	 * 
	 * @return Retourne la r�f�rence vers le ch�teau de destination.
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
	public abstract double getProductionTime();

	@Override
	public abstract int getProductionCost();

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
}
