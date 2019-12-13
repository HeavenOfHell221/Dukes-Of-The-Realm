package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Random;
import java.util.Stack;

import Duke.Actor;
import Interface.IProductionUnit;
import Utility.Point2D;
import Utility.Settings;
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
	private Caserne caserne;

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

	/**
	 *
	 */
	private int nbOstsarriving = 0;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur Castle.
	 * 
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
		this.reserveOfSoldiers = new ReserveOfSoldiers();
		this.caserne = new Caserne(this);
		this.ost = null;
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
		this.caserne.updateProduction();
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
	 * @param  amount
	 * @return        Si les Florins ont ete retire ou non.
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
	 * @param  amount
	 * @return        Si il y a assez de Florin
	 */
	public boolean enoughOfFlorin(final double amount)
	{
		return (amount <= this.totalFlorin);
	}

	/**
	 *
	 * @param  destination
	 * @param  nbPikers
	 * @param  nbKnights
	 * @param  nbOnagers
	 * @return
	 */
	public boolean createOst(final Castle destination, final int nbPikers, final int nbKnights, final int nbOnagers,
			final Actor originActor, final Actor destinationActor)
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
	public double getProductionTime()
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
	 * @see DukesOfTheRealm.ReserveOfSoldiers#addPiker()
	 */
	public void addPiker()
	{
		this.reserveOfSoldiers.addPiker();
	}

	/**
	 *
	 * @see DukesOfTheRealm.ReserveOfSoldiers#addKnight()
	 */
	public void addKnight()
	{
		this.reserveOfSoldiers.addKnight();
	}

	/**
	 *
	 * @see DukesOfTheRealm.ReserveOfSoldiers#addOnager()
	 */
	public void addOnager()
	{
		this.reserveOfSoldiers.addOnager();
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
	public boolean isOstExist()
	{
		return this.ost == null;
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
		return this.caserne.getProductionUnit();
	}

	/**
	 *
	 * @return
	 */
	public double getProductionTimeRemaining()
	{
		return this.caserne.getProductionTime();
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
	 * @see    DukesOfTheRealm.ReserveOfSoldiers#getNbPikers()
	 */
	public int getNbPikers()
	{
		return this.reserveOfSoldiers.getNbPikers();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.ReserveOfSoldiers#getNbKnights()
	 */
	public int getNbKnights()
	{
		return this.reserveOfSoldiers.getNbKnights();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.ReserveOfSoldiers#getNbOnagers()
	 */
	public int getNbOnagers()
	{
		return this.reserveOfSoldiers.getNbOnagers();
	}

	
	
	/**
	 * 
	 * @see DukesOfTheRealm.Caserne#removeLastProduction()
	 */
	public void removeLastProduction()
	{
		caserne.removeLastProduction();
	}

	/**
	 * @param refundFlorin
	 * @see DukesOfTheRealm.Caserne#resetQueue(boolean)
	 */
	public void resetQueue(boolean refundFlorin)
	{
		caserne.resetQueue(refundFlorin);
	}

	/**
	 * @param newProduction
	 * @return
	 * @see DukesOfTheRealm.Caserne#addProduction(Interface.IProductionUnit)
	 */
	public boolean addProduction(IProductionUnit newProduction)
	{
		return caserne.addProduction(newProduction);
	}

	/**
	 * @return
	 * @see DukesOfTheRealm.Caserne#getRatio()
	 */
	public final double getRatio()
	{
		return caserne.getRatio();
	}

	/**
	 * @return the nbOstsarriving
	 */
	public final int getNbOstsarriving()
	{
		return this.nbOstsarriving;
	}

	/**
	 *
	 */
	public final void addNbOstsarriving()
	{
		this.nbOstsarriving += 1;
	}

	/**
	 *
	 */
	public final void removeNbOstsarriving()
	{
		this.nbOstsarriving -= 1;
	}

	/**
	 * @param x
	 * @see     DukesOfTheRealm.ReserveOfSoldiers#randomRemoveHP(int)
	 */
	public void randomRemoveHP(final int x)
	{
		this.reserveOfSoldiers.randomRemoveHP(x);
	}

	/**
	 * @param nbPikers
	 * @param nbKnights
	 * @param nbOnagers
	 * @see             DukesOfTheRealm.ReserveOfSoldiers#removeSoldiers(int, int, int)
	 */
	public void removeSoldiers(final int nbPikers, final int nbKnights, final int nbOnagers)
	{
		this.reserveOfSoldiers.removeSoldiers(nbPikers, nbKnights, nbOnagers);
	}

	/**
	 *
	 * @see DukesOfTheRealm.ReserveOfSoldiers#reactivateAttack()
	 */
	public void reactivateAttack()
	{
		this.reserveOfSoldiers.reactivateAttack();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.ReserveOfSoldiers#isStopAttack()
	 */
	public boolean isStopAttack()
	{
		return this.reserveOfSoldiers.isStopAttack();
	}

	@Override
	public String toString()
	{
		return "Castle [totalFlorin=" + (int) this.totalFlorin + ", level=" + this.level + ", orientation=" + this.orientation
				+ ", myColor=" + this.myColor + "]";
	}
}
