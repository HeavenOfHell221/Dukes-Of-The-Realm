package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Random;
import java.util.Stack;

import Duke.Actor;
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

/**
 * 
 *
 */
public class Castle extends Sprite implements Serializable
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * 
	 *
	 */
	public enum Orientation
	{
		North, South, West, East, NE, // North East
		NW, // North West
		SE, // South East
		SW, // South West
		None;
	}

	/**
	 * 
	 */
	private double totalFlorin; // L'argent que contient le chateau
	
	/**
	 * 
	 */
	private int level; // Le niveau du chateau
	
	/**
	 * 
	 */
	private ReserveOfSoldiers reserveOfSoldiers; // La reserve de soldat du chateau. Contient des Piker, des Onager et des Knight
	
	/**
	 * 
	 */
	private ArrayDeque<IProductionUnit> productionUnit; // L'unite de production. C'est une amelioration ou un
														// soldat en
														// cours de production
	
	/**
	 * 
	 */
	private double productionTime; // Le temps restant a la production de l'unite de production
	
	/**
	 * 
	 */
	private Ost ost;
	
	/**
	 * 
	 */
	private Orientation orientation;
	
	/**
	 * 
	 */
	private transient Color myColor;
	
	/**
	 * 
	 */
	private transient Rectangle door;
	
	/**
	 * 
	 */
	private Stack<Point2D> attackLocations;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur Castle.
	 * @param level
	 */
	public Castle(final int level)
	{
		super();
		this.level = level;
	}

	/**
	 * Constructeur Castle.
	 */
	public Castle()
	{
		super();
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	/**
	 * 
	 * @param level
	 * @param pane
	 * @param coord
	 */
	public void start(final int level, final Pane pane, final Point2D coord)
	{
		this.coordinate = coord;
		this.level = level;
		this.totalFlorin = 0;
		this.productionUnit = null;
		this.productionTime = 0;
		this.reserveOfSoldiers = new ReserveOfSoldiers();
		this.ost = null;
		this.productionUnit = new ArrayDeque<>();
		this.attackLocations = new Stack<>();
		this.orientation = setOrientation();
		startTransient(pane);
		setAttackLocations();
	}

	/**
	 * 
	 * @param pane
	 */
	public void startTransient(final Pane pane)
	{
		this.canvas = pane;
		addRepresentation(pane);
		if (this.ost != null)
		{
			this.ost.startTransient(this.myColor);
		}
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	/**
	 * 
	 */
	public void updateProduction()
	{
		if (this.productionUnit.size() > 0)
		{
			this.productionTime -= (1 * Time.deltaTime);

			final double ratio = 1 - (this.productionTime / this.productionUnit.getFirst().getProductionTime());
			UIManager.getProductionUnitPreview().setFill(ratio);

			if (this.productionTime <= 0)
			{
				final IProductionUnit p = this.productionUnit.pollFirst();

				if (p.getClass() == Castle.class)
				{
					levelUp();
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

	/**
	 * 
	 * @param now
	 * @param pause
	 */
	public void updateOst(final long now, final boolean pause)
	{
		if (this.ost != null)
		{
			this.ost.update(now, pause);
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * 
	 */
	public void randomSoldier()
	{
		final Random rand = new Random();
		this.reserveOfSoldiers.setNbKnights(rand.nextInt(5) * this.level);
		this.reserveOfSoldiers.setNbPikers(rand.nextInt(10) * this.level);
		this.reserveOfSoldiers.setNbOnagers(rand.nextInt(5) + this.level);
	}

	/**
	 * 
	 */
	public void startSoldier()
	{
		this.reserveOfSoldiers.setNbKnights(Settings.STARTER_KNIGHT);
		this.reserveOfSoldiers.setNbPikers(Settings.STARTER_PIKER);
		this.reserveOfSoldiers.setNbOnagers(Settings.STARTER_ONAGER);
	}

	/**
	 * 
	 * @return
	 */
	private Orientation setOrientation()
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

	/**
	 * 
	 * @param pane
	 */
	public void addRepresentation(final Pane pane)
	{
		addCastleRepresentation(pane, Settings.CASTLE_SIZE);
		addDoorRepresentation(pane);
		this.shape.setFill(this.myColor);
	}

	/**
	 * 
	 * @param pane
	 */
	private void addDoorRepresentation(final Pane pane)
	{
		switch (this.orientation)
		{
			case North:
				this.door = new Rectangle(getX() + Settings.CASTLE_SIZE / 4, getY(), Settings.CASTLE_SIZE / 2, Settings.CASTLE_SIZE / 6);
				break;
			case South:
				this.door = new Rectangle(getX() + Settings.CASTLE_SIZE / 4, getY() + Settings.CASTLE_SIZE - Settings.CASTLE_SIZE / 6 + 1,
						Settings.CASTLE_SIZE / 2, Settings.CASTLE_SIZE / 6);
				break;
			case East:
				this.door = new Rectangle(getX() + Settings.CASTLE_SIZE - Settings.CASTLE_SIZE / 6 + 1, getY() + Settings.CASTLE_SIZE / 4,
						Settings.CASTLE_SIZE / 6, Settings.CASTLE_SIZE / 2);
				break;
			case West:
				this.door = new Rectangle(getX(), getY() + Settings.CASTLE_SIZE / 4, Settings.CASTLE_SIZE / 6, Settings.CASTLE_SIZE / 2);
				break;
			default:
				this.door = new Rectangle(0, 0, 0, 0);
				break;
		}
		this.door.setMouseTransparent(true);
		pane.getChildren().add(this.door);
	}

	/**
	 * 
	 */
	public void levelUp()
	{
		this.level++;
	}

	/**
	 * 
	 * @param amount
	 */
	public void addFlorin(final double amount)
	{
		this.totalFlorin += amount;
	}

	/**
	 * 
	 * @param amount
	 * @return Si les Florins ont ete retire ou non.
	 */
	public boolean removeFlorin(final double amount)
	{
		if (enoughOfFlorin(amount))
		{
			this.totalFlorin -= amount;
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param amount
	 * @return Si il y a assez de Florin
	 */
	public boolean enoughOfFlorin(final double amount)
	{
		return (amount <= this.totalFlorin);
	}

	/**
	 * 
	 * @param newProduction
	 * @return
	 */
	public boolean addProduction(final IProductionUnit newProduction)
	{
		if (newProduction == null || !removeFlorin(newProduction.getProductionCost()))
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

	/**
	 * 
	 * @param destination
	 * @param nbPikers
	 * @param nbKnights
	 * @param nbOnagers
	 * @return
	 */
	public boolean createOst(final Castle destination, final int nbPikers, final int nbKnights, final int nbOnagers, final Actor originActor, final Actor destinationActor)
	{
		if (this.ost == null)
		{
			this.ost = new Ost(this, destination, nbPikers, nbKnights, nbOnagers, this.myColor, originActor, destinationActor);
			this.ost.start();
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public void removeOst()
	{
		this.ost = null;
	}
	
	public void switchColor(final Color color)
	{
		setColor(color);
		setColorShape(this.myColor);
	}

	/**
	 * 
	 */
	protected void setAttackLocations()
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

	/**
	 * 
	 * @return
	 */
	public boolean isAvailableAttackLocation()
	{
		return !this.attackLocations.empty();
	}

	/**
	 * 
	 * @return
	 */
	public Point2D getNextAttackLocation()
	{
		return this.attackLocations.pop();
	}

	/**
	 * 
	 * @param FreedAttackLocation
	 */
	public void freeAttackLocation(final Point2D FreedAttackLocation)
	{
		this.attackLocations.push(FreedAttackLocation);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * 
	 */
	@Override
	public int getProductionCost()
	{
		return Settings.LEVEL_UP_COST_FACTOR * this.level;
	}

	/**
	 * 
	 * @return
	 */
	public double getTotalFlorin()
	{
		return this.totalFlorin;
	}

	/**
	 * 
	 */
	@Override
	public int getProductionTime()
	{
		return Settings.LEVEL_UP_DURATION_OFFSET + Settings.LEVEL_UP_DURATION_FACTOR * this.level;
	}

	/**
	 * 
	 * @return
	 */
	public int getLevel()
	{
		return this.level;
	}

	/**
	 * 
	 * @return
	 */
	public ReserveOfSoldiers getReserveOfSoldiers()
	{
		return this.reserveOfSoldiers;
	}

	/**
	 * 
	 * @return
	 */
	public Rectangle getDoor()
	{
		return this.door;
	}

	/**
	 * 
	 * @return
	 */
	public Orientation getOrientation()
	{
		return this.orientation;
	}

	/**
	 * 
	 * @return
	 */
	public Color getMyColor()
	{
		return this.myColor;
	}

	/**
	 * 
	 * @return
	 */
	public Ost getOst()
	{
		return this.ost;
	}

	/**
	 * 
	 * @param ost
	 */
	public void setOst(final Ost ost)
	{
		this.ost = ost;
	}

	/**
	 * 
	 * @return
	 */
	public Stack<Point2D> getAttackLocations()
	{
		return this.attackLocations;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayDeque<IProductionUnit> getProductionUnit()
	{
		return this.productionUnit;
	}

	/**
	 * 
	 * @return
	 */
	public double getProductionTimeRemaining()
	{
		return this.productionTime;
	}

	/**
	 * 
	 * @param level
	 */
	public void setLevel(final int level)
	{
		this.level = level;
	}

	/**
	 * 
	 * @param color
	 */
	public void setColor(final Color color)
	{
		this.myColor = color;
	}

	/**
	 * @return
	 * @see DukesOfTheRealm.ReserveOfSoldiers#getNbPikers()
	 */
	public int getNbPikers()
	{
		return reserveOfSoldiers.getNbPikers();
	}

	/**
	 * @return
	 * @see DukesOfTheRealm.ReserveOfSoldiers#getNbKnights()
	 */
	public int getNbKnights()
	{
		return reserveOfSoldiers.getNbKnights();
	}

	/**
	 * @return
	 * @see DukesOfTheRealm.ReserveOfSoldiers#getNbOnagers()
	 */
	public int getNbOnagers()
	{
		return reserveOfSoldiers.getNbOnagers();
	}

	@Override
	public String toString()
	{
		return "Castle [totalFlorin=" + (int)totalFlorin + ", level=" + level + ", orientation=" + orientation + ", myColor=" + myColor + "]";
	}
}
