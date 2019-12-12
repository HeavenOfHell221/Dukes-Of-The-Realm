package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Duke.Actor;
import Duke.Baron;
import Duke.DukeAI;
import Duke.Player;
import UI.UIManager;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe representant le royaume. C'est la classe centrale du projet.
 */
public class Kingdom extends Parent implements Serializable
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Liste des acteurs (joueur et IA) du royaume.
	 *
	 */
	private ArrayList<Actor> actors;

	/**
	 * Liste des couleurs atribuable ï¿½ chaque acteur.
	 *
	 */
	private transient ArrayList<Color> colors;

	/**
	 * Reference à l'acteur "player" qui est l'utilisateur.
	 */
	private Player player;

	/**
	 * Canvas utilise pour afficher les images du jeu.
	 */
	private transient Pane playfieldLayer;

	/**
	 * Condition pour que le royaume utilise Update.
	 *
	 */
	private transient boolean canUpdate = false;

	// public static Collisions collisionsManagement;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur Kingdom.
	 *
	 */
	public Kingdom()
	{

	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	/**
	 *
	 * @param pane
	 */
	public void start(final Pane pane)
	{
		this.actors = new ArrayList<>();
		startTransient(pane);
		createActors();
		//this.player.getCastles().get(0).createOst(this.actors.get(1).getCastles().get(0), 20, 10, 0);
		this.canUpdate = true;
	}

	/**
	 *
	 * @param pane
	 */
	public void startTransient(final Pane pane)
	{
		this.playfieldLayer = pane;
		UIManager.getInstance().awake(this.playfieldLayer);
		this.colors = new ArrayList<>();
		this.colors.add(Color.DIMGRAY);
		this.colors.add(Color.DARKORANGE);
		this.colors.add(Color.LIGHTSLATEGRAY);
		this.colors.add(Color.AQUA);
		this.colors.add(Color.MEDIUMORCHID);
		this.colors.add(Color.GOLDENROD);

		if (Main.isNewGame)
		{
			this.player = new Player();
			this.player.start();
			this.player.setName("Player");
			this.actors.add(this.player);
			this.player.setColor(Color.LIMEGREEN);
		}
		else
		{
			Random rand = new Random();
			this.actors.stream().filter(actor -> actor.getClass() != Baron.class)
					.forEach(actor -> actor.startTransient(randomColor(rand), pane));
			Color c = randomColor(rand);
			this.actors.stream().filter(actor -> actor.getClass() == Baron.class).forEach(actor -> actor.startTransient(c, pane));

			this.actors.forEach(actor ->
			{
				actor.getCastles().forEach(castle ->
				{
					castle.startTransient(pane);
					// System.out.println(castle.getOst());
				});
				actor.addEventAllCastles();
			});

			UIManager.getInstance().switchCastle(this.player.getCastles().get(0), this.player);

			this.canUpdate = true;
		}
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	/**
	 *
	 * @param now
	 * @param pause
	 */
	public void update(final long now, final boolean pause)
	{
		if (this.canUpdate && !pause)
		{
			Iterator<Actor> it = this.actors.iterator();
			
			while(it.hasNext())
			{
				Actor actor = (Actor) it.next();
				if(actor.getCastles().size() != 0)
				{
					actor.update(now, pause);
				}
				else
				{
					it.remove();
				}
			}
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * 
	 */
	public void createActors()
	{
		Random rand = new Random();

		for (int i = 0; i < Settings.AI_NUMBER; i++)
		{
			Actor a = new DukeAI();
			a.start();
			a.setName("Duke " + (i + 1));
			this.actors.add(a);
			a.setColor(randomColor(rand));
		}

		Color colorBaron = randomColor(rand);

		for (int i = 0; i < Settings.BARON_NUMBER; i++)
		{
			Actor a = new Baron();
			a.start();
			a.setName("Baron " + (i + 1));
			this.actors.add(a);
			a.setColor(colorBaron);
		}

		ArrayList<Castle> list = new ArrayList<>();

		for (int i = 0; i < (Settings.AI_NUMBER + Settings.BARON_NUMBER + 1); i++)
		{

			Point2D p = getRandomCoordinates(rand);

			while (isCastleToClose(list, p) == true)
			{
				p = getRandomCoordinates(rand);
			}

			Castle c = new Castle();
			list.add(c);
			c.setColor(this.actors.get(i).getColor());
			c.start(1, this.playfieldLayer, p);
			this.actors.get(i).addFirstCastle(c);
		}
	}

	/**
	 * 
	 * @param rand
	 * @return
	 */
	private Color randomColor(final Random rand)
	{
		int size = this.colors.size();
		Color color = this.colors.get((rand.nextInt(20) + 1) % size);
		this.colors.remove(color);
		return color;
	}

	/**
	 * 
	 * @param castles
	 * @param coordinate
	 * @return
	 */
	private boolean isCastleToClose(final ArrayList<Castle> castles, final Point2D coordinate)
	{
		final Iterator<Castle> it = castles.iterator();
		while (it.hasNext())
		{
			final Castle currentCastle = it.next();
			final double d = distanceBetween(currentCastle, coordinate);
			if (d < Settings.MIN_DISTANCE_BETWEEN_TWO_CASTLE)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param castle
	 * @param coord
	 * @return
	 */
	private double distanceBetween(final Castle castle, final Point2D coord)
	{
		return Math.sqrt((coord.getY() - castle.getY()) * (coord.getY() - castle.getY())
				+ (coord.getX() - castle.getX()) * (coord.getX() - castle.getX()));
	}

	/**
	 * 
	 * @param rand
	 * @return
	 */
	public Point2D getRandomCoordinates(final Random rand)
	{
		return new Point2D(
				rand.nextInt((int) (Settings.SCENE_WIDTH * Settings.MARGIN_PERCENTAGE - 2 * Settings.CASTLE_SIZE)) + Settings.CASTLE_SIZE,
				rand.nextInt(Settings.SCENE_HEIGHT - (4 * Settings.CASTLE_SIZE)) + Settings.CASTLE_SIZE);
	}

	/**
	 * 
	 */
	protected void setCollisionsManagement()
	{
		// Kingdom.collisionsManagement = new Collisions();
		/*
		 * this.castles.forEach(castle -> { collisionsManagement.addPoint(castle.getCoordinate()); });
		 */
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * 
	 * @param playfieldLayer
	 */
	public void setPlayfieldLayer(final Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
	}
}
