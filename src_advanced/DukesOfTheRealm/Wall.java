package DukesOfTheRealm;

import java.io.Serializable;

import Interface.IUpdate;

import static Utility.Settings.*;

/**
 * Repr�sente un rempart qui prot�ge un ch�teau.
 * Un rempart augmente le maximum de vie des unit�s en d�fense.
 */
public class Wall implements Serializable
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
	 * @see Wall#level
	 */
	public void levelUp()
	{	
		this.level = this.level < WALL_LEVEL_MAX ? this.level++ : this.level;
		increaseMultiplicator();
	}
	
	/**
	 * Incr�mente le multiplicateur en fonction du niveau.
	 * @see Wall#multiplicator
	 */
	private void increaseMultiplicator()
	{
		if(this.level <= 10)
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
	public final int getLevel()
	{
		return level;
	}

	/**
	 * @return the multiplicator
	 */
	public final float getMultiplicator()
	{
		return multiplicator;
	}
}
