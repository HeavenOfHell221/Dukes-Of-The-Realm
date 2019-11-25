package Soldiers;

import Utility.Settings;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Piker extends Soldier {

	public Piker(Pane layer, double x, double y, int speed)
	{
		super(layer, x, y, Settings.PIKER_COST, Settings.PIKER_TIME_PRODUCTION, speed, Settings.PIKER_HP, Settings.PIKER_DAMAGE);
		this.type = SoldierEnum.Piker;
	}
}