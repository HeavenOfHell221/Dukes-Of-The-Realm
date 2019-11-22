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
import Utility.FPS;
import Utility.Input;
import Utility.Settings;
import javafx.geometry.Point2D;

public class Main extends Application {
	
	private Random rand;
	
	private Pane playfieldLayer;
	private Scene scene;
	private AnimationTimer gameLoop;
	private Group root;
	
	private Input input;
	private FPS fps;
	private Kingdom kingdom;
	
	private long lastUpdate = 0;
	private long timeScale = Settings.GAME_FREQUENCY; // => 1 second
	private long timePerTurn = timeScale * Settings.TURN_DURATION;

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
				fps.FrameStart(now);
				processInput(input, now);
				Timer(now);
				fps.Update(now);
				kingdom.Update(now);
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
					
				}
			}
				
			private void Timer(long now)
			{
				if((now - lastUpdate >= timePerTurn))	// Nouveau tour
				{
					System.out.println("Nouveau tour");
					lastUpdate = now;
				}
			}		
		};
		
		gameLoop.start();
	}
	
	private void loadGame()
	{
		/* INPUT */
		input = new Input(scene);
		input.addListeners();
		
		/* RANDOM */
		rand = new Random();
		
		/* FPS MANAGER */
		fps = new FPS(false);
		
		/* KINGDOM */
		kingdom = new Kingdom(playfieldLayer);
		playfieldLayer.getChildren().add(kingdom);
		kingdom.CreateWorld(Settings.AI_NUMBER, Settings.BARON_NUMBER);
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
