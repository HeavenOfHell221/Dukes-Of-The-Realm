package SimpleGoal;

import DukesOfTheRealm.Castle;

/**
 * Objectif visant à avoir un certain montant de Florin en réserve.
 */
public class SaveFlorinGoal extends Goal
{
	/**
	 * Nombre de Florin à avoir en réserve pour accomplir cette objectif.
	 */
	private final int saveAmount;

	/**
	 * Constructeur de SaveFlorinGoal.
	 * 
	 * @param saveAmount Le montant de Florin à avoir.
	 * @see              SaveFlorinGoal#saveAmount
	 * @see              Goal.GeneratorGoal
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
		return "SaveFlorinGoal [saveAmount=" + this.saveAmount + "]";
	}
}
