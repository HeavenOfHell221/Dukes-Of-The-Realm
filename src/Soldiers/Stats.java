package Soldiers;

import java.io.Serializable;

/**
 * Contient les donn�es d'une unit� (vitesse de d�placement, point de vie, d�g�ts).
 * 
 * @see Soldier
 */
public class Stats implements Serializable
{
	/**
	 * La vitesse de d�placement de l'unit�.
	 */
	public int speed;

	/**
	 * Les points de vie de l'unit�.
	 */
	public int health;

	/**
	 * Les d�g�ts de l'unit�.
	 */
	public int damage;

	/**
	 * Constructeur de Stats.
	 * 
	 * @param speed  La vitesse de d�placement de l'unit�.
	 * @param health Les points de vie de l'unit�.
	 * @param damage les d�g�ts de l'unit�.
	 */
	public Stats(final int speed, final int health, final int damage)
	{
		this.speed = speed;
		this.health = health;
		this.damage = damage;
	}
}
