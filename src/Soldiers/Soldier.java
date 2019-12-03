package Soldiers;

import DukesOfTheRealm.*;
import Enum.SoldierEnum;
import Interface.ISave;
import SaveSystem.SoldierData;
import Utility.Time;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Soldier extends Sprite implements ISave<SoldierData> {

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	protected SoldierEnum type;
	protected int productionCost;
	protected int productionTime;
	protected int health;
	protected int damage;
	protected int speed;
	protected Ost itsOst = null;
	protected boolean onField = false;
	protected boolean canMove = false;
	protected boolean isArrived = false;
	protected boolean isInPosition = false;
	public boolean isDead = false;
	protected Point2D attackLocation = null;
	protected boolean isWaitingForAttackLocation = false;
	int tmp = 1;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	public Soldier(Pane layer, double x, double y, Ost itsOst, int productionCost, int productionTime, int speed, int health, int damage)
	{
		super(layer, x, y);
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.health = health;
		this.damage = damage;
		this.speed = speed;
		this.itsOst = itsOst;
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start()
	{
		if (this.attackLocation == null || this.isWaitingForAttackLocation)
		{
			canMove = true;
		}
	}
	
	public void Awake(Color color)
	{	
		this.GetShape().setFill(color);
		this.GetLayer().getChildren().add(this.GetShape());
		UpdateUIShape();
		Start();
	}
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/
	
	@Override
	public void ReceivedDataSave(SoldierData data) 
	{
		data.type = this.type;
		data.health = this.health;
		data.damage = this.damage;
		data.speed = this.speed;
		data.onField = this.onField;
		data.canMove = this.canMove;
		data.isArrived = this.isArrived;
		data.isInPosition = this.isInPosition;
		data.isDead = this.isDead;
		data.attackLocation = this.attackLocation;
		data.isWaitingForAttackLocation = this.isWaitingForAttackLocation;
		data.coordinate = GetCoordinate();
	    data.width = getWidth();
	    data.height = getHeight();
	}

	@Override
	public void SendingDataSave(SoldierData data) 
	{
		
	}
	
	@Override
	public void Update(long now, boolean pause)
	{		
		if (this.canMove)
		{
			Move(this.itsOst.getSeparationPoint());
		}
		
		if (this.isArrived && !this.isInPosition)
		{
			Move(this.attackLocation);
		}

		if (this.isInPosition && !this.isWaitingForAttackLocation)
		{
			Attack();
		}
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	private void SetAttackLocation()
	{
		if (this.itsOst.GetDestination().IsAvailableAttackLocation())
		{
			this.attackLocation = this.itsOst.GetDestination().GetNextAttackLocation();
			this.isWaitingForAttackLocation = false;
		}
		else	/***** Cas par défaut à modifier *****/
		{
			this.attackLocation = this.itsOst.GetDestination().GetWaitAttackLocation();
			this.isWaitingForAttackLocation = true; 
		}
	}
	
	private void Move(Point2D dst)
	{
		int DirectionX = this.GetX() < dst.GetX() ? 1 : this.GetX() == dst.GetX() ? 0 : -1;
		int DirectionY = this.GetY() < dst.GetY() ? 1 : this.GetY() == dst.GetY() ? 0 : -1;
		this.AddMotion(this.speed * Time.deltaTime * DirectionX, this.speed * Time.deltaTime * DirectionY);
		IsOutOfScreen();
		if (!this.isArrived)
		{
			IsArrived();
		}
		else if (!this.isInPosition)
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
		if (this.GetX() == this.itsOst.getSeparationPoint().GetX() && this.GetY() == this.itsOst.getSeparationPoint().GetY())
		{
			this.canMove = false;
			this.isArrived = true;
			SetAttackLocation();
		}
	}
	
	private void IsInPosition()
	{
		if (this.GetX() == this.attackLocation.GetX() && this.GetY() == this.attackLocation.GetY())
		{
			this.isInPosition = true;
		}
	}
	
	private void Attack()
	{
this.itsOst.GetOrigin().GetX();
			this.itsOst.GetOrigin().GetY();
			//System.out.println("Soldat (" + (-1* (x - this.GetX())) + ", " + (-1* (y - this.GetY())) + ") de type " + this.type + " -> J'attaque 1 fois");
			ApplyDamage();
//			this.tmp++;
//		}
	}
	
	private void ApplyDamage()
	{
		this.isDead = (--this.damage <= 0) ? true : false;
		//System.out.println(this.damage);
	}
	
	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	@Override
	public int GetProductionTime()
	{
		return productionTime;
	}
	
	@Override
	public int GetProductionCost()
	{
		return productionCost;
	}

	public SoldierEnum GetType()
	{
		return type;
	}
	
	public int GetSpeed()
	{
		return speed;
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