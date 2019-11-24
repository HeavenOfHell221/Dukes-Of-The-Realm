package Soldiers;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Knight extends Soldier {

	public Knight(Pane layer, double x, double y, Color color) 
	{
		super(layer, x, y, 500, 20, 6, 3, 5);
		this.type = SoldierEnum.Knight;
		Start(color);
	}
}
