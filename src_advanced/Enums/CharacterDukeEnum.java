package Enums;

import java.io.Serializable;
import java.util.Random;

/**
 * Liste des points de caractère possible pour les IA.
 * 
 * @see Duke.DukeAI
 */
public enum CharacterDukeEnum implements Serializable
{
	/**
	 * Duke ayant une probabilitée plus forte d'avoir des châteaux offensifs.
	 */
	Offensive,

	/**
	 * Duke ayant une probabilitée plus forte d'avoir des châteaux défensifs.
	 */
	Defensive,

	/**
	 * Duke n'ayant pas de caractère précis.
	 */
	Neutral;

	/**
	 * Génère aléatoirement un caractère.
	 * 
	 * @return Un caractère.
	 */
	public static CharacterDukeEnum getRandomType()
	{
		Random rand = new Random();
		switch (rand.nextInt(3))
		{
			case 0:
				return Neutral;
			case 1:
				return Offensive;
			case 2:
				return Defensive;
			default:
				return Neutral;
		}
	}
}
