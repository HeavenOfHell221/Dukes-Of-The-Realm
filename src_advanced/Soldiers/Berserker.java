package Soldiers;

import Enums.SoldierEnum;
import javafx.scene.paint.Color;

public class Berserker extends Soldier
{
	public Berserker()
	{
		super();
		this.type = SoldierEnum.Berserker;
	}

	@Override
	public void Awake(final Color color)
	{
		AddBerserkerRepresentation();
		super.Awake(color);
	}
}
