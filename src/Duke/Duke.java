package Duke;



import UI.UIManager;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Duke extends Actor {

	Duke(final String name, final Color myColor)
	{
		super(name, myColor);
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
				if(GetLastOtherCastleClicked() == null)
				{
					SetLastPlayerCastleClicked(null);
				}
				SetLastOtherCastleClicked(castle);
				UIManager.GetInstance().SwitchCastle(castle);
			});
		}
	}
}
