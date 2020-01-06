package Enums;

import java.util.Random;

/**
 * Enum�ration des diff�rents caract�re possible pour les ch�teaux des IA.
 */
public enum CharacterEnum
{
	/**
	 * L'IA ferra davantage d'unit�s offensives.
	 */
	Offensive, 
	
	/**
	 * L'IA ferra davantage d'unit�s d�fensives.
	 */
	Defensive, 
	
	/**
	 * L'IA ferra environ le m�me montant d'unit� d�fensive et d'unit� offensive.
	 */
	Neutral;
	 

	/**
	 * G�n�rateur d'�num�ration al�atoire.
	 *
	 * @return Un type al�atoire.
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
