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

	private final ImageView imageKnight;
	private final ImageView imagePiker;
	private final ImageView imageOnager;
	private final ImageView imageFlorin;

	private final Text level;
	private final Text owner;
	private final Text florinIncome;

	private final Text nbKnight;
	private final Text nbPiker;
	private final Text nbOnager;
	private final Text nbFlorin;

	private final Rectangle background;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	public UICastlePreview()
	{
		super();
		imageKnight = NewImageView("/images/mounted-knight-white.png");
		imagePiker = NewImageView("/images/spartan-white.png");
		imageOnager = NewImageView("/images/catapult-white.png");
		imageFlorin = NewImageView("/images/coins.png");
		level = new Text();
		owner = new Text();
		nbFlorin = new Text();
		nbKnight = new Text();
		nbOnager = new Text();
		nbPiker = new Text();
		florinIncome = new Text();
		background = new Rectangle(240, 440);


	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		addAllNodes();
		relocateAllNodes();
		SetAllTexts();
		SetBackground();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	private void SetAllTexts()
	{
		SetText(level, 24);
		SetText(owner, 24);
		SetText(florinIncome, 24);
		SetText(nbKnight, 30);
		SetText(nbOnager, 30);
		SetText(nbPiker, 30);
		SetText(nbFlorin, 30);
	}

	@Override
	public void update(final long now, final boolean pause)
	{
		if(currentCastle != null) {
			UpdateTexts();
		}
	}

	private void UpdateTexts()
	{
		if(currentCastle.GetActor().getClass() != Baron.class) {
			florinIncome.setText(Settings.FLORIN_PER_SECOND * currentCastle.GetLevel() + " Florin/s");
		} else {
			florinIncome.setText((int)(Settings.FLORIN_PER_SECOND * currentCastle.GetLevel() * Settings.FLORIN_FACTOR_BARON) + " Florin/s");
		}
		owner.setText(currentCastle.GetActor().GetName());
		level.setText("Level: " + currentCastle.GetLevel());
		nbFlorin.setText((int)currentCastle.GetTotalFlorin() + "");
		nbPiker.setText("" + currentCastle.GetReserveOfSoldiers().GetNbPikers());
		nbKnight.setText("" + currentCastle.GetReserveOfSoldiers().GetNbKnights());
		nbOnager.setText("" + currentCastle.GetReserveOfSoldiers().GetNbOnagers());
	}


	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/


	private void SetBackground()
	{
		final Stop[] stops = new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.WHITE)};
		final LinearGradient lg2 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		background.setStroke(lg2);

		background.setStrokeWidth(3);
		background.setArcHeight(60);
		background.setArcWidth(60);
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

		relocate(owner, Settings.SCENE_WIDTH * margin, offset + i * 0);
		relocate(florinIncome, Settings.SCENE_WIDTH * margin, offset + i * 1 - 30);
		relocate(level, Settings.SCENE_WIDTH * margin, offset + i * 2 - 60);
		offset -= 20;
		relocate(imageFlorin, Settings.SCENE_WIDTH * margin, offset + i * 2);
		relocate(imagePiker, Settings.SCENE_WIDTH * margin, offset + i * 3);
		relocate(imageKnight, Settings.SCENE_WIDTH * margin, offset + i * 4);
		relocate(imageOnager, Settings.SCENE_WIDTH * margin, offset + i * 5);

		relocate(nbFlorin, Settings.SCENE_WIDTH * margin + 40, offset + i * 2 + 30);
		relocate(nbPiker, Settings.SCENE_WIDTH * margin + 40, offset + i * 3 + 30);
		relocate(nbKnight, Settings.SCENE_WIDTH * margin + 40, offset + i * 4 + 30);
		relocate(nbOnager, Settings.SCENE_WIDTH * margin + 40, offset + i * 5 + 30);

		relocate(background, Settings.SCENE_WIDTH * margin - 44, offset);
	}

	@Override
	public void addAllNodes()
	{
		addNode(background);
		addNode(imageKnight);
		addNode(level);
		addNode(imageFlorin);
		addNode(imageOnager);
		addNode(imagePiker);
		addNode(owner);
		addNode(florinIncome);
		addNode(nbFlorin);
		addNode(nbKnight);
		addNode(nbOnager);
		addNode(nbPiker);
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
		currentCastle = castle;
	}

	public void SetVisible(final Node node, final boolean visible)
	{
		node.setVisible(visible);
	}

	public void SetAllVisible(final boolean visible)
	{
		SetVisible(background, visible);
		SetVisible(imageKnight, visible);
		SetVisible(level, visible);
		SetVisible(imageFlorin, visible);
		SetVisible(imageOnager, visible);
		SetVisible(imagePiker, visible);
		SetVisible(owner, visible);
		SetVisible(florinIncome, visible);
		SetVisible(nbFlorin, visible);
		SetVisible(nbKnight, visible);
		SetVisible(nbOnager, visible);
		SetVisible(nbPiker, visible);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
}
