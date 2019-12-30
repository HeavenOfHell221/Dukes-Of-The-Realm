package Enum;

import java.util.Random;

/**
 * Enumération des différentes catégories d'objectifs pour les IA.
 */
public enum GoalEnum
{
	Production, Battle, Backup, Finance, Building;

	/**
	 * Permet d'avoir un générateur de catégorie d'objectif aléatoire.
	 * 
	 * @return Une catégorie d'objectif.
	 */
	public static GoalEnum getRandomType()
	{
		Random rand = new Random();
		switch (rand.nextInt(12))
		{
			case 0:
			case 1:
			case 2:
				return Production;
			case 4:
				return Finance;
			case 5:
			case 3:
			case 10:
				return Battle;
			case 6:
			case 7:
				return Backup;
			case 8:
			case 9:
			case 11:
				return Building;
			default:
				return Production;
		}
	}
}
