package Enums;

import static Utility.Settings.CASERNE_COST;
import static Utility.Settings.CASERNE_LEVEL_MAX;
import static Utility.Settings.CASERNE_PRODUCTION_OFFSET;
import static Utility.Settings.CASERNE_PRODUCTION_TIME_PER_LEVEL;
import static Utility.Settings.CASTLE_COST;
import static Utility.Settings.CASTLE_LEVEL_MAX;
import static Utility.Settings.CASTLE_PRODUCTION_OFFSET;
import static Utility.Settings.CASTLE_PRODUCTION_TIME_PER_LEVEL;
import static Utility.Settings.MARKET_COST;
import static Utility.Settings.MARKET_LEVEL_MAX;
import static Utility.Settings.MARKET_PRODUCTION_OFFSET;
import static Utility.Settings.MARKET_PRODUCTION_TIME_PER_LEVEL;
import static Utility.Settings.MILLER_COST;
import static Utility.Settings.MILLER_LEVEL_MAX;
import static Utility.Settings.MILLER_PRODUCTION_OFFSET;
import static Utility.Settings.MILLER_PRODUCTION_TIME_PER_LEVEL;
import static Utility.Settings.WALL_COST;
import static Utility.Settings.WALL_LEVEL_MAX;
import static Utility.Settings.WALL_PRODUCTION_OFFSET;
import static Utility.Settings.WALL_PRODUCTION_TIME_PER_LEVEL;

import DukesOfTheRealm.Caserne;
import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Market;
import DukesOfTheRealm.Miller;
import DukesOfTheRealm.Wall;
import Interface.IBuilding;

/**
 *
 * Enumération contenant les bâtiments.
 */
public enum BuildingEnum
{
	/**
	 *
	 */
	Castle(CASTLE_COST, CASTLE_PRODUCTION_OFFSET, CASTLE_PRODUCTION_TIME_PER_LEVEL, CASTLE_LEVEL_MAX),

	/**
	 *
	 */
	Miller(MILLER_COST, MILLER_PRODUCTION_OFFSET, MILLER_PRODUCTION_TIME_PER_LEVEL, MILLER_LEVEL_MAX),

	/**
	 *
	 */
	Wall(WALL_COST, WALL_PRODUCTION_OFFSET, WALL_PRODUCTION_TIME_PER_LEVEL, WALL_LEVEL_MAX),

	/**
	 *
	 */
	Caserne(CASERNE_COST, CASERNE_PRODUCTION_OFFSET, CASERNE_PRODUCTION_TIME_PER_LEVEL, CASERNE_LEVEL_MAX),

	/**
	 *
	 */
	Market(MARKET_COST, MARKET_PRODUCTION_OFFSET, MARKET_PRODUCTION_TIME_PER_LEVEL, MARKET_LEVEL_MAX);

	public final int cost;
	public final int maxLevel;
	public final float productionTimeOffset;
	public final float productionTimePerLevel;

	private BuildingEnum(final int cost, final float productionTimeOffset, final float productionTimePerLevel, final int maxLevel)
	{
		this.cost = cost;
		this.maxLevel = maxLevel;
		this.productionTimeOffset = productionTimeOffset;
		this.productionTimePerLevel = productionTimePerLevel;
	}

	public IBuilding getObject()
	{
		switch (this)
		{
			case Caserne:
				return new Caserne();
			case Castle:
				return new Castle();
			case Market:
				return new Market();
			case Miller:
				return new Miller();
			case Wall:
				return new Wall();
			default:
				return null;

		}
	}
}
