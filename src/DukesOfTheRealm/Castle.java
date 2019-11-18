package DukesOfTheRealm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import Duke.Duke;
import Soldiers.Soldier;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Castle extends Sprite implements ProductionUnit {
	
	private int totalFlorin;
	private int level;
	private ArrayList<Soldier> reserveOfSoldiers;

	private Duke duke;
	private ProductionUnit productionUnit;
	private int productionTime;
	
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
	

	public void AddRectangle()
	{
		AddRectangle((Settings.CASE_WIDTH * 5) - 2, (Settings.CASE_HEIGHT * 5) - 2);
	}
	
	public boolean CanLevelUp() 
	{
		if(totalFlorin >= Settings.LEVEL_UP_COST_FACTOR * (level + 1))
		{
			return true;
		}
		return false;
	}
	
	public void LevelUp()
	{
		this.level++;
	}
	
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
		System.out.println(totalFlorin);
		totalFlorin += amount;
	}
	
	public void RemoveFlorin(int amount)
	{
		if(EnoughOfFlorin(amount))
		{
			totalFlorin -= amount;
		}
	}
	
	public boolean EnoughOfFlorin(int amount)
	{
		return (amount <= totalFlorin);
	}
	
	public void AddProduction(ProductionUnit newProduction)
	{
		if(this.productionUnit != null)
		{	
			RemoveFlorin(newProduction.GetCost());
			this.productionUnit = newProduction;
			this.productionTime = GetProductionTime(newProduction);	
		}
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
				// TODO
				
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
