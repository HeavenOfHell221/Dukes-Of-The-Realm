package SimpleGoal;

import DukesOfTheRealm.Castle;

/**
 * Objectif visant à augmenter d'un niveau un château.
 */
public class CastleGoal extends Goal
{
	/**
	 * Le niveau du château qu'on veut améliorer.
	 */
	private int level;
	
	/**
	 * Constucteur de CastleGoal
	 * @param castle Le château qu'on veut améliorer.
	 * @see Goal.GeneratorGoal
	 */
	public CastleGoal(Castle castle)
	{
		this.level = castle.getLevel();
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return castle.addProduction(new Castle(this.level));
	}

	@Override
	public String toString()
	{
		return "CastleGoal [level=" + (level+1) + "]";
	}
}
