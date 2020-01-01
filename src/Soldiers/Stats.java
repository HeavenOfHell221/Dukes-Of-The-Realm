package Soldiers;

import java.io.Serializable;

/**
 * Contient les données d'une unité (vitesse de déplacement, point de vie, dégâts).
 *
 * @see Soldier
 */
public class Stats implements Serializable
{
	/**
	 * La vitesse de déplacement de l'unité.
	 */
	public int speed;

	/**
	 * Les points de vie de l'unité.
	 */
	public int health;

	/**
	 * Les dégâts de l'unité.
	 */
	public int damage;

	/**
	 * Constructeur de Stats.
	 *
	 * @param speed  La vitesse de déplacement de l'unité.
	 * @param health Les points de vie de l'unité.
	 * @param damage Les dégâts de l'unité.
	 */
	public Stats(final int speed, final int health, final int damage)
	{
		this.speed = speed;
		this.health = health;
		this.damage = damage;
	}
}
