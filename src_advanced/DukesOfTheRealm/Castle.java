package DukesOfTheRealm;

import static Utility.Settings.*;

import java.io.Serializable;
import java.util.Random;
import java.util.Stack;

import Duke.Actor;
import Enums.BuildingEnum;
import Enums.CharacterCastleEnum;
import Enums.SoldierEnum;
import Interface.IBuilding;
import Interface.IProduction;
import Utility.BuildingPack;
import Utility.Point2D;
import Utility.Settings;
import Utility.SoldierPack;
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
public class Castle extends Sprite implements Serializable, IBuilding, IProduction
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
	 * Caract�re de ce ch�teau.
	 * @see Enums.CharacterCastleEnum
	 */
	private CharacterCastleEnum character;
	
	private ReserveOfSoldiers reserveOfSoldiers;

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
		this.buildingPack = new BuildingPack<IBuilding>(this, new Caserne(this), new Market(), new Miller(), new Wall());
		this.reserveOfSoldiers = new ReserveOfSoldiers(this);
		this.ost = null;
		
		this.attackLocations = new Stack<>();
		this.orientation = setOrientation();
		startTransient(pane);
		setAttackLocations();
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
		getCaserne().getBuildingPack().replace(BuildingEnum.Castle, getCaserne().getBuildingPack().get(BuildingEnum.Castle) - 1);
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
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			getReserveOfSoldiers().getSoldierPack().replace(s, rand.nextInt(levelSq) + rand.nextInt(3) * this.level);
		}
	}

	/**
	 * Donne un nombre fini d'unit�s pour le joueur et les IA.
	 *
	 * @see ReserveOfSoldiers
	 */
	public void startSoldier()
	{
		for(SoldierEnum s : SoldierEnum.values())
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
		this.level++;
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
	public boolean createOst(final Castle destination, SoldierPack<Integer> soldierPack)
	{
		if (this.ost == null)
		{
			if (removeSoldiers(new SoldierPack<Integer>(soldierPack)) && this != destination)
			{
				this.ost = new Ost(this, destination, new SoldierPack<Integer>(soldierPack), this.myColor);
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

	public boolean removeSoldiers(SoldierPack<Integer> soldierPack)
	{
		return getReserveOfSoldiers().removeSoldiers(soldierPack);
	}
	
	/**
	 * @return Retourne le multiplicateur du rempart.
	 * @see Wall#getMultiplicator()
	 */
	public float getWallMultiplicator()
	{
		return ((Wall)getBuilding(BuildingEnum.Wall)).getMultiplicator();
	}
	
	/**
	 * @return Retourne la valeur de retour isStopAttack.
	 * @see ReserveOfSoldiers#isStopAttack()
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
		getReserveOfSoldiers().randomRemoveHP(SoldierEnum.getRandomType());
	}
	
	/**
	 * Ajoute une production dans la caserne.
	 * @param p La production ajout� dans la caserne.
	 * @return Retourne si la production a bien �t� rajout�.
	 */
	public boolean addProduction(IProduction p)
	{
		return getCaserne().addProduction(p);
	}
	
	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @param key La cl�.
	 * @return Retourne le b�timent souhait�.
	 * @see Utility.BuildingPack#get(Enums.BuildingEnum)
	 */
	public IBuilding getBuilding(BuildingEnum key)
	{
		return buildingPack.get(key);
	}
	
	/**
	 * @return La r�serve de ce ch�teau.
	 */
	public ReserveOfSoldiers getReserveOfSoldiers()
	{
		return this.reserveOfSoldiers;
	}
	
	public Caserne getCaserne()
	{
		return (Caserne) getBuilding(BuildingEnum.Caserne);
	}
	
	@Override
	public int getProductionCost(final Castle castle)
	{
		return CASTLE_COST * castle.level + /*castle.getCaserne().getNbCastleInProduction() **/ Settings.CASTLE_COST;
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
	public double getProductionTime()
	{
		return CASTLE_PRODUCTION_OFFSET + CASTLE_PRODUCTION_TIME_PER_LEVEL * this.level;
	}

	/**
	 * @return the level
	 */
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
	 * @return the character
	 */
	public final CharacterCastleEnum getCharacter()
	{
		return character;
	}

	/**
	 * @param character the character to set
	 */
	public final void setCharacter(CharacterCastleEnum character)
	{
		this.character = character;
	}
}
