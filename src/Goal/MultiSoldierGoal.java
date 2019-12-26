package Goal;

import java.util.Random;

import DukesOfTheRealm.Castle;
import Enum.SoldierEnum;
import SimpleGoal.Goal;
import SimpleGoal.SoldierGoal;

public class MultiSoldierGoal extends Goal
{
	private final GenericGoal goals;
	private final int nbPikers;
	private final int nbKnights;
	private final int nbOnagers;

	public MultiSoldierGoal(final Castle castle, int nbPikers, int nbKnights, int nbOnagers)
	{
		this.goals = new GenericGoal();
		Random rand = new Random();

		if (castle.getNbPikersInProduction() + nbPikers > 150)
		{
			nbPikers -= castle.getNbPikersInProduction() + nbPikers - 150;
		}

		if (castle.getNbKnightsInProduction() + nbKnights > 100)
		{
			nbKnights -= castle.getNbKnightsInProduction() + nbKnights - 100;
		}

		if (castle.getNbOnagersInProduction() + nbOnagers > 60)
		{
			nbOnagers -= castle.getNbOnagersInProduction() + nbOnagers - 60;
		}

		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;

		int count = nbPikers + nbKnights + nbOnagers;

		while (count > 0)
		{
			switch (rand.nextInt(3))
			{
				case 0:
					if (nbPikers > 0)
					{
						this.goals.addLast(new SoldierGoal(SoldierEnum.Piker));
						nbPikers--;
					}
					break;

				case 1:
					if (nbKnights > 0)
					{
						this.goals.addLast(new SoldierGoal(SoldierEnum.Knight));
						nbKnights--;
					}
					break;

				case 2:
					if (nbOnagers > 0)
					{
						this.goals.addLast(new SoldierGoal(SoldierEnum.Onager));
						nbOnagers--;
					}
					break;

				default:
					break;
			}
			count = nbPikers + nbKnights + nbOnagers;
		}
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return this.goals.goal(castle);
	}

	@Override
	public String toString()
	{
		return "MultiSoldierGoal [nbPikers= " + this.nbPikers + ", nbKnights= " + this.nbKnights + ", nbOnagers= " + this.nbOnagers + "]";
	}

}
