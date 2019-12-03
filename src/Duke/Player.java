package Duke;

import DukesOfTheRealm.Castle;
import UI.CastleUI;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Actor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1002476852003507617L;
	private transient Castle lastCastleClicked = null;
	
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
