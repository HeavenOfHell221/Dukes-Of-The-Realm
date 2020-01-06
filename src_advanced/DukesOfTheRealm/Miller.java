package DukesOfTheRealm;

import java.io.Serializable;

import Enums.BuildingEnum;
import Interface.IBuilding;
import Interface.IProduction;
import Utility.Settings;

/**
 * Représente le moulin qui permet d'avoir des villageois pour produire des unités.
 */
public class Miller implements Serializable, IBuilding, IProduction
{
	/**
	 * Nombre de villageois maximum de ce moulin.
	 */
	private int villagerMax;
	
	/**
	 * Nombre de villageois libre de ce moulin.
	 */
	private int villagerFree;
	
	/**
	 * Niveau de ce bâtiment.
	 */
	private int level;

	/**
	 * Constructeur par défaut de Miller.
	 */
	public Miller()
	{
		this.level = 1;
		this.villagerMax = Settings.MILLER_VILLAGER_BASE + this.level * Settings.MILLER_VILLAGER_PER_LEVEL;
		this.villagerFree = this.villagerMax - Settings.PIKER_VILLAGER * Settings.STARTER_PIKER
				- Settings.KNIGHT_VILLAGER * Settings.STARTER_KNIGHT - Settings.ONAGER_VILLAGER * Settings.STARTER_ONAGER
				- Settings.ARCHER_VILLAGER * Settings.STARTER_ARCHER - Settings.BERSERKER_VILLAGER * Settings.STARTER_BERSERKER
				- Settings.SPY_VILLAGER * Settings.STARTER_SPY;

	}

	@Override
	public double getProductionTime(final Castle castle, final int level)
	{
		return (Settings.MILLER_PRODUCTION_OFFSET + level * Settings.MILLER_PRODUCTION_TIME_PER_LEVEL)
				* castle.getProductionTimeMultiplier();
	}

	@Override
	public int getProductionCost(int level)
	{
		level += 1;
		return Settings.MILLER_COST * (level - 1) + level * level * (level + 1);
	}

	@Override
	public void levelUp()
	{
		if (this.level < Settings.MILLER_LEVEL_MAX)
		{
			this.level += 1;
			setVillagerMax();

		}
	}
	
	/**
	 * Reset le nombre de villageois une fois que le château change de propriétaire.
	 */
	public void resetVillager()
	{
		this.villagerFree = this.villagerMax;
	}

	/**
	 * Recalcul le nombre de villageois maximum et ajoute la différence dans les villageois libre.
	 */
	private void setVillagerMax()
	{
		int oldVillagerMax = this.villagerMax;
		this.villagerMax = Settings.MILLER_VILLAGER_BASE + this.level * Settings.MILLER_VILLAGER_PER_LEVEL;
		this.villagerFree += this.villagerMax - oldVillagerMax;
	}

	@Override
	public void productionFinished(final Castle castle, final boolean cancel)
	{
		if (!cancel)
		{
			castle.getMiller().levelUp();
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
		return this.villagerFree;
	}

	/**
	 * Retire un certain nombre de villageois libre.
	 * @param value La valeur qu'on retire.
	 */
	public final void removeVillager(final int value)
	{
		this.villagerFree -= value;
	}

	/**
	 * Ajoute un certain nombre de villageois libre.
	 * @param value La valeur qu'on ajoute.
	 */
	public final void addVillager(final int value)
	{
		this.villagerFree += value;
	}

	/**
	 * @return the villagerMax
	 */
	public final int getVillagerMax()
	{
		return this.villagerMax;
	}
}
