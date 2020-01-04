package DukesOfTheRealm;

import static Utility.Settings.AI_NUMBER;
import static Utility.Settings.BARON_NUMBER;
import static Utility.Settings.CASTLE_SIZE;
import static Utility.Settings.MARGIN_PERCENTAGE;
import static Utility.Settings.MIN_DISTANCE_BETWEEN_TWO_CASTLE;
import static Utility.Settings.SCENE_HEIGHT;
import static Utility.Settings.SCENE_WIDTH;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Duke.Actor;
import Duke.Baron;
import Duke.DukeAI;
import Duke.Player;
import Enums.CharacterDukeEnum;
import Interface.IUpdate;
import UI.UIManager;
import Utility.Collision;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Représente le royaume. Contient tous les acteurs du royaume.
 */
public class Kingdom extends Parent implements Serializable, IUpdate
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Liste des acteurs (joueur, IA et baron) du royaume.
	 *
	 * @see Kingdom#update(long now, boolean pause)
	 * @see Actor
	 */
	private ArrayList<Actor> actors;

	/**
	 * Liste des couleurs atribuables à chaque acteur.
	 */
	private transient ArrayList<Color> colors;

	/**
	 * Référence au joueur.
	 *
	 * @see Player
	 */
	private Player player;

	/**
	 * Pane principal du jeu.
	 *
	 * @see Main#playfieldLayer
	 */
	private transient Pane playfieldLayer;

	/**
	 * Condition pour que le royaume se mette à jour.
	 *
	 * @see Kingdom#update(long, boolean)
	 */
	private transient boolean canUpdate = false;

	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	/**
	 * Initialise le kingdom.
	 *
	 * @param pane Le pane principal.
	 * @see        Main#newGame()
	 */
	public void start(final Pane pane)
	{
		this.actors = new ArrayList<>();
		startTransient(pane);
		createWorld();
		this.canUpdate = true;
	}

	/**
	 * Initialise les composants transients du jeu.
	 *
	 * @param pane Le pane principal.
	 * @see        Main#loadGame()
	 * @see        Kingdom#start(Pane)
	 */
	public void startTransient(final Pane pane)
	{
		this.playfieldLayer = pane;
		UIManager.getInstance().awake(this.playfieldLayer);
		this.colors = new ArrayList<>();
		this.colors.add(Color.AQUA);
		this.colors.add(Color.MEDIUMORCHID);
		this.colors.add(Color.GOLDENROD);
		this.colors.add(Color.LIGHTPINK);
		this.colors.add(Color.MAROON);
		this.colors.add(Color.CHOCOLATE);
		this.colors.add(Color.IVORY);

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

			// Donne une couleur aléatoire au Duke et lance le startTransient
			this.actors.stream().filter(actor -> actor.getClass() == DukeAI.class)
					.forEach(actor -> actor.startTransient(randomColor(rand), pane));

			// Donne la même couleur à tout les Baron et lance le startTransient
			this.actors.stream().filter(actor -> actor.getClass() == Baron.class)
					.forEach(actor -> actor.startTransient(Color.DARKGREY, pane));

			// Donne la couleur LIMEGREEN au joueur et lance le startTransient
			this.actors.stream().filter(actor -> actor.isPlayer()).limit(1).forEach(actor -> actor.startTransient(Color.LIMEGREEN, pane));

			// Start la fonction startTransient de tout les châteaux
			this.actors.forEach(actor ->
			{
				actor.getCastles().forEach(castle ->
				{
					castle.startTransient(pane);
					Collision.addPoint(castle.getCoordinate());
				});
				actor.addEventAllCastles();
			});

			if (this.player.getCastles().size() > 0)
			{
				UIManager.getInstance().switchCastle(this.player.getCastles().get(0));
			}

			this.canUpdate = true;
		}

	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	/**
	 * Fonction appelée à chaque image.
	 * <p>
	 * Elle permet de mettre à jour tout le jeu. On met à jour les acteurs qui eux même mettent à jour
	 * leurs châteaux, qui eux même mettent à jour leur ost, <br>
	 * qui eux même mettent à jour leur unités sur le terrain...
	 * </p>
	 *
	 * @see Main#update(long, boolean)
	 * @see Interface.IUpdate
	 */
	@Override
	public void update(final long now, final boolean pause)
	{
		if (this.canUpdate && !pause)
		{
			Iterator<Actor> it = this.actors.iterator();

			while (it.hasNext())
			{
				Actor actor = it.next();
				if (!actor.isDead)
				{
					actor.update(now, pause);
				}
				else
				{
					if (actor.isPlayer())
					{
						Text t = new Text();
						this.playfieldLayer.getChildren().add(t);
						t.setText("Game Over");
						t.setFont(new Font(130));
						t.setWrappingWidth(800);
						t.setTextAlignment(TextAlignment.LEFT);
						t.setFill(Color.BLACK);
						t.relocate(SCENE_WIDTH / 3, SCENE_HEIGHT / 3);
						this.canUpdate = false;
					}

					// Dans tout les cas on retire l'acteur de la liste
					it.remove();
				}
			}

			if (this.actors.size() == 1 && this.actors.get(0).isPlayer())
			{
				Text t = new Text();
				this.playfieldLayer.getChildren().add(t);
				t.setText("Win !");
				t.setFont(new Font(130));
				t.setWrappingWidth(800);
				t.setTextAlignment(TextAlignment.LEFT);
				t.setFill(Color.BLACK);
				t.relocate(SCENE_WIDTH / 2.5f, SCENE_HEIGHT / 3);
				this.canUpdate = false;
			}
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * Fonction créant les acteurs et les châteaux lors d'un nouveau jeu.
	 * <p>
	 * Elle va créer les IA et les Baron puis le nombre de château suffisant pour que chaque acteur ait
	 * un château au début (joueur compris). <br>
	 * A la création d'un château, elle teste la distance avec les autres pour respecter la distance
	 * minimale entre 2 châteaux. <br>
	 * Elle affecte également une couleur à chaque IA et une couleur commune à tous les Baron.
	 * </p>
	 *
	 * @see Kingdom#start(Pane)
	 * @see Kingdom#randomColor(Random)
	 * @see Settings#AI_NUMBER
	 * @see Settings#BARON_NUMBER
	 * @see Settings#MIN_DISTANCE_BETWEEN_TWO_CASTLE
	 */
	public void createWorld()
	{
		Random rand = new Random();

		// Création des IA
		for (int i = 0; i < AI_NUMBER; i++)
		{
			DukeAI a = new DukeAI();
			a.setKingdom(this);
			a.setName("Duke " + (i + 1));
			this.actors.add(a);
			a.setColor(randomColor(rand));
			a.setCharacter(CharacterDukeEnum.getRandomType());
		}

		Color colorBaron = Color.DARKGRAY;

		// Création des Baron
		for (int i = 0; i < BARON_NUMBER; i++)
		{
			Baron a = new Baron();
			a.start();
			a.setName("Baron " + (i + 1));
			this.actors.add(a);
			a.setColor(colorBaron);
		}

		final ArrayList<Castle> listCastle = new ArrayList<>();
		final int iterationMax = 10000;

		// Création des châteaux
		for (int i = 0; i < AI_NUMBER + BARON_NUMBER + 1; i++)
		{
			Point2D p = getRandomCoordinates(rand);
			int k = 0;

			while (isCastleToClose(listCastle, p) == true && k < iterationMax)
			{
				p = getRandomCoordinates(rand);
				k++;
			}

			if (k < iterationMax)
			{
				Castle castle = new Castle();
				listCastle.add(castle);
				castle.setColor(this.actors.get(i).getColor());
				castle.start(1, this.playfieldLayer, p, this.actors.get(i));
				this.actors.get(i).addFirstCastle(castle);
				Collision.addPoint(castle.getCoordinate());
			}
		}
	}

	/**
	 * Récupère une couleur au hasard et la renvoie.
	 *
	 * @param  rand Un objet de type Random.
	 * @return      Une couleur de la liste colors.
	 * @see         Kingdom#createWorld()
	 * @see         Kingdom#colors
	 */
	private Color randomColor(final Random rand)
	{
		Color color = this.colors.get(rand.nextInt(this.colors.size()));
		this.colors.remove(color);
		return color;
	}

	/**
	 * Teste s'il existe un château trop proche des coordonnées données en paramètre.
	 *
	 * @param  castles    Liste des châteaux déjà  crées.
	 * @param  coordinate Les coordonnées du potentiel futur château.
	 * @return            Retourne true si la distance est respectée, false sinon.
	 * @see               Kingdom#createWorld()
	 * @see               Kingdom#distanceBetween(Point2D, Point2D)
	 * @see               Settings#MIN_DISTANCE_BETWEEN_TWO_CASTLE
	 */
	private boolean isCastleToClose(final ArrayList<Castle> castles, final Point2D coordinate)
	{
		final Iterator<Castle> it = castles.iterator();
		while (it.hasNext())
		{
			if (distanceBetween(it.next().getCoordinate(), coordinate) < MIN_DISTANCE_BETWEEN_TWO_CASTLE)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Calcule la distance entre deux points (les coordonnées d'un château et les coordonnées du château
	 * qu'on voudrait créer).
	 *
	 * @param  castleCoord Les coordonnées du château courant.
	 * @param  coord       Les coordonnées du château qu'on voudrait créer.
	 * @return             Retourne la distance entre les deux points.
	 * @see                Kingdom#isCastleToClose(ArrayList, Point2D)
	 */
	private double distanceBetween(final Point2D castleCoord, final Point2D coord)
	{
		final double deltaY = coord.getY() - castleCoord.getY();
		final double deltaX = coord.getX() - castleCoord.getX();
		return Math.sqrt(deltaY * deltaY + deltaX * deltaX);
	}

	/**
	 * Renvoie des coordonnées aléatoires pour positionner un château.
	 *
	 * @param  rand Un objet de type Random pour l'aléatoire.
	 * @return      Retourne des coordonnées.
	 * @see         Kingdom#createWorld()
	 */
	public Point2D getRandomCoordinates(final Random rand)
	{
		final int x = rand.nextInt((int) (SCENE_WIDTH / CASTLE_SIZE * MARGIN_PERCENTAGE) - 1) * CASTLE_SIZE;
		final int y = rand.nextInt(SCENE_HEIGHT / CASTLE_SIZE - 2) * CASTLE_SIZE;
		return new Point2D(x + CASTLE_SIZE, y + CASTLE_SIZE);
	}

	/**
	 * Renvoie un acteur aléatoire du royaume en ne prennant pas en compte l'acteur qui utilise cette
	 * fonction.
	 *
	 * @param  actor L'acteur qui serra retirÃ© de la liste
	 * @return       Retourne un acteur du royaume ou null si la liste ne contient que l'acteur
	 *               appelelant cette fonction.
	 * @see          Goals.GeneratorGoal#getNewGoalBattle(Castle)
	 */
	public Actor getRandomActor(final Actor actor)
	{
		final Random rand = new Random();
		final ArrayList<Actor> list = new ArrayList<>();

		list.addAll(this.actors);
		list.remove(actor);

		if (list.size() > 0)
		{
			return list.get(rand.nextInt(list.size()));
		}

		this.canUpdate = false;
		return null;
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @param playfieldLayer le playfieldLayer à mettre en place
	 */
	public final void setPlayfieldLayer(final Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
	}

}
