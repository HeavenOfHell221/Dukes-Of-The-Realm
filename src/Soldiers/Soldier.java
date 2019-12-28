package Soldiers;

import java.io.Serializable;
import java.util.ArrayList;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Castle.Orientation;
import DukesOfTheRealm.Ost;
import DukesOfTheRealm.Sprite;
import Enum.SoldierEnum;
import Path.Path;
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
	protected Path path;

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
			//this.path = new Path(this.getCoordinate(), getSeparationPoint());
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
		if (this.canMove && !this.isArrived)
		{
			Move(getSeparationPoint()); //Move(this.path.getPath().pop());
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
	private void Move(final Point2D dst)
	{
		int directionX = getX() < dst.getX() ? 1 : getX() == dst.getX() ? 0 : -1;
		int directionY = getY() < dst.getY() ? 1 : getY() == dst.getY() ? 0 : -1;
		
		double offsetX = this.stats.speed * Time.deltaTime * directionX;
		double offsetY = this.stats.speed * Time.deltaTime * directionY;

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
		if (getX() == getSeparationPoint().getX() && getY() == getSeparationPoint().getY()) //if (this.path.getPath().empty())
		{
			this.isArrived = true;
			SetAttackLocation();
			//Calculer chemin vers position d'attaque
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
	
	public Orientation getDestinationArea()
	{
		return this.itsOst.getDestinationArea();
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
