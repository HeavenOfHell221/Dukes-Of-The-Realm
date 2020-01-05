package DukesOfTheRealm;

import java.io.Serializable;

import Enums.BuildingEnum;
import Enums.SoldierEnum;
import Interface.IBuilding;
import Interface.IProduction;
import Interface.IUpdate;
import Soldiers.Conveyors;
import Utility.Settings;

public class Market implements Serializable, IBuilding, IProduction, IUpdate
{
	/**
	 * Le niveau de ce bâtiment.
	 */
	private int level;
	
	/**
	 * Nombre de convoyeur de fond maximum.
	 */
	private int conveyorsMax;
	
	private Castle castle;
	
	private int conveyors;
	
	public Market(Castle castle)
	{
		this.castle = castle;
	}
	
	public Market()
	{
		
	}
	
	@Override
	public void update(long now, boolean pause)
	{
		if(conveyors < conveyorsMax)
		{
			this.castle.addProduction(new Conveyors());
			this.conveyors++;
		}
	}
	
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

		for(int i = 0; i < (this.conveyorsMax - oldConveyorsMax); i++)
		{
			this.castle.addProduction(new Conveyors());
			this.conveyors++;
		}
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
	
	public final void removeConveyors()
	{
		this.conveyors--;
	}
}
