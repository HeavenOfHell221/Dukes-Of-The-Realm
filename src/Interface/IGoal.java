package Interface;

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
	boolean goal();
	
	/**
	 *
	 * @return
	 */
	default boolean isGoalIsCompleted()
	{
		return goal();
	}
}
