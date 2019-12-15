package Goal;

import java.util.ArrayDeque;

import DukesOfTheRealm.Castle;
import SimpleGoal.Goal;

public class GenericGoal
{
	private final ArrayDeque<Goal> goals;

	public GenericGoal()
	{
		this.goals = new ArrayDeque<>();
	}

	public boolean goal(final Castle castle)
	{
		while (!this.goals.isEmpty() && this.goals.getFirst().isGoalIsCompleted(castle))
		{
			this.goals.pollFirst();
		}

		return this.goals.size() == 0;
	}
	
	public void add(Goal goal)
	{
		this.goals.addLast(goal);
	}
	
	public int size()
	{
		return goals.size();
	}
}
