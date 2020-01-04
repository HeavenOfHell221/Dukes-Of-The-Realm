package Enums;

import java.io.Serializable;
import java.util.Random;

/**
 * Liste des points de caractère possible pour un château.
 */
public enum CharacterCastleEnum implements Serializable
{
	/**
	 * N'a pas de caractère précis.
	 */
	Neutral,

	/**
	 * Château ayant une plus forte probabilitée de produire des unités offensives et d'attaquer.
	 */
	Offensive,

	/**
	 * Château ayant une plus forte probabilitée de produire des unités défensives.
	 */
	Defensive,

	/**
	 * Château ayant une plus forte probabilitée de produire des unités de soutien et d'envoyer des
	 * renforts.
	 */
	Support;

	/**
	 * Génère aléatoirement un type de caractère en fonction du caractère d'un Duke.
	 * 
	 * @param  character Le caractère du Duke à qui appartient le château qui aura le caractère
	 *                   retourné.
	 * @return           Un caractère.
	 */
	public static CharacterCastleEnum getRandomType(final CharacterDukeEnum character)
	{
		Random rand = new Random();
		switch (character)
		{
			case Neutral:
				switch (rand.nextInt(4))
				{
					case 0:
						return Defensive;
					case 1:
						return Offensive;
					case 2:
						return Support;
					case 3:
						return Neutral;
					default:
						return Neutral;
				}
			case Offensive:
				switch (rand.nextInt(6))
				{
					case 0:
						return Defensive;
					case 1:
					case 2:
					case 3:
						return Offensive;
					case 4:
						return Support;
					case 5:
						return Neutral;
					default:
						return Neutral;
				}
			case Defensive:
				switch (rand.nextInt(6))
				{
					case 0:
					case 1:
						return Defensive;
					case 2:
						return Offensive;
					case 3:
					case 4:
						return Support;
					case 5:
						return Neutral;
					default:
						return Neutral;
				}
			default:
				return Neutral;
		}
	}
}
