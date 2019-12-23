package Goal;

import java.io.Serializable;
import java.util.ArrayDeque;

import DukesOfTheRealm.Castle;
import SimpleGoal.Goal;

public class GenericGoal implements Serializable
{
	/**
	 * 
	 */
	private final ArrayDeque<Goal> goals;

	/**
	 * 
	 */
	public GenericGoal()
	{
		this.goals = new ArrayDeque<>();
	}

	/**
	 * 
	 * @param castle
	 * @return
	 */
	public boolean goal(final Castle castle)
	{
		while (!this.goals.isEmpty() && this.goals.getFirst().isGoalIsCompleted(castle))
		{
			this.goals.pollFirst();
		}

		return this.goals.size() == 0;
	}
	
	/**
	 * 
	 * @param goal
	 */
	public void addLast(Goal goal)
	{
		this.goals.addLast(goal);
	}

	public void addFirst(Goal goal)
	{
		this.goals.addFirst(goal);
	}
	
	/**
	 * 
	 * @return
	 */
	public Goal pollFirst()
	{
		return this.goals.pollFirst();
	}
	
	/**
	 * 
	 * @return
	 */
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
