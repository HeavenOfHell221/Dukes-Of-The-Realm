package SimpleGoal;

import java.io.Serializable;

import DukesOfTheRealm.Castle;
import Interface.IGoal;

public abstract class Goal implements IGoal, Serializable
{
	public Goal()
	{

	}

	@Override
	public abstract boolean goal(final Castle castle);
}
