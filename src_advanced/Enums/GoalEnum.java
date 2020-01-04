package Enums;

import java.io.Serializable;
import java.util.Random;

/**
 * Enum�ration des diff�rentes cat�gories d'objectifs pour les IA.
 */
public enum GoalEnum implements Serializable
{
	/**
	 * Objectif de production d'unit�
	 */
	Production,

	/**
	 * Objectif d'attaque avec une ost
	 */
	Battle,

	/**
	 * Objectif de renfort avec une ost
	 */
	Backup,

	/**
	 * Objectif d'argent (par exemple: avoir x Florin)
	 */
	Finance,

	/**
	 * Objectif d'am�lioration de b�timent
	 */
	Building;

	/**
	 * Permets d'avoir un g�n�rateur de cat�gorie d'objectif al�atoire o� toutes les cat�rogies n'ont
	 * pas la m�me probabilit� d'�tre tir�es.
	 *
	 * <p>
	 * Neutral:<br>
	 * Production: 4/13 <br>
	 * Finance: 1/13 <br>
	 * Battle: 3/13 <br>
	 * Backup: 2/13 <br>
	 * Building: 3/13 <br>
	 * </p>
	 *
	 * <p>
	 * Offensive: <br>
	 * Production: 4/13 <br>
	 * Finance: 1/13 <br>
	 * Battle: 5/13 <br>
	 * Backup: 0/13 <br>
	 * Building: 3/13 <br>
	 * </p>
	 *
	 * <p>
	 * Defensive: <br>
	 * Production: 5/13 <br>
	 * Finance: 2/13 <br>
	 * Battle: 2/13 <br>
	 * Backup: 1/13 <br>
	 * Building: 3/13 <br>
	 * </p>
	 *
	 * <p>
	 * Support: <br>
	 * Production: 4/13 <br>
	 * Finance: 1/13 <br>
	 * Battle: 1/13 <br>
	 * Backup: 4/13 <br>
	 * Building: 3/13 <br>
	 * </p>
	 *
	 * @param  character Le caract�re du ch�teau qui demande un nouvel objectif.
	 * @return           Une cat�gorie d'objectif.
	 */
	public static GoalEnum getRandomType(final CharacterCastleEnum character)
	{
		Random rand = new Random();
		final int choice = rand.nextInt(13);

		switch (character)
		{
			case Neutral:
				switch (choice)
				{
					case 0:
					case 1:
					case 2:
					case 3:
						return Production;
					case 4:
						return Finance;
					case 5:
					case 6:
					case 7:
						return Battle;
					case 8:
					case 9:
						return Backup;
					case 10:
					case 11:
					case 12:
						return Building;
				}
			case Offensive:
				switch (choice)
				{
					case 0:
					case 1:
					case 2:
					case 9:
						return Production;
					case 3:
						return Finance;
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
						return Battle;
					case 10:
					case 11:
					case 12:
						return Building;
				}
			case Defensive:
				switch (choice)
				{
					case 0:
					case 1:
					case 2:
					case 3:
					case 4:
						return Production;
					case 5:
					case 6:
						return Finance;
					case 7:
					case 8:
						return Battle;
					case 9:
						return Backup;
					case 10:
					case 11:
					case 12:
						return Building;
				}
			case Support:
				switch (choice)
				{
					case 0:
					case 1:
					case 2:
					case 3:
						return Production;
					case 4:
						return Finance;
					case 5:
						return Battle;
					case 6:
					case 7:
					case 8:
					case 9:
						return Backup;
					case 10:
					case 11:
					case 12:
						return Building;
				}
			default:
				break;
		}

		return Production;
	}
}
