package Goal;

import java.io.Serializable;
import java.util.ArrayDeque;

import DukesOfTheRealm.Castle;
import SimpleGoal.Goal;

public class GenericGoal implements Serializable
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
	
	public void addLast(Goal goal)
	{
		this.goals.addLast(goal);
	}

	public void addFirst(Goal goal)
	{
		this.goals.addFirst(goal);
	}
	
	public Goal pollFirst()
	{
		return this.goals.pollFirst();
	}
	
	public int size()
	{
		return goals.size();
	}

	/**
	 * @return
	 * @see java.util.ArrayDeque#getFirst()
	 */
	public Goal peekFirst()
	{
		return goals.peekFirst();
	}
	
	
}
