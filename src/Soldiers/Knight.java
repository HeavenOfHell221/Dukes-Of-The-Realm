package Soldiers;

import DukesOfTheRealm.Ost;
import Enum.SoldierEnum;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Knight extends Soldier {

	public Knight(Pane layer, double x, double y, Ost itsOst, int speed) 
	{
		super(layer, x, y, itsOst, speed, Settings.KNIGHT_HP, Settings.PIKER_DAMAGE);
		this.type = SoldierEnum.Knight;
	}

	public Knight() 
	{
		super();
	}

	@Override
	public void Awake(Color color)
	{
		AddKnightRepresentation();
		super.Awake(color);
	}

	@Override
	public int GetProductionTime() {
		return Settings.KNIGHT_TIME_PRODUCTION;
	}

	@Override
	public int GetProductionCost() {
		return Settings.KNIGHT_COST;
	}
}
