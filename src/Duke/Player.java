package Duke;

import java.util.Random;

import DukesOfTheRealm.Castle;
import UI.CastleUI;
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
	
	public Player(String name, Color myColor)
	{
		super(name, myColor);
	}
	
	@Override
	public void AddCastle(Castle castle) {
		super.AddCastle(castle);
		lastCastleClicked = castle;
		CastleUI.GetInstance().SwitchCastle(castle);
	}

	@Override
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
						SetLastPlayerCastleClicked(castle); 
						CastleUI.GetInstance().SwitchCastle(castle);
					});
		}
	}
	
	
}
