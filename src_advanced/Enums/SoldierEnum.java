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
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Soldiers.Soldier;
import Soldiers.Spy;

/**
 * Enumération des différents type d'unités.
 */
public enum SoldierEnum implements Serializable
{
	/**
	 *
	 */
	Piker(PIKER_HP, PIKER_DAMAGE, PIKER_COST, PIKER_TIME_PRODUCTION, PIKER_SPEED, PIKER_VILLAGER, STARTER_PIKER),

	/**
	 *
	 */
	Knight(KNIGHT_HP, KNIGHT_DAMAGE, KNIGHT_COST, KNIGHT_TIME_PRODUCTION, KNIGHT_SPEED, KNIGHT_VILLAGER, STARTER_KNIGHT),

	/**
	 *
	 */
	Onager(ONAGER_HP, ONAGER_DAMAGE, ONAGER_COST, ONAGER_TIME_PRODUCTION, ONAGER_SPEED, ONAGER_VILLAGER, STARTER_ONAGER),

	/**
	 *
	 */
	Archer(ARCHER_HP, ARCHER_DAMAGE, ARCHER_COST, ARCHER_TIME_PRODUCTION, ARCHER_SPEED, ARCHER_VILLAGER, STARTER_ARCHER),

	/**
	 *
	 */
	Berserker(BERSERKER_HP, BERSERKER_DAMAGE, BERSERKER_COST, BERSERKER_TIME_PRODUCTION, BERSERKER_SPEED, BERSERKER_VILLAGER,
			STARTER_BERSERKER),

	/**
	 *
	 */
	Spy(SPY_HP, SPY_DAMAGE, SPY_COST, SPY_TIME_PRODUCTION, SPY_SPEED, SPY_VILLAGER, STARTER_SPY);

	/**
	 *
	 */
	public final int HP;

	/**
	 *
	 */
	public final int damage;

	/**
	 *
	 */
	public final int cost;

	/**
	 *
	 */
	public final double productionTime;

	/**
	 *
	 */
	public final int villager;

	/**
	 *
	 */
	public final int speed;

	/**
	 *
	 */
	public final int starter;

	/**
	 *
	 * @param HP
	 * @param damage
	 * @param cost
	 * @param productionTime
	 * @param speed
	 * @param villager
	 * @param priorityDefense
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
	 * Générateur d'énumération aléatoire.
	 *
	 * @return Un type d'unité aléatoire.
	 */
	public static SoldierEnum getRandomType()
	{
		Random rand = new Random();
		switch (rand.nextInt(6))
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
			default:
				return null;

		}
	}

	/**
	 * Générateur d'énumération aléatoire où les probabilités sont modifié pour prendre plus souvent des
	 * unités défensives.
	 * 
	 * @return Une type d'unité.
	 */
	public static SoldierEnum getRandomTypeWithDefense()
	{
		Random rand = new Random();
		final int i = rand.nextInt(100);

		if (i >= 0 && i < 25)
		{
			return Piker;
		}
		else if (i >= 25 && i < 50)
		{
			return Archer;
		}
		else if (i >= 50 && i < 70)
		{
			return Knight;
		}
		else if (i >= 70 && i < 85)
		{
			return Berserker;
		}
		else if (i >= 85 && i < 95)
		{
			return Onager;
		}
		else
		{
			return Spy;
		}
	}

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
			default:
				return null;
		}
	}
}
