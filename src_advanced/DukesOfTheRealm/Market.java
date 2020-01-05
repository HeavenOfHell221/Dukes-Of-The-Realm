package DukesOfTheRealm;

import java.io.Serializable;

import Enums.BuildingEnum;
import Interface.IBuilding;
import Interface.IProduction;
import Utility.Settings;

public class Market implements Serializable, IBuilding, IProduction
{
	/**
	 * Le niveau de ce bâtiment.
	 */
	private int level;
	
	/**
	 * Nombre de convoyeur de fond maximum.
	 */
	private int conveyorsMax;
	
	/**
	 * Nombre de convoyeur libre.
	 */
	private int conveyorsFree;
	
	
	@Override
	public double getProductionTime(final Castle castle, int level)
	{
		return (Settings.MARKET_PRODUCTION_OFFSET + Settings.MARKET_PRODUCTION_TIME_PER_LEVEL * level) * castle.getProductionTimeMultiplier();
	}

	@Override
	public int getProductionCost(int level)
	{
		level += 1;
		return Settings.MARKET_COST + (level * level * level * level) / 4;
	}

	@Override
	public void productionFinished(final Castle castle, final boolean cancel)
	{
		if(!cancel)
		{
			((Market)castle.getBuilding(BuildingEnum.Market)).levelUp();
		}
	
		castle.getCaserne().getBuildingPack().replace(BuildingEnum.Market,
				castle.getCaserne().getBuildingPack().get(BuildingEnum.Market) - 1);
	}

	@Override
	public void productionStart(final Caserne caserne)
	{
		caserne.getBuildingPack().replace(BuildingEnum.Market, caserne.getBuildingPack().get(BuildingEnum.Market) + 1);
	}

	@Override
	public void setLevel(final int level)
	{
		this.level = level;
	}
	
	public void levelUp()
	{
		this.level += 1;
		increaseConveyors();
	}
	
	private void increaseConveyors()
	{
		int oldConveyorsMax = this.conveyorsMax;
		this.conveyorsMax = (this.level * this.level) / 2 + this.level;
		this.conveyorsFree += (this.conveyorsMax - oldConveyorsMax);
	}

	@Override
	public int getLevel()
	{
		return this.level;
	}

	/**
	 * @return the conveyorsMax
	 */
	public final int getConveyorsMax()
	{
		return conveyorsMax;
	}

	/**
	 * @return the conveyorsFree
	 */
	public final int getConveyorsFree()
	{
		return conveyorsFree;
	}

	public final void removeConveyors(int value)
	{
		this.conveyorsFree -= value;
	}
}
