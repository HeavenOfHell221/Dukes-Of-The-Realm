package DukesOfTheRealm;

import static Utility.Settings.ATTACK_LOCATIONS_PER_SIDE;
import static Utility.Settings.CASTLE_COST;
import static Utility.Settings.CASTLE_PRODUCTION_OFFSET;
import static Utility.Settings.CASTLE_PRODUCTION_TIME_PER_LEVEL;
import static Utility.Settings.CASTLE_SIZE;
import static Utility.Settings.GAP_WITH_SOLDIER;
import static Utility.Settings.NB_ATTACK_LOCATIONS;
import static Utility.Settings.SOLDIER_SIZE;
import static Utility.Settings.THIRD_OF_CASTLE;

import java.io.Serializable;
import java.util.Random;
import java.util.Stack;

import Duke.Actor;
import Enums.BuildingEnum;
import Enums.SoldierEnum;
import Interface.IBuilding;
import Interface.IProduction;
import Interface.IUpdate;
import Utility.BuildingPack;
import Utility.Point2D;
import Utility.Settings;
import Utility.SoldierPack;
import Utility.Time;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Repr�sente un ch�teau.
 * <p>
 * Extends de la classe Sprite.
 * </p>
 *
 * @see Sprite
 */
public class Castle extends Sprite implements Serializable, IBuilding, IProduction, IUpdate
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Enum contenant les diff�rentes orientations pour les portes et les unit�s.
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
	 * Le nombre total de Florin du ch�teau.
	 */
	private double totalFlorin;

	/**
	 * Le niveau du ch�teau.
	 */
	private int level = 1;

	/**
	 * L'ost du ch�teau.
	 *
	 * @see Ost
	 */
	private Ost ost;

	/**
	 * L'orientation de la porte pour ce ch�teau.
	 *
	 * @see Orientation
	 */
	private Orientation orientation;

	/**
	 * La couleur du ch�teau. Cette couleur vient de l'acteur � qui appartient ce ch�teau.
	 */
	private transient Color myColor;

	/**
	 * La repr�sentation graphique de la porte du ch�teau.
	 */
	private transient Rectangle door;

	/**
	 * Les points o� les unit�s peuvent se placer pour attaquer ce ch�teau.
	 *
	 * @see Castle#setAttackLocations()
	 * @see Castle#freeAttackLocation(Point2D)
	 * @see Ost
	 */
	private Stack<Point2D> attackLocations;

	/**
	 * L'acteur � qui appartient ce ch�teau.
	 *
	 * @see Actor
	 */
	private Actor actor;

	/**
	 * Contient tout les b�timents de ce ch�teau.
	 */
	private BuildingPack<IBuilding> buildingPack;

	/**
	 * Contient les unit�s de ce ch�teau.
	 * @see ReserveOfSoldiers
	 */
	private ReserveOfSoldiers reserveOfSoldiers;
	
	/**
	 * Multiplicateur de dur�e pour toute production li� � ce ch�teau.
	 */
	private float productionTimeMultiplier = 1f;
	
	/**
	 * Boolean sp�cifiant si ce ch�teau est acctuellement espionn� par le joueur.
	 */
	private boolean isSpiedOn = false;
	
	/**
	 * Temps avant que ce ch�teau ne soit plus consid�r� comme espionn�.
	 */
	private long spiedOnTime = 0;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur Castle.
	 *
	 * @param level Le niveau de ce ch�teau.
	 */
	public Castle(final int level)
	{
		super();
		this.level = level;
	}

	/**
	 * Constructeur par d�faut Castle.
	 */
	public Castle()
	{
		super();
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	/**
	 * Initalise ce ch�teau.
	 *
	 * @param level Le niveau de ce ch�teau
	 * @param pane  Le pane pour afficher la r�pr�sentation graphique de ce ch�teau.
	 * @param coord Les coordonn�es de ce ch�teau.
	 * @param actor L'acteur � qui appartient ce ch�teau.
	 * @see         Kingdom#createWorld()
	 */
	public void start(final int level, final Pane pane, final Point2D coord, final Actor actor)
	{
		this.actor = actor;
		this.coordinate = coord;
		this.level = level;
		this.totalFlorin = 0;
		this.buildingPack = new BuildingPack<>(this, new Caserne(this), new Market(), new Miller(), new Wall());
		this.reserveOfSoldiers = new ReserveOfSoldiers(this);
		this.ost = null;

		this.attackLocations = new Stack<>();
		this.orientation = setOrientation();
		startTransient(pane);
		setAttackLocations();
		
		this.productionTimeMultiplier = (float) ((100f - Math.exp(this.level / 6) - this.level) / 100);
		
	}

	/**
	 * Initalise les �l�ments transient de ce ch�teau.
	 *
	 * @param pane Le pane pour afficher la r�pr�sentation graphique de ce ch�teau.
	 * @see        Kingdom#startTransient(Pane)
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


	@Override
	public void update(long now, boolean pause)
	{
		updateProduction();
		updateOst(now, pause);
		
		if(this.isSpiedOn)
		{
			this.spiedOnTime -= Settings.GAME_FREQUENCY * Time.deltaTime;
			if(this.spiedOnTime <= 0)
			{
				this.isSpiedOn = false;
			}
		}
	}
	
	/**
	 * Met � jour la caserne.
	 *
	 * @see Caserne#updateProduction()
	 */
	public void updateProduction()
	{
		getCaserne().updateProduction();
	}

	/**
	 * Met � jour l'ost si ce ch�teau en a une.
	 *
	 * @param now   Le temps actuel.
	 * @param pause Boolean repr�sentant si la pause est activ� ou non.
	 * @see         Ost#update(long, boolean)
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

	@Override
	public void productionFinished(final Castle castle, final boolean cancel)
	{
		if (!cancel)
		{
			castle.levelUp();
		}
		castle.getCaserne().getBuildingPack().replace(BuildingEnum.Castle,
				castle.getCaserne().getBuildingPack().get(BuildingEnum.Castle) - 1);
	}

	@Override
	public void productionStart(final Caserne caserne)
	{
		caserne.getBuildingPack().replace(BuildingEnum.Castle, caserne.getBuildingPack().get(BuildingEnum.Castle) + 1);
	}

	/**
	 * Choisi al�atoirement une orientation.
	 *
	 * @return Une orientation.
	 * @see    Orientation
	 */
	private Orientation setOrientation()
	{
		Random rand = new Random();
		switch (rand.nextInt(4))
		{
			case 0:
				return Orientation.East;
			case 1:
				return Orientation.North;
			case 2:
				return Orientation.West;
			case 3:
				return Orientation.South;
		}
		return Orientation.None;
	}

	/**
	 * Donne � un Baron un nombre al�atoire d'unit�s proportionnel au niveau de ce ch�teau.
	 *
	 * @see Duke.Baron#addFirstCastle(Castle)
	 * @see ReserveOfSoldiers
	 */
	public void randomSoldier()
	{
		final Random rand = new Random();
		final int levelSq = this.level * (this.level / 6) + 1;

		/*for (SoldierEnum s : SoldierEnum.values())
		{
			getReserveOfSoldiers().getSoldierPack().replace(s, rand.nextInt(levelSq) + rand.nextInt(3) * this.level);
		}*/
	}

	/**
	 * Donne un nombre fini d'unit�s pour le joueur et les IA.
	 *
	 * @see ReserveOfSoldiers
	 */
	public void startSoldier()
	{
		for (SoldierEnum s : SoldierEnum.values())
		{
			getReserveOfSoldiers().getSoldierPack().replace(s, s.starter);
		}
	}

	/**
	 * Initialise et affiche la repr�sentation graphique du ch�teau aisni que de la porte.
	 *
	 * @param pane Le pane principale.
	 * @see        Sprite#addCastleRepresentation(Pane)
	 */
	public void addRepresentation(final Pane pane)
	{
		addCastleRepresentation(pane);
		this.door = addDoorRepresentation(pane, this.orientation);
		this.shape.setFill(this.myColor);
	}

	/**
	 * Augmente de 1 le niveau de ce ch�teau.
	 *
	 * @see Castle#level
	 */
	public void levelUp()
	{
		if(this.level < Settings.CASTLE_LEVEL_MAX)
		{
			this.level += 1;
			decreaseMultiplier();
		}
	}
	
	private void decreaseMultiplier()
	{
		this.productionTimeMultiplier = (float) ((100f - Math.exp(this.level / 6) - this.level) / 100);
	}

	/**
	 * Ajoute des Florin au total de ce ch�teau.
	 *
	 * @param amount Le nombre de Florin � ajouter.
	 * @see          Castle#totalFlorin
	 */
	public void addFlorin(final double amount)
	{
		this.totalFlorin += amount;
	}

	/**
	 * Retire le nombre de Florin en param�tre si cela est possible.
	 *
	 * @param  amount Le nombre de Florin � retirer.
	 * @return        Retourne true si les Florin on �t� retir�s et false sinon.
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
	 * Teste si ce ch�teau � au moins un certain nombre de Florin.
	 *
	 * @param  amount Le nombre de Florin qu'on teste.
	 * @return        Retourne true si le ch�teau � au moins le nombre de Florin donn� en param�tre,
	 *                false sinon.
	 * @see           Castle#removeFlorin(double)
	 */
	public boolean enoughOfFlorin(final double amount)
	{
		return amount <= this.totalFlorin;
	}

	/**
	 * Cr�e une ost avec un ch�teau destination et un nombre d'unit�s.
	 * <p>
	 * L'ost serra cr�e si ce ch�teau n'en contient pas d�j� une, si la destination n'est pas ce ch�teau
	 * et que la r�serve contient <br>
	 * le nombre d'unit�s n�cessaire.
	 * </p>
	 *
	 * @param  destination Le ch�teau destination de l'ost.
	 * @param  soldierPack Le nombre d'unit� de l'ost.
	 * @return             Retourne true si l'ost a �t� cr�e, false sinon.
	 * @see                Ost
	 * @see                Ost#start()
	 * @see                ReserveOfSoldiers#removeSoldiers(int, int, int)
	 */
	public boolean createOst(final Castle destination, final SoldierPack<Integer> soldierPack)
	{
		if (this.ost == null)
		{
			if (removeSoldiers(new SoldierPack<>(soldierPack)) && this != destination)
			{
				this.ost = new Ost(this, destination, new SoldierPack<>(soldierPack), this.myColor);
				this.ost.start();
				return true;
			}
		}
		return false;
	}

	/**
	 * Force ce ch�teau � ne plus avoir d'ost.
	 *
	 * @see Ost#update(long, boolean)
	 */
	public void removeOst()
	{
		if (this.ost != null && this.ost.getSoldiers().size() == 0)
		{
			this.ost = null;
		}
	}

	/**
	 * Change la couleur de ce ch�teau.
	 * <p>
	 * Utilis� quand le ch�teau change de propri�taire.
	 * </p>
	 *
	 * @param color La nouvelle couleur.
	 */
	public void switchColor(final Color color)
	{
		setColor(color);
		setColorShape(this.myColor);
	}

	/**
	 * Cr�e les points d'attaque que prendront les unit�s qui attaqueront ce ch�teau.
	 *
	 * @see Castle#attackLocations
	 * @see Soldiers.Soldier#getAttackLocation()
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
	 * @return Retourne true s'il y a des points d'attaque libre, false sinon.
	 */
	public boolean isAvailableAttackLocation()
	{
		return !this.attackLocations.empty();
	}

	/**
	 * @return Retourne le prochain point d'attaque.
	 */
	public Point2D getNextAttackLocation()
	{
		return this.attackLocations.pop();
	}

	/**
	 * Lib�re un point d'attaque apr�s la mort d'une unit�.
	 *
	 * @param FreedAttackLocation Le point d'attaque qui devient libre.
	 */
	public void freeAttackLocation(final Point2D FreedAttackLocation)
	{
		this.attackLocations.push(FreedAttackLocation);
	}

	/*************************************************/
	/************** DELEGATES METHODS ****************/
	/*************************************************/

	public boolean removeSoldiers(final SoldierPack<Integer> soldierPack)
	{
		return getReserveOfSoldiers().removeSoldiers(soldierPack);
	}

	/**
	 * @return Retourne le multiplicateur du rempart.
	 * @see    Wall#getMultiplicator()
	 */
	public double getWallMultiplicator()
	{
		return ((Wall) getBuilding(BuildingEnum.Wall)).getMultiplicator();
	}

	/**
	 * @return Retourne la valeur de retour isStopAttack.
	 * @see    ReserveOfSoldiers#isStopAttack()
	 */
	public boolean isStopAttack()
	{
		return getReserveOfSoldiers().isStopAttack();
	}

	/**
	 * @see ReserveOfSoldiers#randomRemoveHP(SoldierEnum)
	 */
	public void randomRemoveHP()
	{
		getReserveOfSoldiers().randomRemoveHP(SoldierEnum.getRandomTypeWithDefense());
	}

	/**
	 * Ajoute une production dans la caserne.
	 * 
	 * @param  p La production ajout� dans la caserne.
	 * @return   Retourne si la production a bien �t� rajout�.
	 */
	public boolean addProduction(final IProduction p)
	{
		return getCaserne().addProduction(p);
	}
	
	public void spiedOn()
	{
		this.isSpiedOn = true;
		this.spiedOnTime = Settings.GAME_FREQUENCY;
		this.spiedOnTime *= 30;
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @param  key La cl�.
	 * @return     Retourne le b�timent souhait�.
	 * @see        Utility.BuildingPack#get(Enums.BuildingEnum)
	 */
	public IBuilding getBuilding(final BuildingEnum key)
	{
		return this.buildingPack.get(key);
	}

	/**
	 * @return La r�serve de ce ch�teau.
	 */
	public ReserveOfSoldiers getReserveOfSoldiers()
	{
		return this.reserveOfSoldiers;
	}
	
	public Miller getMiller()
	{
		return (Miller) getBuilding(BuildingEnum.Miller);
	}

	public Caserne getCaserne()
	{
		return (Caserne) getBuilding(BuildingEnum.Caserne);
	}

	public Wall getWall()
	{
		return (Wall) getBuilding(BuildingEnum.Wall);
	}
	
	@Override
	public int getProductionCost(int level)
	{
		return CASTLE_COST * level + level * level * level;
	}

	/**
	 * @return the totalFlorin
	 */
	public final double getTotalFlorin()
	{
		return this.totalFlorin;
	}

	/**
	 * Calcul le temps de production de ce ch�teau en fonction de son niveau.
	 *
	 * @return Retourne son temps de production.
	 */
	@Override
	public double getProductionTime(Castle castle, int level)
	{
		return (CASTLE_PRODUCTION_OFFSET + CASTLE_PRODUCTION_TIME_PER_LEVEL * level) * castle.getProductionTimeMultiplier();
	}

	/**
	 * @return the level
	 */
	@Override
	public final int getLevel()
	{
		return this.level;
	}

	/**
	 * @return the orientation
	 */
	public final Orientation getOrientation()
	{
		return this.orientation;
	}

	/**
	 * @return the myColor
	 */
	public final Color getMyColor()
	{
		return this.myColor;
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
	 *
	 * @param level the level to set
	 */
	@Override
	public void setLevel(final int level)
	{
		this.level = level;
	}

	/**
	 * @param myColor the myColor to set
	 */
	public final void setColor(final Color myColor)
	{
		this.myColor = myColor;
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

	/**
	 * @return the ost
	 */
	public final Ost getOst()
	{
		return this.ost;
	}

	/**
	 * @return the productionTimeMultiplier
	 */
	public final float getProductionTimeMultiplier()
	{
		return productionTimeMultiplier;
	}

	/**
	 * @return the isSpiedOn
	 */
	public final boolean isSpiedOn()
	{
		return isSpiedOn;
	}
}
