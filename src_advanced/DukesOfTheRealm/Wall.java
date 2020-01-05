package DukesOfTheRealm;

import static Utility.Settings.WALL_LEVEL_MAX;

import java.io.Serializable;

import Enums.BuildingEnum;
import Interface.IBuilding;
import Interface.IProduction;
import Utility.Settings;

/**
 * Représente un rempart qui protège un château. Un rempart augmente le maximum de vie des unités en
 * défense.
 */
public class Wall implements Serializable, IBuilding, IProduction
{
	/**
	 * Le niveau du rempart, de base à 0.
	 */
	private int level;

	/**
	 * Le multiplicateur de point de vie du rempart.
	 */
	private double multiplier;
	
	/**
	 * 
	 */
	private final double const1 = 0.05d;
	
	/**
	 * 
	 */
	private final double const2 = 0.15d;

	/**
	 * Constructeur par défaut de Wall.
	 */
	public Wall()
	{
		this.level = 0;
		this.multiplier = 1;
	}

	/**
	 * Augmente d'un niveau le rempart et augmente le multiplicateur en fonction du nouveau niveau.
	 * 
	 * @see Wall#level
	 */
	public void levelUp()
	{
		if(this.level < WALL_LEVEL_MAX)
		{
			this.level += 1;
			increaseMultiplicator();
		}	
	}

	/**
	 * Incrémente le multiplicateur en fonction du niveau.
	 * 
	 * @see Wall#multiplier
	 */
	private void increaseMultiplicator()
	{
		if (this.level <= 10)
		{
			this.multiplier += const1;
		}
		else
		{
			this.multiplier += const2;
		}
	}
	
	public double decreaseLevel()
	{	
		double oldMultiplier = this.multiplier;
		if(this.level > 0)
		{
			if (this.level <= 10)
			{
				this.multiplier -= const1;
			}
			else
			{
				this.multiplier -= const2;
			}
			this.level -= 1;
		}
		else
		{
			this.multiplier = 1f;
			return 0;
		}
		return (oldMultiplier - this.multiplier);
	}

	/**
	 * @return the level
	 */
	@Override
	public final int getLevel()
	{
		return this.level;
	}

	/**
	 * @return the multiplicator
	 */
	public final double getMultiplicator()
	{
		return this.multiplier;
	}

	@Override
	public double getProductionTime(final Castle castle, final int level)
	{
		return (Settings.WALL_PRODUCTION_OFFSET + level * Settings.WALL_PRODUCTION_TIME_PER_LEVEL) * castle.getProductionTimeMultiplier();
	}

	@Override
	public int getProductionCost(final int level)
	{
		return Settings.WALL_COST * (level + 1) + level * level * (level / 2);
	}
		
	@Override
	public void productionFinished(final Castle castle, final boolean cancel)
	{
		if(!cancel)
		{
			((Wall)castle.getBuilding(BuildingEnum.Wall)).levelUp();
		}
	
		castle.getCaserne().getBuildingPack().replace(BuildingEnum.Wall,
				castle.getCaserne().getBuildingPack().get(BuildingEnum.Wall) - 1);
	}

	@Override
	public void productionStart(final Caserne caserne)
	{
		caserne.getBuildingPack().replace(BuildingEnum.Wall, caserne.getBuildingPack().get(BuildingEnum.Wall) + 1);
	}

	@Override
	public void setLevel(final int level)
	{
		this.level = level;
	}

	@Override
	public String toString()
	{
		return "Wall [level=" + level + ", multiplicator=" + multiplier + "]";
	}	
}
