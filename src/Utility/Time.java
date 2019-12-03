package Utility;

import Interface.IUpdate;

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
	public static int FPS;
	
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
	
	@Override
	public void Start()
	{
		
	}
	
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/
	
	@Override
	public void Update(long now, boolean pause)
	{
		FrameStart(now);
		if((now - lastUpdate) >= cooldown)
		{
			lastUpdate = now;
			if(print)
				System.out.println(counter + " fps");
			FPS = counter;
			counter = 0;
		}
		counter++;
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
		this.deltaTime /= Settings.GAME_FREQUENCY;
		this.oldTime = now;
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	
	
	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
	
}
