package DukesOfTheRealm;


import java.util.Random;

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

public class Main extends Application implements IUpdate{

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
	private Random rand;

	private long lastTime = 0;
	private boolean pause = false;


	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start(final Stage primaryStage)
	{

		Awake(primaryStage);

		/* LOBBY GAME LOOP */
		lobbyGameLoop = new AnimationTimer()
		{
			@Override
			public void handle(final long now)
			{
				processInput(input, now);
			}

			private void processInput(final Input input, final long now)
			{
				if(input.isExit())
				{
					Platform.exit();
					System.exit(0);
				}
			}
		};

		/* MAIN GAME LOOP */
		mainGameLoop = new AnimationTimer()
		{
			@Override
			public void handle(final long now)
			{
				processInput(input, now);
				Update(now, pause);
			}

			private void processInput(final Input input, final long now)
			{
				if(input.isExit())
				{
					Save();
					Platform.exit();
					System.exit(0);
				}
				if(input.isSpace() && Time(now))
				{
					pause = !pause;
					SaveSystem.Load();
				}
			}
		};

		lobbyGameLoop.start();
	}

	@Override
	public void Start()
	{

		/* UI */
		UIManager.GetInstance().SetPlayfieldLayer(playfieldLayer);
		UIManager.GetInstance().Awake();

		/* KINGDOM */
		kingdom = new Kingdom(playfieldLayer);
		playfieldLayer.getChildren().add(kingdom);
		kingdom.Start();


	}

	private void Awake(final Stage primaryStage)
	{
		root = new Group();
		mainScene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		mainScene.setFill(new ImagePattern(new Image(getClass().getResource("/images/bg2.jpg").toExternalForm()), 0, 0, 1, 1, true));
		primaryStage.setScene(mainScene);
		primaryStage.setResizable(true);
		primaryStage.setMaximized(false);
		primaryStage.setFullScreen(true);
		primaryStage.show();
		Settings.SCENE_WIDTH = (int) primaryStage.getWidth();
		Settings.SCENE_HEIGHT = (int) primaryStage.getHeight();

		/* LAYER */
		playfieldLayer = new Pane();
		root.getChildren().add(playfieldLayer);

		/* INPUT */
		input = new Input(mainScene);
		input.addListeners();

		/* RANDOM */
		rand = new Random();

		/* TIME MANAGER */
		time = new Time(false);

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
			mainGameLoop.start();
			lobbyGameLoop.stop();
			Start();
			root.getChildren().remove(buttonPlay);
			root.getChildren().remove(buttonNew);
		});

		buttonNew.setOnMousePressed(event ->
		{
			mainGameLoop.start();
			lobbyGameLoop.stop();
			Start();
			root.getChildren().remove(buttonPlay);
			root.getChildren().remove(buttonNew);
		});

		root.getChildren().add(buttonPlay);
		root.getChildren().add(buttonNew);
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void Update(final long now, final boolean pause)
	{
		time.Update(now, pause);
		kingdom.Update(now, pause);
		UIManager.GetInstance().Update(now, pause);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/


	private boolean Time(final long now)
	{
		if(now - lastTime > Settings.GAME_FREQUENCY / 5)
		{
			lastTime = now;
			return true;
		}
		return false;
	}

	private void Save()
	{
		SaveSystem.Save(kingdom);
	}

	public static void main(final String[] args) {
		launch(args);
	}
}
