package Soldiers;

import DukesOfTheRealm.Ost;
import Enum.SoldierEnum;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Onager extends Soldier {

	public Onager(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		type = SoldierEnum.Onager;
		stats = new Stats(speed, Settings.ONAGER_HP, Settings.ONAGER_DAMAGE);
	}

	public Onager()
	{
		super();
	}

	@Override
	public void Awake(final Color color)
	{
		AddOnagerRepresentation();
		super.Awake(color);
	}

	@Override
	public int GetProductionTime() {
		return Settings.ONAGER_TIME_PRODUCTION;
	}

	@Override
	public int GetProductionCost() {
		return Settings.ONAGER_COST;
	}
}
