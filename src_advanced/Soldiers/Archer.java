package Soldiers;

import DukesOfTheRealm.Castle;
import Enums.SoldierEnum;
import javafx.scene.paint.Color;

public class Archer extends Soldier
{
	public Archer()
	{
		super();
		this.type = SoldierEnum.Archer;
	}

	@Override
	public void Awake(final Color color)
	{
		AddArcherRepresentation();
		super.Awake(color);
	}
}
