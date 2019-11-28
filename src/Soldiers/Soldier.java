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
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	public Soldier(Pane layer, double x, double y, int productionCost, int productionTime, int speed, int health, int damage)
	{
		super(layer, x, y);
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.health = health;
		this.damage = damage;
		this.speed = speed;
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start()
	{
		canMove = true;
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
		if (canMove)
		{
			Move();
			UpdateUIShape();
		}
	}
	
	private void Move()
	{
		this.AddDx(this.speed * Time.deltaTime);
		testMustDie();
	}
	
	private void testMustDie()
	{
		if(GetX() > Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.04) || GetY() > Settings.SCENE_HEIGHT || GetX() <= 0 || GetY() <= 0)
		{
			RemoveShapeToLayer();
			canMove = false;
			isDead = true;
		}
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	
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