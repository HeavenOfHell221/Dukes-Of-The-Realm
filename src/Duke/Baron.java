package Duke;

import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Castle;
import UI.UIManager;
import Utility.Settings;
import Utility.Time;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class Baron extends Actor implements Serializable
{
	public Baron()
	{
	
	}
	
	@Override
	public String florinIncome(final Castle castle)
	{
		return (int)(Settings.FLORIN_PER_SECOND * castle.getLevel() * Settings.FLORIN_FACTOR_BARON) + " Florin/s";
	}
	
	protected void updateFlorin(Castle castle)
	{
		castle.addFlorin(Settings.FLORIN_PER_SECOND * Settings.FLORIN_FACTOR_BARON * castle.getLevel() * Time.deltaTime);
	}

	@Override
	public void addFirstCastle(final Castle castle)
	{
		final Random rand = new Random();
		castle.addFlorin(rand.nextInt(801) + 200);
		castle.setLevel(rand.nextInt(10) + 1);
		castle.randomSoldier();
		super.addFirstCastle(castle);
	}
}
