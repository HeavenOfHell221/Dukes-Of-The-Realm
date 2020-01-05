package Enums;

import java.io.Serializable;
import java.util.Random;

/**
 * Enumération des différentes catégories d'objectifs pour les IA.
 */
public enum GoalEnum implements Serializable
{
	/**
	 * Objectif de production d'unité
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
	 * Objectif d'amélioration de bâtiment
	 */
	Building;

	/**
	 * Permets d'avoir un générateur de catégorie d'objectif aléatoire où toutes les catérogies n'ont
	 * pas la même probabilité d'être tirées.
	 *
	 * <p>
	 * Production: 4/13 <br>
	 * Finance: 1/13 <br>
	 * Battle: 3/13 <br>
	 * Backup: 2/13 <br>
	 * Building: 3/13 <br>
	 * </p>

	 * @return           Une catégorie d'objectif.
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
			case 4:
				return Production;
			case 5:
				return Finance;
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
