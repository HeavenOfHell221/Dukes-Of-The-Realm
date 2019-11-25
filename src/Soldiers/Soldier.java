package Soldiers;

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
	
	public void Start(Color color)
	{
		switch (this.type)
		{
		case Piker:
			AddPikerRepresentation();
			break;
		case Knight:
			AddKnightRepresentation();
			break;
		case Onager:
			AddOnagerRepresentation();
			break;
		default:
			break;
		}
		
		canMove = true;
		this.GetShape().setFill(color);
		this.getLayer().getChildren().add(this.GetShape());
		UpdateUIShape();
	}
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/
	
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
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	
	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	public int GetProductionTime()
	{
		return productionTime;
	}
	
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