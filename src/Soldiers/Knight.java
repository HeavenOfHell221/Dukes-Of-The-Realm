package Soldiers;

import javafx.scene.image.Image;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;

public class Knight extends Soldier {

	public Knight(Pane layer, Image image, double x, double y) 
	{
		super(layer, image, x, y, 500, 20, 6, 3, 5);
		addCircle(10.0);
	}	
}
