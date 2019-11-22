package DukesOfTheRealm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import Duke.Actor;
import Duke.Duke;
import Soldiers.*;
import Utility.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Castle extends Sprite implements IProductionUnit, IUpdate {
	
	private enum Orientation
	{
		North,
		South,
		West,
		East,
		None;
	}
	
	private int totalFlorin; // L'argent que contient le chateau
	private int level; // Le niveau du chateau
	private ArrayList<Soldier> reserveOfSoldiers; // La reserve de soldat du chateau. Contient des Piker, des Onager et des Knight
	private Actor actor; // Le proprietaire du chateau 
	private IProductionUnit productionUnit; // L'unite de production. C'est une amelioration ou un soldat en cours de production
	private int productionTime; // Le temps restant a la production de l'unite de production
	private Ost ostDeployment;
	private Orientation orientation;
	
	/* Constructeur */
	Castle(Pane layer, double x, double y, int level, Actor actor)
	{
		super(layer, x, y);
		this.level = level;
		this.totalFlorin = 0;
		this.actor = actor;
		this.productionUnit = null;
		this.productionTime = 0;
		this.reserveOfSoldiers = new ArrayList<Soldier>();
		this.ostDeployment = null;
		this.orientation = SetOrientation();
		//AddOst(this, 2);
		Grid.AddCastle(this);
		this.actor.AddCastle(this);
	}
	
	private Orientation SetOrientation()
	{
		Random rand = new Random();
		Orientation orientation;
		
		switch(rand.nextInt(4))
		{
			case 0: orientation = Orientation.North; break;
			case 1: orientation = Orientation.South; break;
			case 2: orientation = Orientation.West; break;
			case 3: orientation = Orientation.East; break;
			default: orientation = Orientation.None; break;
		}
		return orientation;
	}
	
	/* Ajoute un rectangle au layer */
	public void AddRectangle()
	{
		AddRectangle(Settings.CELL_SIZE, Settings.CELL_SIZE);
	}
	
	/* Test si le chateau a assez d'argent pour augmenter d'un niveau */
	public boolean CanLevelUp() 
	{
		if(totalFlorin >= Settings.LEVEL_UP_COST_FACTOR * (level + 1))
		{
			return true;
		}
		return false;
	}
	
	/* Augemente le chateau d'un niveau */
	public void LevelUp()
	{
		this.level++;
	}
	
	/* Met a jour l'argent total du chateau */
	private void UpdateFlorin()
	{
		AddFlorin(Settings.FLORIN_FACTOR * level);
	}
	
	public void Update(long now)
	{	
		UpdateFlorin();
		UpdateProduction();
		if(this.ostDeployment != null) UpdateOst(now);
	}
	
	public void AddFlorin(int amount)
	{
		totalFlorin += amount;
	}
	
	public boolean RemoveFlorin(int amount)
	{
		if(EnoughOfFlorin(amount))
		{
			totalFlorin -= amount;
			return true;
		}
		return false;
	}
	
	public boolean EnoughOfFlorin(int amount)
	{
		return (amount <= totalFlorin);
	}
	
	public void AddProduction(IProductionUnit newProduction)
	{
		if(this.productionUnit != null)
		{	
			if(RemoveFlorin(newProduction.GetProductionCost()))
			{
				this.productionUnit = newProduction;
				this.productionTime = GetProductionTime(newProduction);
			}
		}
	}
	
	public void InflictDamage(SoldierEnum type)
	{		
		this.reserveOfSoldiers.stream()
			.filter(soldier -> soldier.GetType() == type)
			.limit(1)
			.forEach(soldier -> soldier.InflictDamage());
	}
	
	public int GetProductionCost()
	{
		return Settings.LEVEL_UP_COST_FACTOR * level;
	}
	
	private void UpdateProduction()
	{
		if(this.productionUnit != null)
		{
			this.productionTime--;
			if(this.productionTime == 0)
			{
				if(this.productionUnit.getClass() == Castle.class)
					LevelUp();
				else if(this.productionUnit.getClass() == Piker.class)
					this.reserveOfSoldiers.add(new Piker());
				else if(this.productionUnit.getClass() == Onager.class)
					this.reserveOfSoldiers.add(new Onager());
				else if(this.productionUnit.getClass() == Knight.class)
					this.reserveOfSoldiers.add(new Knight());
				
				this.productionUnit = null;
			}
		}
	}

	private int GetProductionTime(IProductionUnit production)
	{
		return production.GetProductionTime();
	}

	public int GetTotalFlorin() {
		return totalFlorin;
	}

	public Actor GetActor() {
		return actor;
	}

	public void SetDuke(Duke duke) {
		this.actor = duke;
	}
	
	public int GetProductionTime()
	{
		return Settings.LEVEL_UP_DURATION_OFFSET + Settings.LEVEL_UP_DURATION_FACTOR * level;
	}
	
	private void UpdateOst(long now)
	{
		this.ostDeployment.UpdateAtEachFrame(now);
	}
	
	public boolean AddOst(Castle destination, int speed)
	{
		if(this.ostDeployment == null)
		{
			this.ostDeployment = new Ost(this.getLayer(), GetX(), GetY(), this, destination, speed);
			if(this.ostDeployment != null)
			{
				this.ostDeployment.AddCircle(10);
				this.getLayer().getChildren().add(this.ostDeployment.GetShape());
			}
			return true;
		}
		return false;
	}
}
