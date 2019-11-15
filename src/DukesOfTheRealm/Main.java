package DukesOfTheRealm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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

import Soldiers.Knight;
import Soldiers.Soldier;

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
	
	private int gridX = (int) Settings.SCENE_WIDTH / 10;
	private int gridY = (int)Settings.SCENE_HEIGHT / 10;
	
	public Input input;

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
					System.out.println("j'appuie sur espace");
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
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void spawnAtRandomCoordinate(Sprite sprite)
	{
		int x = (int) (rand.nextDouble() * gridX) % gridX;
		int y = (int) (rand.nextDouble() * gridY) % gridY;
	}
}
