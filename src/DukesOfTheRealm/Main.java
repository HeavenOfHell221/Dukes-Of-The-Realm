package DukesOfTheRealm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Duke.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.shape.*;

import Soldiers.*;
import javafx.geometry.Point2D;

public class Main extends Application {
	
	private Random rand;
	
	private Image CastleImage;
	private Image PikerImage;
	private Image OnagerImage;
	private Image KnightImage;

	private Knight knight;
	
	private Pane playfieldLayer;
	private Scene scene;
	private AnimationTimer gameLoop;
	private Group root;
	
	public Kingdom kingdom;
	
	public Input input;
	public FPS fps;
	
	private long lastUpdate = 0;
	private long timeScale = Settings.GAME_FREQUENCY; // => 1 second
	private long timePerTurn = timeScale * Settings.TIME_FACTOR;

	public void start(Stage primaryStage) 
	{
		root = new Group();
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT + Settings.STATUS_BAR_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		// create layers
		playfieldLayer = new Pane();
		root.getChildren().add(playfieldLayer);
		
		loadGame();
		
		gameLoop = new AnimationTimer() 
		{
			@Override
			public void handle(long now) 
			{
				processInput(input, now);
				Timer(now);
			}
			
			private void processInput(Input input, long now)
			{
				
				if(input.isExit())
				{
					Platform.exit();
					System.exit(0);
				}
				if(input.isSpace())
				{
					//kingdom.DeleteAllCastle();
					//CreateCastle();
				}
			}		
		};
		
		gameLoop.start();
	}
	
	private void loadGame()
	{
		/* Load images */
		KnightImage = new Image(getClass().getResource("/images/alien.png").toExternalForm(), 100, 100, true, true);
		
		input = new Input(scene);
		input.addListeners();
		rand = new Random();
		kingdom = new Kingdom();
		CreateCastle(10);
	}
	
	private void Timer(long now)
	{
		if((now - lastUpdate >= timePerTurn))
		{
			lastUpdate = now;
			kingdom.Update();
		}
	}
	
	private void CreateCastle(int count)
	{
		int numberValid = 0;
		
		while(numberValid != count)
		{
			if(kingdom.CreateCastle(playfieldLayer, KnightImage, kingdom.GetRandomGridCell(rand), 1, new Player("Player 1")))
			{
				numberValid++;
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
