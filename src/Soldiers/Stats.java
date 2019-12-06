package Soldiers;

import java.io.Serializable;

public class Stats implements Serializable
{

	public int speed;
	public int health;
	public int damage;

	public Stats(final int speed, final int health, final int damage)
	{
		this.speed = speed;
		this.health = health;
		this.damage = damage;
	}
}
