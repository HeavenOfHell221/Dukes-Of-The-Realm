package Duke;

import java.io.Serializable;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Main;
import UI.UIManager;
import Utility.Settings;
import Utility.Time;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Actor implements Serializable
{

	public Player()
	{
		super();
	}
	
	@Override
	public void addFirstCastle(final Castle castle)
	{
		super.addFirstCastle(castle);
		switchCastle(castle);
		castle.startSoldier();
	}
	
	@Override
	protected void switchCastle(Castle castle)
	{
		UIManager.getInstance().switchCastle(castle, this, true, false);
	}
	
	@Override
	protected void updateFlorin(Castle castle)
	{
		castle.addFlorin(Settings.FLORIN_PER_SECOND * castle.getLevel() * Time.deltaTime);
	}
	
	@Override
	public void startTransient(Color color, Pane pane)
	{
		this.color = Color.LIMEGREEN;
		if(!Main.isNewGame)
		{
			castles.forEach(castle -> castle.setColor(this.color));
		}
	}
}
