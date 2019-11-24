package Soldiers;

import Utility.Settings;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Piker extends Soldier {

	public Piker(Pane layer, double x, double y, Color color)
	{
		super(layer, x, y, Settings.PIKER_COST, Settings.PIKER_TIME_PRODUCTION, Settings.PIKER_SPEED, Settings.PIKER_HP, Settings.PIKER_DAMAGE);
		this.type = SoldierEnum.Piker;
		Start(color);
	}
}