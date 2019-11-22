package Soldiers;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Onager extends Soldier {

	public Onager()
	{
		super(1000, 50, 1, 5, 10);
		this.type = SoldierEnum.Onager;
	}
}
