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

	private ImageView imageKnight;
	private ImageView imagePiker;
	private ImageView imageOnager;
	private ImageView imageFlorin;
	private ImageView imageCircleCurrentCastle;
	private ImageView imageCircleLastCastle;

	private Text level;
	private Text owner;
	private Text florinIncome;

	private Text nbKnight;
	private Text nbPiker;
	private Text nbOnager;
	private Text nbFlorin;

	private Rectangle background;

	private Castle currentCastle;
	private Castle lastCastle;
	private Actor currentActor;
	private Actor lastActor;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	public UICastlePreview()
	{
		this.imageKnight = newImageView("/images/mounted-knight-white.png", 64, 64);
		this.imagePiker = newImageView("/images/spartan-white.png", 64, 64);
		this.imageOnager = newImageView("/images/catapult-white.png", 64, 64);
		this.imageFlorin = newImageView("/images/coins.png", 64, 64);
		this.imageCircleCurrentCastle = newImageView("/images/circle.png", 128, 128);
		this.imageCircleLastCastle = newImageView("/images/circle2.png", 128, 128);
		this.level = new Text();
		this.owner = new Text();
		this.nbFlorin = new Text();
		this.nbKnight = new Text();
		this.nbOnager = new Text();
		this.nbPiker = new Text();
		this.florinIncome = new Text();
		this.background = new Rectangle(240, 440);
		
		this.lastActor = null;
		this.lastCastle = null;
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	public void start()
	{
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
	
	private void changeCircle()
	{
		if(this.currentCastle != null)
		{
			relocate(this.imageCircleCurrentCastle, this.currentCastle.getX() - 128/2 + Settings.CASTLE_SIZE/2 + 1, 
					this.currentCastle.getY() - 128/2 + Settings.CASTLE_SIZE/2 + 1);
		}
			
		if(this.lastCastle != null && this.lastCastle != this.currentCastle)
		{
			setVisible(this.imageCircleLastCastle, true);
			relocate(this.imageCircleLastCastle, this.lastCastle.getX() - 128/2 + Settings.CASTLE_SIZE/2 + 1, 
				this.lastCastle.getY() - 128/2 + Settings.CASTLE_SIZE/2 + 1);
		}
		else
			setVisible(this.imageCircleLastCastle, false);
	}

	private void updateTexts()
	{
		if(this.lastActor != null && this.lastActor.isPlayer() && !this.currentActor.isPlayer())
		{
			this.florinIncome.setText(this.lastActor.florinIncome(this.currentCastle));
			this.owner.setText(this.lastActor.getName(this.currentCastle));
			this.level.setText("Level: " + this.lastCastle.getLevel());
		}
		else
		{
			this.florinIncome.setText(this.currentActor.florinIncome(this.currentCastle));
			this.owner.setText(this.currentActor.getName(this.currentCastle));
			this.level.setText("Level: " + this.currentCastle.getLevel());
		}
		
		this.nbFlorin.setText((int) this.currentCastle.getTotalFlorin() + "");
		this.nbPiker.setText("" + this.currentCastle.getReserveOfSoldiers().getNbPikers());
		this.nbKnight.setText("" + this.currentCastle.getReserveOfSoldiers().getNbKnights());
		this.nbOnager.setText("" + this.currentCastle.getReserveOfSoldiers().getNbOnagers());
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/


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
		addNode(this.imageCircleCurrentCastle);
		addNode(this.imageCircleLastCastle);
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
		setVisible(this.imageCircleCurrentCastle, visible);
		setVisible(this.imageCircleLastCastle, visible);
	}

	@Override
	public void switchCastle(final Castle castle, final Actor actor, final boolean productionVisible, final boolean attackVisible)
	{	
		this.lastActor = this.currentActor;
		this.lastCastle = this.currentCastle;
		this.currentCastle = castle;
		this.currentActor = actor;
		changeCircle();
		
		if(this.lastActor != null && this.lastActor.isPlayer() && !this.currentActor.isPlayer())
		{
			this.currentCastle = this.lastCastle;
		}
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
}
