package Duke;

import java.io.Serializable;

import DukesOfTheRealm.Castle;

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
