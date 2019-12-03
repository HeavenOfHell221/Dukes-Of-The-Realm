package Soldiers;

import DukesOfTheRealm.Ost;
import Enum.SoldierEnum;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Onager extends Soldier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2298170734121952998L;

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
