package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Random;
import java.util.Stack;

import Duke.Actor;
import Enum.SoldierEnum;
import Interface.IProductionUnit;
import Utility.Point2D;
import static Utility.Settings.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Représente un château.
 * <p>Extends de la classe Sprite.</p>
 * @see Sprite
 */
public class Castle extends Sprite implements Serializable
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Enum contenant les différentes orientations pour les portes.
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
	 * Le nombre total de Florin du château.
	 */
	private double totalFlorin;

	/**
	 * Le niveau du château.
	 */
	private int level = 1;

	/**
	 * La réserve d'unité du château. 
	 * @see ReserveOfSoldiers
	 */
	private ReserveOfSoldiers reserveOfSoldiers; 

	/**
	 * La caserne du château.
	 * @see Caserne
	 */
	private Caserne caserne;

	/**
	 * L'ost du château.
	 * @see Ost
	 */
	private Ost ost;

	/**
	 * L'orientation de la porte pour ce château.
	 * @see Orientation
	 */
	private Orientation orientation;

	/**
	 * La couleur du château. Cette couleur vient de l'acteur à qui appartient ce château.
	 */
	private transient Color myColor;

	/**
	 * La représentation graphique de la porte du château.
	 */
	private transient Rectangle door;

	/**
	 * Les points où les unités peuvent se placer pour attaquer ce château.
	 * @see Castle#setAttackLocations()
	 * @see Castle#freeAttackLocation(Point2D)
	 * @see Ost
	 */
	private Stack<Point2D> attackLocations;

	/**
	 * L'acteur à qui appartient ce château.
	 * @see Actor
	 */
	private Actor actor;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur Castle.
	 *
	 * @param level Le niveau de ce château.
	 */
	public Castle(final int level)
	{
		super();
		this.level = level;
	}

	/**
	 * Constructeur par défaut Castle.
	 */
	public Castle()
	{
		super();
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	/**
	 * Initalise ce château.
	 * @param level Le niveau de ce château
	 * @param pane Le pane pour afficher la réprésentation graphique de ce château.
	 * @param coord Les coordonnées de ce château.
	 * @param actor L'acteur à qui appartient ce château.
	 * @see Kingdom#createWorld()
	 */
	public void start(final int level, final Pane pane, final Point2D coord, final Actor actor)
	{
		this.actor = actor;
		this.coordinate = coord;
		this.level = level;
		this.totalFlorin = 0;
		this.reserveOfSoldiers = new ReserveOfSoldiers();
		this.caserne = new Caserne(this);
		this.ost = null;
		this.attackLocations = new Stack<>();
		//this.orientation = setOrientation();
		this.orientation = Orientation.North;
		startTransient(pane);
		setAttackLocations();
	}

	/**
	 * Initalise les éléments transient de ce château.
	 * @param pane Le pane pour afficher la réprésentation graphique de ce château. 
	 * @see Kingdom#startTransient(Pane)
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
	 * Met à jour la caserne.
	 * @see Caserne#updateProduction()
	 */
	public void updateProduction()
	{
		this.caserne.updateProduction();
	}

	/**
	 * Met à jour l'ost si ce château en a une.
	 * @param now Le temps actuel.
	 * @param pause Boolean représentant si la pause est activé ou non.
	 * @see Ost#update(long, boolean)
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
	 * Donne à un Baron un nombre aléatoire d'unités proportionnel au niveau de ce château.
	 * @see Duke.Baron#addFirstCastle(Castle)
	 * @see ReserveOfSoldiers
	 */
	public void randomSoldier()
	{
		final Random rand = new Random();
		final int levelSq = this.level * this.level; 
		this.reserveOfSoldiers.setNbKnights(rand.nextInt(levelSq) + rand.nextInt(6) * this.level);
		this.reserveOfSoldiers.setNbPikers(rand.nextInt(levelSq) + rand.nextInt(5) * this.level);
		this.reserveOfSoldiers.setNbOnagers(rand.nextInt(levelSq) + rand.nextInt(4) * this.level);
	}

	/**
	 * Donne un nombre fini d'unités pour le joueur et les IA.
	 * @see ReserveOfSoldiers
	 * @see Utility.Settings#STARTER_KNIGHT
	 * @see Utility.Settings#STARTER_ONAGER
	 * @see Utility.Settings#STARTER_PIKER
	 */
	public void startSoldier()
	{
		this.reserveOfSoldiers.setNbKnights(STARTER_KNIGHT);
		this.reserveOfSoldiers.setNbPikers(STARTER_PIKER);
		this.reserveOfSoldiers.setNbOnagers(STARTER_ONAGER);
	}

	/**
	 * Prend au hasard l'une des 4 orientations de base pour la porte.
	 * @return Retourne l'orientation de la porte.
	 * @see Castle#start(int, Pane, Point2D, Actor)
	 */
	private Orientation setOrientation()
	{
		final Random rand = new Random();
		Orientation orientation = Orientation.None;

		switch (rand.nextInt(4)) // Possibilité : 0, 1, 2, 3
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
				break;
		}
		return orientation;
	}

	/**
	 * Initialise et affiche la représentation graphique du château aisni que de la porte.
	 * @param pane Le pane principale.
	 * @see Sprite#addCastleRepresentation(Pane)
	 * @see DukesOfTheRealm.Sprite#addDoorRepresentation(Pane, Orientation)
	 */
	public void addRepresentation(final Pane pane)
	{
		addCastleRepresentation(pane);
		this.door = addDoorRepresentation(pane, this.orientation);
		this.shape.setFill(this.myColor);
	}

	/**
	 * Augmente de 1 le niveau de ce château.
	 * @see Castle#level
	 */
	public void levelUp()
	{
		this.level++;
	}

	/**
	 * Ajoute des Florin au total de ce château.
	 * @param amount Le nombre de Florin à ajouter.
	 * @see Castle#totalFlorin
	 */
	public void addFlorin(final double amount)
	{
		this.totalFlorin += amount;
	}

	/**
	 * Retire le nombre de Florin en paramètre si cela est possible.
	 * @param amount Le nombre de Florin à retirer.
	 * @return Retourne true si les Florin on été retirés et false sinon.
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
	 * Teste si ce château à au moins un certain nombre de Florin.
	 * @param  amount Le nombre de Florin qu'on teste.
	 * @return Retourne true si le château à au moins le nombre de Florin donné en paramètre, false sinon.
	 * @see Castle#removeFlorin(double)
	 */
	public boolean enoughOfFlorin(final double amount)
	{
		return amount <= this.totalFlorin;
	}

	/**
	 * Crée une ost avec un château destination et un nombre d'unités.
	 * <p>
	 * L'ost serra crée si ce château n'en contient pas déjà une, si la destination n'est pas ce château et que la réserve contient <br>
	 * le nombre d'unités nécessaire.
	 * </p>
	 * @param  destination Le château destination de l'ost.
	 * @param  nbPikers Le nombre de Piker de l'ost.
	 * @param  nbKnights Le nombre de Knight de l'ost.
	 * @param  nbOnagers Le nombre d'Onager de l'ost.
	 * @param isBackup Est ce que c'est une attaque ou des renforts.
	 * @return Retourne true si l'ost a été crée, false sinon.
	 * @see Ost
	 * @see Ost#start()
	 * @see ReserveOfSoldiers#removeSoldiers(int, int, int)
	 */
	public boolean createOst(final Castle destination, final int nbPikers, final int nbKnights, final int nbOnagers, final boolean isBackup)
	{
		if (this.ost == null)
		{
			if (removeSoldiers(nbPikers, nbKnights, nbOnagers) && this != destination)
			{
				this.ost = new Ost(this, destination, nbPikers, nbKnights, nbOnagers, this.myColor, isBackup);
				this.ost.start();
				return true;
			}
		}
		return false;
	}

	/**
	 * Force ce château à ne plus avoir d'ost.
	 * @see Ost#update(long, boolean)
	 */
	public void removeOst()
	{
		if(this.ost != null && this.ost.getSoldiers().size() == 0) 
		{
			this.ost = null;
		}
	}

	/**
	 * Change la couleur de ce château.
	 * <p>
	 * Utilisé quand le château change de propriétaire.
	 * </p>
	 * @param color La nouvelle couleur.
	 */
	public void switchColor(final Color color)
	{
		setColor(color);
		setColorShape(this.myColor);
	}

	/**
	 * Crée les points d'attaque que prendront les unités qui attaqueront ce château.
	 */
	protected void setAttackLocations()
	{
		final int x = getX();
		final int y = getY();
		final double offset = (THIRD_OF_CASTLE - SOLDIER_SIZE) / 2;
		for (int i = 0; i < NB_ATTACK_LOCATIONS; i++)
		{
			final int j = i % ATTACK_LOCATIONS_PER_SIDE; // 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2
			switch (i / ATTACK_LOCATIONS_PER_SIDE) // 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3
			{
				// North
				case 0:
					this.attackLocations.push(new Point2D(x + THIRD_OF_CASTLE * j + offset, y - GAP_WITH_SOLDIER - SOLDIER_SIZE));
					break;
				// East
				case 1:
					this.attackLocations.push(new Point2D(x + CASTLE_SIZE + GAP_WITH_SOLDIER, y + THIRD_OF_CASTLE * j + offset));
					break;
				// South
				case 2:
					this.attackLocations.push(new Point2D(x + THIRD_OF_CASTLE * j + offset, y + CASTLE_SIZE + GAP_WITH_SOLDIER));
					break;
				// West
				case 3:
					this.attackLocations.push(new Point2D(x - GAP_WITH_SOLDIER - SOLDIER_SIZE, y + THIRD_OF_CASTLE * j + offset));
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
	/************** DELEGATES METHODS ****************/
	/*************************************************/
	
	/**
	 * @return
	 * @see    DukesOfTheRealm.Caserne#getNbPikersInProduction()
	 */
	public final int getNbPikersInProduction()
	{
		return this.caserne.getNbPikersInProduction();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.Caserne#getNbOnagersInProduction()
	 */
	public final int getNbOnagersInProduction()
	{
		return this.caserne.getNbOnagersInProduction();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.Caserne#getNbKnightsInProduction()
	 */
	public final int getNbKnightsInProduction()
	{
		return this.caserne.getNbKnightsInProduction();
	}

	/**
	 * @param nbPikersInProduction
	 * @see                        DukesOfTheRealm.Caserne#setNbPikersInProduction(int)
	 */
	public final void setNbPikersInProduction(final int nbPikersInProduction)
	{
		this.caserne.setNbPikersInProduction(nbPikersInProduction);
	}

	/**
	 * @param nbOnagersInProduction
	 * @see                         DukesOfTheRealm.Caserne#setNbOnagersInProduction(int)
	 */
	public final void setNbOnagersInProduction(final int nbOnagersInProduction)
	{
		this.caserne.setNbOnagersInProduction(nbOnagersInProduction);
	}

	/**
	 * @param nbKnightsInProduction
	 * @see                         DukesOfTheRealm.Caserne#setNbKnightsInProduction(int)
	 */
	public final void setNbKnightsInProduction(final int nbKnightsInProduction)
	{
		this.caserne.setNbKnightsInProduction(nbKnightsInProduction);
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
	public void removeLastProduction(final boolean refoundFlorin)
	{
		this.caserne.removeLastProduction(refoundFlorin);
	}

	/**
	 * @param refundFlorin
	 * @see                DukesOfTheRealm.Caserne#resetQueue(boolean)
	 */
	public void resetQueue(final boolean refundFlorin)
	{
		this.caserne.resetQueue(refundFlorin);
	}

	/**
	 * @param  newProduction
	 * @return
	 * @see                  DukesOfTheRealm.Caserne#addProduction(Interface.IProductionUnit)
	 */
	public boolean addProduction(final IProductionUnit newProduction)
	{
		return this.caserne.addProduction(newProduction);
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.Caserne#getRatio()
	 */
	public final double getRatio()
	{
		return this.caserne.getRatio();
	}

	/**
	 * @param x
	 * @see     DukesOfTheRealm.ReserveOfSoldiers#randomRemoveHP(int)
	 */
	public void randomRemoveHP()
	{
		this.reserveOfSoldiers.randomRemoveHP(SoldierEnum.getRandomType());
	}

	/**
	 * @param  nbPikers
	 * @param  nbKnights
	 * @param  nbOnagers
	 * @return
	 * @see              DukesOfTheRealm.ReserveOfSoldiers#removeSoldiers(int, int, int)
	 */
	public boolean removeSoldiers(final int nbPikers, final int nbKnights, final int nbOnagers)
	{
		return this.reserveOfSoldiers.removeSoldiers(nbPikers, nbKnights, nbOnagers);
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

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * Calcul le coût de production de ce château en fonction de son niveau.
	 * @return Retourne son coût de production.
	 */
	@Override
	public int getProductionCost()
	{
		return LEVEL_UP_COST_FACTOR * this.level;
	}

	/**
	 * @return the totalFlorin
	 */
	public final double getTotalFlorin()
	{
		return totalFlorin;
	}

	/**
	 * Calcul le temps de production de ce château en fonction de son niveau.
	 * @return Retourne son temps de production.
	 */
	@Override
	public double getProductionTime()
	{
		return LEVEL_UP_DURATION_OFFSET + LEVEL_UP_DURATION_FACTOR * this.level;
	}

	/**
	 * @return the level
	 */
	public final int getLevel()
	{
		return level;
	}

	/**
	 * @return the orientation
	 */
	public final Orientation getOrientation()
	{
		return orientation;
	}

	/**
	 * @return the myColor
	 */
	public final Color getMyColor()
	{
		return myColor;
	}

	/**
	 * @return the attackLocations
	 */
	public Stack<Point2D> getAttackLocations()
	{
		return this.attackLocations;
	}

	/**
	 * Set the level
	 * @param level
	 */
	public void setLevel(final int level)
	{
		this.level = level;
	}

	/**
	 * Set the color
	 * @param color
	 */
	public void setColor(final Color color)
	{
		this.myColor = color;
	}

	/**
	 * @return the reserveOfSoldiers
	 */
	public final ReserveOfSoldiers getReserveOfSoldiers()
	{
		return this.reserveOfSoldiers;
	}

	/**
	 * @return the actor
	 */
	public final Actor getActor()
	{
		return this.actor;
	}

	/**
	 * @param actor the actor to set
	 */
	public final void setActor(final Actor actor)
	{
		this.actor = actor;
	}	
}
