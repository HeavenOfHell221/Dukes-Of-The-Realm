package Soldiers;

import DukesOfTheRealm.Ost;
import Enum.SoldierEnum;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Piker extends Soldier {

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