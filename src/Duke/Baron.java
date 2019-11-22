package Duke;

import java.util.Random;

import DukesOfTheRealm.Castle;
import javafx.scene.paint.Color;

public class Baron extends Actor {

	public Baron(Color myColor)
	{
		super(myColor);
	}

	@Override
	public boolean AddCastle(Castle castle) {
		Random rand = new Random();
		castle.AddFlorin(rand.nextInt(801) + 200);
		castle.GetShape().setFill(myColor);
		return super.AddCastle(castle);
	}
}
