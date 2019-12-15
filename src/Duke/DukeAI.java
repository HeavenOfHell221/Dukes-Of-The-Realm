package Duke;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import DukesOfTheRealm.Castle;
import SimpleGoal.Goal;

public class DukeAI extends Actor implements Serializable
{
	private long lastTime;
	private final HashMap<Castle, Goal> map;

	public DukeAI()
	{
		super();
		this.map = new HashMap<>();
	}

	@Override
	public void addFirstCastle(final Castle castle)
	{
		super.addFirstCastle(castle);
		castle.startSoldier();
	}

	@Override
	public void update(final long now, final boolean pause)
	{
		super.update(now, pause);
	}

}
