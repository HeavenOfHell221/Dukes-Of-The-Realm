package Soldiers;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Onager extends Soldier {

	public Onager(Pane layer, Image image, double x, double y)
	{
		super(layer, image, x, y, 1000, 50, 1, 5, 10);
	}
	
	public Onager(Pane layer, Image image, Point2D p)
	{
		super(layer, image, p.getX(), p.getY(), 100, 5, 2, 1, 1);
	}
}
