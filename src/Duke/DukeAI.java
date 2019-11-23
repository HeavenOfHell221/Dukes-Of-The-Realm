package Duke;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Kingdom;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DukeAI extends Duke {

	public DukeAI(Color myColor)
	{
		super(myColor);
	}
	
	@Override
	public boolean AddCastle(Castle castle) {
		castle.GetShape().setFill(GetMyColor());
		return super.AddCastle(castle);
	}
}
