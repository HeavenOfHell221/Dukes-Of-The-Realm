package Enum;

import java.util.Random;

/**
 * Enum�ration des diff�rentes cat�gories d'objectifs pour les IA.
 */
public enum GoalEnum
{
	Production, Battle, Backup, Finance, Building;

	/**
	 * Permet d'avoir un g�n�rateur de cat�gorie d'objectif al�atoire.
	 * 
	 * @return Une cat�gorie d'objectif.
	 */
	public static GoalEnum getRandomType()
	{
		Random rand = new Random();
		switch (rand.nextInt(10))
		{
			case 0:
			case 1:
			case 2:
				return Production; // 30%
			case 3:
			case 4:
				return Finance; // 20%
			case 5:
				return Battle; // 10%
			case 6:
			case 7:
				return Backup; // 20%
			case 8:
			case 9:
				return Building; // 20%
			default:
				return Production;
		}
	}
}
