package SimpleGoal;

import DukesOfTheRealm.Castle;

/**
 * 
 */
public class SaveFlorinGoal extends Goal
{
	/**
	 * 
	 */
	private final int saveAmount;

	/**
	 * 
	 * @param saveAmount
	 */
	public SaveFlorinGoal(final int saveAmount)
	{
		this.saveAmount = saveAmount;
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return castle.getTotalFlorin() > this.saveAmount;
	}

	@Override
	public String toString()
	{
		return "SaveFlorinGoal [saveAmount=" + saveAmount + "]";
	}
	
	
}
