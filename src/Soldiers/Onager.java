package Soldiers;

import DukesOfTheRealm.Ost;
import Enum.SoldierEnum;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Onager extends Soldier {

	public Onager(Pane layer, double x, double y, Ost itsOst, int speed)
	{
		super(layer, x, y, itsOst, speed, Settings.ONAGER_HP, Settings.ONAGER_DAMAGE);
		this.type = SoldierEnum.Onager;
	}
	
	public Onager()
	{
		super();
	}
	
	@Override
	public void Awake(Color color)
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
