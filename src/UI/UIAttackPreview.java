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
	public void Update(final long now, final boolean pause)
	{

	}


	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public void AddNode(final Node node)
	{
		getChildren().add(node);
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
	public void Relocate(final Node node, final double x, final double y)
	{
		node.relocate(x, y);
	}

	@Override
	public void SwitchCastle(final Castle castle)
	{

	}


	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
}
