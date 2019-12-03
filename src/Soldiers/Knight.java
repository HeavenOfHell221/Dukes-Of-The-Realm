package Soldiers;

import DukesOfTheRealm.Ost;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Knight extends Soldier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4718678786110650560L;

	public Knight(Pane layer, double x, double y, Ost itsOst, int speed) 
	{
		super(layer, x, y, itsOst, Settings.KNIGHT_COST, Settings.KNIGHT_TIME_PRODUCTION, speed, Settings.KNIGHT_HP, Settings.PIKER_DAMAGE);
		this.type = SoldierEnum.Knight;
	}

	@Override
	public void Awake(Color color)
	{
		AddKnightRepresentation();
		super.Awake(color);
	}
}
