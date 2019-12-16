package SimpleGoal;

import DukesOfTheRealm.Castle;
import Enum.SoldierEnum;

public class SoldierGoal extends Goal
{
	private final SoldierEnum type;

	public SoldierGoal(final SoldierEnum type)
	{
		this.type = type;
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return castle.addProduction(this.type.getProduction());
	}

	@Override
	public String toString()
	{
		return "SoldierGoal [type=" + type + "]";
	}
	
	
}
