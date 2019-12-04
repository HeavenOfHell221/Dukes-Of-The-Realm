package UI;

import DukesOfTheRealm.Castle;
import Interface.IUI;
import Interface.IUpdate;
import Utility.Settings;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;

public final class UIProductionUnitPreview extends Parent implements IUI, IUpdate {

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	private Button buttonCreatePiker;
	private Button buttonCreateKnight;
	private Button buttonCreateOnager;
	private Button buttonUpgradeCastle;
	
	private Rectangle background;
	
	private Rectangle backgroundTime;
	private Rectangle fillTime;
	
	private Castle currentCastle;
	float i =0;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	public UIProductionUnitPreview() 
	{
		super();
		
		this.buttonCreatePiker = new Button();
		this.buttonCreateKnight = new Button();
		this.buttonCreateOnager = new Button();
		this.buttonUpgradeCastle = new Button();
		
		this.background = new Rectangle(280, 300);
		
		this.backgroundTime = new Rectangle(240, 40);
		this.fillTime = new Rectangle(30, 40); // entre +00 et +210
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start() 
	{
		AddAllNodes();
		RelocateAllNodes();
		SetAllButtons();
		SetBackground();
		SetBar();
	}
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void Update(long now, boolean pause) 
	{
		i += 0.001;
		SetFill(i);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	@Override
	public void Relocate(Node node, double x, double y)
	{
		node.relocate(x, y);
		node.toBack();
	}
	
	public void SetFill(double fractionFill)
	{
		if(fractionFill >= 1)
		{
			this.fillTime.setWidth(0);
			return;
		}
		this.fillTime.setWidth(20 + (fractionFill * 220));
	}
	
	private void SetBar()
	{
		this.backgroundTime.setFill(Color.TAN);
		this.backgroundTime.setStrokeWidth(4);
		this.backgroundTime.setStrokeType(StrokeType.OUTSIDE);
		this.backgroundTime.setStroke(Color.BLANCHEDALMOND);
		this.backgroundTime.setArcHeight(40);
		this.backgroundTime.setArcWidth(40);
			
		this.fillTime.setFill(Color.SEAGREEN);
		this.fillTime.setArcHeight(40);
		this.fillTime.setArcWidth(40);
	}
	
	private void SetAllButtons()
	{
		this.buttonCreateKnight.setStyle(""
				+ "-fx-background-color: transparent;"
				+ "-fx-background-image: url('/images/mounted-knight2.png'); "
				+ "-fx-background-size: 64px 64px; "
				+ "-fx-background-repeat: no-repeat; "
				);
		
		this.buttonCreatePiker.setStyle(""
				+ "-fx-background-color: transparent;"
				+ "-fx-background-image: url('/images/spartan2.png'); "
				+ "-fx-background-size: 64px 64px; "
				+ "-fx-background-repeat: no-repeat; "
				);
		

		this.buttonCreateOnager.setStyle(""
				+ "-fx-background-color: transparent;"
				+ "-fx-background-image: url('/images/catapult2.png'); "
				+ "-fx-background-size: 64px 64px; "
				+ "-fx-background-repeat: no-repeat; "
				);
		
		this.buttonUpgradeCastle.setStyle(""
				+ "-fx-background-color: transparent;"
				+ "-fx-background-image: url('/images/mounted-knight.png'); "
				+ "-fx-background-size: 64px 64px; "
				+ "-fx-background-repeat: no-repeat; "
				);
		
		this.buttonCreateOnager.setOnMousePressed(
			event -> 
			{ 
				buttonCreateOnager.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.LIGHTGREEN, 15, 0.33, 0, 0)); 
				// TODO : Creation d'un onager
			});
		
		this.buttonCreatePiker.setOnMousePressed(
			event -> 
			{ 
				buttonCreatePiker.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.LIGHTGREEN, 15, 0.33, 0, 0)); 
				// TODO : Creation d'un piker
			});
		
		
		this.buttonCreateKnight.setOnMousePressed(
			event -> 
			{ 
				buttonCreateKnight.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.ORANGERED, 15, 0.33, 0, 0)); 
				// TODO : Creation d'un knight
			});
		
		this.buttonUpgradeCastle.setOnMousePressed(
				event -> 
				{ 
					buttonUpgradeCastle.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.ORANGERED, 15, 0.33, 0, 0)); 
					// TODO : upgrade castle
				});
		
		AddEventMouse(this.buttonCreateKnight);
		AddEventMouse(this.buttonCreateOnager);
		AddEventMouse(this.buttonCreatePiker);
		AddEventMouse(this.buttonUpgradeCastle);
	}
	
	private void SetBackground()
	{
		Stop[] stops = new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.WHITE)};
	    LinearGradient lg2 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		this.background.setStroke(lg2);
		
		this.background.setStrokeWidth(3);
		this.background.setArcHeight(60);
		this.background.setArcWidth(60);
	}
	
	private void AddEventMouse(Button b)
	{
		b.setMinSize(64, 64);
		b.setMaxSize(64, 64);
		b.setCursor(Cursor.HAND);
		b.addEventHandler(MouseEvent.MOUSE_ENTERED, 
			event -> 
			{ 
				Bloom bloom = new Bloom();
			    bloom.setThreshold(0.85);
			    bloom.setInput(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 15, 0.25, 0, 0)); 
			    b.setEffect(bloom);
			});
		
		b.addEventHandler(MouseEvent.MOUSE_EXITED, 
			event -> 
			{ 
				b.setEffect(null); 
			});
	
		b.setOnMouseClicked(
			event -> 
			{
				b.setEffect(null);
				Bloom bloom = new Bloom();
			    bloom.setThreshold(0.85);
			    bloom.setInput(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 15, 0.25, 0, 0)); 
			    b.setEffect(bloom);
			});
	}
	
	@Override
	public void AddAllNodes()
	{
		AddNode(this.background);
		AddNode(this.backgroundTime);
		AddNode(this.buttonCreateKnight);
		AddNode(this.buttonCreateOnager);
		AddNode(this.buttonCreatePiker);
		AddNode(this.buttonUpgradeCastle);
		AddNode(this.fillTime);
	}

	@Override
	public void RelocateAllNodes() 
	{
		int i = 90;
		int offset = 540;
		
		float margin = (float) (Settings.MARGIN_PERCENTAGE) + 0.076f;
		
		Relocate(this.buttonCreateKnight,  Settings.SCENE_WIDTH * margin + i * 0, offset);
		Relocate(this.buttonCreateOnager,  Settings.SCENE_WIDTH * margin + i * 1, offset);
		Relocate(this.buttonCreatePiker, Settings.SCENE_WIDTH * margin + i * 2, offset);
		
		Relocate(this.buttonUpgradeCastle, Settings.SCENE_WIDTH * margin + i * 1 , offset + 90);
		
		Relocate(this.fillTime, Settings.SCENE_WIDTH * margin+ 1, offset + 190);
		Relocate(this.backgroundTime, Settings.SCENE_WIDTH * margin+ 1, offset + 190);
		
		Relocate(this.background, Settings.SCENE_WIDTH * margin - 17, offset - 22);
	}
	

	@Override
	public void AddNode(Node node) 
	{
		this.getChildren().add(node);
	}
	
	@Override
	public void SwitchCastle(Castle castle) 
	{
		this.currentCastle = castle;
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
	
}
