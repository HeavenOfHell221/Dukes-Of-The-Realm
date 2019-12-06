package Duke;

import java.util.Random;

import DukesOfTheRealm.Castle;
import javafx.scene.paint.Color;

public class Baron extends Duke {

	public Baron(final String name, final Color myColor)
	{
		super(name, myColor);
	}

	@Override
	public void AddCastle(final Castle castle) {
		final Random rand = new Random();
		castle.AddFlorin(rand.nextInt(801) + 200);
		super.AddCastle(castle);
	}
}
