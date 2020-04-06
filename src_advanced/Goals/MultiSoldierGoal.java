package Goals;

import DukesOfTheRealm.Castle;
import Enums.BuildingEnum;
import Enums.SoldierEnum;
import SimpleGoal.BuildingGoal;
import SimpleGoal.Goal;
import SimpleGoal.SoldierGoal;
import Utility.SoldierPack;

/**
 * Objectif visant à produire des unités.
 */
public class MultiSoldierGoal extends Goal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3736730122848296557L;
	/**
	 * Queue des objectifs à accomplir pour accomplir cet objectif.
	 *
	 * @see GenericGoal
	 */
	private final GenericGoal goals;

	/**
	 * Constructeur De MultiSoldierGoal.
	 * <p>
	 * Va remplir la queue aléatoirement entre les différents type d'unité.
	 * </p>
	 *
	 * @param castle      Le château sur lequel cet ojectif est à accomplir.
	 * @param soldierPack Contient le nombre d'unité de chaque type à produire.
	 * @see               SimpleGoal.SoldierGoal
	 */
	public MultiSoldierGoal(final Castle castle, final SoldierPack<Integer> soldierPack)
	{
		this.goals = new GenericGoal();

		int count = 0;

		for (int i : soldierPack.values())
		{
			count += i;
		}

		while (count > 0)
		{
			SoldierEnum s = SoldierEnum.getRandomType();
			if (soldierPack.get(s) > 0)
			{
				soldierPack.replace(s, soldierPack.get(s) - 1);
				this.goals.addLast(new SoldierGoal(s));
			}
			count = 0;
			for (int i : soldierPack.values())
			{
				count += i;
			}
		}
	}

	@Override
	public boolean goal(final Castle castle)
	{
		if (this.goals.goal(castle) == false)
		{
			if (castle.getMiller().getVillagerFree() < ((SoldierGoal) this.goals.peekFirst()).type.villager)
			{
				if (castle.getMiller().getLevel() < BuildingEnum.Miller.maxLevel)
				{
					this.goals.addFirst(new BuildingGoal(BuildingEnum.Miller));
				}
				else
				{
					return true;
				}
			}
			else
			{
				return false;
			}
		}
		return true;
	}
}
