package Soldiers;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;

public class Knight extends Soldier {

	public Knight() 
	{
		super(500, 20, 6, 3, 5);
		this.type = SoldiersType.Knight;
	}
}
