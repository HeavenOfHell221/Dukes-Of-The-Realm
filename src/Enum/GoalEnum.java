package Enum;

import java.util.Random;

public enum GoalEnum
{
	Production, Battle, Backup, Finance, Building;
	
	public static GoalEnum getRandomType(Random rand)
	{
		switch(rand.nextInt(10))
		{
			case 0: return Production;
			case 1: return Battle;
			case 2: return Backup;
			case 3: return Finance;
			case 4: return Building;
			case 5: return Production;
			case 6: return Backup;
			case 7: return Finance;
			case 8: return Production;
			case 9: return Building;
			default: return Production;
		}
	}
}
