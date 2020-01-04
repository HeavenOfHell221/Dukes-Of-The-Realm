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
	 * Production: 4/13 <br>
	 * Finance: 1/13 <br>
	 * Battle: 3/13 <br>
	 * Backup: 2/13 <br>
	 * Building: 3/13 <br>
	 * </p>

	 * @return           Une cat�gorie d'objectif.
	 */
	public static GoalEnum getRandomType()
	{
		Random rand = new Random();
		switch (rand.nextInt(13))
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
		
		return Production;
	}
}
