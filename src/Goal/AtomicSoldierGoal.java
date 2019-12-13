package Goal;

import Interface.IParameter;

public class AtomicSoldierGoal extends AtomicGoal
{
	public AtomicSoldierGoal(IParameter parameter)
	{
		super(parameter);
	}
	
	public boolean goal()
	{
		AtomicSoldierParameter p = (AtomicSoldierParameter) parameter;
		
		if(p.castle.removeFlorin(p.type.getCost()))
		{
			p.castle.addProduction(p.type.getProduction());
			return true;
		}
		return false;
	}
}
