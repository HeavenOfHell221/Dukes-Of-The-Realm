package Goals;

import DukesOfTheRealm.Castle;
import Enums.BuildingEnum;
import Enums.SoldierEnum;
import SimpleGoal.BuildingGoal;
import SimpleGoal.Goal;
import SimpleGoal.SoldierGoal;
import Utility.SoldierPack;

/**
 * Objectif visant � produire des unit�s.
 */
public class MultiSoldierGoal extends Goal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3736730122848296557L;
	/**
	 * Queue des objectifs � accomplir pour accomplir cet objectif.
	 *
	 * @see GenericGoal
	 */
	private final GenericGoal goals;

	/**
	 * Constructeur De MultiSoldierGoal.
	 * <p>
	 * Va remplir la queue al�atoirement entre les diff�rents type d'unit�.
	 * </p>
	 *
	 * @param castle      Le ch�teau sur lequel cet ojectif est � accomplir.
	 * @param soldierPack Contient le nombre d'unit� de chaque type � produire.
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
