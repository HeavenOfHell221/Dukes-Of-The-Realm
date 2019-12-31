package SimpleGoal;

import DukesOfTheRealm.Castle;
import Enum.SoldierEnum;

/**
 * Objectif visant � cr�er une unit�.
 */
public class SoldierGoal extends Goal
{
	/**
	 * Le type de l'unit� que l'ont veut cr�er.
	 * 
	 * @see Enum.SoldierEnum
	 */
	private final SoldierEnum type;

	/**
	 * Constructeur de SoldierGoal.
	 * 
	 * @param type Le type de l'unit� que l'ont veut cr�er.
	 * @see        Goal.MultiSoldierGoal
	 */
	public SoldierGoal(final SoldierEnum type)
	{
		this.type = type;
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return castle.addProduction(this.type.getProduction(castle));
	}
}
