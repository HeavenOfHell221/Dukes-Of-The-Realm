package Duke;

import java.io.Serializable;

import DukesOfTheRealm.Castle;
import UI.UIManager;

public class Player extends Actor implements Serializable
{
	public Player(final String name)
	{
		super(name);
	}

	@Override
	public void addCastle(final Castle castle)
	{
		super.addCastle(castle);
		UIManager.GetInstance().switchCastle(castle);
	}
}
