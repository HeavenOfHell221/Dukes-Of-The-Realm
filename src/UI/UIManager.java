package UI;

import DukesOfTheRealm.Castle;
import Interface.IUpdate;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class UIManager extends Parent implements IUpdate {

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	private static final UIManager instance = new UIManager();
	private UIAttackPreview attackPreview;
	private UIProductionUnitPreview productionUnitPreview;
	private UICastlePreview castlePreview;

	protected Castle currentCastle;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	protected UIManager()
	{
		
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start() 
	{
		AddNode(attackPreview);
		AddNode(castlePreview);
		AddNode(productionUnitPreview);
		
		GetAttackPreview().Start();
		GetCastlePreview().Start();
		GetProductionUnitPreview().Start();
	}
	
	public void Awake()
	{
		instance.attackPreview = new UIAttackPreview();
		instance.castlePreview = new UICastlePreview();
		instance.productionUnitPreview = new UIProductionUnitPreview();
		instance.Start();
	}
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void Update(long now, boolean pause) 
	{
		
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	protected void AddNode(Node node)
	{
		instance.getChildren().add(node);
	}
	
	public void SwitchCastle(Castle castle)
	{
		instance.currentCastle = castle;
	}
	
	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return the instance
	 */
	public static final UIManager GetInstance() 
	{
		return instance;
	}

	/**
	 * @return the attackPreview
	 */
	public final UIAttackPreview GetAttackPreview() 
	{
		return instance.attackPreview;
	}

	/**
	 * @return the productionUnitPreview
	 */
	public final UIProductionUnitPreview GetProductionUnitPreview() 
	{
		return instance.productionUnitPreview;
	}

	/**
	 * @return the castlePreview
	 */
	public final UICastlePreview GetCastlePreview() 
	{
		return instance.castlePreview;
	}
	
	
	/*
	private Castle castle;

	private UIManager()
	{	
		this.text = new Text();
		this.background = new Rectangle(Settings.SCENE_WIDTH * (1 - Settings.MARGIN_PERCENTAGE), Settings.SCENE_HEIGHT);
		this.backgroundText = new Rectangle(220, 0);
		this.backgroundButtonSoldier = new Rectangle(320, 260);
		this.textFPS = new Text();

	    this.background.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.05), 0);
		this.backgroundText.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.0908), 60);
		this.text.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.0908), 100);
		this.textFPS.relocate(10, 10);
		this.backgroundButtonSoldier.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.0633), 550);
		
		this.imageCoins.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.0908), 250 - 64 - 20);
		
		this.imagePiker.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.097), 250);
		this.imageKnight.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.1), 250 + 64 + 20);
		this.imageOnager.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.1), 250 + 64 + 20 + 64 + 20);
		
		this.buttonCreatePiker.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.1 - 0.021), 600);
		this.buttonCreateKnight.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.15 - 0.021), 600);
		this.buttonCreateOnager.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.20 - 0.021), 600);
	}
	
	@Override
	public void Start()
	{
		StartTexts();
		StartBackgroundText();
		StartBackground();
		StartButtons();
		StartBackgroundButtonSoldier();
	}
	
	private void StartButtons()
	{
		StartButtonCreatePiker();
		StartButtonCreateOnager();
		StartButtonCreateKnight();
	}
	
	private void StartButtonCreateKnight() 
	{
		this.buttonCreateKnight.setStyle(""
				+ "-fx-background-color: transparent;"
				+ "-fx-background-image: url('/images/mounted-knight2.png'); "
				+ "-fx-background-size: 64px 64px; "
				+ "-fx-background-repeat: no-repeat; "
				);
		
		StartButtonCreate(this.buttonCreateKnight);
		this.buttonCreateKnight.setOnMousePressed(
			event -> 
			{ 
				buttonCreateKnight.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.ORANGERED, 15, 0.33, 0, 0)); 
			});
	}

	private void StartButtonCreateOnager() 
	{
		this.buttonCreateOnager.setStyle(""
				+ "-fx-background-color: transparent;"
				+ "-fx-background-image: url('/images/catapult2.png'); "
				+ "-fx-background-size: 64px 64px; "
				+ "-fx-background-repeat: no-repeat; "
				);
		
		StartButtonCreate(this.buttonCreateOnager);
		this.buttonCreateOnager.setOnMousePressed(
			event -> 
			{ 
				buttonCreateOnager.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.LIGHTGREEN, 15, 0.33, 0, 0)); 
			});
	}

	private void StartButtonCreatePiker()
	{
		this.buttonCreatePiker.setStyle(""
				+ "-fx-background-color: transparent;"
				+ "-fx-background-image: url('/images/spartan2.png'); "
				+ "-fx-background-size: 64px 64px; "
				+ "-fx-background-repeat: no-repeat; "
				);
		
		StartButtonCreate(this.buttonCreatePiker);
		this.buttonCreatePiker.setOnMousePressed(
			event -> 
			{ 
				buttonCreatePiker.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.LIGHTGREEN, 15, 0.33, 0, 0)); 
			});
		
	}
	
	private void StartButtonCreate(Button b)
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
	
	private void StartTexts()
	{
		this.text.setFont(new Font(20));
		this.text.setWrappingWidth(220);
		this.text.setTextAlignment(TextAlignment.CENTER);
		this.text.setFill(Color.WHITE);
		
		this.textFPS.setFont(new Font(20));
	}
	
	private void StartBackgroundText()
	{
		Stop[] stops = new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.WHITE)};
	    LinearGradient lg2 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		this.backgroundText.setStroke(lg2);
		
		this.backgroundText.setStrokeWidth(3);
		this.backgroundText.setArcHeight(60);
		this.backgroundText.setArcWidth(60);
	}
	
	private void StartBackgroundButtonSoldier()
	{
		Stop[] stops = new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.WHITE)};
	    LinearGradient lg2 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		this.backgroundButtonSoldier.setStroke(lg2);
		this.backgroundButtonSoldier.setStrokeWidth(3);
		this.backgroundButtonSoldier.setArcHeight(60);
		this.backgroundButtonSoldier.setArcWidth(60);
	}
	
	private void StartBackground()
	{
		Stop[] stops = new Stop[] { new Stop(0, Color.web("#753F0B")), new Stop(1, Color.web("#4F2E0F"))};
	    LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, stops);
	    this.background.setFill(lg1);
	     
		DropShadow a = new DropShadow();
		a.setOffsetX(-2);
		a.setOffsetY(0);
		a.setSpread(0.1);
		a.setRadius(5);
		a.setColor(Color.BLACK);
		this.background.setEffect(a);
	}
	
	@Override
	public void Update(long now, boolean pause)
	{
		if(this.castle != null)
		{
			if(this.castle.GetActor().getClass() == Baron.class)
			{
				TextBaron();
			}
			else
			{
				TextPlayerOrDuke();
			}
		}
		textFPS.setText("" + Time.FPS);
	}
	
	private void TextPlayerOrDuke()
	{
		this.text.setText(
				"Name : " + castle.GetActor().GetName() + "\n" + 
				"Level : " + this.castle.GetLevel() + "\n" +
				Settings.FLORIN_PER_SECOND * this.castle.GetLevel() + " Florin(s) / s" + "\n" +
				"Florin : " + (int)this.castle.GetTotalFlorin() + "\n\n" //+
				//"Piker : " + this.castle.GetReserveOfSoldiers().getNbPikers() + "\n" +
				//"Knights : " + this.castle.GetReserveOfSoldiers().getNbKnights() + "\n" +
				//"Onager : " + this.castle.GetReserveOfSoldiers().getNbOnagers() + "\n"
				);
		this.backgroundText.setHeight(450);
	}
	
	private void TextBaron()
	{
		this.text.setText(
				"Name : " + castle.GetActor().GetName() + "\n" + 
				"Level : " + this.castle.GetLevel() + "\n" +
				Settings.FLORIN_PER_SECOND * this.castle.GetLevel() * Settings.FLORIN_FACTOR_BARON + " Florin(s) / s" + "\n" +
				"Florin : " + (int)this.castle.GetTotalFlorin() + "\n\n" //+
				//"Piker : " + this.castle.GetReserveOfSoldiers().getNbPikers() + "\n" +
				//"Knights : " + this.castle.GetReserveOfSoldiers().getNbKnights() + "\n" +
				//"Onager : " + this.castle.GetReserveOfSoldiers().getNbOnagers() + "\n"
				);
		this.backgroundText.setHeight(450);
	}
	
	public void SwitchCastle(Castle castle)
	{
		this.castle = castle;
	}
	
	public static UIManager GetInstance()
	{
		return instance;
	}
	
	public void SetCastle(Castle castle)
	{
		this.castle = castle;
	}
	
	public Castle GetCastle()
	{
		return this.castle;
	}
	
	public void SetPlayfieldLayer(Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
		this.playfieldLayer.getChildren().add(instance);
		
		Start();
		
		instance.getChildren().add(this.background);
		instance.getChildren().add(this.backgroundButtonSoldier);
		instance.getChildren().add(this.backgroundText);
		instance.getChildren().add(this.text);
		instance.getChildren().add(this.textFPS);
		instance.getChildren().add(this.buttonCreatePiker);
		instance.getChildren().add(this.buttonCreateKnight);
		instance.getChildren().add(this.buttonCreateOnager);
		instance.getChildren().add(this.imageKnight);
		instance.getChildren().add(this.imageOnager);
		instance.getChildren().add(this.imagePiker);
		//instance.getChildren().add(this.imageCoins);
		
	}*/
}
