package SimpleGoal;

import DukesOfTheRealm.Castle;

public class CastleGoal extends Goal
{
	public CastleGoal()
	{

	}

	@Override
	public boolean goal(final Castle castle)
	{
		return castle.addProduction(new Castle(castle.getLevel()));
	}

}
