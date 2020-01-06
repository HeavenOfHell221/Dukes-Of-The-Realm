package SimpleGoal;

import DukesOfTheRealm.Castle;

/**
 * Objectif visant � avoir un certain montant de Florin en r�serve.
 */
public class SaveFlorinGoal extends Goal
{
	/**
	 * Nombre de Florin � avoir en r�serve pour accomplir cette objectif.
	 */
	private final int saveAmount;

	/**
	 * Constructeur de SaveFlorinGoal.
	 *
	 * @param saveAmount Le montant de Florin � avoir.
	 * @see              SaveFlorinGoal#saveAmount
	 * @see              Goals.GeneratorGoal
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
}
