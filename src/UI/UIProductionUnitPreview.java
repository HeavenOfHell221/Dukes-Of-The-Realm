package UI;

import Interface.IUI;
import Interface.IUpdate;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public final class UIProductionUnitPreview implements IUI, IUpdate {

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	private Button buttonCreatePiker;
	private Button buttonCreateKnight;
	private Button buttonCreateOnager;
	private Button buttonUpgradeCastle;
	
	private Rectangle background;
	
	private Rectangle backgroundTime;
	private Rectangle fillTime;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	protected UIProductionUnitPreview() 
	{
		super();
		
		this.buttonCreatePiker = new Button();
		this.buttonCreateKnight = new Button();
		this.buttonCreateOnager = new Button();
		this.buttonUpgradeCastle = new Button();
		
		this.background = new Rectangle(0, 0);
		
		this.backgroundTime = new Rectangle(0, 0);
		this.fillTime = new Rectangle(0, 0);
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start() 
	{
		AddAllNodes();
		RelocateAllNodes();
	}
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void Update(long now, boolean pause) 
	{
		
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
	public void AddAllNodes()
	{
		AddNode(this.background);
		AddNode(this.backgroundTime);
		AddNode(this.buttonCreateKnight);
		AddNode(this.buttonCreateOnager);
		AddNode(this.buttonCreatePiker);
		AddNode(this.buttonCreateKnight);
		AddNode(this.fillTime);
		AddNode(this.buttonUpgradeCastle);
	}

	@Override
	public void RelocateAllNodes() 
	{
		Relocate(this.background, 0, 0);
		Relocate(this.backgroundTime, 0, 0);
		Relocate(this.buttonCreateKnight, 0, 0);
		Relocate(this.buttonCreateOnager, 0, 0);
		Relocate(this.buttonCreatePiker, 0, 0);
		Relocate(this.buttonCreateKnight, 0, 0);
		Relocate(this.fillTime, 0, 0);
		Relocate(this.buttonUpgradeCastle, 0, 0);
	}

	

	@Override
	public void AddNode(Node node) 
	{

	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
	
}
