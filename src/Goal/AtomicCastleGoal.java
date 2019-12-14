package Goal;

import DukesOfTheRealm.Castle;
import Interface.IParameter;

public class AtomicCastleGoal extends AtomicGoal
{
	public AtomicCastleGoal()
	{
		super();
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return castle.addProduction(new Castle(castle.getLevel()));
	}
}
