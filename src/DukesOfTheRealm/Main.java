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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.scene.shape.*;

import Soldiers.*;
import UI.CastleUI;
import Utility.Time;
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
	private Time time;
	private Kingdom kingdom;
	
	private CastleUI castleUI;
	
	private long lastTime = 0;
	private boolean pause = false;

	@Override
	public void start(Stage primaryStage) 
	{
		root = new Group();
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		Image i = new Image(getClass().getResource("/images/bg2.jpg").toExternalForm());
		ImagePattern pattern = new ImagePattern(i, 0, 0, 1, 1, true);
		scene.setFill(pattern);
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		primaryStage.setFullScreen(false);
		primaryStage.show();
		Settings.SCENE_WIDTH = (int) primaryStage.getWidth();
		Settings.SCENE_HEIGHT = (int) primaryStage.getHeight();

		// create layers
		playfieldLayer = new Pane();
		root.getChildren().add(playfieldLayer);
		
		Start();
		
		gameLoop = new AnimationTimer() 
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
	
	@Override
	public void Start()
	{
		/* INPUT */
		input = new Input(scene);
		input.addListeners();
		
		/* RANDOM */
		rand = new Random();
		
		/* TIME MANAGER */
		time = new Time(false);
		
		/* KINGDOM */
		kingdom = new Kingdom(playfieldLayer);
		playfieldLayer.getChildren().add(kingdom);
		kingdom.Start();
		
		/* UI */
		CastleUI.GetInstance().SetPlayfieldLayer(playfieldLayer);
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
