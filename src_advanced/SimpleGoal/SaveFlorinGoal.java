package SimpleGoal;

import DukesOfTheRealm.Castle;

/**
 * Objectif visant à avoir un certain montant de Florin en réserve.
 */
public class SaveFlorinGoal extends Goal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1698784490106841593L;
	/**
	 * Nombre de Florin à avoir en réserve pour accomplir cette objectif.
	 */
	private final int saveAmount;

	/**
	 * Constructeur de SaveFlorinGoal.
	 *
	 * @param saveAmount Le montant de Florin à avoir.
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
