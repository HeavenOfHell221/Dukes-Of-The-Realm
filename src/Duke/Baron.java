package Duke;

import static Utility.Settings.FLORIN_FACTOR_BARON;
import static Utility.Settings.FLORIN_PER_SECOND;
import static Utility.Settings.OFFSET_LEVEL_CASTLE_BARON;
import static Utility.Settings.RANDOM_LEVEL_CASTLE_BARON;

import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Castle;
import Utility.Time;

public class Baron extends Actor implements Serializable
{
	public Baron()
	{

	}

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
	protected void updateFlorin(final Castle castle)
	{
		castle.addFlorin(FLORIN_PER_SECOND * FLORIN_FACTOR_BARON * castle.getLevel() * Time.deltaTime);
	}

	@Override
	public void addFirstCastle(final Castle castle)
	{
		final Random rand = new Random();
		castle.addFlorin(rand.nextInt(8001) + 200);
		castle.setLevel(rand.nextInt(RANDOM_LEVEL_CASTLE_BARON) + OFFSET_LEVEL_CASTLE_BARON);
		castle.randomSoldier();
		super.addFirstCastle(castle);
	}
}
