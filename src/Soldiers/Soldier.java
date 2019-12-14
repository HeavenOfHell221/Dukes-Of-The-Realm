package Soldiers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Ost;
import DukesOfTheRealm.Sprite;
import Enum.SoldierEnum;
import Utility.Collisions;
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
	protected boolean canMove = false;
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
		if (this.attackLocation == null || this.isWaitingForAttackLocation)
		{
			this.canMove = true;
		}
	}

	public void Awake(final Color color)
	{
		getShape().setFill(color);
		getLayer().getChildren().add(getShape());
		this.onField = true;
		updateUIShape();
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
		if (!this.isArrived)
		{
			Move(getSeparationPoint());
		}

		if (this.isArrived && !this.isInPosition)
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
			Attack();
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	private final void SetAttackLocation()
	{
		if (getDestination().isAvailableAttackLocation())
		{
			this.attackLocation = getDestination().getNextAttackLocation();
			this.isWaitingForAttackLocation = false;
			this.canMove = true;
		}
		else
		{
			this.isWaitingForAttackLocation = true;
			this.canMove = false;
		}
	}
	
	/**
	 * 
	 * @param dst
	 */
	private void Move(final Point2D dst)
	{
		final int directionX = getX() < dst.getX() ? 1 : getX() == dst.getX() ? 0 : -1;
		final int directionY = getY() < dst.getY() ? 1 : getY() == dst.getY() ? 0 : -1;
		
		double offsetX = this.stats.speed * Time.deltaTime * directionX;
		double offsetY = this.stats.speed * Time.deltaTime * directionY;

//		int alternateDirectionX;
//		int alternateDirectionY;
//		switch (Collisions.isCollisionApproaching(this.getCoordinate(), offsetX))
//		{
//			case Settings.X_COLLISION:
//				offsetX = 0;
//				System.out.println("off x bloqué");
//				alternateDirectionY = getY() < dst.getY() ? 1 : -1;
//				offsetY = this.stats.speed * Time.deltaTime * alternateDirectionY;
//				break;
//			case Settings.Y_COLLISION:
//				System.out.println("off y bloqué");
//				offsetY = 0;
//				alternateDirectionX = getX() < dst.getX() ? 1 : -1;
//				offsetX = this.stats.speed * Time.deltaTime * alternateDirectionX;
//				break;
//			default: break;
//		}

		if (this.canMove)
		{
			addMotion(offsetX, offsetY);
			updateUIShape();
		}

		isOutOfScreen();

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
			RemoveShapeToLayer();
			this.canMove = false;
			this.isDead = true;
		}
	}

	private void isArrived()
	{
		if (getX() == getSeparationPoint().getX() && getY() == getSeparationPoint().getY())
		{
			this.isArrived = true;
			this.canMove = false;
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
				this.canMove = false;
			}
		}
	}

	private void Attack()
	{
		if (!isStopAttack())
		{
			//applyDamage();
		}
		else
		{
			this.isDead = true;
			// TODO aller dans la reserve
		}

	}

	private void applyDamage()
	{
		getDestination().randomRemoveHP(new Random().nextInt());

		if (!getDestination().isStopAttack())
		{
			this.isDead = (--this.stats.damage <= 0) ? true : false;
		}
		else
		{
			this.isDead = true;
			win();
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
		return "Soldier [type=" + this.type + ", onField=" + this.onField + ", canMove=" + this.canMove + "]";
	}
}
