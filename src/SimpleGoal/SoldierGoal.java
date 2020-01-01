package SimpleGoal;

import DukesOfTheRealm.Castle;
import Enums.SoldierEnum;

/**
 * Objectif visant � cr�er une unit�.
 */
public class SoldierGoal extends Goal
{
	/**
	 * Le type de l'unit� que l'ont veut cr�er.
	 *
	 * @see Enums.SoldierEnum
	 */
	private final SoldierEnum type;

	/**
	 * Constructeur de SoldierGoal.
	 *
	 * @param type Le type de l'unit� que l'ont veut cr�er.
	 * @see        Goals.MultiSoldierGoal
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
