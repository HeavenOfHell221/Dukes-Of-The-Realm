package Soldiers;

import DukesOfTheRealm.Ost;
import Utility.Settings;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Onager extends Soldier {

	public Onager(Pane layer, double x, double y, Ost itsOst, int speed)
	{
		super(layer, x, y, itsOst, Settings.ONAGER_COST, Settings.ONAGER_TIME_PRODUCTION, speed, Settings.ONAGER_HP, Settings.ONAGER_DAMAGE);
		this.type = SoldierEnum.Onager;
	}
	
	@Override
	public void Awake(Color color)
	{
		AddOnagerRepresentation();
		super.Awake(color);
	}
}
