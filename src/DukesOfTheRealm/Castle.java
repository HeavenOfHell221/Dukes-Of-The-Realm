package DukesOfTheRealm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import Duke.Duke;
import Soldiers.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Castle extends Sprite implements ProductionUnit {
	
	private enum Orientation
	{
		North,
		South,
		West,
		East,
		None;
	}
	
	private int totalFlorin; // L'argent que contient le ch�teau
	private int level; // Le niveau du ch�teau
	private ArrayList<Soldier> reserveOfSoldiers; // La r�serve de soldat du ch�teau. Contient des Piker, des Onager et des Knight
	private Duke duke; // Le propri�taire du ch�teau 
	private ProductionUnit productionUnit; // L'unit� de production. C'est une am�lioration ou un soldat en cours de production
	private int productionTime; // Le temps restant � la production de l'unit� de production
	private ArrayList<Ost> ostsDeployment;
	private Orientation orientation;
	
	/* Constructeur */
	Castle(Pane layer, Image image, double x, double y, int level, Duke duke)
	{
		super(layer, image, x, y);
		this.level = level;
		this.totalFlorin = 0;
		this.duke = duke;
		this.productionUnit = null;
		this.productionTime = 0;
		this.reserveOfSoldiers = new ArrayList<Soldier>();
		this.ostsDeployment = new ArrayList<Ost>();
		this.orientation = SetOrientation();
		ostsDeployment.add(new Ost(layer, image, x, y, this, this, 2));
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
	
	/* Test si le ch�teau a assez d'argent pour augmenter d'un niveau */
	public boolean CanLevelUp() 
	{
		if(totalFlorin >= Settings.LEVEL_UP_COST_FACTOR * (level + 1))
		{
			return true;
		}
		return false;
	}
	
	/* Augemente le ch�teau d'un niveau */
	public void LevelUp()
	{
		this.level++;
	}
	
	/* Met � jour l'argent total du ch�teau */
	private void UpdateFlorin()
	{
		AddFlorin(Settings.FLORIN_FACTOR * level);
	}
	
	public void UpdateTurn()
	{
		UpdateFlorin();
		UpdateProduction();
	}
	
	public void UpdateAtEachFrame()
	{		
		UpdateOst();
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
	
	public void AddProduction(ProductionUnit newProduction)
	{
		if(this.productionUnit != null)
		{	
			if(RemoveFlorin(newProduction.GetCost()))
			{
				this.productionUnit = newProduction;
				this.productionTime = GetProductionTime(newProduction);
			}
		}
	}
	
	public void InflictDamage(SoldiersType type)
	{		
		this.reserveOfSoldiers.stream()
			.filter(soldier -> soldier.GetType() == type)
			.limit(1)
			.forEach(soldier -> soldier.InflictDamage());
	}
	
	public int GetCost()
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

	private int GetProductionTime(ProductionUnit production)
	{
		return production.GetTime();
	}

	public int GetTotalFlorin() {
		return totalFlorin;
	}

	public Duke GetDuke() {
		return duke;
	}

	public void SetDuke(Duke duke) {
		this.duke = duke;
	}
	
	public int GetTime()
	{
		return Settings.LEVEL_UP_DURATION_OFFSET + Settings.LEVEL_UP_DURATION_FACTOR * level;
	}
	
	private void UpdateOst()
	{
		ostsDeployment.forEach(ost -> ost.UpdateAtEachFrame());
	}
}
