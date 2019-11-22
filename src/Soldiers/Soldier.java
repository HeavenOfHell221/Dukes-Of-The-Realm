package Soldiers;

import DukesOfTheRealm.*;
import Utility.FPS;
import Utility.Settings;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

public abstract class Soldier extends Sprite implements IProductionUnit {
	protected SoldierEnum type;
	protected int productionCost;
	protected int productionTime;
	protected int health;
	protected int damage;
	protected int speed;
	protected Ost itsOst = null; 
//	protected double movement;
	protected boolean canMove = false;
	protected boolean isArrived;
	
	public Soldier(Pane layer, double x, double y, int productionCost, int productionTime, int speed, int health, int damage)
	{
		super(layer, x + (Settings.CELL_SIZE-1)/2 +1 + Settings.CELL_SIZE, y + (Settings.CELL_SIZE-1)/2 +1 + Settings.CELL_SIZE);
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.health = health;
		this.damage = damage;
		this.speed = speed;
//		this.movement = (this.itsOst.getOstSpeed() * (double) Settings.CELL_SIZE) / (double) this.timeToMove; //Settings.TIME_FACTOR;
	}
	
	public void InflictDamage()
	{
		health--;
	}

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
	
	public void UpdateAtEachFrame(long now)
	{		
		if (canMove)
		{
			Move();
			UpdateUIShape();
		}
	}
	
	private void Move()
	{
		int horizontalDirection = itsOst.getDestination().GetX() > itsOst.getOrigin().GetX() ? 1 : -1;
		int verticalDirection = itsOst.getDestination().GetY() > itsOst.getOrigin().GetX() ? 1 : -1;
		boolean toggleXMovement = true;
		boolean toggleYMovement = true;
		
		toggleXMovement = Grid.GetCellWithCoordinates(GetX(), GetY()).getX() 
				== Grid.GetCellWithCoordinates(itsOst.getDestination().GetX(), itsOst.getDestination().GetY()).getX() ? false : true;
		 
		
		toggleYMovement = Grid.GetCellWithCoordinates(GetX(), GetY()).getY()
				== Grid.GetCellWithCoordinates(itsOst.getDestination().GetX(), itsOst.getDestination().GetY()).getY() ? false : true;
		
		if(toggleXMovement)
		{
			AddDx(this.speed * horizontalDirection);
		}
		
		else if(toggleYMovement)
		{
			AddDy(this.speed * verticalDirection);
		}
	}
	
	public void Start()
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
		this.getLayer().getChildren().add(this.GetShape());
	}
}