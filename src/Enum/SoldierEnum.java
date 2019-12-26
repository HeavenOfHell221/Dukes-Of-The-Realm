package Enum;

import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Castle;
import Interface.IProductionUnit;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Utility.Settings;

/**
 *
 *
 */
public enum SoldierEnum implements Serializable
{
	Piker, Knight, Onager;

	public int getCost()
	{
		switch (this)
		{
			case Piker:
				return Settings.PIKER_COST;
			case Knight:
				return Settings.KNIGHT_COST;
			case Onager:
				return Settings.ONAGER_COST;
			default:
				return Integer.MAX_VALUE;
		}
	}

	public IProductionUnit getProduction(final Castle currentCastle)
	{
		switch (this)
		{
			case Piker:
				currentCastle.setNbPikersInProduction(currentCastle.getNbPikersInProduction() + 1);
				return new Piker();
			case Knight:
				currentCastle.setNbKnightsInProduction(currentCastle.getNbKnightsInProduction() + 1);
				return new Knight();
			case Onager:
				currentCastle.setNbOnagersInProduction(currentCastle.getNbOnagersInProduction() + 1);
				return new Onager();
			default:
				return null;
		}
	}

	public static SoldierEnum getRandomType(final Random rand)
	{
		switch (rand.nextInt(3))
		{
			case 0:
				return Piker;
			case 1:
				return Knight;
			case 2:
				return Onager;
		}
		return null;
	}
}
