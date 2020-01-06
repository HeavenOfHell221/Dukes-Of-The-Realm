package Enums;

import static Utility.Settings.ARCHER_COST;
import static Utility.Settings.ARCHER_DAMAGE;
import static Utility.Settings.ARCHER_HP;
import static Utility.Settings.ARCHER_SPEED;
import static Utility.Settings.ARCHER_TIME_PRODUCTION;
import static Utility.Settings.ARCHER_VILLAGER;
import static Utility.Settings.BERSERKER_COST;
import static Utility.Settings.BERSERKER_DAMAGE;
import static Utility.Settings.BERSERKER_HP;
import static Utility.Settings.BERSERKER_SPEED;
import static Utility.Settings.BERSERKER_TIME_PRODUCTION;
import static Utility.Settings.BERSERKER_VILLAGER;
import static Utility.Settings.CONVEYORS_SPEED;
import static Utility.Settings.CONVEYORS_TIME_PRODUCTION;
import static Utility.Settings.KNIGHT_COST;
import static Utility.Settings.KNIGHT_DAMAGE;
import static Utility.Settings.KNIGHT_HP;
import static Utility.Settings.KNIGHT_SPEED;
import static Utility.Settings.KNIGHT_TIME_PRODUCTION;
import static Utility.Settings.KNIGHT_VILLAGER;
import static Utility.Settings.ONAGER_COST;
import static Utility.Settings.ONAGER_DAMAGE;
import static Utility.Settings.ONAGER_HP;
import static Utility.Settings.ONAGER_SPEED;
import static Utility.Settings.ONAGER_TIME_PRODUCTION;
import static Utility.Settings.ONAGER_VILLAGER;
import static Utility.Settings.PIKER_COST;
import static Utility.Settings.PIKER_DAMAGE;
import static Utility.Settings.PIKER_HP;
import static Utility.Settings.PIKER_SPEED;
import static Utility.Settings.PIKER_TIME_PRODUCTION;
import static Utility.Settings.PIKER_VILLAGER;
import static Utility.Settings.SPY_COST;
import static Utility.Settings.SPY_DAMAGE;
import static Utility.Settings.SPY_HP;
import static Utility.Settings.SPY_SPEED;
import static Utility.Settings.SPY_TIME_PRODUCTION;
import static Utility.Settings.SPY_VILLAGER;
import static Utility.Settings.STARTER_ARCHER;
import static Utility.Settings.STARTER_BERSERKER;
import static Utility.Settings.STARTER_KNIGHT;
import static Utility.Settings.STARTER_ONAGER;
import static Utility.Settings.STARTER_PIKER;
import static Utility.Settings.STARTER_SPY;

import java.io.Serializable;
import java.util.Random;

import Soldiers.Archer;
import Soldiers.Berserker;
import Soldiers.Conveyors;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Soldiers.Soldier;
import Soldiers.Spy;

/**
 * Enum�ration des diff�rents type d'unit�s avec leurs constantes.
 */
public enum SoldierEnum implements Serializable
{
	/**
	 * Repr�sente un Piker.
	 */
	Piker(PIKER_HP, PIKER_DAMAGE, PIKER_COST, PIKER_TIME_PRODUCTION, PIKER_SPEED, PIKER_VILLAGER, STARTER_PIKER),

	/**
	 * Repr�sente un Knight.
	 */
	Knight(KNIGHT_HP, KNIGHT_DAMAGE, KNIGHT_COST, KNIGHT_TIME_PRODUCTION, KNIGHT_SPEED, KNIGHT_VILLAGER, STARTER_KNIGHT),

	/**
	 * Repr�sente un Onager.
	 */
	Onager(ONAGER_HP, ONAGER_DAMAGE, ONAGER_COST, ONAGER_TIME_PRODUCTION, ONAGER_SPEED, ONAGER_VILLAGER, STARTER_ONAGER),

	/**
	 * Repr�sente un Archer.
	 */
	Archer(ARCHER_HP, ARCHER_DAMAGE, ARCHER_COST, ARCHER_TIME_PRODUCTION, ARCHER_SPEED, ARCHER_VILLAGER, STARTER_ARCHER),

