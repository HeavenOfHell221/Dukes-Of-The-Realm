package UI;

import Duke.Baron;
import DukesOfTheRealm.Castle;
import Interface.IUI;
import Interface.IUpdate;
import Utility.Settings;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public final class UICastlePreview extends Parent implements IUpdate, IUI {

	
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	private Castle currentCastle;
	
	private ImageView imageKnight;
	private ImageView imagePiker;
	private ImageView imageOnager;
	private ImageView imageFlorin;
	
	private Text level;
	private Text owner;
	private Text florinIncome;
	
	private Text nbKnight;
	private Text nbPiker;
	private Text nbOnager;
	private Text nbFlorin;
	
	private Rectangle background;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	public UICastlePreview() 
	{
		super();
		this.imageKnight = NewImageView("/images/mounted-knight-white.png");
		this.imagePiker = NewImageView("/images/spartan-white.png");
		this.imageOnager = NewImageView("/images/catapult-white.png");
		this.imageFlorin = NewImageView("/images/coins.png");
		this.level = new Text();
		this.owner = new Text();
		this.nbFlorin = new Text();
		this.nbKnight = new Text();
		this.nbOnager = new Text();
		this.nbPiker = new Text();
		this.florinIncome = new Text();
		this.background = new Rectangle(240, 440);
		
		
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start() 
	{
		AddAllNodes();
		RelocateAllNodes();
		SetAllTexts();
		SetBackground();
	}
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	private void SetAllTexts()
	{
		SetText(this.level, 24);
		SetText(this.owner, 24);
		SetText(this.florinIncome, 24);
		SetText(this.nbKnight, 30);
		SetText(this.nbOnager, 30);
		SetText(this.nbPiker, 30);
		SetText(this.nbFlorin, 30);
	}
	
	@Override
	public void Update(long now, boolean pause) 
	{
		if(this.currentCastle != null)
			UpdateTexts();
	}
	
	private void UpdateTexts()
	{
		if(this.currentCastle.GetActor().getClass() != Baron.class)
			this.florinIncome.setText(Settings.FLORIN_PER_SECOND * this.currentCastle.GetLevel() + " Florin/s");
		else
			this.florinIncome.setText((int)(Settings.FLORIN_PER_SECOND * this.currentCastle.GetLevel() * Settings.FLORIN_FACTOR_BARON) + " Florin/s");
		this.owner.setText(this.currentCastle.GetActor().GetName());
		this.level.setText("Level: " + this.currentCastle.GetLevel());
		this.nbFlorin.setText((int)this.currentCastle.GetTotalFlorin() + "");
		this.nbPiker.setText("" + this.currentCastle.GetReserveOfSoldiers().GetNbPikers());
		this.nbKnight.setText("" + this.currentCastle.GetReserveOfSoldiers().GetNbKnights());
		this.nbOnager.setText("" + this.currentCastle.GetReserveOfSoldiers().GetNbOnagers());
	}
	

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	
	private void SetBackground()
	{
		Stop[] stops = new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.WHITE)};
	    LinearGradient lg2 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		this.background.setStroke(lg2);
		
		this.background.setStrokeWidth(3);
		this.background.setArcHeight(60);
		this.background.setArcWidth(60);
	}
	
	private ImageView NewImageView(String path)
	{
		return new ImageView(new Image(getClass().getResource(path).toExternalForm(), 64, 64, false, true)); 
	}
	
	@Override
	public void RelocateAllNodes()
	{
		float margin = (float) (Settings.MARGIN_PERCENTAGE) + 0.1f;
		int offset = 50;
		int i = 69;
		
		Relocate(this.owner, Settings.SCENE_WIDTH * margin, offset + i * 0);
		Relocate(this.florinIncome, Settings.SCENE_WIDTH * margin, offset + i * 1 - 30);
		Relocate(this.level, Settings.SCENE_WIDTH * margin, offset + i * 2 - 60);
		offset -= 20;
		Relocate(this.imageFlorin, Settings.SCENE_WIDTH * margin, offset + i * 2);
		Relocate(this.imagePiker, Settings.SCENE_WIDTH * margin, offset + i * 3);
		Relocate(this.imageKnight, Settings.SCENE_WIDTH * margin, offset + i * 4);
		Relocate(this.imageOnager, Settings.SCENE_WIDTH * margin, offset + i * 5);
		
		Relocate(this.nbFlorin, Settings.SCENE_WIDTH * margin + 40, offset + i * 2 + 30);
		Relocate(this.nbPiker, Settings.SCENE_WIDTH * margin + 40, offset + i * 3 + 30);
		Relocate(this.nbKnight, Settings.SCENE_WIDTH * margin + 40, offset + i * 4 + 30);
		Relocate(this.nbOnager, Settings.SCENE_WIDTH * margin + 40, offset + i * 5 + 30);
		
		Relocate(this.background, Settings.SCENE_WIDTH * margin - 44, offset);
	}
	
	@Override
	public void AddAllNodes()
	{
		AddNode(this.background);
		AddNode(this.imageKnight);
		AddNode(this.level);
		AddNode(this.imageFlorin);
		AddNode(this.imageOnager);
		AddNode(this.imagePiker);
		AddNode(this.owner);
		AddNode(this.florinIncome);
		AddNode(this.nbFlorin);
		AddNode(this.nbKnight);
		AddNode(this.nbOnager);
		AddNode(this.nbPiker);
	}
	
	@Override
	public void Relocate(Node node, double x, double y)
	{
		node.relocate(x, y);
		node.toBack();
	}

	@Override
	public void AddNode(Node node) 
	{
		this.getChildren().add(node);
	}
	
	private void SetText(Text text, int font)
	{
		text.setFont(new Font(font));
		text.setWrappingWidth(155);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
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
