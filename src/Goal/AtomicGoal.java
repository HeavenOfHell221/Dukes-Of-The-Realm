package Goal;

import Interface.IGoal;
import Interface.IParameter;

public abstract class AtomicGoal implements IGoal
{
	IParameter parameter;
	
	public AtomicGoal(IParameter parameter)
	{
		this.parameter = parameter;
	}

	public abstract boolean goal();
}
