package Goal;

import DukesOfTheRealm.Castle;
import Interface.IParameter;

public class AtomicSoldierGoal extends AtomicGoal
{
	public AtomicSoldierGoal(final IParameter parameter)
	{
		super(parameter);
	}
	
	public AtomicSoldierGoal(final AtomicGoal goal)
	{
		super(new AtomicSoldierParameter((AtomicSoldierParameter) goal.parameter));
	}
	
	public boolean goal(final Castle castle)
	{
		AtomicSoldierParameter p = (AtomicSoldierParameter) parameter;
		return castle.addProduction(p.type.getProduction());
	}
}
