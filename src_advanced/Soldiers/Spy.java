package Soldiers;

import Enums.SoldierEnum;
import javafx.scene.paint.Color;

public class Spy extends Soldier
{
	public Spy()
	{
		super();
		this.type = SoldierEnum.Spy;
	}

	@Override
	public void Awake(final Color color)
	{
		AddKnightRepresentation();
		super.Awake(color);
	}
}
