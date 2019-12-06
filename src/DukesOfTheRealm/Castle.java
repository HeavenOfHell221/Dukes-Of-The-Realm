package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Random;
import java.util.Stack;

import Duke.Actor;
import Duke.Baron;
import Interface.IProductionUnit;
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

public class Castle extends Sprite implements Serializable
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	public enum Orientation
	{
		North, South, West, East, NE, // North East
		NW, // North West
		SE, // South East
		SW, // South West
		None;
	}

	private double totalFlorin; // L'argent que contient le chateau
	private int level; // Le niveau du chateau
	private ReserveOfSoldiers reserveOfSoldiers; // La reserve de soldat du chateau. Contient des Piker, des
													// Onager et
													// des Knight
	private Actor actor; // Le proprietaire du chateau
	private ArrayDeque<IProductionUnit> productionUnit; // L'unite de production. C'est une amelioration ou un
														// soldat en
														// cours de production
	private double productionTime; // Le temps restant a la production de l'unite de production
	private Ost ost;
	private Orientation orientation;
	private transient Color myColor;
	private transient Rectangle door;
	private Stack<Point2D> attackLocations;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	Castle(final Pane layer, final Point2D coord, final int level, final Actor actor)
	{
		super(layer, coord);
		this.level = level;
		this.totalFlorin = 0;
		this.actor = actor;
		this.productionUnit = null;
		this.productionTime = 0;
		this.reserveOfSoldiers = new ReserveOfSoldiers();
		this.ost = null;
		this.productionUnit = new ArrayDeque<>();
		this.myColor = actor.getMyColor();
		this.attackLocations = new Stack<>();
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
	public void start()
	{
		this.actor.addCastle(this);

		if (this.actor.getClass() == Baron.class)
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
	public void update(final long now, final boolean pause)
	{
		if (!pause)
		{
			UpdateFlorin();
			UpdateProduction();
		}
		if (this.ost != null)
		{
			UpdateOst(now, pause);
		}
	}

	private void UpdateFlorin()
	{
		if (this.actor.getClass() != Baron.class)
		{
			AddFlorin(Settings.FLORIN_PER_SECOND * this.level * Time.deltaTime);
		}
		else
		{
			AddFlorin(Settings.FLORIN_PER_SECOND * this.level * Time.deltaTime * Settings.FLORIN_FACTOR_BARON);
		}
	}

	private void UpdateProduction()
	{
		if (this.productionUnit.size() > 0)
		{
			this.productionTime -= (1 * Time.deltaTime);

			final double ratio = 1 - (this.productionTime / this.productionUnit.getFirst().getProductionTime());
			UIManager.GetProductionUnitPreview().SetFill(ratio);

			if (this.productionTime <= 0)
			{
				final IProductionUnit p = this.productionUnit.pollFirst();

				if (p.getClass() == Castle.class)
				{
					LevelUp();
				}
				else if (p.getClass() == Piker.class)
				{
					this.reserveOfSoldiers.addPiker();
				}
				else if (p.getClass() == Onager.class)
				{
					this.reserveOfSoldiers.addOnager();
				}
				else if (p.getClass() == Knight.class)
				{
					this.reserveOfSoldiers.addKnight();
				}

				if (this.productionUnit.size() > 0)
				{
					this.productionTime = this.productionUnit.getFirst().getProductionTime();
				}
			}
		}
	}

	private void UpdateOst(final long now, final boolean pause)
	{
		this.ost.update(now, pause);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	private void RandomSoldier()
	{
		final Random rand = new Random();
		this.reserveOfSoldiers.setNbKnights(rand.nextInt(5) * this.level);
		this.reserveOfSoldiers.setNbPikers(rand.nextInt(10) * this.level);
		this.reserveOfSoldiers.setNbOnagers(rand.nextInt(5) + this.level);
	}

	private void StartSoldier()
	{
		this.reserveOfSoldiers.setNbKnights(Settings.STARTER_KNIGHT);
		this.reserveOfSoldiers.setNbPikers(Settings.STARTER_PIKER);
		this.reserveOfSoldiers.setNbOnagers(Settings.STARTER_ONAGER);
	}

	private void RandomFlorin()
	{
		final Random rand = new Random();
		this.totalFlorin += (rand.nextInt(1000) * this.level);
	}

	private Orientation SetOrientation()
	{
		final Random rand = new Random();
		Orientation orientation;

		switch (rand.nextInt(4))
		{
			case 0:
				orientation = Orientation.North;
				break;
			case 1:
				orientation = Orientation.South;
				break;
			case 2:
				orientation = Orientation.West;
				break;
			case 3:
				orientation = Orientation.East;
				break;
			default:
				orientation = Orientation.None;
				break;
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

		switch (this.orientation)
		{
			case North:
				this.door = new Rectangle(getX() + Settings.CASTLE_SIZE / 4, getY(), Settings.CASTLE_SIZE / 2, Settings.CASTLE_SIZE / 6);
				break;
			case South:
				this.door = new Rectangle(getX() + Settings.CASTLE_SIZE / 4, getY() + Settings.CASTLE_SIZE - Settings.CASTLE_SIZE / 6,
						Settings.CASTLE_SIZE / 2, Settings.CASTLE_SIZE / 6);
				break;
			case East:
				this.door = new Rectangle(getX() + +Settings.CASTLE_SIZE - Settings.CASTLE_SIZE / 6, getY() + Settings.CASTLE_SIZE / 4,
						Settings.CASTLE_SIZE / 6, Settings.CASTLE_SIZE / 2);
				break;
			case West:
				this.door = new Rectangle(getX(), getY() + +Settings.CASTLE_SIZE / 4, Settings.CASTLE_SIZE / 6, Settings.CASTLE_SIZE / 2);
				break;
			default:
				this.door = new Rectangle(0, 0, 0, 0);
				break;
		}
		this.door.setMouseTransparent(true);
	}

	/* Augmente le chateau d'un niveau */
	public void LevelUp()
	{
		this.level++;
	}

	public void AddFlorin(final double amount)
	{
		this.totalFlorin += amount;
	}

	public boolean RemoveFlorin(final double amount)
	{
		if (EnoughOfFlorin(amount))
		{
			this.totalFlorin -= amount;
			return true;
		}
		return false;
	}

	public boolean EnoughOfFlorin(final double amount)
	{
		return (amount <= this.totalFlorin);
	}

	public boolean AddProduction(final IProductionUnit newProduction)
	{
		if (newProduction == null || !RemoveFlorin(newProduction.getProductionCost()))
		{
			return false;
		}

		this.productionUnit.addLast(newProduction);

		if (this.productionUnit.size() == 1)
		{
			this.productionTime = newProduction.getProductionTime();
		}

		return true;
	}

	public boolean CreateOst(final Castle destination, final int nbPikers, final int nbKnights, final int nbOnagers)
	{
		if (this.ost == null)
		{
			this.ost = new Ost(this, destination, nbPikers, nbKnights, nbOnagers, this.myColor);
			this.ost.start();
			return true;
		}
		return false;
	}

	public void RemoveOst()
	{
		this.ost = null;
	}

	protected void SetAttackLocations()
	{
		final int x = getX();
		final int y = getY();
		for (int i = 0; i < Settings.NB_ATTACK_LOCATIONS; i++)
		{
			final int j = i % Settings.ATTACK_LOCATIONS_PER_SIDE; // 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2
			switch (i / Settings.ATTACK_LOCATIONS_PER_SIDE) // 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3
			{
				// North
				case 0:
					this.attackLocations.push(new Point2D(x + (Settings.THIRD_OF_CASTLE * j), y - 10 - Settings.SOLDIER_SIZE));
					break;
				// East
				case 1:
					this.attackLocations.push(new Point2D(x + Settings.CASTLE_SIZE + 10, y + (Settings.THIRD_OF_CASTLE * j)));
					break;
				// South
				case 2:
					this.attackLocations.push(new Point2D(x + (Settings.THIRD_OF_CASTLE * j), y + Settings.CASTLE_SIZE + 10));
					break;
				// West
				case 3:
					this.attackLocations.push(new Point2D(x - 10 - Settings.SOLDIER_SIZE, y + (Settings.THIRD_OF_CASTLE * j)));
					break;
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

	public void FreeAttackLocation(final Point2D FreedAttackLocation)
	{
		this.attackLocations.push(FreedAttackLocation);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	@Override
	public int getProductionCost()
	{
		return Settings.LEVEL_UP_COST_FACTOR * this.level;
	}

	public double GetTotalFlorin()
	{
		return this.totalFlorin;
	}

	public Actor GetActor()
	{
		return this.actor;
	}

	public void SetDuke(final Actor actor)
	{
		this.actor = actor;
	}

	@Override
	public int getProductionTime()
	{
		return Settings.LEVEL_UP_DURATION_OFFSET + Settings.LEVEL_UP_DURATION_FACTOR * this.level;
	}

	public int GetLevel()
	{
		return this.level;
	}

	public ReserveOfSoldiers GetReserveOfSoldiers()
	{
		return this.reserveOfSoldiers;
	}

	public Rectangle GetDoor()
	{
		return this.door;
	}

	public Orientation GetOrientation()
	{
		return this.orientation;
	}

	public Color GetMyColor()
	{
		return this.myColor;
	}

	public Ost GetOst()
	{
		return this.ost;
	}

	public void SetOst(final Ost ost)
	{
		this.ost = ost;
	}

	public Stack<Point2D> GetAttackLocations()
	{
		return this.attackLocations;
	}

	public ArrayDeque<IProductionUnit> GetProductionUnit()
	{
		return this.productionUnit;
	}

	public double GetProductionTimeRemaining()
	{
		return this.productionTime;
	}
}
