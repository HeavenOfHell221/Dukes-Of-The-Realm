package Enums;

import java.io.Serializable;
import java.util.Random;

/**
 * Liste des points de caract�re possible pour un ch�teau.
 */
public enum CharacterCastleEnum implements Serializable
{
	/**
	 * N'a pas de caract�re pr�cis.
	 */
	Neutral,

	/**
	 * Ch�teau ayant une plus forte probabilit�e de produire des unit�s offensives et d'attaquer.
	 */
	Offensive,

	/**
	 * Ch�teau ayant une plus forte probabilit�e de produire des unit�s d�fensives.
	 */
	Defensive,

	/**
	 * Ch�teau ayant une plus forte probabilit�e de produire des unit�s de soutien et d'envoyer des
	 * renforts.
	 */
	Support;

	/**
	 * G�n�re al�atoirement un type de caract�re en fonction du caract�re d'un Duke.
	 * 
	 * @param  character Le caract�re du Duke � qui appartient le ch�teau qui aura le caract�re
	 *                   retourn�.
	 * @return           Un caract�re.
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
