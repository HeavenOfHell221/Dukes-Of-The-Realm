package Soldiers;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Onager extends Soldier {

	public Onager(Pane layer, double x, double y)
	{
		super(layer, x, y, 1000, 50, 1, 5, 10);
		this.type = SoldierEnum.Onager;
		Start();
	}
}
