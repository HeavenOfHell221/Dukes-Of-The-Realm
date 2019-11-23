package DukesOfTheRealm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Duke.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.*;

import Soldiers.*;
import Utility.FPS;
import Utility.Input;
import Utility.Settings;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public class Main extends Application implements IUpdate{
	
	private Random rand;
	
	private Pane playfieldLayer;
	private Scene scene;
	private AnimationTimer gameLoop;
	private Group root;
	
	private Input input;
	private FPS fps;
	private Kingdom kingdom;
	
	private CastleUI castleUI;
	
	private long lastTime = 0;
	private boolean pause = false;

	public void start(Stage primaryStage) 
	{
		root = new Group();
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT, Color.DARKGREEN);
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		primaryStage.show();
		
		System.out.println(Settings.SCENE_WIDTH);
		
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
				if(!pause)
					Update(now);
			}
			
			private void processInput(Input input, long now)
			{
				if(input.isExit())
				{
					Platform.exit();
					System.exit(0);
				}
				if(input.isSpace() && Time(now))
				{
					pause = !pause;
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
		kingdom.Start();
		
		/* UI */
		CastleUI.GetInstance().SetPlayfieldLayer(playfieldLayer);
	}
	
	public void Update(long now)
	{
		fps.Update(now);
		kingdom.Update(now);
	}
	
	private boolean Time(long now)
	{
		if(now - lastTime > Settings.GAME_FREQUENCY)
		{
			lastTime = now;
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
