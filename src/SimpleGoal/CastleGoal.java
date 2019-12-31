package SimpleGoal;

import DukesOfTheRealm.Castle;

/**
 * Objectif visant � augmenter d'un niveau un ch�teau.
 */
public class CastleGoal extends Goal
{
	/**
	 * Le niveau du ch�teau qu'on veut am�liorer.
	 */
	private final int level;

	/**
	 * Constucteur de CastleGoal
	 * 
	 * @param castle Le ch�teau qu'on veut am�liorer.
	 * @see          Goal.GeneratorGoal
	 */
	public CastleGoal(final Castle castle)
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
		return "CastleGoal [level=" + (this.level + 1) + "]";
	}
}
