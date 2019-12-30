package UI;

import static Utility.Settings.KNIGHT_COST;
import static Utility.Settings.LEVEL_UP_COST_FACTOR;
import static Utility.Settings.MARGIN_PERCENTAGE;
import static Utility.Settings.ONAGER_COST;
import static Utility.Settings.PIKER_COST;
import static Utility.Settings.SCENE_WIDTH;

import DukesOfTheRealm.Castle;
import Interface.IProductionUnit;
import Interface.IUI;
import Interface.IUpdate;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * 
 */
public final class UIProductionUnitPreview extends Parent implements IUpdate, IUI
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * 
	 */
	private final Button buttonCreatePiker;
	
	/**
	 * 
	 */
	private final Button buttonCreateKnight;
	
	/**
	 * 
	 */
	private final Button buttonCreateOnager;
	
	/**
	 * 
	 */
	private final Button buttonUpgradeCastle;

	/**
	 * 
	 */
	private final Button removeLastProduction;
	
	/**
	 * 
	 */
	private final Button removeAllProduction;

	/**
	 * 
	 */
	private final Rectangle background;

	/**
	 * 
	 */
	private final Rectangle backgroundTime;
	
	/**
	 * 
	 */
	private final Rectangle fillTime;

	/**
	 * 
	 */
	private Castle currentCastle;

	/**
	 * 
	 */
	private Text pikerCost;
	
	/**
	 * 
	 */
	private Text onagerCost;
	
	/**
	 * 
	 */
	private Text knightCost;

	/**
	 * 
	 */
	private Text castleCost;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * 
	 */
	public UIProductionUnitPreview()
	{
		this.buttonCreatePiker = new Button();
		this.buttonCreateKnight = new Button();
		this.buttonCreateOnager = new Button();
		this.buttonUpgradeCastle = new Button();
		this.removeAllProduction = new Button();
		this.removeLastProduction = new Button();

		this.background = new Rectangle(280, 450); // 280 / 300

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
		setAllVisible(false);
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

	/**
	 * 
	 * @param node
	 */
	public void removeNode(final Node node)
	{
		getChildren().remove(node);
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
		setVisible(this.removeAllProduction, visible);
		setVisible(this.removeLastProduction, visible);
	}

	/**
	 * 
	 * @param fractionFill
	 */
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

	/**
	 * 
	 */
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

	/**
	 * 
	 */
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
				.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/egyptian-temple-b.png'); "
						+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.removeAllProduction.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/cancel.png'); "
				+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.removeLastProduction
				.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/anticlockwise-rotation.png'); "
						+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.buttonCreateOnager.setOnMousePressed(event -> addProduction(this.buttonCreateOnager, new Onager()));
		this.buttonCreatePiker.setOnMousePressed(event -> addProduction(this.buttonCreatePiker, new Piker()));
		this.buttonCreateKnight.setOnMousePressed(event -> addProduction(this.buttonCreateKnight, new Knight()));

		this.buttonUpgradeCastle
				.setOnMousePressed(event -> addProduction(this.buttonUpgradeCastle, new Castle(this.currentCastle.getLevel())));

		this.removeAllProduction.setOnMousePressed(event -> this.currentCastle.resetQueue(true));
		this.removeLastProduction.setOnMousePressed(event -> this.currentCastle.removeLastProduction(true));

		this.buttonCreatePiker.setOnMouseEntered(event ->
		{
			Text t = new Text();
			this.pikerCost = t;
			t.setText(PIKER_COST + "");
			t.setFont(new Font(30));
			t.setFill(Color.ORANGERED);
			t.setStyle("-fx-font-weight: bold");
			t.setWrappingWidth(50);
			t.setStroke(Color.BLACK);
			t.setStrokeWidth(2);
			t.setTextAlignment(TextAlignment.CENTER);
			t.relocate(this.buttonCreatePiker.getLayoutX(), this.buttonCreatePiker.getLayoutY());
			t.setMouseTransparent(true);
			addNode(t);
		});

		this.buttonCreateKnight.setOnMouseEntered(event ->
		{
			Text t = new Text();
			this.knightCost = t;
			t.setText(KNIGHT_COST + "");
			t.setFont(new Font(30));
			t.setFill(Color.ORANGERED);
			t.setStyle("-fx-font-weight: bold");
			t.setWrappingWidth(50);
			t.setStroke(Color.BLACK);
			t.setStrokeWidth(2);
			t.setTextAlignment(TextAlignment.CENTER);
			t.relocate(this.buttonCreateKnight.getLayoutX(), this.buttonCreateKnight.getLayoutY());
			t.setMouseTransparent(true);
			addNode(t);
		});

		this.buttonCreateOnager.setOnMouseEntered(event ->
		{
			Text t = new Text();
			this.onagerCost = t;
			t.setText(ONAGER_COST + "");
			t.setFont(new Font(30));
			t.setFill(Color.ORANGERED);
			t.setStyle("-fx-font-weight: bold");
			t.setWrappingWidth(70);
			t.setStroke(Color.BLACK);
			t.setStrokeWidth(2);
			t.setTextAlignment(TextAlignment.CENTER);
			t.relocate(this.buttonCreateOnager.getLayoutX(), this.buttonCreateOnager.getLayoutY());
			t.setMouseTransparent(true);
			addNode(t);
		});

		this.buttonUpgradeCastle.setOnMouseEntered(event ->
		{
			Text t = new Text();
			this.castleCost = t;
			t.setText(LEVEL_UP_COST_FACTOR * this.currentCastle.getLevel() + "");
			t.setFont(new Font(30));
			t.setFill(Color.ORANGERED);
			t.setStyle("-fx-font-weight: bold");
			t.setWrappingWidth(70);
			t.setStroke(Color.BLACK);
			t.setStrokeWidth(2);
			t.setTextAlignment(TextAlignment.CENTER);
			t.relocate(this.buttonUpgradeCastle.getLayoutX(), this.buttonUpgradeCastle.getLayoutY());
			t.setMouseTransparent(true);
			addNode(t);
		});

		this.buttonCreateKnight.setOnMouseExited(event -> removeNode(this.knightCost));
		this.buttonCreatePiker.setOnMouseExited(event -> removeNode(this.pikerCost));
		this.buttonCreateOnager.setOnMouseExited(event -> removeNode(this.onagerCost));
		this.buttonUpgradeCastle.setOnMouseExited(event -> removeNode(this.castleCost));

		addEventMouse(this.buttonCreateKnight);
		addEventMouse(this.buttonCreateOnager);
		addEventMouse(this.buttonCreatePiker);
		addEventMouse(this.buttonUpgradeCastle);
		addEventMouse(this.removeAllProduction);
		addEventMouse(this.removeLastProduction);
	}

	/**
	 * 
	 * @param b
	 * @param p
	 */
	private void addProduction(final Button b, final IProductionUnit p)
	{
		if (this.currentCastle.addProduction(p))
		{
			b.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.LIGHTGREEN, 30, 0.33, 0, 0));
		}
		else
		{
			b.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.ORANGERED, 30, 0.33, 0, 0));
		}
	}

	/**
	 * 
	 */
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

	/**
	 * 
	 * @param b
	 */
	private void addEventMouse(final Button b)
	{
		b.setMinSize(64, 64);
		b.setMaxSize(64, 64);
		b.setCursor(Cursor.HAND);
		b.addEventHandler(MouseEvent.MOUSE_ENTERED, event ->
		{
			setButtonShadow(b);
		});

		b.addEventHandler(MouseEvent.MOUSE_EXITED, event ->
		{
			b.setEffect(null);
		});

		b.setOnMouseClicked(event ->
		{
			setButtonShadow(b);
		});
	}

	/**
	 * 
	 * @param b
	 */
	private void setButtonShadow(final Button b)
	{
		final Bloom bloom = new Bloom();
		bloom.setThreshold(1);
		bloom.setInput(new DropShadow(BlurType.GAUSSIAN, Color.BISQUE, 20, 0.15, 0, 0));
		b.setEffect(bloom);
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
		addNode(this.removeAllProduction);
		addNode(this.removeLastProduction);
	}

	@Override
	public void relocateAllNodes()
	{
		final int i = 90;
		final int offset = 540;

		final float margin = (float) MARGIN_PERCENTAGE + 0.076f;

		relocate(this.buttonCreatePiker, SCENE_WIDTH * margin + i * 0, offset);
		relocate(this.buttonCreateKnight, SCENE_WIDTH * margin + i * 1, offset);
		relocate(this.buttonCreateOnager, SCENE_WIDTH * margin + i * 2, offset);

		relocate(this.removeAllProduction, SCENE_WIDTH * margin + i * 2 - i * 0.5f, offset + i * 3);
		relocate(this.removeLastProduction, SCENE_WIDTH * margin + i * 1 - i * 0.5f, offset + i * 3);

		relocate(this.buttonUpgradeCastle, SCENE_WIDTH * margin + i * 1, offset + i * 1);

		relocate(this.fillTime, SCENE_WIDTH * margin + 1, offset + i * 4);
		relocate(this.backgroundTime, SCENE_WIDTH * margin + 1, offset + i * 4);

		relocate(this.background, SCENE_WIDTH * margin - 17, offset - 22);
	}

	/**
	 * 
	 * @param castle
	 * @param productionVisible
	 */
	public void switchCastle(final Castle castle, final boolean productionVisible)
	{
		this.currentCastle = castle;
		setAllVisible(productionVisible);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

}
