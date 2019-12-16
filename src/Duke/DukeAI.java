package Duke;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Kingdom;
import DukesOfTheRealm.Main;
import Goal.AttackGoal;

import static Goal.GeneratorGoal.*;
import SimpleGoal.Goal;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
		//System.out.println(this.name + " -> " + this.castles.size());
		while (it.hasNext())
		{
			Castle castle = it.next();
			updateFlorin(castle);
			castle.updateProduction();
			castle.updateUIShape();
			castle.updateOst(now, pause);
		}
		
		if(time(now, pause))
		{
			it = this.castles.iterator();
			
			while(it.hasNext())
			{
				Castle castle = it.next();
				if(map.containsKey(castle))
				{
					if(map.get(castle).isGoalIsCompleted(castle))
					{
						putNewGoal(castle);
						System.out.println(this.name + " -> castle {" + (int)castle.getTotalFlorin() + "} {" + castle.getLevel() +"} " + map.get(castle));
					}
				}
				else
				{
					putNewGoal(castle);
					System.out.println(this.name + " -> castle {" + (int)castle.getTotalFlorin() + "} {" + castle.getLevel() +"} " + map.get(castle));
				}
			}
		}

		this.addOrRemoveCastleList();
	}
	
	private void putNewGoal(Castle castle)
	{
		Goal goal = getNewGoal(castle);
		
		if(goal.getClass() == AttackGoal.class)
		{
			Actor actorTarget = getRandomActor();
			Castle castleTarget = actorTarget.castles.get(rand.nextInt(actorTarget.castles.size()));
			
			((AttackGoal) goal).setGoal(castleTarget, rand.nextInt(10), rand.nextInt(10), rand.nextInt(5));
		}
		map.put(castle, goal);
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
	 * @return
	 * @see DukesOfTheRealm.Kingdom#getRandomActor()
	 */
	public Actor getRandomActor()
	{
		return kingdom.getRandomActor(this);
	}
	
	
}
