package Soldiers;

import DukesOfTheRealm.Sprite;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

public abstract class Soldier extends Sprite {
	protected int productionCost;
	protected int productionTime;
	protected int health;
	protected int damage;
	protected int speed;
	
	public Soldier(Pane layer, Image image, double x, double y, int productionCost, int productionTime, int speed, int health, int damage)
	{
		super(layer, image, x , y);
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.health = health;
		this.damage = damage;
		this.speed = speed;
	}
	
	public Soldier(Pane layer, Image image, Point2D p, int productionCost, int productionTime, int speed, int health, int damage)
	{
		super(layer, image, p.getX(), p.getY());
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.health = health;
		this.damage = damage;
		this.speed = speed;
	}

}