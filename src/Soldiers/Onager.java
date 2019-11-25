package Soldiers;

import Utility.Settings;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Onager extends Soldier {

	public Onager(Pane layer, double x, double y, int speed)
	{
		super(layer, x, y, Settings.ONAGER_COST, Settings.ONAGER_TIME_PRODUCTION, speed, Settings.ONAGER_HP, Settings.ONAGER_DAMAGE);
		this.type = SoldierEnum.Onager;
	}
	
	@Override
	public void Start(Color color) 
	{
		AddOnagerRepresentation();
		super.Start(color);
	}
}
