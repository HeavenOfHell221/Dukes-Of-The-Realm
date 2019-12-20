package Goal;

import java.util.ArrayList;
import java.util.Random;

import DukesOfTheRealm.Castle;
import SimpleGoal.Goal;

public class BackupGoal extends Goal
{
	private final GenericGoal goals;
	private final Castle origin;
	private Castle destination;
	
	public BackupGoal(Castle origin, int nbPikers, int nbKnights, int nbOnagers)
	{
		goals = new GenericGoal();
		this.origin = origin;
		
		Random rand = new Random();
		ArrayList<Castle> list = origin.getActor().getCastles();
		
		do
		{
			this.destination = list.get(rand.nextInt(list.size()));
		}
		while(this.destination == this.origin);
		
		AttackGoal g = new AttackGoal(origin, nbPikers, nbKnights, nbOnagers);
		g.setGoal(this.destination);
		
		goals.addFirst(g);
	}
	
	
	@Override
	public boolean goal(Castle castle)
	{
		return goals.goal(castle);
	}

}
