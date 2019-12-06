package UI;

import DukesOfTheRealm.Castle;
import Interface.IProductionUnit;
import Interface.IUI;
import Interface.IUpdate;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Utility.Settings;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public final class UIProductionUnitPreview extends Parent implements IUI, IUpdate {

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	private final Button buttonCreatePiker;
	private final Button buttonCreateKnight;
	private final Button buttonCreateOnager;
	private final Button buttonUpgradeCastle;

	private final Rectangle background;

	private final Rectangle backgroundTime;
	private final Rectangle fillTime;

	private Castle currentCastle;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	public UIProductionUnitPreview()
	{
		super();

		buttonCreatePiker = new Button();
		buttonCreateKnight = new Button();
		buttonCreateOnager = new Button();
		buttonUpgradeCastle = new Button();

		background = new Rectangle(280, 300);

		backgroundTime = new Rectangle(240, 40);
		fillTime = new Rectangle(0, 40); // entre +00 et +210
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		addAllNodes();
		relocateAllNodes();
		SetAllButtons();
		SetBackground();
		SetBar();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{

	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public void relocate(final Node node, final double x, final double y)
	{
		node.relocate(x, y);
		node.toBack();
	}

	public void SetAllVisible(final boolean visible)
	{
		SetVisible(backgroundTime, visible);
		SetVisible(background, visible);
		SetVisible(fillTime, visible);
		SetVisible(buttonCreateKnight, visible);
		SetVisible(buttonCreateOnager, visible);
		SetVisible(buttonCreatePiker, visible);
		SetVisible(buttonUpgradeCastle, visible);
	}

	public void SetVisible(final Node node, final boolean visible)
	{
		node.setVisible(visible);
	}

	public void SetFill(final double fractionFill)
	{
		if(fractionFill >= 1)
		{
			fillTime.setWidth(0);
			return;
		}
		fillTime.setWidth(5 + fractionFill * 235);
	}

	private void SetBar()
	{
		backgroundTime.setFill(Color.TAN);
		backgroundTime.setStrokeWidth(5);
		backgroundTime.setStrokeType(StrokeType.OUTSIDE);
		backgroundTime.setStroke(Color.BLANCHEDALMOND);
		backgroundTime.setArcHeight(20);
		backgroundTime.setArcWidth(20);

		fillTime.setFill(Color.SEAGREEN);
		fillTime.setArcHeight(20);
		fillTime.setArcWidth(20);

		final InnerShadow i = new InnerShadow();
		i.setColor(Color.BLACK);
		i.setHeight(10);

		fillTime.setEffect(i);
	}

	private void SetAllButtons()
	{
		buttonCreateKnight.setStyle(""
				+ "-fx-background-color: transparent;"
				+ "-fx-background-image: url('/images/mounted-knight2.png'); "
				+ "-fx-background-size: 64px 64px; "
				+ "-fx-background-repeat: no-repeat; "
				);

		buttonCreatePiker.setStyle(""
				+ "-fx-background-color: transparent;"
				+ "-fx-background-image: url('/images/spartan2.png'); "
				+ "-fx-background-size: 64px 64px; "
				+ "-fx-background-repeat: no-repeat; "
				);


		buttonCreateOnager.setStyle(""
				+ "-fx-background-color: transparent;"
				+ "-fx-background-image: url('/images/catapult2.png'); "
				+ "-fx-background-size: 64px 64px; "
				+ "-fx-background-repeat: no-repeat; "
				);

		buttonUpgradeCastle.setStyle(""
				+ "-fx-background-color: transparent;"
				+ "-fx-background-image: url('/images/egyptian-temple.png'); "
				+ "-fx-background-size: 64px 64px; "
				+ "-fx-background-repeat: no-repeat; "
				);

		buttonCreateOnager.setOnMousePressed(event -> AddProduction(buttonCreateOnager, new Onager()));
		buttonCreatePiker.setOnMousePressed(event -> AddProduction(buttonCreatePiker, new Piker()));
		buttonCreateKnight.setOnMousePressed(event -> AddProduction(buttonCreateKnight, new Knight()));
		buttonUpgradeCastle.setOnMousePressed(event -> AddProduction(buttonUpgradeCastle, new Castle(currentCastle.GetLevel())));

		AddEventMouse(buttonCreateKnight);
		AddEventMouse(buttonCreateOnager);
		AddEventMouse(buttonCreatePiker);
		AddEventMouse(buttonUpgradeCastle);
	}

	private void AddProduction(final Button b, final IProductionUnit p)
	{
		if(currentCastle.AddProduction(p))
		{
			b.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.LIGHTGREEN, 15, 0.33, 0, 0));
		}
		else
		{
			b.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.ORANGERED, 15, 0.33, 0, 0));
		}
	}

	private void SetBackground()
	{
		final Stop[] stops = new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.WHITE)};
		final LinearGradient lg2 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		background.setStroke(lg2);

		background.setStrokeWidth(3);
		background.setArcHeight(60);
		background.setArcWidth(60);
	}

	private void AddEventMouse(final Button b)
	{
		b.setMinSize(64, 64);
		b.setMaxSize(64, 64);
		b.setCursor(Cursor.HAND);
		b.addEventHandler(MouseEvent.MOUSE_ENTERED,
				event ->
		{
			final Bloom bloom = new Bloom();
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
					final Bloom bloom = new Bloom();
					bloom.setThreshold(0.85);
					bloom.setInput(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 15, 0.25, 0, 0));
					b.setEffect(bloom);
				});
	}

	@Override
	public void addAllNodes()
	{
		addNode(background);
		addNode(backgroundTime);
		addNode(buttonCreateKnight);
		addNode(buttonCreateOnager);
		addNode(buttonCreatePiker);
		addNode(buttonUpgradeCastle);
		addNode(fillTime);
	}

	@Override
	public void relocateAllNodes()
	{
		final int i = 90;
		final int offset = 540;

		final float margin = (float) (Settings.MARGIN_PERCENTAGE) + 0.076f;

		relocate(buttonCreateKnight,  Settings.SCENE_WIDTH * margin + i * 0, offset);
		relocate(buttonCreateOnager,  Settings.SCENE_WIDTH * margin + i * 1, offset);
		relocate(buttonCreatePiker, Settings.SCENE_WIDTH * margin + i * 2, offset);

		relocate(buttonUpgradeCastle, Settings.SCENE_WIDTH * margin + i * 1 , offset + 90);

		relocate(fillTime, Settings.SCENE_WIDTH * margin+ 1, offset + 190);
		relocate(backgroundTime, Settings.SCENE_WIDTH * margin+ 1, offset + 190);

		relocate(background, Settings.SCENE_WIDTH * margin - 17, offset - 22);
	}


	@Override
	public void addNode(final Node node)
	{
		getChildren().add(node);
	}

	@Override
	public void switchCastle(final Castle castle)
	{
		currentCastle = castle;
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

}
