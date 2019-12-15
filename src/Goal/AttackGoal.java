package Goal;

import DukesOfTheRealm.Castle;
import SimpleGoal.Goal;
import SimpleGoal.SendOstGoal;
import Duke.Actor;

public class AttackGoal extends Goal
{
	private final GenericGoal goals;
	private final int nbPikers;
	private final int nbKnights;
	private final int nbOnagers; 
	
	public AttackGoal(final Castle castleOrigin, final Castle castleDestination, final int nbPikers, final int nbKnights, final int nbOnagers, 
			Actor oActor, Actor dActor)
	{
		this.goals = new GenericGoal();
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
		
		int realNbPikers = castleOrigin.getNbPikers() < nbPikers ? nbPikers - castleOrigin.getNbPikers() : 0;
		int realNbKnights = castleOrigin.getNbKnights() < nbKnights ? nbKnights - castleOrigin.getNbKnights() : 0;
		int realNbOnagers = castleOrigin.getNbOnagers() < nbOnagers ? nbOnagers - castleOrigin.getNbOnagers() : 0;
		
		this.goals.add(new MultiSoldierGoal(realNbPikers, realNbKnights, realNbOnagers));
		this.goals.add(new SendOstGoal(castleDestination, nbPikers, nbKnights, nbOnagers, oActor, dActor));
	}

	@Override
	public boolean goal(Castle castle)
	{
		if(goals.size() == 1)
		{
			if(castle.getNbPikers() >= this.nbPikers && castle.getNbKnights() >= this.nbKnights && castle.getNbOnagers() >= this.nbOnagers)
			{
				return goals.goal(castle);
			}
			else
			{
				int realNbPikers = castle.getNbPikers() < nbPikers ? nbPikers - castle.getNbPikers() : 0;
				int realNbKnights = castle.getNbKnights() < nbKnights ? nbKnights - castle.getNbKnights() : 0;
				int realNbOnagers = castle.getNbOnagers() < nbOnagers ? nbOnagers - castle.getNbOnagers() : 0;
				
				this.goals.add(new MultiSoldierGoal(realNbPikers, realNbKnights, realNbOnagers));
				return false;
			}
		}
		
		return goals.goal(castle);
	}
}
