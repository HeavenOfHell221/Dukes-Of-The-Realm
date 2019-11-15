package Soldiers;

import DukesOfTheRealm.Sprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

public abstract class Soldier extends Sprite {
	protected int productionCost;
	protected int productionTime;
	protected int health;
	protected int damage;
	
	public Soldier(Pane layer, Image image, double x, double y, int productionCost, int productionTime, int speed, int health, int damage)
	{
		super(layer, image, x , y, speed);
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.health = health;
		this.damage = damage;
	}
}