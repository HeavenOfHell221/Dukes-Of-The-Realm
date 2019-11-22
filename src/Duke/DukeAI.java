package Duke;

import DukesOfTheRealm.Castle;
import javafx.scene.paint.Color;

public class DukeAI extends Duke {

	public DukeAI(Color myColor)
	{
		super(myColor);
	}
	
	@Override
	public boolean AddCastle(Castle castle) {
		castle.GetShape().setFill(myColor);
		return super.AddCastle(castle);
	}
}
