package DukesOfTheRealm;

import java.io.Serializable;

import Enums.BuildingEnum;
import Enums.SoldierEnum;
import Interface.IBuilding;
import Interface.IProduction;
import Utility.Settings;

public class Miller implements Serializable, IBuilding, IProduction
{
	private int villagerMax;
	private int villagerFree;
	private int level;
	
	public Miller()
	{
		this.level = 1;
		this.villagerFree = Settings.MILLER_VILLAGER_BASE 
				- Settings.PIKER_VILLAGER * Settings.STARTER_PIKER
				- Settings.KNIGHT_VILLAGER * Settings.STARTER_KNIGHT
				- Settings.ONAGER_VILLAGER * Settings.STARTER_ONAGER
				- Settings.ARCHER_VILLAGER * Settings.STARTER_ARCHER
				- Settings.BERSERKER_VILLAGER * Settings.STARTER_BERSERKER
				- Settings.SPY_VILLAGER * Settings.STARTER_SPY;
		setVillagerMax();
	}
	
	@Override
	public double getProductionTime(final Castle castle, int level)
	{
		return (0 + 0 * Settings.MILLER_PRODUCTION_TIME_PER_LEVEL) * castle.getProductionTimeMultiplier();
	}

	@Override
	public int getProductionCost(final int level)
	{
		return Settings.MILLER_COST * level + level * (level + 1) * (level + 2);
	}
	
	private void levelUp()
	{
		if(this.level < Settings.MILLER_LEVEL_MAX)
		{
			this.level += 1;
			setVillagerMax();
		}
	}
	
	private void setVillagerMax()
	{
		this.villagerMax = Settings.MILLER_VILLAGER_BASE + this.level * 7;
		
	}

	@Override
	public void productionFinished(final Castle castle, final boolean cancel)
	{
		if(!cancel)
		{
			((Miller)castle.getBuilding(BuildingEnum.Miller)).levelUp();
		}
	
		castle.getCaserne().getBuildingPack().replace(BuildingEnum.Miller,
				castle.getCaserne().getBuildingPack().get(BuildingEnum.Miller) - 1);
	}

	@Override
	public void productionStart(final Caserne caserne)
	{
		caserne.getBuildingPack().replace(BuildingEnum.Miller, caserne.getBuildingPack().get(BuildingEnum.Miller) + 1);
	}

	@Override
	public void setLevel(final int level)
	{
		this.level = level;
	}

	@Override
	public int getLevel()
	{
		return this.level;
	}

	/**
	 * @return the villagerFree
	 */
	public final int getVillagerFree()
	{
		return villagerFree;
	}
	
	public final void removeVillager(int value)
	{
		this.villagerFree -= value;
	}
	
	public final void addVillager(int value)
	{
		this.villagerFree += value;
	}
}
