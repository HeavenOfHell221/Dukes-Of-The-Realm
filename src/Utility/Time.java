package Utility;

import Interface.IUpdate;

public class Time implements IUpdate{

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
		cooldown = Settings.GAME_FREQUENCY;
		lastUpdate = 0;
		this.print = print;
		deltaTime = 0;
		oldTime = 0;
		firstFrame = true;
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void Start()
	{

	}


	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void Update(final long now, final boolean pause)
	{
		FrameStart(now);
		if((now - lastUpdate) >= cooldown)
		{
			lastUpdate = now;
			if(print) {
				System.out.println(counter + " fps");
			}
			FPS = counter;
			counter = 0;
		}
		counter++;
	}

	public void FrameStart(final long now)
	{
		if(firstFrame)
		{
			firstFrame = false;
			oldTime = now;
			return;
		}
		deltaTime = (now - oldTime);
		deltaTime /= Settings.GAME_FREQUENCY;
		oldTime = now;
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/



	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

}
