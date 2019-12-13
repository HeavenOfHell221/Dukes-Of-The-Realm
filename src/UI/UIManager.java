package UI;

import java.io.Serializable;

import Duke.Actor;
import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Main;
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
	private Pane playfieldLayer;
	private Rectangle background;

	private Castle currentCastle;
	private Castle lastCastle;
	private Actor currentActor;
	private Actor lastActor;

	private boolean productionVisible = false;
	private boolean attackVisible = false;

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
		this.productionUnitPreview.update(now, pause);
		this.attackPreview.update(now, pause);
		this.castlePreview.update(now, pause);
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

	public void switchCastle(final Castle castle, final Actor actor)
	{
		this.lastActor = this.currentActor;
		this.lastCastle = this.currentCastle;
		this.currentCastle = castle;
		this.currentActor = actor;

		if (this.lastActor == null)
		{
			this.attackVisible = false;
			this.productionVisible = true;
		}
		else if (!this.currentActor.isPlayer() && this.lastActor.isPlayer())
		{
			this.attackVisible = true;
			this.productionVisible = false;
		}
		else if (this.currentActor.isPlayer() && this.lastActor.isPlayer())
		{
			if (this.currentCastle == this.lastCastle)
			{
				this.attackVisible = false;
				this.productionVisible = true;
			}
			else
			{
				this.attackVisible = true;
				this.productionVisible = false;
			}
		}
		else if (!this.currentActor.isPlayer() && !this.lastActor.isPlayer())
		{
			this.attackVisible = false;
			this.productionVisible = false;
		}
		else if (this.currentActor.isPlayer() && !this.lastActor.isPlayer())
		{
			this.attackVisible = false;
			this.productionVisible = true;
		}

		Main.pause = this.attackVisible;

		this.attackPreview.switchCastle(castle, actor, this.attackVisible);
		this.productionUnitPreview.switchCastle(castle, actor, this.productionVisible);
		this.castlePreview.switchCastle(castle, actor, this.productionVisible, this.attackVisible);
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
