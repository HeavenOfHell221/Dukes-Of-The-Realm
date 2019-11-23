package Duke;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Duke extends Actor{

	Duke(Color myColor) 
	{
		super(myColor);
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
						if(GetLastPlayerCastleClicked() == null)
						{
							if(GetLastOtherCastleClicked() == null)
							{
								SetLastOtherCastleClicked(castle); 
								System.out.println("OUVERTURE " + castle);
							}
							else
							{
								SetLastOtherCastleClicked(castle); 
								System.out.println("FERMETURE + OUVERTURE" + castle);
							}
						}
						else
						{
							SetLastPlayerCastleClicked(null); 
							System.out.println("FERMETURE + ATTAQUE " + castle);
						}
					});
		}
	}
}
