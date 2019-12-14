package Duke;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import DukesOfTheRealm.Castle;
import Goal.AtomicGoal;
import Goal.GeneratorAtomicGoal;
import Utility.Settings;

public class DukeAI extends Actor implements Serializable
{
	private long lastTime;
	private HashMap<Castle, AtomicGoal> map;
	
	public DukeAI()
	{
		super();
		map = new HashMap<>();
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
		
		if(cooldown(now, pause))
		{
			Iterator<Castle> it = this.castles.iterator();
	
			while (it.hasNext())
			{
				Castle castle = it.next();
				
				if(map.containsKey(castle))
				{
					goal(castle);
				}
				else
				{
					map.put(castle, GeneratorAtomicGoal.getRandomGoal());
					goal(castle);
				}
			}
		}
	}
	
	private void goal(Castle castle)
	{
		if(map.get(castle).isGoalIsCompleted(castle)) // J'essaie de faire l'objectif
		{
			// Si l'objectif a ete acomplie, j'en reprend un nouveau
			map.put(castle, GeneratorAtomicGoal.getRandomGoal()); 
		}
	}
	
	private boolean cooldown(final long now, final boolean pause)
	{
		if (pause)
		{
			this.lastTime = now;
		}
		if (now - this.lastTime > Settings.GAME_FREQUENCY)
		{
			this.lastTime = now;
			return true;
		}
		return false;
	}
}
