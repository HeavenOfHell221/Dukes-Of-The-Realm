package Duke;

import static Utility.Settings.FLORIN_FACTOR_BARON;
import static Utility.Settings.OFFSET_LEVEL_CASTLE_BARON;
import static Utility.Settings.RANDOM_LEVEL_CASTLE_BARON;

import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Castle;
import Enums.BuildingEnum;
import Utility.Settings;
import Utility.Time;

/**
 * Repr�sente les acteurs neutre qui n'ont pas d'IA.
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
	 * Constructeur par d�faut de Baron.
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
		castle.addFlorin((Settings.FLORIN_PER_SECOND 
				+ castle.getLevel() * Settings.FLORIN_PER_SECOND
				+ Math.exp(castle.getLevel() / 4)) * Time.deltaTime * Settings.FLORIN_FACTOR_BARON);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public String florinIncome(final Castle castle)
	{
		if (this.castles.contains(castle))
		{
			String tmp = String.format("%.1f", (Settings.FLORIN_PER_SECOND 
					+ castle.getLevel() * Settings.FLORIN_PER_SECOND
					+ Math.exp(castle.getLevel() / 4)) * Settings.FLORIN_FACTOR_BARON);
			return tmp + " Florin/s";
		}
		return " -- Florin/s";
	}

	@Override
	public void addFirstCastle(final Castle castle)
	{
		final Random rand = new Random();

		for(BuildingEnum b : BuildingEnum.values())
		{
			final int k = rand.nextInt(RANDOM_LEVEL_CASTLE_BARON) + OFFSET_LEVEL_CASTLE_BARON;
			for(int i = 0; i < k; i++)
			{
				castle.getBuilding(b).levelUp();
			}
		
		}
		
		castle.addFlorin(rand.nextInt(castle.getLevel() * 200) + castle.getLevel() * 50);
		castle.randomSoldier();
		super.addFirstCastle(castle);
	}
}
