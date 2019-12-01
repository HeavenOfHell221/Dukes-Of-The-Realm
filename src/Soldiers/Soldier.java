package Soldiers;

import java.util.Deque;

import DukesOfTheRealm.*;
import Utility.Time;
import Utility.Settings;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public abstract class Soldier extends Sprite implements IProductionUnit, IUpdate {
	
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
			SetAttackLocation();
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
	public void Update(long now, boolean pause)
	{		
		if (this.canMove)
		{
			Move();
			UpdateUIShape();
		}
		
		if (this.isArrived)
		{
			Attack();
		}
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	private void Move()
	{
		int DirectionX = this.GetX() < this.attackLocation.getX() ? 1 : this.GetX() == this.attackLocation.getX() ? 0 : -1;
		int DirectionY = this.GetY() < this.attackLocation.getY() ? 1 : this.GetY() == this.attackLocation.getY() ? 0 : -1;
		this.AddMotion(this.speed * Time.deltaTime * DirectionX, this.speed * Time.deltaTime * DirectionY);
		IsOutOfScreen();
		IsArrived();
	}
	
	private void IsArrived() {
		if (this.GetX() == this.attackLocation.getX() && this.GetY() == this.attackLocation.getY())
		{
			this.isArrived = true;
			this.canMove = false;
		}
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
	
	private void Attack()
	{
		if (this.tmp == 1)
		{
			int x = this.itsOst.GetOrigin().GetX();
			int y = this.itsOst.GetOrigin().GetY();
			System.out.println("Soldat (" + (-1* (x - this.GetX())) + ", " + (-1* (y - this.GetY())) + ") -> J'attaque 1 fois");
			this.tmp++;
		}
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
	
	public int getSpeed()
	{
		return speed;
	}

	public boolean isOnField()
	{
		return onField;
	}
}