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
	
	private int totalFlorin; // L'argent que contient le château
	private int level; // Le niveau du château
	private ArrayList<Soldier> reserveOfSoldiers; // La réserve de soldat du château. Contient des Piker, des Onager et des Knight
	private Duke duke; // Le propriétaire du château 
	private ProductionUnit productionUnit; // L'unité de production. C'est une amélioration ou un soldat en cours de production
	private int productionTime; // Le temps restant à la production de l'unité de production
	
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
	}
	
	/* Ajoute un rectangle au layer */
	public void AddRectangle()
	{
		AddRectangle(Settings.CASE_WIDTH, Settings.CASE_HEIGHT);
	}
	
	/* Test si le château a assez d'argent pour augmenter d'un niveau */
	public boolean CanLevelUp() 
	{
		if(totalFlorin >= Settings.LEVEL_UP_COST_FACTOR * (level + 1))
		{
			return true;
		}
		return false;
	}
	
	/* Augemente le château d'un niveau */
	public void LevelUp()
	{
		this.level++;
	}
	
	/* Met à jour l'argent total du château */
	private void UpdateFlorin()
	{
		AddFlorin(Settings.FLORIN_FACTOR * level);
	}
	
	public void Update()
	{
		UpdateFlorin();
		UpdateProduction();
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
	
	public void InflictDamage()
	{
		Random rand = new Random();
		int n = rand.nextInt(3);
		
		this.reserveOfSoldiers.stream()
			.filter(soldier -> soldier.getClass() == Knight.class)
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
}
