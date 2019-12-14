package Goal;

import java.io.Serializable;

import DukesOfTheRealm.Castle;
import Interface.IGoal;
import Interface.IParameter;

public abstract class AtomicGoal implements IGoal, Serializable
{
	final IParameter parameter;
	
	public AtomicGoal(final IParameter parameter)
	{
		this.parameter = parameter;
	}
	
	public AtomicGoal()
	{
		this.parameter = null;
	}
	
	public abstract boolean goal(final Castle castle);
}