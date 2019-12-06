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
	public void start()
	{

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
	public void addNode(final Node node)
	{
		getChildren().add(node);
	}

	@Override
	public void addAllNodes()
	{

	}

	@Override
	public void relocateAllNodes()
	{

	}

	@Override
	public void relocate(final Node node, final double x, final double y)
	{
		node.relocate(x, y);
	}

	@Override
	public void switchCastle(final Castle castle)
	{

	}


	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
}
