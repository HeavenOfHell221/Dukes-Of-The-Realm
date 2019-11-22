package Duke;

import java.util.Random;

import DukesOfTheRealm.Castle;
import javafx.scene.paint.Color;

public class Player extends Duke {

	public Player(Color myColor)
	{
		super(myColor);
	}
	
	@Override
	public boolean AddCastle(Castle castle) 
	{
		castle.GetShape().setFill(myColor);
		return super.AddCastle(castle);
	}
}
