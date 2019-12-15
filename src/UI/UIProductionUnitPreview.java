package UI;

import java.io.Serializable;

import Duke.Actor;
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

public final class UIProductionUnitPreview extends Parent implements IUpdate, Serializable, IUI
{

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
		this.buttonCreatePiker = new Button();
		this.buttonCreateKnight = new Button();
		this.buttonCreateOnager = new Button();
		this.buttonUpgradeCastle = new Button();

		this.background = new Rectangle(280, 300);

		this.backgroundTime = new Rectangle(240, 40);
		this.fillTime = new Rectangle(0, 40); // entre +00 et +210

	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		addAllNodes();
		relocateAllNodes();
		setAllButtons();
		setBackground();
		setBar();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		setFill(this.currentCastle.getRatio());
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public void addNode(final Node node)
	{
		getChildren().add(node);
	}

	@Override
	public void setAllVisible(final boolean visible)
	{
		setVisible(this.backgroundTime, visible);
		setVisible(this.background, visible);
		setVisible(this.fillTime, visible);
		setVisible(this.buttonCreateKnight, visible);
		setVisible(this.buttonCreateOnager, visible);
		setVisible(this.buttonCreatePiker, visible);
		setVisible(this.buttonUpgradeCastle, visible);
	}

	public void setFill(final double fractionFill)
	{
		if (fractionFill >= 1)
		{
			this.fillTime.setWidth(0);
			return;
		}
		if (fractionFill == 0)
		{
			this.fillTime.setWidth(0);
			return;
		}
		this.fillTime.setWidth(5 + fractionFill * 235);
	}

	private void setBar()
	{
		this.backgroundTime.setFill(Color.TAN);
		this.backgroundTime.setStrokeWidth(5);
		this.backgroundTime.setStrokeType(StrokeType.OUTSIDE);
		this.backgroundTime.setStroke(Color.BLANCHEDALMOND);
		this.backgroundTime.setArcHeight(20);
		this.backgroundTime.setArcWidth(20);

		this.fillTime.setFill(Color.SEAGREEN);
		this.fillTime.setArcHeight(20);
		this.fillTime.setArcWidth(20);

		final InnerShadow i = new InnerShadow();
		i.setColor(Color.BLACK);
		i.setHeight(10);

		this.fillTime.setEffect(i);
	}

	private void setAllButtons()
	{
		this.buttonCreateKnight
				.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/mounted-knight2.png'); "
						+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.buttonCreatePiker.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/spartan2.png'); "
				+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.buttonCreateOnager.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/catapult2.png'); "
				+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.buttonUpgradeCastle
				.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/egyptian-temple.png'); "
						+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.buttonCreateOnager.setOnMousePressed(event -> addProduction(this.buttonCreateOnager, new Onager()));
		this.buttonCreatePiker.setOnMousePressed(event -> addProduction(this.buttonCreatePiker, new Piker()));
		this.buttonCreateKnight.setOnMousePressed(event -> addProduction(this.buttonCreateKnight, new Knight()));
		this.buttonUpgradeCastle
				.setOnMousePressed(event -> addProduction(this.buttonUpgradeCastle, new Castle(this.currentCastle.getLevel())));

		addEventMouse(this.buttonCreateKnight);
		addEventMouse(this.buttonCreateOnager);
		addEventMouse(this.buttonCreatePiker);
		addEventMouse(this.buttonUpgradeCastle);
	}

	private void addProduction(final Button b, final IProductionUnit p)
	{
		if (this.currentCastle.addProduction(p))
		{
			b.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.LIGHTGREEN, 15, 0.33, 0, 0));
		}
		else
		{
			b.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.ORANGERED, 15, 0.33, 0, 0));
		}
	}

	private void setBackground()
	{
		final Stop[] stops = new Stop[]
		{
				new Stop(0, Color.WHITE), new Stop(1, Color.WHITE)
		};
		final LinearGradient lg2 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		this.background.setStroke(lg2);

		this.background.setStrokeWidth(3);
		this.background.setArcHeight(60);
		this.background.setArcWidth(60);
	}

	private void addEventMouse(final Button b)
	{
		b.setMinSize(64, 64);
		b.setMaxSize(64, 64);
		b.setCursor(Cursor.HAND);
		b.addEventHandler(MouseEvent.MOUSE_ENTERED, event ->
		{
			final Bloom bloom = new Bloom();
			bloom.setThreshold(0.85);
			bloom.setInput(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 15, 0.25, 0, 0));
			b.setEffect(bloom);
		});

		b.addEventHandler(MouseEvent.MOUSE_EXITED, event ->
		{
			b.setEffect(null);
		});

		b.setOnMouseClicked(event ->
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
		addNode(this.background);
		addNode(this.backgroundTime);
		addNode(this.buttonCreateKnight);
		addNode(this.buttonCreateOnager);
		addNode(this.buttonCreatePiker);
		addNode(this.buttonUpgradeCastle);
		addNode(this.fillTime);
	}

	@Override
	public void relocateAllNodes()
	{
		final int i = 90;
		final int offset = 540;

		final float margin = (float) (Settings.MARGIN_PERCENTAGE) + 0.076f;

		relocate(this.buttonCreatePiker, Settings.SCENE_WIDTH * margin + i * 0, offset);
		relocate(this.buttonCreateKnight, Settings.SCENE_WIDTH * margin + i * 1, offset);
		relocate(this.buttonCreateOnager, Settings.SCENE_WIDTH * margin + i * 2, offset);

		relocate(this.buttonUpgradeCastle, Settings.SCENE_WIDTH * margin + i * 1, offset + 90);

		relocate(this.fillTime, Settings.SCENE_WIDTH * margin + 1, offset + 190);
		relocate(this.backgroundTime, Settings.SCENE_WIDTH * margin + 1, offset + 190);

		relocate(this.background, Settings.SCENE_WIDTH * margin - 17, offset - 22);
	}

	public void switchCastle(final Castle castle, final Actor actor, final boolean productionVisible)
	{
		this.currentCastle = castle;
		setAllVisible(productionVisible);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

}
