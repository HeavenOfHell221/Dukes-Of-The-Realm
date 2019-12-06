package UI;

import java.io.Serializable;

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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public final class UICastlePreview extends Parent implements IUpdate, IUI, Serializable
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	private Castle currentCastle;

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

	@Override
	public void start()
	{
		awake();
		addAllNodes();
		relocateAllNodes();
		SetAllTexts();
		SetBackground();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		if (this.currentCastle != null)
		{
			UpdateTexts();
		}
	}

	private void UpdateTexts()
	{
		if (this.currentCastle.GetActor().getClass() != Baron.class)
		{
			this.florinIncome.setText(Settings.FLORIN_PER_SECOND * this.currentCastle.GetLevel() + " Florin/s");
		}
		else
		{
			this.florinIncome.setText(
					(int) (Settings.FLORIN_PER_SECOND * this.currentCastle.GetLevel() * Settings.FLORIN_FACTOR_BARON) + " Florin/s");
		}
		this.owner.setText(this.currentCastle.GetActor().getName());
		this.level.setText("Level: " + this.currentCastle.GetLevel());
		this.nbFlorin.setText((int) this.currentCastle.GetTotalFlorin() + "");
		this.nbPiker.setText("" + this.currentCastle.GetReserveOfSoldiers().getNbPikers());
		this.nbKnight.setText("" + this.currentCastle.GetReserveOfSoldiers().getNbKnights());
		this.nbOnager.setText("" + this.currentCastle.GetReserveOfSoldiers().getNbOnagers());
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	public void awake()
	{
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

	private void SetBackground()
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

	private ImageView NewImageView(final String path)
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

	@Override
	public void relocate(final Node node, final double x, final double y)
	{
		node.relocate(x, y);
		node.toBack();
	}

	@Override
	public void addNode(final Node node)
	{
		getChildren().add(node);
	}

	private void SetText(final Text text, final int font)
	{
		text.setFont(new Font(font));
		text.setWrappingWidth(155);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
	}

	@Override
	public void switchCastle(final Castle castle)
	{
		this.currentCastle = castle;
	}

	public void SetVisible(final Node node, final boolean visible)
	{
		node.setVisible(visible);
	}

	public void SetAllVisible(final boolean visible)
	{
		SetVisible(this.background, visible);
		SetVisible(this.imageKnight, visible);
		SetVisible(this.level, visible);
		SetVisible(this.imageFlorin, visible);
		SetVisible(this.imageOnager, visible);
		SetVisible(this.imagePiker, visible);
		SetVisible(this.owner, visible);
		SetVisible(this.florinIncome, visible);
		SetVisible(this.nbFlorin, visible);
		SetVisible(this.nbKnight, visible);
		SetVisible(this.nbOnager, visible);
		SetVisible(this.nbPiker, visible);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
}
