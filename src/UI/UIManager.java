package UI;

import DukesOfTheRealm.Castle;
import Interface.IUI;
import Interface.IUpdate;
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

public class UIManager extends Parent implements IUpdate, IUI {

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	private static final UIManager instance = new UIManager();
	private UIAttackPreview attackPreview;
	private UIProductionUnitPreview productionUnitPreview;
	private UICastlePreview castlePreview;

	private Pane playfieldLayer;

	private final Rectangle background;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	private UIManager()
	{
		background = new Rectangle(Settings.SCENE_WIDTH * (1 - Settings.MARGIN_PERCENTAGE), Settings.SCENE_HEIGHT);
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		addAllNodes();
		relocateAllNodes();
		SetBackground();

		GetAttackPreview().start();
		GetCastlePreview().start();
		GetProductionUnitPreview().start();
	}

	public void Awake()
	{
		attackPreview = new UIAttackPreview();
		castlePreview = new UICastlePreview();
		productionUnitPreview = new UIProductionUnitPreview();
		start();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		castlePreview.update(now, pause);
		productionUnitPreview.update(now, pause);
		attackPreview.update(now, pause);
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
		playfieldLayer.getChildren().add(node);
		background.toBack();
	}

	private void SetBackground()
	{
		final Stop[] stops = new Stop[] { new Stop(0, Color.web("#753F0B")), new Stop(1, Color.web("#4F2E0F"))};
		final LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, stops);
		background.setFill(lg1);

		final DropShadow a = new DropShadow();
		a.setOffsetX(-2);
		a.setOffsetY(0);
		a.setSpread(0.1);
		a.setRadius(5);
		a.setColor(Color.BLACK);
		background.setEffect(a);
	}

	@Override
	public void addAllNodes()
	{
		addNode(attackPreview);
		addNode(castlePreview);
		addNode(productionUnitPreview);
		addNode(background);
	}

	@Override
	public void relocateAllNodes()
	{
		relocate(background, Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.0375), 0);
	}

	@Override
	public void switchCastle(final Castle castle)
	{
		attackPreview.switchCastle(castle);
		productionUnitPreview.switchCastle(castle);
		castlePreview.switchCastle(castle);
	}

	public void SetPlayfieldLayer(final Pane playfieldLayer)
	{
		instance.playfieldLayer = playfieldLayer;
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return the instance
	 */
	public static final UIManager GetInstance()
	{
		return instance;
	}

	/**
	 * @return the attackPreview
	 */
	public static final UIAttackPreview GetAttackPreview()
	{
		return instance.attackPreview;
	}

	/**
	 * @return the productionUnitPreview
	 */
	public static final UIProductionUnitPreview GetProductionUnitPreview()
	{
		return instance.productionUnitPreview;
	}

	/**
	 * @return the castlePreview
	 */
	public static final UICastlePreview GetCastlePreview()
	{
		return instance.castlePreview;
	}
}
