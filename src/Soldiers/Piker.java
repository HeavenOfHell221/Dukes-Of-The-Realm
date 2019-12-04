package Soldiers;

import DukesOfTheRealm.Ost;
import Enum.SoldierEnum;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Piker extends Soldier {

	public Piker(Pane layer, double x, double y, Ost itsOst, int speed)
	{
		super(layer, x, y, itsOst, speed, Settings.PIKER_HP, Settings.PIKER_DAMAGE);
		this.type = SoldierEnum.Piker;
	}
	
	public Piker()
	{
		super();
	}
	
	@Override
	public void Awake(Color color)
	{
		AddPikerRepresentation();
		super.Awake(color);
	}

	@Override
	public int GetProductionTime() {
		return Settings.PIKER_TIME_PRODUCTION;
	}

	@Override
	public int GetProductionCost() {
		return Settings.PIKER_COST;
	}
}