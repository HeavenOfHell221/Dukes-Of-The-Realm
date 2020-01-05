package Enums;

import java.util.Random;

public enum CharacterEnum
{
	Offensive,
	Defensive,
	Neutral;

	/**
	 * G�n�rateur d'�num�ration al�atoire.
	 *
	 * @return Un type al�atoire.
	 */
	public static CharacterEnum getRandomType()
	{
		Random rand = new Random();
		
		switch(rand.nextInt(3))
		{
			case 0: return Defensive;
			case 1: return Offensive;
			case 2:  return Neutral;
			default: return Neutral;
		}
	}

}