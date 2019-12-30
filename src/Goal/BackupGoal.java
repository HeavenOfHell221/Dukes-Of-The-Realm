package Goal;

import java.util.ArrayList;
import java.util.Random;

import DukesOfTheRealm.Castle;
import SimpleGoal.Goal;

public class BackupGoal extends Goal
{
	private final GenericGoal goals;
	private Castle destination;
	private int nbPikers = -1;
	private int nbKnights = -1;
	private int nbOnagers = -1;

	public BackupGoal(final Castle origin, final int nbPikers, final int nbKnights, final int nbOnagers)
	{
		this.goals = new GenericGoal();
		Random rand = new Random();
		ArrayList<Castle> list = new ArrayList<>();
		list.addAll(origin.getActor().getCastles());
		list.remove(origin);
		if (!list.isEmpty())
		{
			this.nbPikers = nbPikers;
			this.nbOnagers = nbOnagers;
			this.nbKnights = nbKnights;
			this.destination = list.get(rand.nextInt(list.size()));
			AttackGoal g = new AttackGoal(origin, nbPikers, nbKnights, nbOnagers);
			g.setGoal(this.destination);
			this.goals.addLast(g);
			this.goals.addLast(new SaveSoldierGoal(origin, nbPikers, nbKnights, 0));
		}
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return this.goals.goal(castle);
	}

	@Override
	public String toString()
	{
		if(this.nbPikers == -1)
		{
			return "BackupGoal [Failed : No second castle]";
		}
		return "BackupGoal [nbPikers= " + this.nbPikers + ", nbKnights= " + this.nbKnights + ", nbOnagers= " + this.nbOnagers + "]";
	}
}
