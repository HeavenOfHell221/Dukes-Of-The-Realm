package Soldiers;

import DukesOfTheRealm.Ost;
import Enum.SoldierEnum;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Knight extends Soldier {

	public Knight(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		type = SoldierEnum.Knight;
		stats = new Stats(speed, Settings.KNIGHT_HP, Settings.KNIGHT_DAMAGE);
	}

	public Knight()
	{
		super();
	}

	@Override
	public void Awake(final Color color)
	{
		AddKnightRepresentation();
		super.Awake(color);
	}

	@Override
	public int getProductionTime() {
		return Settings.KNIGHT_TIME_PRODUCTION;
	}

	@Override
	public int getProductionCost() {
		return Settings.KNIGHT_COST;
	}
}
