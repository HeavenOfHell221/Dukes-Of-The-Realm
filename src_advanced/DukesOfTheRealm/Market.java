package DukesOfTheRealm;

import java.io.Serializable;

import Enums.BuildingEnum;
import Interface.IBuilding;
import Interface.IProduction;
import Interface.IUpdate;
import Soldiers.Conveyors;
import Utility.Settings;

/**
 * Représente le marché qui permet d'avoir des convoyeurs de fond pour transporter des Florin.
 */
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

	/**
	 * Château à qui appartient de bâtiment.
	 */
	private Castle castle;

	/**
	 * Nombre de convoyeur acctuellement dans ce bâtiment.
	 */
	private int conveyors;

	/**
	 * Constructeur de Market.
	 * 
	 * @param castle Le château à qui appartient ce bâtiment.
	 */
	public Market(final Castle castle)
	{
		this.castle = castle;
	}

	/**
	 * Constructeur par défaut de Market.
	 */
	public Market()
	{

	}

	@Override
	public void update(final long now, final boolean pause)
	{
		if (this.conveyors < this.conveyorsMax)
		{
			this.castle.addProduction(new Conveyors());
			this.conveyors++;
		}
	}

	@Override
	public double getProductionTime(final Castle castle, final int level)
	{
		return (Settings.MARKET_PRODUCTION_OFFSET + Settings.MARKET_PRODUCTION_TIME_PER_LEVEL * level)
				* castle.getProductionTimeMultiplier();
	}

	@Override
	public int getProductionCost(int level)
	{
		level += 1;
		return Settings.MARKET_COST + level * level * level * level / 4;
	}

	@Override
	public void productionFinished(final Castle castle, final boolean cancel)
	{
		if (!cancel)
		{
			castle.getMarket().levelUp();
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

	@Override
	public void levelUp()
	{
		if (this.level < Settings.MILLER_LEVEL_MAX)
		{
			this.level += 1;
			increaseConveyors();
		}
	}

	/**
	 * Augmente le nombre de convoyeurs et les ajoute dans la caserne pour les produire.
	 */
	private void increaseConveyors()
	{
		int oldConveyorsMax = this.conveyorsMax;

		this.conveyorsMax = (int) ((double) (this.level * this.level) / 2d) + this.level;

		for (int i = 0; i < this.conveyorsMax - oldConveyorsMax; i++)
		{
			this.castle.addProduction(new Conveyors());
			this.conveyors++;
		}
	}

	/**
	 * Retire un convoyeur de ce marché.
	 */
	public final void removeConveyors()
	{
		this.conveyors--;
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
		return this.conveyorsMax;
	}

}
