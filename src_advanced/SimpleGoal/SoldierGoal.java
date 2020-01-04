package SimpleGoal;

import DukesOfTheRealm.Castle;
import Enums.SoldierEnum;

/**
 * Objectif visant à créer une unité.
 */
public class SoldierGoal extends Goal
{
	/**
	 * Le type de l'unité que l'ont veut créer.
	 *
	 * @see Enums.SoldierEnum
	 */
	private final SoldierEnum type;

	/**
	 * Constructeur de SoldierGoal.
	 *
	 * @param type Le type de l'unité que l'ont veut créer.
	 * @see        Goals.MultiSoldierGoal
	 */
	public SoldierGoal(final SoldierEnum type)
	{
		this.type = type;
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return castle.addProduction(this.type.getObject());
	}
	
	
}
