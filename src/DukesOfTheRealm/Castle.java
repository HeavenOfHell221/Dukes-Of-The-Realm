package DukesOfTheRealm;

import java.util.ArrayDeque;
import java.util.Random;
import java.util.Stack;

import Duke.Actor;
import Duke.Baron;
import Interface.IProductionUnit;
import Interface.ISave;
import SaveSystem.CastleData;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import UI.UIManager;
import Utility.Point2D;
import Utility.Settings;
import Utility.Time;
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
	private double productionTime; 						// Le temps restant a la production de l'unite de production
	private Ost ost;
	private Orientation orientation;
	private transient Color myColor;
	private transient Rectangle door;
	private Stack<Point2D> attackLocations;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	Castle(final Pane layer, final double x, final double y, final int level, final Actor actor)
	{
		super(layer, x, y);
		this.level = level;
		totalFlorin = 0;
		this.actor = actor;
		productionUnit = null;
		productionTime = 0;
		reserveOfSoldiers = new ReserveOfSoldiers();
		ost = null;
		productionUnit = new ArrayDeque<>();
		myColor = actor.GetMyColor();
		attackLocations = new Stack<>();
	}

	public Castle(final Pane layer, final double x, final double y)
	{
		super(layer, x, y);
	}

	public Castle(final int level)
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
	public void Update(final long now, final boolean pause)
	{
		if(!pause)
		{
			UpdateFlorin();
			UpdateProduction();
		}
		if(ost != null) {
			UpdateOst(now, pause);
		}
	}

	private void UpdateFlorin()
	{
		if(actor.getClass() != Baron.class) {
			AddFlorin(Settings.FLORIN_PER_SECOND * level * Time.deltaTime);
		} else {
			AddFlorin(Settings.FLORIN_PER_SECOND * level * Time.deltaTime * Settings.FLORIN_FACTOR_BARON);
		}
	}

	private void UpdateProduction()
	{
		if(productionUnit.size() > 0)
		{
			productionTime -= (1 * Time.deltaTime);

			final double ratio = 1 - (productionTime / productionUnit.getFirst().GetProductionTime());
			UIManager.GetProductionUnitPreview().SetFill(ratio);

			if(productionTime <= 0)
			{
				final IProductionUnit p = productionUnit.pollFirst();

				if(p.getClass() == Castle.class) {
					LevelUp();
				} else if(p.getClass() == Piker.class) {
					reserveOfSoldiers.AddPiker();
				} else if(p.getClass() == Onager.class) {
					reserveOfSoldiers.AddOnager();
				} else if(p.getClass() == Knight.class) {
					reserveOfSoldiers.AddKnight();
				}

				if(productionUnit.size() > 0)
				{
					productionTime = productionUnit.getFirst().GetProductionTime();
				}
			}
		}
	}

	private void UpdateOst(final long now, final boolean pause)
	{
		ost.Update(now, pause);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public void ReceivedDataSave(final CastleData data)
	{
		data.attackLocations = attackLocations;
		data.level = level;
		data.orientation = orientation;
		data.reserveOfSoldiers = reserveOfSoldiers;
		data.totalFlorin = totalFlorin;
		//data.productionTime = this.productionTime;
		data.productionUnit = productionUnit;
		data.coordinate = GetCoordinate();
		data.width = getWidth();
		data.height = getHeight();
	}

	@Override
	public void SendingDataSave(final CastleData castleData)
	{

	}

	private void RandomSoldier()
	{
		final Random rand = new Random();
		reserveOfSoldiers.SetNbKnights(rand.nextInt(5) * level);
		reserveOfSoldiers.SetNbPikers(rand.nextInt(10) * level);
		reserveOfSoldiers.SetNbOnagers(rand.nextInt(5) + level);
	}

	private void StartSoldier()
	{
		reserveOfSoldiers.SetNbKnights(Settings.STARTER_KNIGHT);
		reserveOfSoldiers.SetNbPikers(Settings.STARTER_PIKER);
		reserveOfSoldiers.SetNbOnagers(Settings.STARTER_ONAGER);
	}


	private void RandomFlorin()
	{
		final Random rand = new Random();
		totalFlorin += (rand.nextInt(1000) * level);
	}

	private Orientation SetOrientation()
	{
		final Random rand = new Random();
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
		orientation = SetOrientation();

		switch(orientation)
		{
		case North: door = new Rectangle(GetX() + Settings.CASTLE_SIZE / 4, GetY(), Settings.CASTLE_SIZE / 2, Settings.CASTLE_SIZE / 6); break;
		case South: door = new Rectangle(GetX() + Settings.CASTLE_SIZE / 4, GetY() + Settings.CASTLE_SIZE - Settings.CASTLE_SIZE / 6, Settings.CASTLE_SIZE / 2, Settings.CASTLE_SIZE / 6); break;
		case East: door = new Rectangle(GetX() +  + Settings.CASTLE_SIZE - Settings.CASTLE_SIZE / 6, GetY() + Settings.CASTLE_SIZE / 4, Settings.CASTLE_SIZE / 6, Settings.CASTLE_SIZE / 2); break;
		case West: door = new Rectangle(GetX(), GetY() + + Settings.CASTLE_SIZE / 4, Settings.CASTLE_SIZE / 6, Settings.CASTLE_SIZE / 2); break;
		default: door = new Rectangle(0, 0, 0, 0); break;
		}
		door.setMouseTransparent(true);
	}

	/* Augmente le chateau d'un niveau */
	public void LevelUp()
	{
		level++;
	}

	public void AddFlorin(final double amount)
	{
		totalFlorin += amount;
	}

	public boolean RemoveFlorin(final double amount)
	{
		if(EnoughOfFlorin(amount))
		{
			totalFlorin -= amount;
			return true;
		}
		return false;
	}

	public boolean EnoughOfFlorin(final double amount)
	{
		return (amount <= totalFlorin);
	}

	public boolean AddProduction(final IProductionUnit newProduction)
	{
		if(newProduction == null || !RemoveFlorin(newProduction.GetProductionCost())) {
			return false;
		}

		productionUnit.addLast(newProduction);

		if(productionUnit.size() == 1)
		{
			productionTime = newProduction.GetProductionTime();
		}

		return true;
	}


	public boolean CreateOst(final Castle destination, final int nbPikers, final int nbKnights, final int nbOnagers)
	{
		if (ost == null)
		{
			ost = new Ost(this, destination, nbPikers, nbKnights, nbOnagers, myColor);
			ost.Start();
			return true;
		}
		return false;
	}

	public void RemoveOst()
	{
		ost = null;
	}

	protected void SetAttackLocations() {
		final int x = GetX();
		final int y = GetY();
		for (int i = 0; i < Settings.NB_ATTACK_LOCATIONS; i++) {
			final int j = i % Settings.ATTACK_LOCATIONS_PER_SIDE;	// 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2
			switch (i / Settings.ATTACK_LOCATIONS_PER_SIDE)	// 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3
			{
			// North
			case 0: attackLocations.push(new Point2D(x + (Settings.THIRD_OF_CASTLE * j), y - 10 - Settings.SOLDIER_SIZE)); break;
			// East
			case 1: attackLocations.push(new Point2D(x + Settings.CASTLE_SIZE + 10, y + (Settings.THIRD_OF_CASTLE * j))); break;
			// South
			case 2: attackLocations.push(new Point2D(x + (Settings.THIRD_OF_CASTLE * j), y + Settings.CASTLE_SIZE + 10)); break;
			// West
			case 3: attackLocations.push(new Point2D(x - 10 - Settings.SOLDIER_SIZE, y + (Settings.THIRD_OF_CASTLE * j))); break;
			}
		}
	}

	public boolean IsAvailableAttackLocation()
	{
		return !attackLocations.empty();
	}

	public Point2D GetNextAttackLocation()
	{
		return attackLocations.pop();
	}

	public void FreeAttackLocation(final Point2D FreedAttackLocation)
	{
		attackLocations.push(FreedAttackLocation);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	@Override
	public int GetProductionCost()
	{
		return Settings.LEVEL_UP_COST_FACTOR * level;
	}

	public double GetTotalFlorin() {
		return totalFlorin;
	}

	public Actor GetActor() {
		return actor;
	}

	public void SetDuke(final Actor actor) {
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

	public void SetOst(final Ost ost)
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

	public double GetProductionTimeRemaining()
	{
		return productionTime;
	}
}
