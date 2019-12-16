package Goal;

import java.util.Random;

import DukesOfTheRealm.Castle;
import Enum.SoldierEnum;
import SimpleGoal.Goal;
import SimpleGoal.SoldierGoal;

public class MultiSoldierGoal extends Goal
{
	private final GenericGoal goals;
	
	public MultiSoldierGoal(int nbPikers, int nbKnights, int nbOnagers)
	{
		this.goals = new GenericGoal();
		Random rand = new Random();
		int count = nbPikers + nbKnights + nbOnagers;
		
		while(count > 0)
		{
			switch(rand.nextInt(3))
			{
				case 0: 
					if(nbPikers > 0) 
					{
						goals.addLast(new SoldierGoal(SoldierEnum.Piker)); 
						nbPikers--;
					} 
					break;
					
				case 1: 
					if(nbKnights > 0) 
					{
						goals.addLast(new SoldierGoal(SoldierEnum.Knight)); 
						nbKnights--;
					} 
					break;
					
				case 2: 
					if(nbOnagers > 0) 
					{
						goals.addLast(new SoldierGoal(SoldierEnum.Onager)); 
						nbOnagers--;
					} 
					break;
					
				default: break;
			}
			count = nbPikers + nbKnights + nbOnagers;
		}	
	}

	@Override
	public boolean goal(Castle castle)
	{
		return goals.goal(castle);
	}
}
