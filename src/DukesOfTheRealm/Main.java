package DukesOfTheRealm;

import Interface.IUpdate;
import SaveSystem.SaveSystem;
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

public class Main extends Application
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	private Pane playfieldLayer;
	private Scene mainScene;
	private AnimationTimer lobbyGameLoop;
	private AnimationTimer mainGameLoop;
	private Group root;
	private Input input;
	private Time time;
	private Kingdom kingdom;

	private long lastTime = 0;
	private boolean pause = false;
	
	public static boolean isNewGame = false;

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

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
				update(now, Main.this.pause);
			}

			private void processInput(final Input input, final long now)
			{
				if (input.isExit())
				{
				    SaveSystem.save(kingdom);
					Platform.exit();
					System.exit(0);
				}
				if (input.isSpace() && Time(now))
				{
					Main.this.pause = !Main.this.pause;
				}
			}
		};

		this.lobbyGameLoop.start();
	}

	private void Awake(final Stage primaryStage)
	{
		this.root = new Group();
		this.mainScene = new Scene(this.root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		this.mainScene.setFill(new ImagePattern(new Image(getClass().getResource("/images/bg2.jpg").toExternalForm()), 0, 0, 1, 1, true));
		primaryStage.setScene(this.mainScene);
		primaryStage.setResizable(true);
		primaryStage.setMaximized(false);
		primaryStage.setFullScreen(false);
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

	public void update(final long now, final boolean pause)
	{
		this.time.update(now, pause);
		this.kingdom.update(now, pause);
		UIManager.getInstance().update(now, pause);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	private boolean Time(final long now)
	{
		if (now - this.lastTime > Settings.GAME_FREQUENCY / 5)
		{
			this.lastTime = now;
			SaveSystem.save(kingdom);
			return true;
		}
		return false;
	}

	private void newGame()
	{
		System.out.println("Start new game... ");
		isNewGame = true;
		kingdom = new Kingdom();
		kingdom.start(this.playfieldLayer);
		
		System.out.println("New game done !");
	}
	
	private void loadGame()
	{
		System.out.println("Start load game... ");
		kingdom = SaveSystem.load();
		if(kingdom != null)
			System.out.println("Load game done !\n");
		else
			System.out.println("Error load !");
		
		kingdom.startTransient(this.playfieldLayer);
	}

	public static void main(final String[] args)
	{
		launch(args);
	}
}
