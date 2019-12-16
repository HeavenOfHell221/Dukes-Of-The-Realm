package Enum;

import java.util.Random;

public enum GoalEnum
{
	Production, Battle, Backup, Finance;
	
	public static GoalEnum getRandomType(Random rand)
	{
		switch(rand.nextInt(4))
		{
			case 0: return Production;
			case 1: return Battle;
			case 2: return Backup;
			case 3: return Finance;
		}
		return null;
	}
}
