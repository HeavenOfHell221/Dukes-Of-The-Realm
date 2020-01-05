package Enums;

import static Utility.Settings.*;

import java.io.Serializable;
import java.util.Random;

import Soldiers.*;
/**
 * Enum�ration des diff�rents type d'unit�s.
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
	Spy(SPY_HP, SPY_DAMAGE, SPY_COST, SPY_TIME_PRODUCTION, SPY_SPEED, SPY_VILLAGER, STARTER_SPY),

	
	Conveyors(0, 0, 0, CONVEYORS_TIME_PRODUCTION, CONVEYORS_SPEED, 0, 0);
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
			case Conveyors:
				return new Conveyors();
			default:
				return null;
		}
	}
}
