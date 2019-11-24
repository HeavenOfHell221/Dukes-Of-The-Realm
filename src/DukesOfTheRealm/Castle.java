package DukesOfTheRealm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Random;

import Duke.*;
import Soldiers.*;
import Utility.FPS;
import Utility.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
	
	private double totalFlorin; 							// L'argent que contient le chateau
	private int level; 									// Le niveau du chateau
	private ReserveOfSoldiers reserveOfSoldiers; 		// La reserve de soldat du chateau. Contient des Piker, des Onager et des Knight
	private Actor actor; 								// Le proprietaire du chateau 
	private ArrayDeque<IProductionUnit> productionUnit; // L'unite de production. C'est une amelioration ou un soldat en cours de production
	private int productionTime; 						// Le temps restant a la production de l'unite de production
	private Ost ost;
	//private Soldier firstSoldier = null;				// ***** PROVISOIRE *****
	private Orientation orientation;
	private Color myColor;
	
	/* Constructeur */
	Castle(Pane layer, double x, double y, int level, Actor actor)
	{
		super(layer, x, y);
		this.level = level;
		this.totalFlorin = 0;
		this.actor = actor;
		this.productionUnit = null;
		this.productionTime = 0;
		this.reserveOfSoldiers = new ReserveOfSoldiers();
		this.ost = null;
		this.productionUnit = new ArrayDeque<IProductionUnit>();
		this.orientation = SetOrientation();
		this.myColor = actor.GetMyColor();
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
	
	public void Start()
	{
		actor.AddCastle(this);
	}
	
	/* Ajoute un rectangle au layer */
	public void AddRepresentation()
	{
		AddCastleRepresentation(Settings.CASTLE_SIZE);
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
	
	/* Augmente le chateau d'un niveau */
	public void LevelUp()
	{
		this.level++;
	}
	
	public void Update(long now, boolean pause)
	{	
		if(!pause)
		{
			UpdateFlorin();
			UpdateProduction();
		}
		if(this.ost != null)
			UpdateOst(now, pause);
	}
	
	/* Met a jour l'argent total du chateau */
	private void UpdateFlorin()
	{
		AddFlorin(Settings.FLORIN_PER_SECOND * level * FPS.deltaTime);
	}
	
	public void AddFlorin(double amount)
	{
		totalFlorin += amount;
	}
	
	public boolean RemoveFlorin(double amount)
	{
		if(EnoughOfFlorin(amount))
		{
			totalFlorin -= amount;
			return true;
		}
		return false;
	}
	
	public boolean EnoughOfFlorin(double amount)
	{
		return (amount <= totalFlorin);
	}
	
	public void AddProduction(IProductionUnit newProduction)
	{
		if(newProduction == null || !RemoveFlorin(newProduction.GetProductionCost()))
			return;
		
		this.productionUnit.addLast(newProduction);
	}
	
	public int GetProductionCost()
	{
		return Settings.LEVEL_UP_COST_FACTOR * level;
	}
	
	private void UpdateProduction()
	{
		if(this.productionUnit.size() > 0)
		{
			this.productionTime--;
			if(this.productionTime == 0)
			{
				IProductionUnit p = this.productionUnit.pollFirst();
				
				if(p.getClass() == Castle.class)
					LevelUp();
				else if(p.getClass() == Piker.class)
					this.reserveOfSoldiers.AddPiker();
				else if(p.getClass() == Onager.class)
					this.reserveOfSoldiers.AddOnager();
				else if(p.getClass() == Knight.class)
					this.reserveOfSoldiers.AddKnight();
				
				if(this.productionUnit.size() > 0)
				{
					this.productionTime = this.productionUnit.getFirst().GetProductionTime();
				}
			}
		}
	}

	private int GetProductionTime(IProductionUnit production)
	{
		return production.GetProductionTime();
	}

	public double GetTotalFlorin() {
		return totalFlorin;
	}

	public Actor GetActor() {
		return actor;
	}

	public void SetDuke(Actor actor) {
		this.actor = actor;
	}
	
	public int GetProductionTime()
	{
		return Settings.LEVEL_UP_DURATION_OFFSET + Settings.LEVEL_UP_DURATION_FACTOR * level;
	}
	
	private void UpdateOst(long now, boolean pause)
	{
		this.ost.Update(now, pause);
	}
	
	public boolean CreateOst(int nbPikers, int nbKnights, int nbOnagers)
	{
		if (this.ost == null)
		{
			this.ost = new Ost(this, this, nbPikers, nbKnights, nbOnagers, this.myColor);
			return true;
		}
		return false;
	}
	
	/*public boolean AddFirstSoldier()
	{
		if (this.firstSoldier == null)
		{
			this.firstSoldier = new Knight(this.getLayer(), GetX(), GetY());
			return true;
		}
		return false;
	}*/
	
	public int GetLevel()
	{
		return level;
	}
	
	public ReserveOfSoldiers GetReserveOfSoldiers()
	{
		return reserveOfSoldiers;
	}
}
