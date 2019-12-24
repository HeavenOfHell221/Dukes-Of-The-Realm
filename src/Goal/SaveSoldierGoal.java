package Goal;

import DukesOfTheRealm.Castle;
import SimpleGoal.Goal;

public class SaveSoldierGoal extends Goal
{
	private final GenericGoal goals;
	private final int nbPikers;
	private final int nbKnights;
	private final int nbOnagers;
	
	public SaveSoldierGoal(final Castle castle, final int nbPikers, final int nbKnights, final int nbOnagers)
	{
		goals = new GenericGoal();
		this.nbKnights = nbKnights;
		this.nbPikers = nbPikers;
		this.nbOnagers = nbOnagers;
		
		int realNbPikers = castle.getNbPikers() < nbPikers ? nbPikers - castle.getNbPikers() : 0;
		int realNbKnights = castle.getNbKnights() < nbKnights ? nbKnights - castle.getNbKnights() : 0;
		int realNbOnagers = castle.getNbOnagers() < nbOnagers ? nbOnagers - castle.getNbOnagers() : 0;
		
		this.goals.addLast(new MultiSoldierGoal(castle, realNbPikers, realNbKnights, realNbOnagers));
	}

	@Override
	public boolean goal(Castle castle)
	{
		return goals.goal(castle);
	}

	@Override
	public String toString()
	{
		return "SaveSoldierGoal [nbPikers=" + nbPikers + ", nbKnights=" + nbKnights + ", nbOnagers=" + nbOnagers + "]";
	}
}
