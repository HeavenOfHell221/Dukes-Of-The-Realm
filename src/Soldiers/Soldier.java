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
	protected Deque<Point2D> path;
	
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
		Grid.GetPath(GetCoordinate());
	}
	
	public void Start(Color color)
	{
		canMove = true;
		this.GetShape().setFill(color);
		this.getLayer().getChildren().add(this.GetShape());
		UpdateUIShape();
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
		
		/*
		 * Pour chaque Point2D dans le path -> Aller jusqu'a ce point puis prendre le prochain et y aller...
		 */
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