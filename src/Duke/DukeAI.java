package Duke;

import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Castle;
import UI.UIManager;
import Utility.Settings;
import Utility.Time;

public class DukeAI extends Actor implements Serializable
{

	public DukeAI()
	{
		super();
	}
	
	@Override
	public void addFirstCastle(final Castle castle)
	{
		super.addFirstCastle(castle);
		castle.startSoldier();
	}

}
