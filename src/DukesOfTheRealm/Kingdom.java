package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Duke.Actor;
import Duke.Baron;
import Duke.DukeAI;
import Duke.Player;
import Interface.IUpdate;
import UI.UIManager;
import Utility.Collisions;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe repr�sentant le royaume. C'est la classe centrale du projet.
 */
public class Kingdom extends Parent implements IUpdate, Serializable
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Liste des ch�teaux du royaume.
	 *
	 * @see Kingdom#CreateCastle(Pane layer, Point2D coordinate, int level, Actor actor)
	 * @see Kingdom#update(long now, boolean pause)
	 */
	private final ArrayList<Castle> castles;

	/**
	 * Liste des acteurs (joueur et IA) du royaume.
	 *
	 * @see Kingdom#CreateWorld(int AINumber, int baronNumber)
	 */
	private final ArrayList<Actor> actors;

	/**
	 * Liste des couleurs atribuable � chaque acteur.
	 *
	 * @see Kingdom#Kingdom(Pane)
	 * @see Kingdom#CreateWorld(int AINumber, int baronNumber)
	 */
	private final transient ArrayList<Color> colors;

	/**
	 * R�f�rence � l'acteur "player" qui est l'utilisateur.
	 */
	private static Player player;

	/**
	 * Canvas utilis� pour afficher les images du jeu.
	 */
	private transient Pane playfieldLayer;

	/**
	 * R�f�rence sur l'instance UIManager.
	 *
	 * @see UIManager
	 * @see UpdateUI(long now, boolean pause)
	 */
	public final transient UIManager castleUIInstance;

	/**
	 * Condition pour que le royaume utilise Update.
	 *
	 * @see Kingdom#update(long, boolean)
	 * @see Main#update(long, boolean)
	 */
	private boolean canUpdate = false;
	
	public static Collisions collisionsManagement;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur Kingdom.
	 *
	 * @param playfieldLayer
	 * @see                  Kingdom#playfieldLayer
	 */
	public Kingdom()
	{
		this.castles = new ArrayList<>();
		this.actors = new ArrayList<>();
		this.colors = new ArrayList<>();
		this.colors.add(Color.DIMGRAY);
		this.colors.add(Color.DARKORANGE);
		this.colors.add(Color.LIGHTSLATEGRAY);
		this.colors.add(Color.AQUA);
		this.colors.add(Color.MEDIUMORCHID);
		this.colors.add(Color.GOLDENROD);
		player = new Player("Player");
		player.setColor(Color.LIMEGREEN);
		this.castleUIInstance = UIManager.GetInstance();
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		CreateWorld(Settings.AI_NUMBER, Settings.BARON_NUMBER);
		setCollisionsManagement();
		
		castles.forEach(castle -> castle.start());
//		castles.forEach(castle ->
//		{
//			if (castle != castles.get(0))
//			{
//				castle.CreateOst(actors.get(0).GetMyCastles().get(0), 5, 5, 0);
//			}
//		});
		Kingdom.player.getMyCastles().get(0).CreateOst(actors.get(1).getMyCastles().get(0), 9, 9, 0);
		canUpdate = true;
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		if (this.canUpdate)
		{
			this.castles.forEach(castle -> castle.update(now, pause));
			UpdateUI(now, pause);
		}
	}

	private void UpdateUI(final long now, final boolean pause)
	{
		if (this.castleUIInstance != null)
		{
			this.castleUIInstance.update(now, pause);
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	public boolean CreateCastle(final Pane layer, final Point2D coordinate, final int level, final Actor actor)
	{
		if (!IsCastleToClose(coordinate))
		{
			final Castle newCastle = new Castle(layer, coordinate, level, actor);
			newCastle.AddRepresentation();
			getChildren().add(newCastle.getShape());
			getChildren().add(newCastle.GetDoor());
			return AddCastle(newCastle);
		}
		return false;
	}

	public void CreateWorld(final int AINumber, final int baronNumber)
	{
		final Random rand = new Random();

		Color c = this.colors.get(rand.nextInt(this.colors.size() - 1));
		this.actors.add(Kingdom.player);
		this.colors.remove(c);

		int numberTest = 0;

		while (numberTest < Settings.NB_TOTAL_TEST_CREATE_CASTLE)
		{
			if (CreateCastle(this.playfieldLayer, GetRandomCoordinates(rand), 1, player))
			{
				break;
			}
			numberTest++;
		}

		for (int i = 0; i < AINumber; i++)
		{
			c = this.colors.get(rand.nextInt(this.colors.size() - 1));
			final Actor a = new DukeAI("Duke " + (i + 1));
			a.setColor(c);
			this.actors.add(a);
			this.colors.remove(c);

			numberTest = 0;

			while (numberTest < Settings.NB_TOTAL_TEST_CREATE_CASTLE)
			{
				if (CreateCastle(this.playfieldLayer, GetRandomCoordinates(rand), 1, a))
				{
					break;
				}
				numberTest++;
			}
		}

		for (int i = 0; i < baronNumber; i++)
		{
			final Actor a = new Baron("Baron " + (i + 1));
			a.setColor(Color.WHEAT);
			this.actors.add(a);

			numberTest = 0;

			while (numberTest < 1000)
			{
				if (CreateCastle(this.playfieldLayer, GetRandomCoordinates(rand), rand.nextInt(6) + 1, a))
				{
					break;
				}
				numberTest++;
			}
		}
	}
	private boolean AddCastle(final Castle castle)
	{
		return this.castles.add(castle);
	}

	private boolean IsCastleToClose(final Point2D coordinate)
	{
		final Iterator<Castle> it = this.castles.iterator();
		while (it.hasNext())
		{
			final Castle currentCastle = it.next();
			final double d = DistanceBetween(currentCastle, coordinate);
			if (d < Settings.MIN_DISTANCE_BETWEEN_TWO_CASTLE)
			{
				return true;
			}
		}
		return false;
	}

	private double DistanceBetween(final Castle castle, final Point2D coord)
	{
		return Math.sqrt((coord.getY() - castle.getY()) * (coord.getY() - castle.getY())
				+ (coord.getX() - castle.getX()) * (coord.getX() - castle.getX()));
	}

	public Point2D GetRandomCoordinates(final Random rand)
	{
		return new Point2D(
				rand.nextInt((int) (Settings.SCENE_WIDTH * Settings.MARGIN_PERCENTAGE - 2 * Settings.CASTLE_SIZE)) + Settings.CASTLE_SIZE,
				rand.nextInt(Settings.SCENE_HEIGHT - (4 * Settings.CASTLE_SIZE)) + Settings.CASTLE_SIZE);
	}
	
	protected void setCollisionsManagement()
	{
		Kingdom.collisionsManagement = new Collisions();
		this.castles.forEach(castle ->
			{
				collisionsManagement.addPoint(castle.getCoordinate());
			});
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
		return this.castles;
	}

	public void setPlayfieldLayer(final Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
	}
}
