package Duke;

import DukesOfTheRealm.Castle;
import UI.UIManager;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Actor {

	public Player(final String name, final Color myColor)
	{
		super(name, myColor);
	}

	@Override
	public void AddCastle(final Castle castle) {
		super.AddCastle(castle);
		UIManager.GetInstance().SwitchCastle(castle);
	}

	@Override
	protected void CastleHandle(final MouseEvent e)
	{
		if(e.getButton() == MouseButton.PRIMARY) // Clique gauche
		{
			final Rectangle rectangle = (Rectangle) e.getSource();

			GetMyCastles()
			.stream()
			.filter(castle -> castle.GetShape() == rectangle)
			.limit(1)
			.forEach( castle -> {
				SetLastPlayerCastleClicked(castle);
				UIManager.GetInstance().SwitchCastle(castle);
			});
		}
	}


}
