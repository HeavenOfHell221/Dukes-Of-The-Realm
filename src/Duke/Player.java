package Duke;

import java.util.Random;

import DukesOfTheRealm.Castle;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
								Text t = new Text();
								t.setFont(new Font(20));
								t.setWrappingWidth(200);
								t.setTextAlignment(TextAlignment.JUSTIFY);
								t.setText("The quick brown fox jumps over the lazy dog");
								castle.getLayer().getChildren().add(t);
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
