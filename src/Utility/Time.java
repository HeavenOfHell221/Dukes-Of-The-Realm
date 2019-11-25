package Utility;

import DukesOfTheRealm.IUpdate;

public class Time implements IUpdate{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	private final long cooldown;
	private int counter;
	private long lastUpdate;
	private boolean print;
	private long oldTime;
	public static double deltaTime;
	private boolean firstFrame;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	public Time(boolean print)
	{
		this.cooldown = Settings.GAME_FREQUENCY;
		this.lastUpdate = 0;
		this.print = print;
		this.deltaTime = 0;
		this.oldTime = 0;
		this.firstFrame = true;
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/
	
	public void Update(long now, boolean pause)
	{
		FrameStart(now);
		/*if((now - lastUpdate) >= cooldown)
		{
			lastUpdate = now;
			if(print)
				System.out.println(counter + " fps");
			counter = 0;
		}
		counter++;*/
	}
	
	public void FrameStart(long now)
	{
		if(firstFrame)
		{
			firstFrame = false;
			this.oldTime = now;
			return;
		}
		this.deltaTime = (now - this.oldTime);
		this.deltaTime /= (double)Settings.GAME_FREQUENCY;
		this.oldTime = now;
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	
	
	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
	
}
