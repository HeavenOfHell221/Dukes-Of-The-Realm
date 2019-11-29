package UI;

import Duke.Baron;
import DukesOfTheRealm.Castle;
import DukesOfTheRealm.IUpdate;
import DukesOfTheRealm.Kingdom;
import Utility.Settings;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CastleUI extends Parent implements IUpdate{

	private Castle castle;
	private Text text;
	private Pane playfieldLayer;
	private Rectangle backgroundText;
	private Rectangle background;
	
	private Button buttonCreatePiker;
	private Button buttonCreateKnight;
	private Button buttonCreateOnager;
	
	private Image enemyImage;
	
	private static CastleUI instance = new CastleUI();
	
	private CastleUI()
	{	
		enemyImage = new Image(getClass().getResource("/images/farmer.png").toExternalForm(), 50, 50, true, true);
		ImageView iv = new ImageView(enemyImage);
		
		this.text = new Text();
		this.background = new Rectangle(Settings.SCENE_WIDTH * (1 - Settings.MARGIN_PERCENTAGE), Settings.SCENE_HEIGHT);
		this.backgroundText = new Rectangle(200, 0);
		
		this.buttonCreatePiker = new Button("", iv);
		this.buttonCreateKnight = new Button();
		this.buttonCreateOnager = new Button();

	    this.background.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.05), 0);
		this.backgroundText.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.1), 125);
		this.text.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.1), 150);
		
		this.buttonCreatePiker.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.1), 600);
		this.buttonCreateKnight.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.1), 660);
		this.buttonCreateOnager.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.1), 720);
	}
	
	@Override
	public void Start()
	{
		StartText();
		StartBackgroundText();
		StartBackground();
		StartButtons();
	}
	
	private void StartButtons()
	{
		this.buttonCreatePiker.setMaxSize(40, 40);
		this.buttonCreateKnight.setMinSize(50, 50);
		this.buttonCreateOnager.setMinSize(50, 50);
	}
	
	private void StartText()
	{
		this.text.setFont(new Font(20));
		this.text.setWrappingWidth(200);
		this.text.setTextAlignment(TextAlignment.CENTER);
		this.text.setFill(Color.WHITE);
	}
	
	private void StartBackgroundText()
	{
		Stop[] stops = new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.BLACK)};
	    LinearGradient lg2 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		this.backgroundText.setStroke(lg2);
		
		this.backgroundText.setStrokeWidth(3);
		this.backgroundText.setArcHeight(60);
		this.backgroundText.setArcWidth(60);
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
			
	}
	
	private void TextPlayerOrDuke()
	{
		this.text.setText(
				"Name : " + castle.GetActor().GetName() + "\n" + 
				"Level : " + this.castle.GetLevel() + "\n" +
				Settings.FLORIN_PER_SECOND * this.castle.GetLevel() + " Florin(s) / s" + "\n" +
				"Florin : " + (int)this.castle.GetTotalFlorin() + "\n\n" +
				"Piker : " + this.castle.GetReserveOfSoldiers().getNbPikers() + "\n" +
				"Knights : " + this.castle.GetReserveOfSoldiers().getNbKnights() + "\n" +
				"Onager : " + this.castle.GetReserveOfSoldiers().getNbOnagers() + "\n");
		this.backgroundText.setHeight(280);
	}
	
	private void TextBaron()
	{
		this.text.setText(
				"Name : " + castle.GetActor().GetName() + "\n" + 
				"Level : " + this.castle.GetLevel() + "\n" +
				String.format("%.1f" , 0.1 * Settings.FLORIN_PER_SECOND * this.castle.GetLevel()) + " Florin(s) / s" + "\n" +
				"Florin : " + (int)this.castle.GetTotalFlorin() + "\n\n" +
				"Piker : " + this.castle.GetReserveOfSoldiers().getNbPikers() + "\n" +
				"Knights : " + this.castle.GetReserveOfSoldiers().getNbKnights() + "\n" +
				"Onager : " + this.castle.GetReserveOfSoldiers().getNbOnagers() + "\n");
		this.backgroundText.setHeight(280);
	}
	
	public void SwitchCastle(Castle castle)
	{
		this.castle = castle;
	}
	
	public static CastleUI GetInstance()
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
		instance.getChildren().add(this.backgroundText);
		instance.getChildren().add(this.text);
		instance.getChildren().add(this.buttonCreatePiker);
		instance.getChildren().add(this.buttonCreateKnight);
		instance.getChildren().add(this.buttonCreateOnager);
	}
}
