package UI;

import java.io.Serializable;

import Duke.Actor;
import DukesOfTheRealm.Castle;
import Interface.IUI;
import Interface.IUpdate;
import javafx.scene.Node;
import javafx.scene.Parent;

public final class UIAttackPreview extends Parent implements IUpdate, Serializable, IUI
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	private Castle currentCastle;
	private Actor currentActor;

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
	public void addNode(Node node)
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
	public void setAllVisible(final boolean visible)
	{

	}

	@Override
	public void switchCastle(final Castle castle, final Actor actor, boolean productionVisible, boolean attackVisible)
	{
		this.currentCastle = castle;
		this.currentActor = actor;
		setAllVisible(attackVisible);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
}
