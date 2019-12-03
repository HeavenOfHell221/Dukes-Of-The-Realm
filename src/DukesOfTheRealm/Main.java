package DukesOfTheRealm;


import java.util.Random;

import Interface.IUpdate;
import SaveSystem.SaveSystem;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import UI.UIManager;
import Utility.Time;
import Utility.Input;
import Utility.Settings;

public class Main extends Application implements IUpdate{
	
	public static Pane playfieldLayer;
	private Scene mainScene;
	private AnimationTimer lobbyGameLoop;
	private AnimationTimer mainGameLoop;
	private Group root;
	private Input input;
	private Time time;
	public static Kingdom kingdom;
	
	private boolean stopGame = false;
	private boolean lobby = true;
	private boolean mainGame = false;
	
	/********************* UI ***********************/
	
	private UIManager castleUI;
	
	/******************** Other ********************/
	
	private Random rand;
	private long lastTime = 0;
	private boolean pause = false;
	
	/******************* Methodes ***************/

	@Override
	public void start(Stage primaryStage) 
	{
		root = new Group();
		mainScene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		Image i = new Image(getClass().getResource("/images/bg2.jpg").toExternalForm());
		ImagePattern pattern = new ImagePattern(i, 0, 0, 1, 1, true);
		mainScene.setFill(pattern);
		primaryStage.setScene(mainScene);
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		primaryStage.setFullScreen(false);
		primaryStage.show();
		Settings.SCENE_WIDTH = (int) primaryStage.getWidth();
		Settings.SCENE_HEIGHT = (int) primaryStage.getHeight();

		// create layers
		playfieldLayer = new Pane();
		root.getChildren().add(playfieldLayer);
		
		/* INPUT */
		input = new Input(mainScene);
		input.addListeners();
		
		/* RANDOM */
		rand = new Random();
		
		/* TIME MANAGER */
		time = new Time(false);
		
		Button buttonPlay = new Button();
		Button buttonNew = new Button();
		
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
		
		
		lobbyGameLoop = new AnimationTimer()
		{
			@Override
			public void handle(long now) 
			{	
				processInput(input, now);
			}
			
			private void processInput(Input input, long now)
			{
				if(input.isExit())
				{
					Platform.exit();
					System.exit(0);
				}
			}
		};
		
		mainGameLoop = new AnimationTimer() 
		{
			@Override
			public void handle(long now) 
			{
				processInput(input, now);
				Update(now, pause);
			}
			
			private void processInput(Input input, long now)
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
		/* KINGDOM */
		kingdom = new Kingdom(playfieldLayer);
		//kingdom = Load();
		playfieldLayer.getChildren().add(kingdom);
		kingdom.Start();
		
		/* UI */
		UIManager.GetInstance().SetPlayfieldLayer(playfieldLayer);
	}
	
	@Override
	public void Update(long now, boolean pause)
	{
		time.Update(now, pause);
		kingdom.Update(now, pause);
	}
	
	private boolean Time(long now)
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
