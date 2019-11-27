package Soldiers;

import Utility.Settings;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Knight extends Soldier {

	public Knight(Pane layer, double x, double y, int speed) 
	{
		super(layer, x, y, Settings.KNIGHT_COST, Settings.KNIGHT_TIME_PRODUCTION, speed, Settings.KNIGHT_HP, Settings.PIKER_DAMAGE);
		this.type = SoldierEnum.Knight;
	}

	@Override
	public void Awake(Color color)
	{
		AddKnightRepresentation();
		super.Awake(color);
	}
}
