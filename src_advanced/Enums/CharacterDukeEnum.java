package Enums;

import java.io.Serializable;
import java.util.Random;

/**
 * Liste des points de caract�re possible pour les IA.
 * @see Duke.DukeAI 
 */
public enum CharacterDukeEnum implements Serializable
{
	/**
	 * Duke ayant une probabilit�e plus forte d'avoir des ch�teaux offensifs.
	 */
	Offensive,
	
	/**
	 * Duke ayant une probabilit�e plus forte d'avoir des ch�teaux d�fensifs.
	 */
	Defensive,
	
	/**
	 * Duke n'ayant pas de caract�re pr�cis.
	 */
	Neutral;
	
	/**
	 * G�n�re al�atoirement un caract�re.
	 * @return Un caract�re.
	 */
	public static CharacterDukeEnum getRandomType()
	{
		Random rand = new Random();
		switch(rand.nextInt(3))
		{
			case 0: return Neutral;
			case 1: return Offensive;
			case 2: return Defensive;
			default: return Neutral;
		}
	}
}
