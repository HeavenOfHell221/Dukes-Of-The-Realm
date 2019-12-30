package UI;

import java.io.Serializable;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Main;
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

/**
 *
 */
public class UIManager extends Parent implements IUI, Serializable, IUpdate
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 *
	 */
	private static UIManager instance = new UIManager();

	/**
	 *
	 */
	private UIAttackPreview attackPreview;

	/**
	 *
	 */
	private UIProductionUnitPreview productionUnitPreview;

	/**
	 *
	 */
	private UICastlePreview castlePreview;

	/**
	 *
	 */
	private Pane playfieldLayer;

	/**
	 *
	 */
	private Rectangle background;

	/**
	 *
	 */
	private Castle currentCastle;

	/**
	 *
	 */
	private Castle lastCastle;

	/**
	 *
	 */
	private boolean productionVisible = false;

	/**
	 *
	 */
	private boolean attackVisible = false;

	/**
	 *
	 */
	private boolean castleSwitch = false;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 *
	 */
	private UIManager()
	{

	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	/**
	 *
	 */
	@Override
	public void start()
	{
		addAllNodes();
		relocateAllNodes();
		setBackground();

		getAttackPreview().start();
		getCastlePreview().start();
		getProductionUnitPreview().start();
	}

	/**
	 *
	 * @param pane
	 */
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

	@Override
	public void update(final long now, final boolean pause)
	{
		if (this.currentCastle != null && this.currentCastle.getActor() != null)
		{
			this.productionUnitPreview.update(now, pause);
			this.attackPreview.update(now, pause);
			this.castlePreview.update(now, pause);
		}
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

	/**
	 *
	 */
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

	/**
	 *
	 * @param castle
	 */
	public void switchCastle(final Castle castle)
	{
		this.lastCastle = this.currentCastle;
		this.currentCastle = castle;

		this.attackVisible = false;
		this.productionVisible = false;
		this.castleSwitch = false;

		if (this.lastCastle == null)
		{
			this.productionVisible = true;
		}
		else if (!this.currentCastle.getActor().isPlayer() && this.lastCastle.getActor().isPlayer())
		{
			this.attackVisible = true;
		}
		else if (this.currentCastle.getActor().isPlayer() && this.lastCastle.getActor().isPlayer())
		{
			if (this.currentCastle == this.lastCastle)
			{
				this.productionVisible = true;
			}
			else
			{
				this.attackVisible = true;
			}
		}
		else if (this.currentCastle.getActor().isPlayer() && !this.lastCastle.getActor().isPlayer())
		{
			this.productionVisible = true;
		}

		Main.pause = this.attackVisible;
		this.castleSwitch = !this.attackVisible;

		this.attackPreview.switchCastle(castle, this.attackVisible);
		this.productionUnitPreview.switchCastle(castle, this.productionVisible);
		this.castlePreview.switchCastle(castle, this.castleSwitch, this.productionVisible, this.attackVisible);
	}

	@Override
	public void setAllVisible(final boolean visible)
	{
		setVisible(this.background, visible);
	}

	@Override
	public void setVisible(final Node node, final boolean visible)
	{
		node.setVisible(visible);
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
	 * @param playfieldLayer the playfieldLayer to set
	 */
	public final void setPlayfieldLayer(final Pane playfieldLayer)
	{
		instance.playfieldLayer = playfieldLayer;
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
}
