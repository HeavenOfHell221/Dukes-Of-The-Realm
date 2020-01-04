package Enums;

import static Utility.Settings.*;
import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Castle;
import Interface.IProduction;
import Soldiers.*;

/**
 * Enumération des différents type d'unités.
 */
public enum SoldierEnum implements Serializable
{	
	/**
	 * 
	 */
	Piker(PIKER_HP, PIKER_DAMAGE, PIKER_COST, PIKER_TIME_PRODUCTION, PIKER_SPEED, PIKER_VILLAGER, STARTER_PIKER, 10, new Piker()), 
	
	/**
	 * 
	 */
	Knight(KNIGHT_HP, KNIGHT_DAMAGE, KNIGHT_COST, KNIGHT_TIME_PRODUCTION, KNIGHT_SPEED, KNIGHT_VILLAGER, STARTER_KNIGHT, 9, new Knight()), 
	
	/**
	 * 
	 */
	Onager(ONAGER_HP, ONAGER_DAMAGE, ONAGER_COST, ONAGER_TIME_PRODUCTION, ONAGER_SPEED, ONAGER_VILLAGER, STARTER_ONAGER, 7, new Onager()), 
	
	/**
	 * 
	 */
	Archer(ARCHER_HP, ARCHER_DAMAGE, ARCHER_COST, ARCHER_TIME_PRODUCTION, ARCHER_SPEED, ARCHER_VILLAGER, STARTER_ARCHER, 10, new Archer()), 
	
	/**
	 * 
	 */
	Berserker(BERSERKER_HP, BERSERKER_DAMAGE, BERSERKER_COST, BERSERKER_TIME_PRODUCTION, BERSERKER_SPEED, BERSERKER_VILLAGER, STARTER_BERSERKER, 8, new Berserker()),
	
	/**
	 * 
	 */
	Spy(SPY_HP, SPY_DAMAGE, SPY_COST, SPY_TIME_PRODUCTION, SPY_SPEED, SPY_VILLAGER, STARTER_SPY, 5, new Spy());

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
	public final int priorityDefense;
	
	/**
	 * 
	 */
	public final Soldier soldier;
	
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
	 * @param soldier
	 */
	private SoldierEnum(int HP, int damage, int cost, double productionTime, int speed, int villager, int starter, int priorityDefense, Soldier soldier)
	{
		this.HP = HP;
		this.damage = damage;
		this.cost = cost;
		this.productionTime = productionTime;
		this.speed = speed;
		this.villager = villager;
		this.priorityDefense = priorityDefense;
		this.soldier = soldier;
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
}
