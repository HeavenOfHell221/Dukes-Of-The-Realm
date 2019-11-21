package Soldiers;

import DukesOfTheRealm.*;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

public abstract class Soldier implements IProductionUnit {
	protected SoldiersType type;
	protected int productionCost;
	protected int productionTime;
	protected int health;
	protected int damage;
	protected int speed;
	
	public Soldier(int productionCost, int productionTime, int speed, int health, int damage)
	{
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.health = health;
		this.damage = damage;
		this.speed = speed;
	}
	
	public void InflictDamage()
	{
		health--;
	}

	public int GetTime()
	{
		return productionTime;
	}
	
	public int GetCost()
	{
		return productionCost;
	}

	public SoldiersType GetType()
	{
		return type;
	}

}