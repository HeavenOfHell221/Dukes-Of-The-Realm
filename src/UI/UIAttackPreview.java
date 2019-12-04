package UI;

import DukesOfTheRealm.Castle;
import Interface.IUI;
import Interface.IUpdate;
import javafx.scene.Node;
import javafx.scene.Parent;

public final class UIAttackPreview extends Parent implements IUpdate, IUI {

	
	
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	public UIAttackPreview() 
	{
		super();
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start() 
	{
		
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
	public void AddNode(Node node)
	{
		this.getChildren().add(node);
	}

	@Override
	public void AddAllNodes() 
	{
	
	}

	@Override
	public void RelocateAllNodes() 
	{

	}

	@Override
	public void Relocate(Node node, double x, double y) 
	{
		node.relocate(x, y);
	}

	@Override
	public void SwitchCastle(Castle castle) 
	{

	}


	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
}
