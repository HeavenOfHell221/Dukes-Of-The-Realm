package Enum;

import java.util.Random;

/**
 * Enum�ration des diff�rentes cat�gories d'objectifs pour les IA.
 */
public enum GoalEnum
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
	 * Permets d'avoir un g�n�rateur de cat�gorie d'objectif al�atoire o� toutes les cat�rogies n'ont pas la m�me probabilit� d'�tre tir�es.
	 *
	 *<p>
	 * Production: 30.8% <br>
	 * Finance:    7.7% <br>
	 * Battle:     23.1% <br>
	 * Backup:     15.3% <br>
	 * Building:   23.1% <br>
	 *</p>
	 *
	 * @return Une cat�gorie d'objectif.
	 */
	public static GoalEnum getRandomType()
	{
		Random rand = new Random();
		switch (rand.nextInt(13))
		{
			case 0:
			case 1:
			case 2:
			case 12:
				return Production;
			case 3:
				return Finance;
			case 4:
			case 5:
			case 6:
				return Battle;
			case 7:
			case 8:
				return Backup;
			case 9:
			case 10:
			case 11:
				return Building;
			default:
				return Production;
		}
	}
}
