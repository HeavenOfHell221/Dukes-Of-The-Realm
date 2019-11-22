package Soldiers;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Piker extends Soldier {

	public Piker()
	{
		super(100, 5, 2, 1, 1);
		this.type = SoldierEnum.Piker;
	}

}