package Soldiers;

import DukesOfTheRealm.Ost;
import Enum.SoldierEnum;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Piker extends Soldier
{

	public Piker(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Piker;
		this.stats = new Stats(speed, Settings.PIKER_HP, Settings.PIKER_DAMAGE);
	}

	public Piker()
	{
		super();
	}

	@Override
	public void Awake(final Color color)
	{
		AddPikerRepresentation();
		super.Awake(color);
	}

	@Override
	public int getProductionTime()
	{
		return Settings.PIKER_TIME_PRODUCTION;
	}

	@Override
	public int getProductionCost()
	{
		return Settings.PIKER_COST;
	}
}
