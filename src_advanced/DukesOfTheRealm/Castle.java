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
 * Représente un château.
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
	 * Enum contenant les différentes orientations pour les portes et les unités.
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
	 * L'ost du château.
	 *
	 * @see Ost
	 */
	private Ost ost;

	/**
	 * L'orientation de la porte pour ce château.
	 *
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
	 *
	 * @see Castle#setAttackLocations()
	 * @see Castle#freeAttackLocation(Point2D)
	 * @see Ost
	 */
	private Stack<Point2D> attackLocations;

	/**
	 * L'acteur à qui appartient ce château.
	 *
	 * @see Actor
	 */
	private Actor actor;
	
	/**
	 * Contient tout les bâtiments de ce château.
	 */
	private BuildingPack<IBuilding> buildingPack;
	
	/**
	 * Caractère de ce château.
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
	 *
	 * @param level Le niveau de ce château
	 * @param pane  Le pane pour afficher la réprésentation graphique de ce château.
	 * @param coord Les coordonnées de ce château.
	 * @param actor L'acteur à qui appartient ce château.
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
	 * Initalise les éléments transient de ce château.
	 *
	 * @param pane Le pane pour afficher la réprésentation graphique de ce château.
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
	 * Met à jour la caserne.
	 *
	 * @see Caserne#updateProduction()
	 */
	public void updateProduction()
	{
		getCaserne().updateProduction();
	}

	/**
	 * Met à jour l'ost si ce château en a une.
	 *
	 * @param now   Le temps actuel.
	 * @param pause Boolean représentant si la pause est activé ou non.
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
		castle.getCaserne().getBuildingPack().replace(BuildingEnum.Castle, castle.getCaserne().getBuildingPack().get(BuildingEnum.Castle) - 1);
	}

	@Override
	public void productionStart(final Caserne caserne)
	{
		caserne.getBuildingPack().replace(BuildingEnum.Castle, caserne.getBuildingPack().get(BuildingEnum.Castle) + 1);
	}

	/**
	 * Choisi aléatoirement une orientation.
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
	 * Donne à un Baron un nombre aléatoire d'unités proportionnel au niveau de ce château.
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
	 * Donne un nombre fini d'unités pour le joueur et les IA.
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
	 * Initialise et affiche la représentation graphique du château aisni que de la porte.
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
	 * Augmente de 1 le niveau de ce château.
	 *
	 * @see Castle#level
	 */
	public void levelUp()
	{
		this.level++;
	}

	/**
	 * Ajoute des Florin au total de ce château.
	 *
	 * @param amount Le nombre de Florin à ajouter.
	 * @see          Castle#totalFlorin
	 */
	public void addFlorin(final double amount)
	{
		this.totalFlorin += amount;
	}

	/**
	 * Retire le nombre de Florin en paramètre si cela est possible.
	 *
	 * @param  amount Le nombre de Florin à retirer.
	 * @return        Retourne true si les Florin on été retirés et false sinon.
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
	 *
	 * @param  amount Le nombre de Florin qu'on teste.
	 * @return        Retourne true si le château à au moins le nombre de Florin donné en paramètre,
	 *                false sinon.
	 * @see           Castle#removeFlorin(double)
	 */
	public boolean enoughOfFlorin(final double amount)
	{
		return amount <= this.totalFlorin;
	}

	/**
	 * Crée une ost avec un château destination et un nombre d'unités.
	 * <p>
	 * L'ost serra crée si ce château n'en contient pas déjà une, si la destination n'est pas ce château
	 * et que la réserve contient <br>
	 * le nombre d'unités nécessaire.
	 * </p>
	 *
	 * @param  destination Le château destination de l'ost.
	 * @param  soldierPack Le nombre d'unité de l'ost.
	 * @return             Retourne true si l'ost a été crée, false sinon.
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
	 * Force ce château à ne plus avoir d'ost.
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
	 * Change la couleur de ce château.
	 * <p>
	 * Utilisé quand le château change de propriétaire.
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
	 * Crée les points d'attaque que prendront les unités qui attaqueront ce château.
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
	 * Libère un point d'attaque après la mort d'une unité.
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
	 * @param p La production ajouté dans la caserne.
	 * @return Retourne si la production a bien été rajouté.
	 */
	public boolean addProduction(IProduction p)
	{
		return getCaserne().addProduction(p);
	}
	
	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @param key La clé.
	 * @return Retourne le bâtiment souhaité.
	 * @see Utility.BuildingPack#get(Enums.BuildingEnum)
	 */
	public IBuilding getBuilding(BuildingEnum key)
	{
		return buildingPack.get(key);
	}
	
	/**
	 * @return La réserve de ce château.
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
		return CASTLE_COST * castle.level + castle.getCaserne().getBuildingPack().get(BuildingEnum.Castle) * Settings.CASTLE_COST;
	}

	/**
	 * @return the totalFlorin
	 */
	public final double getTotalFlorin()
	{
		return this.totalFlorin;
	}

	/**
	 * Calcul le temps de production de ce château en fonction de son niveau.
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
