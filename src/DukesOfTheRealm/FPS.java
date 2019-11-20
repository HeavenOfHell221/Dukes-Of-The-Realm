package DukesOfTheRealm;

public class FPS {

	private final long cooldown;
	private int counter;
	private long lastUpdate;
	private boolean print;
	
	FPS(boolean print)
	{
		this.cooldown = Settings.GAME_FREQUENCY;
		this.lastUpdate = 0;
		this.print = print;
	}
	
	public void Update(long now)
	{
		if((now - lastUpdate) >= cooldown)
		{
			lastUpdate = now;
			if(print)
				System.out.println(counter + " fps");
			counter = 0;
		}
		counter++;
	}
}
