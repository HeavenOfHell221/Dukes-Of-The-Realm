package Goal;

import java.io.Serializable;

import DukesOfTheRealm.Castle;
import Interface.IGoal;
import Interface.IParameter;

public abstract class Goal implements IGoal, Serializable
{
	final IParameter parameter;
	
	public Goal(final IParameter parameter)
	{
		this.parameter = parameter;
	}
	
	public Goal()
	{
		this.parameter = null;
	}
	
	@Override
	public abstract boolean goal(final Castle castle);
}