package Interface;

/**
 * 
 *
 */
public interface IUpdate
{
	/**
	 * 
	 */
	void start();

	/**
	 * 
	 * @param now
	 * @param pause
	 */
	void update(long now, boolean pause);
}
