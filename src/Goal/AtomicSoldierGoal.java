package Goal;

import DukesOfTheRealm.Castle;
import Interface.IParameter;

public class AtomicSoldierGoal extends Goal
{
	public AtomicSoldierGoal(final IParameter parameter)
	{
		super(parameter);
	}
	
	public AtomicSoldierGoal(final Goal goal)
	{
		super(new AtomicSoldierParameter((AtomicSoldierParameter) goal.parameter));
	}
	
	public boolean goal(final Castle castle)
	{
		AtomicSoldierParameter p = (AtomicSoldierParameter) parameter;
		return castle.addProduction(p.type.getProduction());
	}
}