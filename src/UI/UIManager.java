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

	private Castle currentCastle;
	private Pane playfieldLayer;
	
	private Rectangle background;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	private UIManager()
	{
		this.background = new Rectangle(Settings.SCENE_WIDTH * (1 - Settings.MARGIN_PERCENTAGE), Settings.SCENE_HEIGHT);
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start() 
	{
		AddAllNodes();
		RelocateAllNodes();
		SetBackground();
		
		GetAttackPreview().Start();
		GetCastlePreview().Start();
		GetProductionUnitPreview().Start();
	}
	
	public void Awake()
	{
		attackPreview = new UIAttackPreview();
		castlePreview = new UICastlePreview();
		productionUnitPreview = new UIProductionUnitPreview();
		Start();
	}
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void Update(long now, boolean pause) 
	{
		castlePreview.Update(now, pause);
		productionUnitPreview.Update(now, pause);
		attackPreview.Update(now, pause);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public void Relocate(Node node, double x, double y) 
	{
		node.relocate(x, y);
	}

	@Override
	public void AddNode(Node node) 
	{
		this.playfieldLayer.getChildren().add(node);
		this.background.toBack();
	}
	
	private void SetBackground()
	{
		Stop[] stops = new Stop[] { new Stop(0, Color.web("#753F0B")), new Stop(1, Color.web("#4F2E0F"))};
	    LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, stops);
	    this.background.setFill(lg1);
	     
		DropShadow a = new DropShadow();
		a.setOffsetX(-2);
		a.setOffsetY(0);
		a.setSpread(0.1);
		a.setRadius(5);
		a.setColor(Color.BLACK);
		this.background.setEffect(a);	
	}
	
	@Override
	public void AddAllNodes() 
	{
		AddNode(this.attackPreview);
		AddNode(this.castlePreview);
		AddNode(this.productionUnitPreview);
		AddNode(this.background);
	}

	@Override
	public void RelocateAllNodes() 
	{
		Relocate(this.background, Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.0375), 0);
	}
	
	public void SwitchCastle(Castle castle)
	{
		currentCastle = castle;
		attackPreview.SwitchCastle(castle);
		productionUnitPreview.SwitchCastle(castle);
		castlePreview.SwitchCastle(castle);
	}
	
	public void SetPlayfieldLayer(Pane playfieldLayer)
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
