package Soldiers;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;

public class Knight extends Soldier {

	public Knight(Pane layer, double x, double y) 
	{
		super(layer, x, y, 500, 20, 6, 3, 5);
		this.type = SoldierEnum.Knight;
	}
	
	public void Start()
	{
		AddKnightRepresentation();
		canMove = true;
	}
}
