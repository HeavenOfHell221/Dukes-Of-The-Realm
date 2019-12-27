package Duke;

import static Utility.Settings.FLORIN_FACTOR_BARON;
import static Utility.Settings.FLORIN_PER_SECOND;
import static Utility.Settings.OFFSET_LEVEL_CASTLE_BARON;
import static Utility.Settings.RANDOM_LEVEL_CASTLE_BARON;

import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Castle;
import Utility.Time;

/**
 * Classe dérivée d'Actor, représente les acteurs neutre qui n'ont pas d'IA.
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
		castle.addFlorin(FLORIN_PER_SECOND * FLORIN_FACTOR_BARON * castle.getLevel() * Time.deltaTime);
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public String florinIncome(final Castle castle)
	{
		if (this.castles.contains(castle))
		{
			String tmp = String.format("%.1f", FLORIN_PER_SECOND * castle.getLevel() * FLORIN_FACTOR_BARON);
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
