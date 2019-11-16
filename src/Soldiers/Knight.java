package Soldiers;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;

public class Knight extends Soldier {

	public Knight(Pane layer, Image image, double x, double y) 
	{
		super(layer, image, x, y, 500, 20, 6, 3, 5);
		addRectangle(10.0, 10.0);
	}
	
	public Knight(Pane layer, Image image, Point2D p)
	{
		super(layer, image, p.getX(), p.getY(), 100, 5, 2, 1, 1);
	}
}
