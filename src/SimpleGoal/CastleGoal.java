package SimpleGoal;

import DukesOfTheRealm.Castle;

public class CastleGoal extends Goal
{
	private int level;  
	
	public CastleGoal()
	{

	}

	@Override
	public boolean goal(final Castle castle)
	{
		this.level = castle.getLevel();
		return castle.addProduction(new Castle(this.level));
	}

	@Override
	public String toString()
	{
		return "CastleGoal [level++]";
	}

	
}
