package Duke;

import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Castle;
import Utility.Settings;
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
			return (int) (Settings.FLORIN_PER_SECOND * castle.getLevel() * Settings.FLORIN_FACTOR_BARON) + " Florin/s";
		}
		return " -- Florin/s";
	}

	@Override
	protected void updateFlorin(final Castle castle)
	{
		castle.addFlorin(Settings.FLORIN_PER_SECOND * Settings.FLORIN_FACTOR_BARON * castle.getLevel() * Time.deltaTime);
	}

	@Override
	public void addFirstCastle(final Castle castle)
	{
		final Random rand = new Random();
		castle.addFlorin(rand.nextInt(8001) + 200);
		castle.setLevel(rand.nextInt(20) + 11);
		castle.randomSoldier();
		super.addFirstCastle(castle);
	}
}
