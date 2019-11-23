package DukesOfTheRealm;

import Utility.FPS;
import Utility.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Ost {
	
	private Castle origin;
	private Castle destination;
	private double ostSpeed;

	
	public Ost(Castle origin, Castle destination, int ostSpeed)
	{
		this.origin = origin;
		this.destination = destination;
		this.ostSpeed = ostSpeed;
	}
	
	public void Update(long now)
	{
		//TO DO
	}
	
	public Castle GetOrigin()
	{
		return origin;
	}

	public Castle GetDestination()
	{
		return destination;
	}

	public double GetOstSpeed()
	{
		return ostSpeed;
	}
}
