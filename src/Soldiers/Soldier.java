package Soldiers;

import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Kingdom;
import DukesOfTheRealm.Ost;
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
	protected boolean onField = false;
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
		updateUIShape();
		start();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	public void update(final long now, final boolean pause)
	{
		if (!this.isArrived)
		{
			Move(this.itsOst.GetSeparationPoint());
		}

		if (this.isArrived && !this.isInPosition)
		{
			if (this.attackLocation != null)
			{
				Move(this.attackLocation);
			}
			else
			{
				Move(this.itsOst.GetWaitingPoint());
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
		if (this.itsOst.GetDestination().isAvailableAttackLocation())
		{
			this.attackLocation = this.itsOst.GetDestination().getNextAttackLocation();
			this.isWaitingForAttackLocation = false;
		}
		else
		{
			this.isWaitingForAttackLocation = true;
		}
	}

	private void Move(final Point2D dst)
	{
		final int directionX = getX() < dst.getX() ? 1 : getX() == dst.getX() ? 0 : -1;
		final int directionY = getY() < dst.getY() ? 1 : getY() == dst.getY() ? 0 : -1;
		
		double offsetX = stats.speed * Time.deltaTime * directionX;
		double offsetY = stats.speed * Time.deltaTime * directionY;
		
//		if (Kingdom.collisionsManagement.isCollisionApproaching(this.GetCoordinate(), offsetX, Settings.X_DIRECTION))
//		{
//			offsetX = 0;
//			System.out.println("off x bloqué");
//		}
//		if (Kingdom.collisionsManagement.isCollisionApproaching(this.GetCoordinate(), offsetY, Settings.Y_DIRECTION))
//		{
//			offsetY = 0;
//			System.out.println("off y bloqué");
//		}

		addMotion(this.stats.speed * Time.deltaTime * directionX, this.stats.speed * Time.deltaTime * directionY);
		IsOutOfScreen();
		if (!this.isArrived)
		{
			IsArrived();
		}
		else if (!this.isInPosition)
		{
			IsInPosition();
		}
		updateUIShape();
	}

	private void IsOutOfScreen()
	{
		if (getX() > Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.04) || getY() > Settings.SCENE_HEIGHT || getX() <= 0
				|| getY() <= 0)
		{
			RemoveShapeToLayer();
			this.canMove = false;
			this.isDead = true;
		}
	}

	private void IsArrived()
	{
		if (getX() == this.itsOst.GetSeparationPoint().getX() && getY() == this.itsOst.GetSeparationPoint().getY())
		{
			this.canMove = false;
			this.isArrived = true;
			SetAttackLocation();
		}
	}

	private void IsInPosition()
	{
		if (!this.isWaitingForAttackLocation)
		{
			if (getX() == this.attackLocation.getX() && getY() == this.attackLocation.getY())
			{
				this.isInPosition = true;
			}
		}
	}

	private void Attack()
	{
		this.isDead = (--this.stats.damage <= 0) ? true : false;
		this.itsOst.GetDestination().getReserveOfSoldiers().randomRemoveHP(new Random().nextInt());
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	@Override
	public abstract int getProductionTime();

	@Override
	public abstract int getProductionCost();

	public SoldierEnum GetType()
	{
		return this.type;
	}

	public int GetSpeed()
	{
		return this.stats.speed;
	}

	public int GetDamage()
	{
		return this.stats.damage;
	}

	public boolean isOnField()
	{
		return this.onField;
	}

	public Point2D GetAttackLocation()
	{
		return this.attackLocation;
	}
}
