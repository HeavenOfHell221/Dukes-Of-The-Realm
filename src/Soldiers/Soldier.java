package Soldiers;

import java.util.Random;

import DukesOfTheRealm.Kingdom;
import DukesOfTheRealm.Ost;
import DukesOfTheRealm.Sprite;
import Enum.SoldierEnum;
import Interface.ISave;
import SaveSystem.SoldierData;
import Utility.Point2D;
import Utility.Settings;
import Utility.Time;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Soldier extends Sprite implements ISave<SoldierData>{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	protected SoldierEnum type;
	protected Stats stats;
	protected transient Ost itsOst = null;
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
		super();
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		if (attackLocation == null || isWaitingForAttackLocation)
		{
			canMove = true;
		}
	}

	public void Awake(final Color color)
	{
		GetShape().setFill(color);
		GetLayer().getChildren().add(GetShape());
		UpdateUIShape();
		start();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void receivedDataSave(final SoldierData data)
	{

	}

	@Override
	public void sendingDataSave(final SoldierData data)
	{

	}

	@Override
	public void update(final long now, final boolean pause)
	{
		if (!isArrived)
		{
			Move(itsOst.GetSeparationPoint());
		}

		if (isArrived && !isInPosition)
		{
			if (attackLocation != null)
			{
				Move(attackLocation);
			}
			else
			{
				Move(itsOst.GetWaitingPoint());
			}
		}

		if (isWaitingForAttackLocation)
		{
			SetAttackLocation();
		}

		if (isInPosition && !isWaitingForAttackLocation)
		{
			Attack();
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	private final void SetAttackLocation()
	{
		if (itsOst.GetDestination().IsAvailableAttackLocation())
		{
			attackLocation = itsOst.GetDestination().GetNextAttackLocation();
			isWaitingForAttackLocation = false;
		}
		else
		{
			isWaitingForAttackLocation = true;
		}
	}

	private void Move(final Point2D dst)
	{
		final int directionX = GetX() < dst.getX() ? 1 : GetX() == dst.getX() ? 0 : -1;
		final int directionY = GetY() < dst.getY() ? 1 : GetY() == dst.getY() ? 0 : -1;
		
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

		AddMotion(offsetX, offsetY);
		IsOutOfScreen();
		if (!isArrived)
		{
			IsArrived();
		}
		else if (!isInPosition)
		{
			IsInPosition();
		}
		UpdateUIShape();
	}

	private void IsOutOfScreen()
	{
		if(GetX() > Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.04) || GetY() > Settings.SCENE_HEIGHT || GetX() <= 0 || GetY() <= 0)
		{
			RemoveShapeToLayer();
			canMove = false;
			isDead = true;
		}
	}

	private void IsArrived() {
		if (GetX() == itsOst.GetSeparationPoint().getX() && GetY() == itsOst.GetSeparationPoint().getY())
		{
			canMove = false;
			isArrived = true;
			SetAttackLocation();
		}
	}

	private void IsInPosition()
	{
		if (!isWaitingForAttackLocation)
		{
			if (GetX() == attackLocation.getX() && GetY() == attackLocation.getY())
			{
				isInPosition = true;
			}
		}
	}

	private void Attack()
	{
		isDead = (--stats.damage <= 0) ? true : false;
		itsOst.GetDestination().GetReserveOfSoldiers().RandomRemoveHP(new Random().nextInt());
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
		return type;
	}

	public int GetSpeed()
	{
		return stats.speed;
	}

	public int GetDamage()
	{
		return stats.damage;
	}

	public boolean isOnField()
	{
		return onField;
	}

	public Point2D GetAttackLocation()
	{
		return attackLocation;
	}
}