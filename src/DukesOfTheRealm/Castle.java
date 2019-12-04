package DukesOfTheRealm;

import java.util.ArrayDeque;
import java.util.Random;
import java.util.Stack;

import Utility.Point2D;

import Duke.*;
import Interface.IProductionUnit;
import Interface.ISave;
import SaveSystem.CastleData;
import Soldiers.*;
import Utility.Time;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Castle extends Sprite implements ISave<CastleData> {

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	public enum Orientation
	{
		North,
		South,
		West,
		East,
		NE,		// North East
		NW,		// North West
		SE,		// South East
		SW,		// South West
		None;
	}
	
	private double totalFlorin; 						// L'argent que contient le chateau
	private int level; 									// Le niveau du chateau
	private ReserveOfSoldiers reserveOfSoldiers; 		// La reserve de soldat du chateau. Contient des Piker, des Onager et des Knight
	private transient Actor actor; 								// Le proprietaire du chateau 
	private ArrayDeque<IProductionUnit> productionUnit; // L'unite de production. C'est une amelioration ou un soldat en cours de production
	private int productionTime; 						// Le temps restant a la production de l'unite de production
	private Ost ost;
	private Orientation orientation;
	private transient Color myColor;
	private transient Rectangle door;
	private Stack<Point2D> attackLocations;
	
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
		this.myColor = actor.GetMyColor();
		this.attackLocations = new Stack<Point2D>();
	}
	
	public Castle(Pane layer, double x, double y) 
	{
		super(layer, x, y);
	}
	
	public Castle(int level)
	{
		super();
		this.level = level;
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
		}
		
		SetAttackLocations();
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
			AddFlorin(Settings.FLORIN_PER_SECOND * level * Time.deltaTime * Settings.FLORIN_FACTOR_BARON);
	}
	
	private void UpdateProduction()
	{
		if(this.productionUnit.size() > 0)
		{
			this.productionTime--;
			//System.out.println(this.productionTime);
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
	
	@Override
	public void ReceivedDataSave(CastleData data) 
	{
		data.attackLocations = this.attackLocations;
		data.level = this.level;
		data.orientation = this.orientation;
		data.reserveOfSoldiers = this.reserveOfSoldiers;
		data.totalFlorin = this.totalFlorin;
		data.productionTime = this.productionTime;
		data.productionUnit = this.productionUnit;
		data.coordinate = GetCoordinate();
	    data.width = getWidth();
	    data.height = getHeight();
	}

	@Override
	public void SendingDataSave(CastleData castleData) 
	{
		
	}
	
	private void RandomSoldier()
	{
		Random rand = new Random();
		this.reserveOfSoldiers.SetNbKnights(rand.nextInt(5) * level);
		this.reserveOfSoldiers.SetNbPikers(rand.nextInt(10) * level);
		this.reserveOfSoldiers.SetNbOnagers(rand.nextInt(5) + level);
	}
	
	private void StartSoldier()
	{
		this.reserveOfSoldiers.SetNbKnights(Settings.STARTER_KNIGHT);
		this.reserveOfSoldiers.SetNbPikers(Settings.STARTER_PIKER);
		this.reserveOfSoldiers.SetNbOnagers(Settings.STARTER_ONAGER);
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
		AddDoorRepresentation();
	}
	
	private void AddDoorRepresentation()
	{
		this.orientation = SetOrientation();
		
		switch(this.orientation)
		{
			case North: this.door = new Rectangle(GetX() + Settings.CASTLE_SIZE / 4, GetY(), Settings.CASTLE_SIZE / 2, Settings.CASTLE_SIZE / 6); break;
			case South: this.door = new Rectangle(GetX() + Settings.CASTLE_SIZE / 4, GetY() + Settings.CASTLE_SIZE - Settings.CASTLE_SIZE / 6, Settings.CASTLE_SIZE / 2, Settings.CASTLE_SIZE / 6); break;
			case East: this.door = new Rectangle(GetX() +  + Settings.CASTLE_SIZE - Settings.CASTLE_SIZE / 6, GetY() + Settings.CASTLE_SIZE / 4, Settings.CASTLE_SIZE / 6, Settings.CASTLE_SIZE / 2); break;
			case West: this.door = new Rectangle(GetX(), GetY() + + Settings.CASTLE_SIZE / 4, Settings.CASTLE_SIZE / 6, Settings.CASTLE_SIZE / 2); break;
			default: this.door = new Rectangle(0, 0, 0, 0); break;	
		}		
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
	
	public boolean AddProduction(IProductionUnit newProduction)
	{
		if(newProduction == null || !RemoveFlorin(newProduction.GetProductionCost()))
			return false;
		
		this.productionUnit.addLast(newProduction);
		
		if(this.productionUnit.size() == 1)
		{
			this.productionTime = newProduction.GetProductionTime();
		}
		
		return true;
	}
	

	public boolean CreateOst(Castle destination, int nbPikers, int nbKnights, int nbOnagers)
	{
		if (this.ost == null)
		{
			this.ost = new Ost(this, destination, nbPikers, nbKnights, nbOnagers, this.myColor);
			this.ost.Start();
			return true;
		}
		return false;
	}
	
	public void RemoveOst()
	{
		this.ost = null;
	}

	protected void SetAttackLocations() {
		int x = this.GetX();
		int y = this.GetY();
		for (int i = 0; i < Settings.NB_ATTACK_LOCATIONS; i++) {
			int j = i % Settings.ATTACK_LOCATIONS_PER_SIDE;	// 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2
			switch (i / Settings.ATTACK_LOCATIONS_PER_SIDE)	// 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3
			{
			// North
			case 0: this.attackLocations.push(new Point2D(x + (Settings.THIRD_OF_CASTLE * j), y - 10 - Settings.SOLDIER_SIZE)); break;
			// East
			case 1: this.attackLocations.push(new Point2D(x + Settings.CASTLE_SIZE + 10, y + (Settings.THIRD_OF_CASTLE * j))); break;
			// South
			case 2: this.attackLocations.push(new Point2D(x + (Settings.THIRD_OF_CASTLE * j), y + Settings.CASTLE_SIZE + 10)); break;
			// West
			case 3: this.attackLocations.push(new Point2D(x - 10 - Settings.SOLDIER_SIZE, y + (Settings.THIRD_OF_CASTLE * j))); break;
			}
		}
	}
	
	public boolean IsAvailableAttackLocation()
	{
		return !this.attackLocations.empty();
	}
	
	public Point2D GetNextAttackLocation()
	{
		return this.attackLocations.pop();
	}
	
	public void FreeAttackLocation(Point2D FreedAttackLocation)
	{
		this.attackLocations.push(FreedAttackLocation);
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
	
	public Rectangle GetDoor()
	{
		return door;
	}
	
	public Orientation GetOrientation()
	{
		return orientation;
	}
	
	public Color GetMyColor()
	{
		return myColor;
	}

	public Ost GetOst() 
	{
		return ost;
	}
	
	public void SetOst(Ost ost)
	{
		this.ost = ost;
	}

	public Stack<Point2D> GetAttackLocations() 
	{
		return attackLocations;
	}
	
	public ArrayDeque<IProductionUnit> GetProductionUnit()
	{
		return productionUnit;
	}
	
	public int GetProductionTimeRemaining()
	{
		return productionTime;
	}
}
