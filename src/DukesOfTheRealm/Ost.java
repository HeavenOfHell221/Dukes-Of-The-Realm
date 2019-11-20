package DukesOfTheRealm;

import Duke.Duke;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Ost extends Sprite {
	
	private Castle origin;
	private Castle destination;
	private int speed;
	
	public Ost(Pane layer, Image image, double x, double y, Castle origin, Castle destination, int speed)
	{
		super(layer, image, x + (Settings.CASE_WIDTH-2)/4 +1, y + (Settings.CASE_HEIGHT-2)/4 +1);
//		super(layer, image, x, y);
		this.origin = origin;
		this.destination = destination;
		this.speed = speed;
		AddCircle(10);
	}
	
	public void UpdateAtEachFrame()
	{
		this.x +=3;
		UpdateUIShape();
	}
}
