package Goal;

import DukesOfTheRealm.Castle;
import Interface.IParameter;

public class ComplexeSoldierGoal extends Goal 
{
	ComplexeSoldierParameter p = null;
	
	public ComplexeSoldierGoal(final IParameter parameter)
	{
		super(parameter);
		p = castExplicit(parameter);
	}
	
	public ComplexeSoldierGoal(final Goal goal)
	{
		super(new ComplexeSoldierParameter((ComplexeSoldierParameter) goal.parameter));
	}
	
	/**
	 * 
	 */
	public boolean goal(final Castle castle)
	{
		// Tant que je peux completed le goal courant 
		while(!p.queue.isEmpty() && p.queue.getFirst().isGoalIsCompleted(castle))
		{
			p.queue.pollFirst();
		}
		
		return this.p.queue.size() == 0;
	}

	
	private ComplexeSoldierParameter castExplicit(IParameter parameter)
	{
		return (ComplexeSoldierParameter) parameter;
	}
}
