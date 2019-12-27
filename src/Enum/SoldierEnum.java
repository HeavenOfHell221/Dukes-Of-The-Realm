package Enum;

import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Castle;
import Interface.IProductionUnit;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import static Utility.Settings.*;

/**
 * Enumération des différents type d'unités.
 */
public enum SoldierEnum implements Serializable
{
	Piker, Knight, Onager;

	/**
	 * Récupère le coût de production à partir du type de l'énumération.
	 * @return Le coût de production.
	 */
	public int getCost()
	{
		switch (this)
		{
			case Piker:
				return PIKER_COST;
			case Knight:
				return KNIGHT_COST;
			case Onager:
				return ONAGER_COST;
			default:
				return Integer.MAX_VALUE;
		}
	}

	/**
	 * Incrémente le nombre d'unité en production et renvoi l'objet associé à l'énumération.
	 * @param currentCastle Le château auquel va être produit l'unité.
	 * @return L'objet qui serra produit suivant l'énumération.
	 */
	public IProductionUnit getProduction(final Castle currentCastle)
	{
		switch (this)
		{
			case Piker:
				currentCastle.setNbPikersInProduction(currentCastle.getNbPikersInProduction() + 1);
				return new Piker();
			case Knight:
				currentCastle.setNbKnightsInProduction(currentCastle.getNbKnightsInProduction() + 1);
				return new Knight();
			case Onager:
				currentCastle.setNbOnagersInProduction(currentCastle.getNbOnagersInProduction() + 1);
				return new Onager();
			default:
				return null;
		}
	}

	/**
	 * Générateur d'énumération aléatoire.
	 * @return Un type d'unité aléatoire.
	 */
	public static SoldierEnum getRandomType()
	{
		Random rand = new Random();
		switch (rand.nextInt(3))
		{
			case 0:
				return Piker;
			case 1:
				return Knight;
			case 2:
				return Onager;
			default:
				return null;
					
		}
	}
}
