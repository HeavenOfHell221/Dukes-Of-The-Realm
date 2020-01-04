package DukesOfTheRealm;

import static Utility.Settings.WALL_LEVEL_MAX;

import java.io.Serializable;

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
	 * Le multiplicateur de point de vie du rempart, de base à 1 et à 3 au max.
	 */
	private float multiplicator;

	/**
	 * Constructeur par défaut de Wall.
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
		this.level = this.level < WALL_LEVEL_MAX ? this.level++ : this.level;
		increaseMultiplicator();
	}

	/**
	 * Incrémente le multiplicateur en fonction du niveau.
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
	public double getProductionTime(int level)
	{
		return Settings.WALL_PRODUCTION_OFFSET + level * Settings.WALL_PRODUCTION_TIME_PER_LEVEL;
	}

	@Override
	public int getProductionCost(final int level)
	{
		return Settings.WALL_COST * (level + 1) + level * level * (level / 2);
	}

	@Override
	public void productionFinished(final Castle castle, final boolean cancel)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void productionStart(final Caserne caserne)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setLevel(final int level)
	{
		// TODO Auto-generated method stub

	}
}
