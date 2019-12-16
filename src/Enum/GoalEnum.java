package Enum;

import java.util.Random;

public enum GoalEnum
{
	Production, Battle, Backup, Finance, Building;
	
	public static GoalEnum getRandomType(Random rand)
	{
		switch(rand.nextInt(5))
		{
			case 0: return Production;
			case 1: return Battle;
			case 2: return Backup;
			case 3: return Finance;
			case 4: return Building;
		}
		return null;
	}
}
