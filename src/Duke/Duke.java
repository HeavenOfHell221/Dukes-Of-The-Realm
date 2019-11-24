package Duke;

import UI.CastleUI;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Duke extends Actor{

	Duke(String name, Color myColor) 
	{
		super(name, myColor);
	}
	
	protected void CastleHandle(MouseEvent e)
	{
		if(e.getButton() == MouseButton.PRIMARY) // Clique gauche
		{	
			Rectangle rectangle = (Rectangle) e.getSource();
			
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
						CastleUI.GetInstance().SwitchCastle(castle);
					});
		}
	}
}
