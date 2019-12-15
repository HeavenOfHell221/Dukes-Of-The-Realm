package Enum;

import java.io.Serializable;

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

	public IProductionUnit getProduction()
	{
		switch (this)
		{
			case Piker:
				return new Piker();
			case Knight:
				return new Knight();
			case Onager:
				return new Onager();
			default:
				return null;
		}
	}
}
