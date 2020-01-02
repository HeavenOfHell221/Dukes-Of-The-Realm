package Duke;

import static Utility.Settings.FLORIN_FACTOR_BARON;
import static Utility.Settings.OFFSET_LEVEL_CASTLE_BARON;
import static Utility.Settings.RANDOM_LEVEL_CASTLE_BARON;

import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Castle;
import Utility.Settings;
import Utility.Time;

/**
 * Représente les acteurs neutre qui n'ont pas d'IA.
 *
 * <p>
 * Extends de la classe Actor.
 * </p>
 *
 * @see Actor
 */
public class Baron extends Actor implements Serializable
{
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par défaut de Baron.
	 */
	public Baron()
	{
		super();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	protected void updateFlorin(final Castle castle)
	{
		castle.addFlorin(Settings.FLORIN_PER_SECOND * FLORIN_FACTOR_BARON * castle.getLevel() * Time.deltaTime
				+ FLORIN_FACTOR_BARON * Settings.FLORIN_PER_SECOND_OFFSET * Time.deltaTime);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public String florinIncome(final Castle castle)
	{
		if (this.castles.contains(castle))
		{
			String tmp = String.format("%.1f", Settings.FLORIN_PER_SECOND * castle.getLevel() * FLORIN_FACTOR_BARON
					+ FLORIN_FACTOR_BARON * Settings.FLORIN_PER_SECOND_OFFSET);
			return tmp + " Florin/s";
		}
		return " -- Florin/s";
	}

	@Override
	public void addFirstCastle(final Castle castle)
	{
		final Random rand = new Random();

		castle.setLevel(rand.nextInt(RANDOM_LEVEL_CASTLE_BARON) + OFFSET_LEVEL_CASTLE_BARON);
		castle.addFlorin(rand.nextInt(castle.getLevel() * 200) + castle.getLevel() * 50);
		castle.randomSoldier();
		super.addFirstCastle(castle);
	}
}
