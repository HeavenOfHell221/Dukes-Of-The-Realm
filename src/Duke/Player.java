package Duke;

import java.util.Random;

import DukesOfTheRealm.Castle;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Actor {
	
	private Castle lastCastleClicked;
	
	public Player(Color myColor)
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
							SetLastPlayerCastleClicked(castle);
							
							if(GetLastOtherCastleClicked() == null)
							{
								System.out.println("OUVERTURE " + castle);
							}
								
							else
							{
								SetLastOtherCastleClicked(null);
								System.out.println("FERMETURE + OUVERTURE " + castle);
							}
						}
						else
						{
							SetLastPlayerCastleClicked(null); 
							System.out.println("FERMETURE " + castle);
						}
					});
		}
	}
	
	
}
