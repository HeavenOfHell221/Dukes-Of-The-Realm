package DukesOfTheRealm;

import static Utility.Settings.WALL_LEVEL_MAX;

import java.io.Serializable;

import Enums.BuildingEnum;
import Interface.IBuilding;
import Interface.IProduction;
import Utility.Settings;

/**
 * Repr�sente un rempart qui prot�ge un ch�teau. Un rempart augmente le maximum de vie des unit�s en
 * d�fense.
 */
public class Wall implements Serializable, IBuilding, IProduction
{
	/**
	 * Le niveau du rempart, de base � 0.
	 */
	private int level;

	/**
	 * Le multiplicateur de point de vie du rempart.
	 */
	private float multiplicator;

	/**
	 * Constructeur par d�faut de Wall.
	 */
	public Wall()
	{
		this.level = 0;
		this.multiplicator = 1;
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
	 * Incr�mente le multiplicateur en fonction du niveau.
	 * 
	 * @see Wall#multiplicator
	 */
	private void increaseMultiplicator()
	{
		if (this.level <= 10)
		{
			this.multiplicator += 0.05;
		}
		else
		{
			this.multiplicator += 0.15;
		}
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
	public final float getMultiplicator()
	{
		return this.multiplicator;
	}

	@Override
	public double getProductionTime(final Castle castle, int level)
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
		return "Wall [level=" + level + ", multiplicator=" + multiplicator + "]";
	}	
}
