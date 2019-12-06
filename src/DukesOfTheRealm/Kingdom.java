package DukesOfTheRealm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Duke.Actor;
import Duke.Baron;
import Duke.DukeAI;
import Duke.Player;
import Interface.ISave;
import Interface.IUpdate;
import SaveSystem.KingdomData;
import UI.UIManager;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe représentant le royaume.
 * C'est la classe centrale du projet.
 *
 */
public class Kingdom extends Parent implements IUpdate, ISave<KingdomData> {

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Liste des châteaux du royaume.
	 * @see Kingdom#CreateCastle(Pane layer, Point2D coordinate, int level, Actor actor)
	 * @see Kingdom#Update(long now, boolean pause)
	 */
	private ArrayList<Castle> castles;

	/**
	 * Liste des acteurs (joueur et IA) du royaume.
	 * @see Kingdom#CreateWorld(int AINumber, int baronNumber)
	 */
	private final ArrayList<Actor> actors;

	/**
	 * Liste des couleurs atribuable à chaque acteur.
	 * @see Kingdom#Kingdom(Pane)
	 * @see Kingdom#CreateWorld(int AINumber, int baronNumber)
	 */
	private final ArrayList<Color> colors;

	/**
	 * Référence à l'acteur "player" qui est l'utilisateur.
	 */
	private static Player player;

	/**
	 * Canvas utilisé pour afficher les images du jeu.
	 */
	private final Pane playfieldLayer;

	/**
	 * Référence sur l'instance UIManager.
	 * @see UIManager
	 * @see UpdateUI(long now, boolean pause)
	 */
	public UIManager castleUIInstance;

	/**
	 * Condition pour que le royaume utilise Update.
	 * @see Kingdom#Update(long, boolean)
	 * @see Main#Update(long, boolean)
	 */
	private boolean canUpdate = false;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur Kingdom.
	 *
	 * @param playfieldLayer
	 * @see Kingdom#playfieldLayer
	 */
	public Kingdom(final Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
		castles = new ArrayList<>();
		actors = new ArrayList<>();
		colors = new ArrayList<>();
		colors.add(Color.DIMGRAY);
		colors.add(Color.DARKORANGE);
		colors.add(Color.LIGHTSLATEGRAY);
		//colors.add(Color.SILVER);
		//colors.add(Color.TOMATO);
		colors.add(Color.AQUA);
		//colors.add(Color.KHAKI);
		//colors.add(Color.LINEN);
		colors.add(Color.MEDIUMORCHID);
		colors.add(Color.GOLDENROD);
	}


	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void Start()
	{
		castleUIInstance = UIManager.GetInstance();
		CreateWorld(Settings.AI_NUMBER, Settings.BARON_NUMBER);
		castles.forEach(castle -> castle.Start());
		castles.forEach(castle ->
		{
			if (castle != castles.get(0))
			{
				castle.CreateOst(actors.get(0).GetMyCastles().get(0), 5, 5, 0);
			}
		});
		Kingdom.player.GetMyCastles().get(0).CreateOst(actors.get(1).GetMyCastles().get(0), 9, 9, 0);
		canUpdate = true;
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void Update(final long now, final boolean pause)
	{
		if(canUpdate)
		{
			castles.forEach(castle -> castle.Update(now, pause));
			UpdateUI(now, pause);
		}
	}

	private void UpdateUI(final long now, final boolean pause)
	{
		if(castleUIInstance != null)
		{
			castleUIInstance.Update(now, pause);
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	public boolean CreateCastle(final Pane layer, final Point2D coordinate, final int level, final Actor actor)
	{
		if(!IsCastleToClose(coordinate.GetX(), coordinate.GetY()))
		{
			final Castle newCastle = new Castle(layer, coordinate.GetX(), coordinate.GetY(), level, actor);
			newCastle.AddRepresentation();
			getChildren().add(newCastle.GetShape());
			getChildren().add(newCastle.GetDoor());
			return AddCastle(newCastle);
		}
		return false;
	}

	public void CreateWorld(final int AINumber, final int baronNumber)
	{
		final Random rand = new Random();

		Color c = colors.get(rand.nextInt(colors.size() - 1));
		Kingdom.player = new Player("Player", Color.LIMEGREEN);
		actors.add(Kingdom.player);
		colors.remove(c);

		int numberTest = 0;

		while(numberTest < Settings.NB_TOTAL_TEST_CREATE_CASTLE)
		{
			if(CreateCastle(playfieldLayer, GetRandomCoordinates(rand), 1, player))
			{
				break;
			}
			numberTest++;
		}


		for(int i = 0; i < AINumber; i++)
		{
			c = colors.get(rand.nextInt(colors.size() - 1));
			final Actor a = new DukeAI("Duke " + (i+1), c);
			actors.add(a);
			colors.remove(c);

			numberTest = 0;

			while(numberTest < Settings.NB_TOTAL_TEST_CREATE_CASTLE)
			{
				if(CreateCastle(playfieldLayer, GetRandomCoordinates(rand), 1, a))
				{
					break;
				}
				numberTest++;
			}
		}


		for(int i = 0; i < baronNumber; i++)
		{
			final Actor a = new Baron("Baron " + (i+1), Color.WHEAT);
			actors.add(a);

			numberTest = 0;

			while(numberTest < 1000)
			{
				if(CreateCastle(playfieldLayer, GetRandomCoordinates(rand), rand.nextInt(6) + 1, a))
				{
					break;
				}
				numberTest++;
			}
		}
	}

	private boolean AddCastle(final Castle castle)
	{
		return castles.add(castle);
	}

	private boolean IsCastleToClose(final double x, final double y)
	{
		final Iterator<Castle> it = castles.iterator();
		while(it.hasNext())
		{
			final Castle currentCastle = it.next();
			final double d = DistanceBetween(currentCastle, x, y);
			if(d < Settings.MIN_DISTANCE_BETWEEN_TWO_CASTLE)
			{
				return true;
			}
		}
		return false;
	}

	private double DistanceBetween(final Castle castle, final double x, final double y)
	{
		return Math.sqrt((y - castle.GetY()) * (y - castle.GetY()) + (x - castle.GetX()) * (x - castle.GetX()));
	}

	public Point2D GetRandomCoordinates(final Random rand)
	{
		return new Point2D(rand.nextInt((int)(Settings.SCENE_WIDTH * Settings.MARGIN_PERCENTAGE - 2 * Settings.CASTLE_SIZE)) + Settings.CASTLE_SIZE, rand.nextInt(Settings.SCENE_HEIGHT - (4 * Settings.CASTLE_SIZE)) + Settings.CASTLE_SIZE);
	}

	@Override
	public void ReceivedDataSave(final KingdomData data)
	{

	}

	@Override
	public void SendingDataSave(final KingdomData data)
	{

	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	public static Player GetPlayer()
	{
		return player;
	}

	public ArrayList<Castle> GetCastles()
	{
		return castles;
	}

	public void SetCastles(final ArrayList<Castle> castles)
	{
		this.castles = castles;
	}
}
