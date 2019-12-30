package SimpleGoal;

import DukesOfTheRealm.Castle;
import Enum.SoldierEnum;

/**
 * Objectif visant à créer une unité.
 */
public class SoldierGoal extends Goal
{
	/**
	 * Le type de l'unité que l'ont veut créer.
	 * @see Enum.SoldierEnum
	 */
	private final SoldierEnum type;

	/**
	 * Constructeur de SoldierGoal.
	 * @param type Le type de l'unité que l'ont veut créer.
	 * @see Goal.MultiSoldierGoal
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
