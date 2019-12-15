package Interface;

import DukesOfTheRealm.Castle;

/**
 *
 *
 */
public interface IGoal
{
	/**
	 *
	 * @return
	 */
	boolean goal(Castle castle);

	/**
	 *
	 * @return
	 */
	default boolean isGoalIsCompleted(final Castle castle)
	{
		return goal(castle);
	}
}
