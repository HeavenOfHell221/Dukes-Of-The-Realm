package Soldiers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Ost;
import DukesOfTheRealm.ReserveOfSoldiers;
import DukesOfTheRealm.Sprite;
import Enum.SoldierEnum;
import Utility.Point2D;
import Utility.Settings;
import Utility.Time;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Soldier extends Sprite implements Serializable
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
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	public Soldier(final Pane layer, final Point2D coord, final Ost itsOst)
	{
		super(layer, coord);
		this.itsOst = itsOst;
	}

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


	
	public void update(final long now, final boolean pause)
	{	
		if(isDead)
			return;
		
		if (!this.isArrived)
		{
			Move(getSeparationPoint(), 1d);
		}

		if (this.isArrived && !this.isInPosition)
		{
			if (this.attackLocation != null)
			{
				Move(this.attackLocation, 1d);
			}
			else
			{
				Move(getWaitingPoint(), 0.5d);
			}
		}

		if (this.isWaitingForAttackLocation)
		{
			SetAttackLocation();
		}

		if (this.isInPosition && !this.isWaitingForAttackLocation)
		{
			if(!this.itsOst.isBackup() || this.isStopAttack())
			{
				attack();
			}
			else
			{
				addInReserve(getDestination().getReserveOfSoldiers());
				isDead = true;
			}
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	protected abstract void addInReserve(ReserveOfSoldiers reserve);
	
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
	 */
	private void Move(final Point2D dst, double factorSpeed)
	{
		if(this.isWaitingForAttackLocation)
			return;
		
		isOutOfScreen();

		final int directionX = getX() < dst.getX() ? 1 : (getX() == (int)dst.getX()) || (dst.delta(this.coordinate).getX() <= 0.5d) ? 0 : -1;
		final int directionY = getY() < dst.getY() ? 1 : (getY() == (int)dst.getY()) || (dst.delta(this.coordinate).getY() <= 0.5d) ? 0 : -1;

		double offsetX = this.stats.speed * Time.deltaTime * directionX * factorSpeed;
		double offsetY = this.stats.speed * Time.deltaTime * directionY * factorSpeed;

		// int alternateDirectionX;
		// int alternateDirectionY;
		// switch (Collisions.isCollisionApproaching(this.getCoordinate(), offsetX, offsetY))
		// {
		// case Settings.X_COLLISION:
		// offsetX = 0;
		// System.out.println("off x bloque");
		// alternateDirectionY = getY() < dst.getY() ? 1 : -1;	//blocage infini, � changer
		// offsetY = this.stats.speed * Time.deltaTime * alternateDirectionY;
		// break;
		// case Settings.Y_COLLISION:
		// System.out.println("off y bloque");
		// offsetY = 0;
		// alternateDirectionX = getX() < dst.getX() ? 1 : -1;
		// offsetX = this.stats.speed * Time.deltaTime * alternateDirectionX;
		// break;
		// default: break;
		// }
		addMotion(offsetX, offsetY);

		if (!this.isArrived)
		{
			isArrived();
		}
		else if (!this.isInPosition)
		{
			isInPosition();
		}
	}

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
		if (getX() == getSeparationPoint().getX() && getY() == getSeparationPoint().getY())
		{
			this.isArrived = true;
			SetAttackLocation();
		}
	}

	private void isInPosition()
	{
		if (!this.isWaitingForAttackLocation)
		{
			if (getX() == this.attackLocation.getX() && getY() == this.attackLocation.getY())
			{
				this.isInPosition = true;
			}
		}
	}

	private void attack()
	{
		if(!isStopAttack())
		{
			// Il va vouloir faire 1 point de damage
			getDestination().randomRemoveHP();
			
			// Si le point de damage n'a pas reussi, la reserve bloque et stop l'attaque
			if (!getDestination().isStopAttack())
			{
				this.isDead = (--this.stats.damage <= 0) ? true : false;
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
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/


	
	@Override
	public abstract double getProductionTime();

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

	@Override
	public abstract int getProductionCost();

	public SoldierEnum GetType()
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
	public String toString()
	{
		return "Soldier [type=" + this.type + ", onField=" + this.onField + ", canMove=" /*+ this.canMove*/ + "]";
	}
}
