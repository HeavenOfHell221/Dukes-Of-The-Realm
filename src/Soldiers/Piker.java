package Soldiers;

import DukesOfTheRealm.Ost;
import Utility.Settings;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Piker extends Soldier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7839430643972783013L;

	public Piker(Pane layer, double x, double y, Ost itsOst, int speed)
	{
		super(layer, x, y, itsOst, Settings.PIKER_COST, Settings.PIKER_TIME_PRODUCTION, speed, Settings.PIKER_HP, Settings.PIKER_DAMAGE);
		this.type = SoldierEnum.Piker;
	}
	
	@Override
	public void Awake(Color color)
	{
		AddPikerRepresentation();
		super.Awake(color);
	}
}