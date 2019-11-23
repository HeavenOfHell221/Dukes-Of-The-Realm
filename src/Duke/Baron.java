package Duke;

import java.util.Random;

import DukesOfTheRealm.Castle;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Baron extends Duke {

	public Baron(Color myColor)
	{
		super(myColor);
	}

	@Override
	public boolean AddCastle(Castle castle) {
		Random rand = new Random();
		castle.AddFlorin(rand.nextInt(801) + 200);
		castle.GetShape().setFill(GetMyColor());
		return super.AddCastle(castle);
	}
}
