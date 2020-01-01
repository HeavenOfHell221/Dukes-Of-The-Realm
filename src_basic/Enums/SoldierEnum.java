package Enums;

import static Utility.Settings.KNIGHT_COST;
import static Utility.Settings.ONAGER_COST;
import static Utility.Settings.PIKER_COST;

import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Castle;
import Interface.IProductionUnit;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;

/**
 * Enum�ration des diff�rents type d'unit�s.
 */
public enum SoldierEnum implements Serializable
{
	Piker, Knight, Onager;

	/**
	 * R�cup�re le co�t de production � partir du type de l'�num�ration.
	 *
	 * @return Le co�t de production.
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
	 * Incr�mente le nombre d'unit� en production et retourne l'objet associ� � l'�num�ration (si
	 * l'�num�ration est Piker alors on retourne un objet de type Piker).
	 *
	 * @param  currentCastle Le ch�teau o� va �tre produit l'unit�.
	 * @return               L'objet qui serra produit suivant l'�num�ration.
	 */
	public IProductionUnit getProduction(final Castle currentCastle)
	{
		switch (this)
		{
			case Piker:
				return new Piker();
			case Knight:
				return new Knight();
			case Onager:
				return new Onager();
			default:
				return null;
		}
	}

	/**
	 * G�n�rateur d'�num�ration al�atoire.
	 *
	 * @return Un type d'unit� al�atoire.
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
