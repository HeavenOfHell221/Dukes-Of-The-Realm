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

	public MultiSoldierGoal(final Castle castle, int nbPikers_, int nbKnights_, int nbOnagers_)
	{
		//System.out.println(nbPikers_ + " " + nbKnights_ + " " + nbOnagers_);
		this.goals = new GenericGoal();
		Random rand = new Random();

		this.nbPikers = nbPikers_;
		this.nbKnights = nbKnights_;
		this.nbOnagers = nbOnagers_;

		int count = nbPikers_ + nbKnights_ + nbOnagers_;

		while (count > 0)
		{
			switch (rand.nextInt(3))
			{
				case 0:
					if (nbPikers_ > 0)
					{
						this.goals.addLast(new SoldierGoal(SoldierEnum.Piker));
						nbPikers_--;
					}
					break;

				case 1:
					if (nbKnights_ > 0)
					{
						this.goals.addLast(new SoldierGoal(SoldierEnum.Knight));
						nbKnights_--;
					}
					break;

				case 2:
					if (nbOnagers_ > 0)
					{
						this.goals.addLast(new SoldierGoal(SoldierEnum.Onager));
						nbOnagers_--;
					}
					break;

				default:
					break;
			}
			count = nbPikers_ + nbKnights_ + nbOnagers_;
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
