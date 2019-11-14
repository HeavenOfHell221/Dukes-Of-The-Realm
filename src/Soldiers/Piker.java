package Soldiers;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Piker extends Soldier {

	public Piker(Pane layer, Image image, double x, double y)
	{
		super(layer, image, x, y, 100, 5, 2, 1, 1);
	}
}