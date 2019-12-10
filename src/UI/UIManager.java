package UI;

import java.io.Serializable;

import Duke.Actor;
import DukesOfTheRealm.Castle;
import Interface.IUI;
import Utility.Settings;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class UIManager extends Parent implements IUI, Serializable
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	private static UIManager instance = new UIManager();
	private UIAttackPreview attackPreview;
	private UIProductionUnitPreview productionUnitPreview;
	private UICastlePreview castlePreview;

	private transient Pane playfieldLayer;

	private transient Rectangle background;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	private UIManager()
	{

	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	public void start()
	{

		addAllNodes();
		relocateAllNodes();
		setBackground();

		getAttackPreview().start();
		getCastlePreview().start();
		getProductionUnitPreview().start();
	}

	public void awake(final Pane pane)
	{
		this.playfieldLayer = pane;
		this.background = new Rectangle(Settings.SCENE_WIDTH * (1 - Settings.MARGIN_PERCENTAGE), Settings.SCENE_HEIGHT * 2);
		this.attackPreview = new UIAttackPreview();
		this.castlePreview = new UICastlePreview();
		this.productionUnitPreview = new UIProductionUnitPreview();
		start();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	public void update(final long now, final boolean pause)
	{
		this.castlePreview.update(now, pause);
		this.productionUnitPreview.update(now, pause);
		this.attackPreview.update(now, pause);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public void relocate(final Node node, final double x, final double y)
	{
		node.relocate(x, y);
	}

	@Override
	public void addNode(final Node node)
	{
		this.playfieldLayer.getChildren().add(node);
		this.background.toBack();
	}

	private void setBackground()
	{
		final Stop[] stops = new Stop[]
		{
				new Stop(0, Color.web("#753F0B")), new Stop(1, Color.web("#4F2E0F"))
		};
		final LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, stops);
		this.background.setFill(lg1);

		final DropShadow a = new DropShadow();
		a.setOffsetX(-2);
		a.setOffsetY(0);
		a.setSpread(0.1);
		a.setRadius(5);
		a.setColor(Color.BLACK);
		this.background.setEffect(a);
	}

	@Override
	public void addAllNodes()
	{
		addNode(this.attackPreview);
		addNode(this.castlePreview);
		addNode(this.productionUnitPreview);
		addNode(this.background);
	}

	@Override
	public void relocateAllNodes()
	{
		relocate(this.background, Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.0375), 0);
	}

	@Override
	public void switchCastle(final Castle castle, final Actor actor, final boolean productionVisible, final boolean attackVisible)
	{
		this.attackPreview.switchCastle(castle, actor, productionVisible, attackVisible);
		this.productionUnitPreview.switchCastle(castle, actor, productionVisible, attackVisible);
		this.castlePreview.switchCastle(castle, actor, productionVisible, attackVisible);
	}

	public void setPlayfieldLayer(final Pane playfieldLayer)
	{
		instance.playfieldLayer = playfieldLayer;
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return the instance
	 */
	public static final UIManager getInstance()
	{
		return instance;
	}

	/**
	 * @return the attackPreview
	 */
	public static final UIAttackPreview getAttackPreview()
	{
		return instance.attackPreview;
	}

	/**
	 * @return the productionUnitPreview
	 */
	public static final UIProductionUnitPreview getProductionUnitPreview()
	{
		return instance.productionUnitPreview;
	}

	/**
	 * @return the castlePreview
	 */
	public static final UICastlePreview getCastlePreview()
	{
		return instance.castlePreview;
	}

	@Override
	public void setAllVisible(final boolean visible)
	{

	}

	@Override
	public void setVisible(final Node node, final boolean visible)
	{
		node.setVisible(visible);
	}
}
