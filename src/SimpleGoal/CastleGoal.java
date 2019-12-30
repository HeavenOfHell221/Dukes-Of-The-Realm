package SimpleGoal;

import DukesOfTheRealm.Castle;

/**
 * 
 */
public class CastleGoal extends Goal
{
	private int level;
	/**
	 * 
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
