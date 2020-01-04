package Goals;

import java.util.Random;

import DukesOfTheRealm.Castle;
import Enums.SoldierEnum;
import SimpleGoal.Goal;
import SimpleGoal.SoldierGoal;
import Utility.Settings;
import Utility.SoldierPack;

/**
 * Objectif visant � produire des unit�s.
 */
public class MultiSoldierGoal extends Goal
{
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
	 * @param castle     Le ch�teau sur lequel cet ojectif est � accomplir.
	 * @param soldierPack Contient le nombre d'unit� de chaque type � produire.
	 * @see              SimpleGoal.SoldierGoal
	 */
	public MultiSoldierGoal(final Castle castle, final SoldierPack<Integer> soldierPack)
	{
		this.goals = new GenericGoal();

		int count = 0;
		
		for(int i : soldierPack.values())
		{
			count += i;
		}
	
		while (count > 0)
		{
			SoldierEnum s = SoldierEnum.getRandomType();
			if(soldierPack.get(s) > 0)
			{
				soldierPack.replace(s, soldierPack.get(s) - 1);
				this.goals.addLast(new SoldierGoal(s));
			}
			count = 0;
			for(int i : soldierPack.values())
			{
				count += i;
			}
		}
	}


	@Override
	public boolean goal(final Castle castle)
	{
		return this.goals.goal(castle);
	}
}
