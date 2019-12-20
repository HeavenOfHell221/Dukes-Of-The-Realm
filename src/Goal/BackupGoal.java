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
	private int nbPikers = -1;
	private int nbKnights = -1;
	private int nbOnagers = -1;
	
	public BackupGoal(Castle origin, int nbPikers, int nbKnights, int nbOnagers)
	{
		goals = new GenericGoal();
		this.origin = origin;
		
		Random rand = new Random();
		ArrayList<Castle> list = new ArrayList<>();
		list.addAll(origin.getActor().getCastles());
		list.remove(origin);
		if(!list.isEmpty())
		{
			this.nbPikers = nbPikers;
			this.nbOnagers = nbOnagers;
			this.nbKnights = nbKnights;
			this.destination = list.get(rand.nextInt(list.size()));
			AttackGoal g = new AttackGoal(origin, nbPikers, nbKnights, nbOnagers);
			g.setGoal(this.destination);
			goals.addLast(g);
			goals.addLast(new SaveSoldierGoal(origin, nbPikers, nbKnights, 0));
		}
	}
	
	@Override
	public boolean goal(Castle castle)
	{
		return goals.goal(castle);
	}

	@Override
	public String toString()
	{
		return "BackupGoal [nbPikers= " + nbPikers + ", nbKnights= " + nbKnights + ", nbOnagers= " + nbOnagers + "]";
	}
}
