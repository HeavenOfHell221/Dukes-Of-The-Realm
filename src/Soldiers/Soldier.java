package Soldiers;

import java.io.Serializable;
import java.util.ArrayList;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Castle.Orientation;
import DukesOfTheRealm.Ost;
import DukesOfTheRealm.ReserveOfSoldiers;
import DukesOfTheRealm.Sprite;
import Enum.CollisionEnum;
import Enum.SoldierEnum;
import Interface.IUpdate;
import Utility.Collision;
import Utility.Point2D;
import Utility.Settings;
import Utility.Time;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Soldier extends Sprite implements Serializable, IUpdate
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	protected SoldierEnum type;
	protected Stats stats;
	protected Ost itsOst = null;
	public boolean onField = false;
	protected boolean isArrived = false;
	protected boolean isInPosition = false;
	public boolean isDead = false;
	protected Point2D attackLocation = null;
	protected boolean isWaitingForAttackLocation = false;
	private CollisionEnum collisionState = CollisionEnum.None;
	
	private CollisionEnum lastCollision = CollisionEnum.None;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * 
	 * @param layer
	 * @param coord
	 * @param itsOst
	 */
	public Soldier(final Pane layer, final Point2D coord, final Ost itsOst)
	{
		this.canvas = layer;
		this.coordinate = coord;
		this.itsOst = itsOst;
	}

	/**
	 * 
	 */
	protected Soldier()
	{

	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	public void start()
	{

	}

	public void Awake(final Color color)
	{
		getShape().setFill(color);
		getLayer().getChildren().add(getShape());
		this.onField = true;
		start();
	}

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

		if (this.isArrived && !this.isInPosition)	// Arrived to castle but not in position to attack
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
	 * 
	 * @param reserve
	 */
	protected abstract void addInReserve(ReserveOfSoldiers reserve);

	/**
	 * 
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
	 * 
	 * @param dst
	 * @param factorSpeed
	 */
	private void Move(final Point2D dst)
	{
		if (this.isWaitingForAttackLocation || this.isDead)
		{
			return;
		}
		
		isOutOfScreen();
		
		if(this.isDead)
		{
			return;
		}
		
		if(dst.delta(this.coordinate).getX() <= 0.5d)
		{
			this.coordinate.setX(dst.getX());
		}
		
		if(dst.delta(this.coordinate).getY() <= 0.5d)
		{
			this.coordinate.setY(dst.getY());
		}
		
		int directionX = getX() < dst.getX() ? 1 : getX() == (int) dst.getX() /*|| dst.delta(this.coordinate).getX() <= 0.5d*/ ? 0 : -1;
		int directionY = getY() < dst.getY() ? 1 : getY() == (int) dst.getY() /*|| dst.delta(this.coordinate).getY() <= 0.5d*/ ? 0 : -1;
			
		double offsetX = getMotion(directionX);
		double offsetY = getMotion(directionY);

		this.collisionState = Collision.testCollisionWithAllCastlesNearby(new Point2D(coordinate.getX() + offsetX, coordinate.getY() + offsetY));
		
		switch(this.collisionState)
		{		
			case LeftTop:
				
				switch(this.lastCollision)
				{
					case Left:
						addMotion(0, getMotion(-1));
						break;
					case None:
						if(AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() > this.coordinate.getY(), 1, 0)) {}
						else if(AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() < this.coordinate.getY(), directionX, directionY)) {}
						else if(AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() > this.coordinate.getY(), 1, 0)) {}
						else if(AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() < this.coordinate.getY(), directionX, directionY)) {}
						else if(AIMoveHandle(dst.getX() == this.coordinate.getX(), 0, directionY)) {}
						else if(AIMoveHandle(dst.getY() == this.coordinate.getY(), directionX, 0)) {}
						break;
					default:
						break;
				}
				
				break;
			case TopRight:
				switch(this.lastCollision)
				{
					case Top:
						addMotion(getMotion(1), 0);
						break;
					case None:
						if(AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() > this.coordinate.getY(), directionX, directionY)) {}
						else if(AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() < this.coordinate.getY(), directionX, directionY)) {}
						else if(AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() > this.coordinate.getY(), 0, 1)) {}
						else if(AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() < this.coordinate.getY(), 0, 1)) {}
						else if(AIMoveHandle(dst.getX() == this.coordinate.getX(), 0, directionY)) {}
						else if(AIMoveHandle(dst.getY() == this.coordinate.getY(), directionX, 0)) {}
						break;
					default:
						break;
				}
				
				break;
			case RightBottom:
				
				switch(this.lastCollision)
				{
					case Right:
						addMotion(0, getMotion(1));
						break;
					case None:
						if(AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() > this.coordinate.getY(), directionX, directionY)) {}
						else if(AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() < this.coordinate.getY(), -1, 0)) {}
						else if(AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() > this.coordinate.getY(), directionX, directionY)) {}
						else if(AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() < this.coordinate.getY(), -1, 0)) {}
						else if(AIMoveHandle(dst.getX() == this.coordinate.getX(), 0, directionY)) {}
						else if(AIMoveHandle(dst.getY() == this.coordinate.getY(), directionX, 0)) {}
						break;
					default:
						break;
				}
				break;
			case BottomLeft:
				switch(this.lastCollision)
				{
					case Bottom:
						addMotion(getMotion(-1), 0);
						break;
					case None:
						if(AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() > this.coordinate.getY(), 0, -1)) {}
						else if(AIMoveHandle(dst.getX() > this.coordinate.getX() && dst.getY() < this.coordinate.getY(), 0, -1)) {}
						else if(AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() > this.coordinate.getY(), directionX, directionY)) {}
						else if(AIMoveHandle(dst.getX() < this.coordinate.getX() && dst.getY() < this.coordinate.getY(), directionX, directionY)) {}
						else if(AIMoveHandle(dst.getX() == this.coordinate.getX(), 0, directionY)) {}
						else if(AIMoveHandle(dst.getY() == this.coordinate.getY(), directionX, 0)) {}
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
				switch(this.lastCollision)
				{
					case Left: addMotion(getMotion(-1), 0); break;
					case Right: addMotion(getMotion(1), 0); break;
					case Bottom: addMotion(0, getMotion(1)); break;
					case Top: addMotion(0, getMotion(-1)); break;
					case None: addMotion(-offsetX, -offsetY); break;
					default: break;
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

	private boolean AIMoveHandle(boolean predicat, int directionX, int directionY)
	{
		if(predicat)
		{
			addMotion(getMotion(directionX), getMotion(directionY));
		}
		return predicat;
	}
	
	private double getMotion(int direction)
	{
		return this.stats.speed * Time.deltaTime * direction;
	}

	/**
	 * 
	 */
	private void isOutOfScreen()
	{
		if (getX() > Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.04) || getY() > Settings.SCENE_HEIGHT || getX() <= 0
				|| getY() <= 0)
		{
			this.isDead = true;
		}
	}

	private void isArrived()
	{
		Point2D p = this.coordinate.delta(getSeparationPoint());
		
		if(p.getX() < 1 && p.getY() < 1)
		{
			this.isArrived = true;
			SetAttackLocation();
		}
	}

	private void isInPosition()
	{
		if (!this.isWaitingForAttackLocation)
		{
			Point2D p = this.coordinate.delta(this.attackLocation);
			
			if(p.getX() < 1 && p.getY() < 1)
			{
				this.isInPosition = true;
			}
		}
	}

	/**
	 * 
	 */
	private void attack()
	{
		if (!isStopAttack())
		{
			// Il va vouloir faire 1 point de damage
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
	 * @return
	 * @see    DukesOfTheRealm.Ost#getSeparationPoint()
	 */
	public Point2D getSeparationPoint()
	{
		return this.itsOst.getSeparationPoint();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.Ost#getWaitingPoint()
	 */
	public Point2D getWaitingPoint()
	{
		return this.itsOst.getWaitingPoint();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.Ost#getSoldiers()
	 */
	public ArrayList<Soldier> getSoldiers()
	{
		return this.itsOst.getSoldiers();
	}

	/**
	 *
	 * @see DukesOfTheRealm.Ost#win()
	 */
	public void win()
	{
		this.itsOst.win();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.Ost#isStopAttack()
	 */
	public boolean isStopAttack()
	{
		return this.itsOst.isStopAttack();
	}

	/**
	 * @return
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
	
	/**
	 * 
	 */
	@Override
	public abstract int getProductionCost();

	/**
	 * @return the type
	 */
	public final SoldierEnum getType()
	{
		return type;
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
