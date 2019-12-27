package DukesOfTheRealm;

import Interface.IUpdate;
import SaveSystem.SaveSystem;
import UI.UIAttackPreview;
import UI.UIManager;
import Utility.Input;
import Utility.Settings;
import Utility.Time;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Main, initialise l'application.
 */
public class Main extends Application implements IUpdate
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Nombre d'unité affiché sur la grille de jeu. Utilisé pour effectuer des tests de performance.
	 */
	public static int nbSoldier;

	/**
	 * Pane principal pour afficher les éléments graphiques.
	 * 
	 * @see Main#Awake(Stage)
	 */
	private Pane playfieldLayer;

	/**
	 * Scene principale.
	 * 
	 * @see Main#Awake(Stage)
	 */
	private Scene mainScene;

	/**
	 * Boucle de jeu du Lobby.
	 * 
	 * @see Main#Awake(Stage)
	 * @see Main#start(Stage)
	 */
	private AnimationTimer lobbyGameLoop;

	/**
	 * Boucle de jeu principale.
	 * 
	 * @see Main#Awake(Stage)
	 * @see Main#start(Stage)
	 */
	private AnimationTimer mainGameLoop;

	/**
	 * Group root de JavaFX.
	 * 
	 * @see Main#start(Stage)
	 */
	private Group root;

	/**
	 * Gère les inputs (Espace et Space) du joueur.
	 * 
	 * @see Input
	 */
	private Input input;

	/**
	 * Utilitaire pour calculer les IPS (Image Par Seconde).
	 * 
	 * @see Time
	 */
	private Time time;

	/**
	 * Le royaume dans lequel le jeu ce déroulera.
	 * 
	 * @see Kingdom
	 */
	private Kingdom kingdom;

	/**
	 * Temps à laquelle l'image précédente à commencé.
	 * 
	 * @see Main#Time(long)
	 */
	private long lastTime = 0;

	/**
	 * Active ou non la pause du jeu.
	 * 
	 * @see Main#start(Stage)
	 */
	public static boolean pause = false;

	/**
	 * Force la pause du jeu lors d'un lancement d'une ost.
	 * 
	 * @see UIManager#switchCastle(Castle)
	 * @see UIAttackPreview#reset()
	 */
	public boolean pauseForce = false;

	/**
	 * Est ce que c'est un nouveau jeu ou une sauvegarde qu'on récupère.
	 * 
	 * @see Main#newGame()
	 * @see Kingdom#startTransient(Pane)
	 */
	public static boolean isNewGame = false;

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	/**
	 * Fonction start de l'application.
	 * 
	 * @param primaryStage Le stage par défaut construit par JavaFX.
	 */
	@Override
	public void start(final Stage primaryStage)
	{

		Awake(primaryStage);

		/* LOBBY GAME LOOP */
		this.lobbyGameLoop = new AnimationTimer()
		{
			@Override
			public void handle(final long now)
			{
				processInput(Main.this.input, now);
			}

			private void processInput(final Input input, final long now)
			{
				if (input.isExit())
				{
					Platform.exit();
					System.exit(0);
				}
			}
		};

		/* MAIN GAME LOOP */
		this.mainGameLoop = new AnimationTimer()
		{
			@Override
			public void handle(final long now)
			{
				processInput(Main.this.input, now);
				update(now, Main.pause || Main.this.pauseForce);
			}

			private void processInput(final Input input, final long now)
			{
				if (input.isExit())
				{
					SaveSystem.save(Main.this.kingdom);
					Platform.exit();
					System.exit(0);
				}
				if (input.isSpace() && Time(now))
				{
					Main.this.pauseForce = !Main.this.pauseForce;
				}
			}
		};

		this.lobbyGameLoop.start();
	}

	/**
	 * Création des premiers éléments du jeu (group, mainScene, playfieldLayer, etc).
	 * <p>
	 * Les boutons du Lobby sont également créés ici.
	 * </p>
	 * 
	 * @param primaryStage Le stage par défaut construit par JavaFX.
	 * @see                Main#root
	 * @see                Main#mainScene
	 * @see                Main#playfieldLayer
	 * @see                Main#input
	 * @see                Main#time
	 */
	private void Awake(final Stage primaryStage)
	{
		this.root = new Group();
		this.mainScene = new Scene(this.root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		this.mainScene.setFill(new ImagePattern(new Image(getClass().getResource("/images/bg2.jpg").toExternalForm()), 0, 0, 1, 1, true));
		primaryStage.setScene(this.mainScene);
		primaryStage.setResizable(false);
		primaryStage.setMaximized(false);
		primaryStage.setFullScreen(true);
		primaryStage.show();
		Settings.SCENE_WIDTH = (int) primaryStage.getWidth();
		Settings.SCENE_HEIGHT = (int) primaryStage.getHeight();

		/* LAYER */
		this.playfieldLayer = new Pane();
		this.root.getChildren().add(this.playfieldLayer);

		/* INPUT */
		this.input = new Input(this.mainScene);
		this.input.addListeners();

		/* TIME MANAGER */
		this.time = new Time(false);

		/* BUTTONS */
		final Button buttonPlay = new Button();
		final Button buttonNew = new Button();

		buttonPlay.setText("Play");
		buttonNew.setText("New Game");

		buttonPlay.setFont(new Font(30));
		buttonNew.setFont(new Font(30));

		buttonPlay.relocate(900, 430);
		buttonNew.relocate(854, 520);

		buttonPlay.setOnMousePressed(event ->
		{
			this.root.getChildren().remove(buttonPlay);
			this.root.getChildren().remove(buttonNew);
			loadGame();
			this.mainGameLoop.start();
			this.lobbyGameLoop.stop();
		});

		buttonNew.setOnMousePressed(event ->
		{
			this.root.getChildren().remove(buttonPlay);
			this.root.getChildren().remove(buttonNew);
			newGame();
			this.mainGameLoop.start();
			this.lobbyGameLoop.stop();

		});

		this.root.getChildren().add(buttonPlay);
		this.root.getChildren().add(buttonNew);
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	/**
	 * Actualise à chaque image le royaume, l'utilitaire time ainsi que le UI.
	 * <p>
	 * Va être appelé dans le handle de la boucle mainGameLoop.
	 * </p>
	 * 
	 * @see         Main#start(Stage)
	 * @see Interface.IUpdate
	 */
	@Override
	public void update(final long now, final boolean pause)
	{
		this.time.update(now, pause);
		this.kingdom.update(now, pause);
		UIManager.getInstance().update(now, pause);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * Calcul le delta entre le temps now et le lastTime.
	 * 
	 * @param  now Temps à laquelle l'image à commencé.
	 * @return     Retourne true si le delta entre now et lastTime est supérieur à 0.2 secondes.
	 * @see        Main#lastTime
	 * @see        Main#start(Stage)
	 */
	private boolean Time(final long now)
	{
		if (now - this.lastTime > Settings.GAME_FREQUENCY / 5)
		{
			this.lastTime = now;
			return true;
		}
		return false;
	}

	/**
	 * Initialise un nouveau jeu en créant un royaume.
	 * 
	 * @see Main#Awake(Stage)
	 * @see Kingdom
	 * @see Kingdom#start(Pane)
	 * @see Main#isNewGame
	 */
	private void newGame()
	{
		System.out.println("Start new game... ");
		isNewGame = true;
		this.kingdom = new Kingdom();
		this.kingdom.start(this.playfieldLayer);

		System.out.println("New game done !");
	}

	/**
	 * Charge une sauvegarde précédemment créée.
	 * <p>
	 * Utilise la fonction startTrantient(Pane) pour recréer les éléments de JavaFX non sauvegardé.
	 * </p>
	 * 
	 * @see Main#Awake(Stage)
	 * @see SaveSystem#load()
	 * @see Kingdom#startTransient(Pane)
	 */
	private void loadGame()
	{
		System.out.println("Start load game... ");
		this.kingdom = SaveSystem.load();
		if (this.kingdom != null)
		{
			System.out.println("Load game done !\n");
		}
		else
		{
			System.out.println("Error load !");
		}

		this.kingdom.startTransient(this.playfieldLayer);
	}

	/**
	 * Entrée du programme (main).
	 * 
	 * @param args Les arguments du programme.
	 */
	public static void main(final String[] args)
	{
		launch(args);
	}
}
