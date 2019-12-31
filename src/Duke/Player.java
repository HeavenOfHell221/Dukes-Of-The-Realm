package Duke;

import java.io.Serializable;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Main;
import Utility.Settings;
import Utility.Time;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe dérivée d'Actor, réprésente le joueur.
 *
 * @see Actor
 */
public class Player extends Actor implements Serializable
{
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par défaut de Player.
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
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	protected void updateFlorin(final Castle castle)
	{
		castle.addFlorin(Settings.FLORIN_PER_SECOND * castle.getLevel() * Time.deltaTime);
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
