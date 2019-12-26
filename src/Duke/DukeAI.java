package Duke;

import static Goal.GeneratorGoal.getNewGoal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Kingdom;
import SimpleGoal.Goal;
import Utility.Settings;

public class DukeAI extends Actor implements Serializable
{
	private long lastTime;
	private final HashMap<Castle, Goal> map;
	private Kingdom kingdom;

	public DukeAI()
	{
		super();
		this.map = new HashMap<>();
	}

	public void start(final Kingdom kingdom)
	{
		super.start();

		this.kingdom = kingdom;
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
		Iterator<Castle> it = this.castles.iterator();
		while (it.hasNext())
		{
			Castle castle = it.next();
			updateFlorin(castle);
			castle.updateProduction();
			castle.updateOst(now, pause);
		}

		if (time(now, pause))
		{
			it = this.castles.iterator();

			while (it.hasNext())
			{
				Castle castle = it.next();
				// System.out.println(castle);
				if (this.map.containsKey(castle))
				{
					Goal g = this.map.get(castle);
					if (g == null || g.isGoalIsCompleted(castle))
					{
						putNewGoal(castle);
					}
				}
				else
				{
					putNewGoal(castle);
				}
			}
		}
		addOrRemoveCastleList();
	}

	private void putNewGoal(final Castle castle)
	{
		this.map.put(castle, getNewGoal(castle));
		// System.out.println(this.name + " -> castle {" + (int)castle.getTotalFlorin() + "} {" +
		// castle.getLevel() +"} " + map.get(castle));
	}

	@Override
	protected void switchCastle(final Castle castle)
	{
		super.switchCastle(castle);
		// System.out.println(this.name + " -> " + map.get(castle) + "\n");
	}

	private boolean time(final long now, final boolean pause)
	{
		if (pause)
		{
			this.lastTime = now;
		}
		if (now - this.lastTime > Settings.GAME_FREQUENCY / 2)
		{
			this.lastTime = now;
			return true;
		}
		return false;
	}

	/**
	 * @return the kingdom
	 */
	public final Kingdom getKingdom()
	{
		return this.kingdom;
	}

}
