package Duke;

import java.io.Serializable;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Main;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Repr�sente le joueur.
 *
 * <p>
 * Extends de la classe Actor.
 * </p>
 *
 * @see Actor
 */
public class Player extends Actor implements Serializable
{
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par d�faut de Player.
	 */
	public Player()
	{
		super();
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void startTransient(final Color color, final Pane pane)
	{
		this.color = Color.LIMEGREEN;
		if (!Main.isNewGame)
		{
			this.castles.forEach(castle -> castle.setColor(this.color));
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public void addFirstCastle(final Castle castle)
	{
		super.addFirstCastle(castle);
		switchCastle(castle);
		castle.startSoldier();
	}

	@Override
	public boolean isPlayer()
	{
		return true;
	}
}
