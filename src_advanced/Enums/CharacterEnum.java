package Enums;

import java.util.Random;

/**
 * Enumération des différents caractère possible pour les châteaux des IA.
 */
public enum CharacterEnum
{
	/**
	 * L'IA ferra davantage d'unités offensives.
	 */
	Offensive, 
	
	/**
	 * L'IA ferra davantage d'unités défensives.
	 */
	Defensive, 
	
	/**
	 * L'IA ferra environ le même montant d'unité défensive et d'unité offensive.
	 */
	Neutral;
	 

	/**
	 * Générateur d'énumération aléatoire.
	 *
	 * @return Un type aléatoire.
	 */
	public static CharacterEnum getRandomType()
	{
		Random rand = new Random();

		switch (rand.nextInt(3))
		{
			case 0:
				return Defensive;
			case 1:
				return Offensive;
			case 2:
				return Neutral;
			default:
				return Neutral;
		}
	}

}