	/**
	 * Repr�sente un Berserker.
	 */
	Berserker(BERSERKER_HP, BERSERKER_DAMAGE, BERSERKER_COST, BERSERKER_TIME_PRODUCTION, BERSERKER_SPEED, BERSERKER_VILLAGER,
			STARTER_BERSERKER),

	/**
	 * Repr�sente un Psy.
	 */
	Spy(SPY_HP, SPY_DAMAGE, SPY_COST, SPY_TIME_PRODUCTION, SPY_SPEED, SPY_VILLAGER, STARTER_SPY),

	/**
	 * Rep�sente un Conveyors.
	 */
	Conveyors(0, 0, 0, CONVEYORS_TIME_PRODUCTION, CONVEYORS_SPEED, 0, 0);

	/**
	 * Le nombre de point de vie.
	 */
	public final int HP;

	/**
	 * Le nombre de d�g�ts.
	 */
	public final int damage;

	/**
	 * Le co�t.
	 */
	public final int cost;

	/**
	 * Le temps de production.
	 */
	public final double productionTime;

	/**
	 * Le nombre de villageois n�cessaire.
	 */
	public final int villager;

	/**
	 * La vitesse de d�placement.
	 */
	public final int speed;

	/**
	 * Le nombre de cette unit� au d�but du jeu pour les IA et le joueur.
	 */
	public final int starter;

	/**
	 * Constructeur de SoldierEnum.
	 * @param HP Les points de vie.
	 * @param damage Les d�g�ts.
	 * @param cost Le co�t.
	 * @param productionTime Le temps de production.
	 * @param speed La vitesse de d�placement.
	 * @param villager Le nombre de villageois n�c�ssaire.
	 * @param starter Le nombre de base d'unit� pour le joueur et les IA.
	 */
	private SoldierEnum(final int HP, final int damage, final int cost, final double productionTime, final int speed, final int villager,
			final int starter)
	{
		this.HP = HP;
		this.damage = damage;
		this.cost = cost;
		this.productionTime = productionTime;
		this.speed = speed;
		this.villager = villager;
		this.starter = starter;
	}

	/**
	 * G�n�rateur d'�num�ration al�atoire.
	 *
	 * @return Un type d'unit� al�atoire.
	 */
	public static SoldierEnum getRandomType()
	{
		Random rand = new Random();
		switch (rand.nextInt(7))
		{
			case 0:
				return Piker;
			case 1:
				return Knight;
			case 2:
				return Onager;
			case 3:
				return Archer;
			case 4:
				return Berserker;
			case 5:
				return Spy;
			case 6:
				return Conveyors;
			default:
				return null;

		}
	}

	/**
	 * G�n�rateur d'�num�ration al�atoire o� les probabilit�s sont modifi� pour prendre plus souvent des
	 * unit�s d�fensives.
	 *
	 * @return Une type d'unit�.
	 */
	public static SoldierEnum getRandomTypeWithDefense()
	{
		Random rand = new Random();
		final int i = rand.nextInt(100);

		if (i >= 0 && i < 25)
		{
			return Piker; // 25%
		}
		else if (i >= 25 && i < 50)
		{
			return Archer;  // 25%
		}
		else if (i >= 50 && i < 70)
		{
			return Knight; // 20%
		}
		else if (i >= 70 && i < 85)
		{
			return Berserker; // 15%
		}
		else if (i >= 85 && i < 95)
		{
			return Onager; // 10%
		}
		else
		{
			return Spy; // 5%
		}
	}

	/**
	 * @return Retourne une instance de l'unit� li� � l'�num�ration.
	 */
	public Soldier getObject()
	{
		switch (this)
		{
			case Piker:
				return new Piker();
			case Knight:
				return new Knight();
			case Onager:
				return new Onager();
			case Archer:
				return new Archer();
			case Berserker:
				return new Berserker();
			case Spy:
				return new Spy();
			case Conveyors:
				return new Conveyors();
			default:
				return null;
		}
	}
}
