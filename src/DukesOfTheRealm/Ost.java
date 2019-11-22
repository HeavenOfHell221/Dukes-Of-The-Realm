package DukesOfTheRealm;

import Duke.Duke;
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
	
	public void UpdateAtEachFrame(long now)
	{
		//TO DO
	}
	
	public Castle getOrigin()
	{
		return origin;
	}

	public Castle getDestination()
	{
		return destination;
	}

	public double getOstSpeed()
	{
		return ostSpeed;
	}
}
