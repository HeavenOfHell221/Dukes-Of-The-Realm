package DukesOfTheRealm;

import static Utility.Settings.WALL_LEVEL_MAX;

import java.io.Serializable;

import Interface.IBuilding;
import Interface.IProduction;

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
	 * Le multiplicateur de point de vie du rempart, de base � 1 et � 3 au max.
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
		this.level = this.level < WALL_LEVEL_MAX ? this.level++ : this.level;
		increaseMultiplicator();
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
	public double getProductionTime()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getProductionCost(final Castle castle)
	{
		// TODO Auto-generated method stub
		return 0;
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
