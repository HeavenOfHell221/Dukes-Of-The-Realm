package Utility;

import DukesOfTheRealm.Main;

/**
 *
 *
 * @author Utilisateur
 *
 */
public class Time
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 *
	 */
	private final long cooldown;

	/**
	 *
	 */
	private int counter;

	/**
	 *
	 */
	private long lastUpdate;

	/**
	 *
	 */
	private final boolean print;

	/**
	 *
	 */
	private long oldTime;

	/**
	 *
	 */
	public static double deltaTime;

	/**
	 *
	 */
	private boolean firstFrame;

	/**
	 *
	 */
	public static int FPS;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 *
	 * @param print
	 */
	public Time(final boolean print)
	{
		this.cooldown = Settings.GAME_FREQUENCY;
		this.lastUpdate = 0;
		this.print = print;
		deltaTime = 0;
		this.oldTime = 0;
		this.firstFrame = true;
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	/**
	 *
	 */
	public void update(final long now, final boolean pause)
	{
		frameStart(now);
		if (now - this.lastUpdate >= this.cooldown)
		{
			this.lastUpdate = now;
			if (this.print)
			{
				System.out.println(this.counter + " fps");
				System.out.println(Main.nbSoldier + " soldiers" + "\n");
			}
			FPS = this.counter;
			this.counter = 0;
		}
		this.counter++;
	}

	/**
	 *
	 * @param now
	 */
	public void frameStart(final long now)
	{
		if (this.firstFrame)
		{
			this.firstFrame = false;
			this.oldTime = now;
			return;
		}
		deltaTime = now - this.oldTime;
		deltaTime /= Settings.GAME_FREQUENCY;
		this.oldTime = now;
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

}
