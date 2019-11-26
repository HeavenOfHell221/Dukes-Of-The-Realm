package DukesOfTheRealm;

import java.util.ArrayDeque;
import java.util.Random;

import Duke.*;
import Soldiers.*;
import Utility.Time;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Castle extends Sprite implements IProductionUnit, IUpdate {
	
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	private enum Orientation
	{
		North,
		South,
		West,
		East,
		None;
	}
	
	private double totalFlorin; 						// L'argent que contient le chateau
	private int level; 									// Le niveau du chateau
	private ReserveOfSoldiers reserveOfSoldiers; 		// La reserve de soldat du chateau. Contient des Piker, des Onager et des Knight
	private Actor actor; 								// Le proprietaire du chateau 
	private ArrayDeque<IProductionUnit> productionUnit; // L'unite de production. C'est une amelioration ou un soldat en cours de production
	private int productionTime; 						// Le temps restant a la production de l'unite de production
	private Ost ost;
	private Orientation orientation;
	private Color myColor;
	
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
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
		this.productionUnit = new ArrayDeque<>();
		this.orientation = SetOrientation();
		this.myColor = actor.GetMyColor();
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start()
	{
		actor.AddCastle(this);
		
		if(actor.getClass() == Baron.class)
		{
			RandomSoldier();
			RandomFlorin();
		}
		else
		{
			StartSoldier();
			StartFlorin();
		}
	}
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/
	
	@Override
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
	
	private void UpdateFlorin()
	{
		if(actor.getClass() != Baron.class)
			AddFlorin(Settings.FLORIN_PER_SECOND * level * Time.deltaTime);
		else
			AddFlorin(Settings.FLORIN_PER_SECOND * level * Time.deltaTime * 0.1);
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
	
	private void UpdateOst(long now, boolean pause)
	{
		this.ost.Update(now, pause);
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	private void RandomSoldier()
	{
		Random rand = new Random();
		this.reserveOfSoldiers.setNbKnights(rand.nextInt(5) * level);
		this.reserveOfSoldiers.setNbPikers(rand.nextInt(10) * level);
		this.reserveOfSoldiers.setNbOnagers(rand.nextInt(5) + level);
	}
	
	private void StartSoldier()
	{
		this.reserveOfSoldiers.setNbKnights(10);
		this.reserveOfSoldiers.setNbPikers(30);
		this.reserveOfSoldiers.setNbOnagers(5);
	}
	
	private void StartFlorin()
	{
		this.totalFlorin += 500;
	}
	
	private void RandomFlorin()
	{
		Random rand = new Random();
		this.totalFlorin += (rand.nextInt(1000) * level);
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
	

	public boolean CreateOst(int nbPikers, int nbKnights, int nbOnagers)
	{
		if (this.ost == null)
		{
			this.ost = new Ost(this, this, nbPikers, nbKnights, nbOnagers, this.myColor);
			this.ost.Start();
			return true;
		}
		return false;
	}
	
	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
	
	@Override
	public int GetProductionCost()
	{
		return Settings.LEVEL_UP_COST_FACTOR * level;
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
	
	@Override
	public int GetProductionTime()
	{
		return Settings.LEVEL_UP_DURATION_OFFSET + Settings.LEVEL_UP_DURATION_FACTOR * level;
	}
	
	public int GetLevel()
	{
		return level;
	}
	
	public ReserveOfSoldiers GetReserveOfSoldiers()
	{
		return reserveOfSoldiers;
	}
	
	
}
