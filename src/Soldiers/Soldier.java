package Soldiers;

import DukesOfTheRealm.Sprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Soldier extends Sprite {
	protected int productionCost;
	protected int productionTime;
	protected int speed;
	protected int health;
	protected int damage;
	
	public Soldier(Pane layer, Image image, double x, double y, int productionCost, int productionTime, int speed, int health, int damage)
	{
		super(layer, image, x , y);
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.speed = speed;
		this.health = health;
		this.damage = damage;
	}
}