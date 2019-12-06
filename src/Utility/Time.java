package Utility;

import Interface.IUpdate;

public class Time implements IUpdate
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	private final long cooldown;
	private int counter;
	private long lastUpdate;
	private final boolean print;
	private long oldTime;
	public static double deltaTime;
	private boolean firstFrame;
	public static int FPS;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

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

	@Override
	public void start()
	{

	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		frameStart(now);
		if ((now - this.lastUpdate) >= this.cooldown)
		{
			this.lastUpdate = now;
			if (this.print)
			{
				System.out.println(this.counter + " fps");
			}
			FPS = this.counter;
			this.counter = 0;
		}
		this.counter++;
	}

	public void frameStart(final long now)
	{
		if (this.firstFrame)
		{
			this.firstFrame = false;
			this.oldTime = now;
			return;
		}
		deltaTime = (now - this.oldTime);
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
