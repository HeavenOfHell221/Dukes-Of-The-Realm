package Enums;

import static Utility.Settings.*;
import DukesOfTheRealm.*;
import Interface.IBuilding;

/**
 * 
 * Enum�ration contenant les b�timents.
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
	
	private BuildingEnum(int cost, float productionTimeOffset, float productionTimePerLevel, int maxLevel)
	{
		this.cost = cost;
		this.maxLevel = maxLevel;
		this.productionTimeOffset = productionTimeOffset;
		this.productionTimePerLevel = productionTimePerLevel;
	}
	
	public IBuilding getObject()
	{
		switch(this)
		{
			case Caserne: return new Caserne();
			case Castle: return new Castle();
			case Market: return new Market();
			case Miller: return new Miller();
			case Wall: return new Wall();
			default: return null;
			
		}
	}
}
