package UI;

import java.io.Serializable;

import Duke.Actor;
import DukesOfTheRealm.Castle;
import Interface.IUI;
import Utility.Settings;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public final class UICastlePreview extends Parent implements Serializable, IUI
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	private transient ImageView imageKnight;
	private transient ImageView imagePiker;
	private transient ImageView imageOnager;
	private transient ImageView imageFlorin;

	private transient Text level;
	private transient Text owner;
	private transient Text florinIncome;

	private transient Text nbKnight;
	private transient Text nbPiker;
	private transient Text nbOnager;
	private transient Text nbFlorin;

	private transient Rectangle background;
	
	private Castle currentCastle;
	private Actor currentActor;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	public UICastlePreview()
	{
		super();
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	public void start()
	{
		awake();
		addAllNodes();
		relocateAllNodes();
		setAllTexts();
		setBackground();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	
	public void update(final long now, final boolean pause) 
	{ 
		if (this.currentCastle != null && this.currentActor != null) 
		{
			updateTexts(); 
		} 
	}
	
	private void updateTexts() 
	{ 
		this.florinIncome.setText(this.currentActor.florinIncome(this.currentCastle));
		this.owner.setText(this.currentActor.getName());
		this.level.setText("Level: " + this.currentCastle.getLevel()); 
		this.nbFlorin.setText((int) this.currentCastle.getTotalFlorin() + "");
		this.nbPiker.setText("" + this.currentCastle.getReserveOfSoldiers().getNbPikers());
		this.nbKnight.setText("" + this.currentCastle.getReserveOfSoldiers().getNbKnights());
		this.nbOnager.setText("" + this.currentCastle.getReserveOfSoldiers().getNbOnagers()); 
	 }
	 

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	public void awake()
	{
		this.imageKnight = newImageView("/images/mounted-knight-white.png");
		this.imagePiker = newImageView("/images/spartan-white.png");
		this.imageOnager = newImageView("/images/catapult-white.png");
		this.imageFlorin = newImageView("/images/coins.png");
		this.level = new Text();
		this.owner = new Text();
		this.nbFlorin = new Text();
		this.nbKnight = new Text();
		this.nbOnager = new Text();
		this.nbPiker = new Text();
		this.florinIncome = new Text();
		this.background = new Rectangle(240, 440);
	}

	private void setAllTexts()
	{
		setText(this.level, 24);
		setText(this.owner, 24);
		setText(this.florinIncome, 24);
		setText(this.nbKnight, 30);
		setText(this.nbOnager, 30);
		setText(this.nbPiker, 30);
		setText(this.nbFlorin, 30);
	}
	
	@Override
	public void addNode(final Node node)
	{
		getChildren().add(node);
	}

	private void setBackground()
	{
		/*
		 * final Stop[] stops = new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.BLACK)}; final
		 * LinearGradient lg2 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		 * background.setStroke(lg2);
		 */
		this.background.setStroke(Color.WHITE);
		this.background.setStrokeWidth(3);
		this.background.setArcHeight(60);
		this.background.setArcWidth(60);
	}

	private ImageView newImageView(final String path)
	{
		return new ImageView(new Image(getClass().getResource(path).toExternalForm(), 64, 64, false, true));
	}

	@Override
	public void relocateAllNodes()
	{
		final float margin = (float) (Settings.MARGIN_PERCENTAGE) + 0.1f;
		int offset = 50;
		final int i = 69;

		relocate(this.owner, Settings.SCENE_WIDTH * margin, offset + i * 0);
		relocate(this.florinIncome, Settings.SCENE_WIDTH * margin, offset + i * 1 - 30);
		relocate(this.level, Settings.SCENE_WIDTH * margin, offset + i * 2 - 60);
		offset -= 20;
		relocate(this.imageFlorin, Settings.SCENE_WIDTH * margin, offset + i * 2);
		relocate(this.imagePiker, Settings.SCENE_WIDTH * margin, offset + i * 3);
		relocate(this.imageKnight, Settings.SCENE_WIDTH * margin, offset + i * 4);
		relocate(this.imageOnager, Settings.SCENE_WIDTH * margin, offset + i * 5);

		relocate(this.nbFlorin, Settings.SCENE_WIDTH * margin + 40, offset + i * 2 + 30);
		relocate(this.nbPiker, Settings.SCENE_WIDTH * margin + 40, offset + i * 3 + 30);
		relocate(this.nbKnight, Settings.SCENE_WIDTH * margin + 40, offset + i * 4 + 30);
		relocate(this.nbOnager, Settings.SCENE_WIDTH * margin + 40, offset + i * 5 + 30);

		relocate(this.background, Settings.SCENE_WIDTH * margin - 44, offset);
	}

	@Override
	public void addAllNodes()
	{
		addNode(this.background);
		addNode(this.imageKnight);
		addNode(this.level);
		addNode(this.imageFlorin);
		addNode(this.imageOnager);
		addNode(this.imagePiker);
		addNode(this.owner);
		addNode(this.florinIncome);
		addNode(this.nbFlorin);
		addNode(this.nbKnight);
		addNode(this.nbOnager);
		addNode(this.nbPiker);
	}

	private void setText(final Text text, final int font)
	{
		text.setFont(new Font(font));
		text.setWrappingWidth(155);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
	}

	@Override
	public void setAllVisible(final boolean visible)
	{
		setVisible(this.background, visible);
		setVisible(this.imageKnight, visible);
		setVisible(this.level, visible);
		setVisible(this.imageFlorin, visible);
		setVisible(this.imageOnager, visible);
		setVisible(this.imagePiker, visible);
		setVisible(this.owner, visible);
		setVisible(this.florinIncome, visible);
		setVisible(this.nbFlorin, visible);
		setVisible(this.nbKnight, visible);
		setVisible(this.nbOnager, visible);
		setVisible(this.nbPiker, visible);
	}

	@Override
	public void switchCastle(final Castle castle, final Actor actor, boolean productionVisible, boolean attackVisible)
	{
		this.currentCastle = castle;
		this.currentActor = actor;
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
}
