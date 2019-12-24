package Goal;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Kingdom;
import SimpleGoal.Goal;
import SimpleGoal.SendOstGoal;

import static Goal.GeneratorGoal.rand;

import java.util.Random;

import Duke.Actor;
import Duke.DukeAI;

public class AttackGoal extends Goal
{
	private final GenericGoal goals;
	private final Castle castleOrigin;
	private Castle castleDestination;
	private int nbPikers;
	private int nbKnights;
	private int nbOnagers;
	
	public AttackGoal(final Castle castleOrigin, final int nbPikers, final int nbKnights, final int nbOnagers)
	{
		this.goals = new GenericGoal();
		this.castleOrigin = castleOrigin;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
	}
	
	public void setGoal(final Castle castleDestination)
	{
		this.castleDestination = castleDestination;
		
		int nbPikers_ = castleOrigin.getNbPikers() + castleOrigin.getNbPikersInProduction();
		int nbOnagers_ = castleOrigin.getNbOnagers() + castleOrigin.getNbOnagersInProduction();
		int nbKnights_ = castleOrigin.getNbKnights() + castleOrigin.getNbKnightsInProduction();
		
		int realNbPikers = nbPikers_ < nbPikers ? (nbPikers - nbPikers_) : 0;
		int realNbKnights = nbKnights_ < nbKnights ? (nbKnights - nbKnights_) : 0;
		int realNbOnagers = nbOnagers_ < nbOnagers ? (nbOnagers - nbOnagers_) : 0;
		
		this.goals.addLast(new MultiSoldierGoal(castleOrigin, realNbPikers, realNbKnights, realNbOnagers));
		this.goals.addLast(new SendOstGoal(castleDestination, nbPikers, nbKnights, nbOnagers));
	}

	@Override
	public boolean goal(Castle castleOrigin)
	{
		if(goals.size() == 1)
		{ 	
			if(this.castleOrigin.getActor() == this.castleDestination.getActor())
			{
				this.goals.pollFirst();
			}
			
			// Si nous n'avons plus assez d'unites
			if(castleOrigin.getNbPikers() < this.nbPikers || castleOrigin.getNbKnights() < this.nbKnights 
					|| castleOrigin.getNbOnagers() < this.nbOnagers)
			{
				int nbPikers_ = castleOrigin.getNbPikers() + castleOrigin.getNbPikersInProduction();
				int nbOnagers_ = castleOrigin.getNbOnagers() + castleOrigin.getNbOnagersInProduction();
				int nbKnights_ = castleOrigin.getNbKnights() + castleOrigin.getNbKnightsInProduction();
				
				int realNbPikers = nbPikers_ < nbPikers ? (nbPikers - nbPikers_) : 0;
				int realNbKnights = nbKnights_ < nbKnights ? (nbKnights - nbKnights_) : 0;
				int realNbOnagers = nbOnagers_ < nbOnagers ? (nbOnagers - nbOnagers_) : 0;
				
				this.goals.addFirst(new MultiSoldierGoal(castleOrigin, realNbPikers, realNbKnights, realNbOnagers));
				return false;
			}
		}
		
		return goals.goal(castleOrigin);
	}

	@Override
	public String toString()
	{
		return "AttackGoal [ nbPikers= " + nbPikers + ", nbKnights= " + nbKnights + ", nbOnagers= " + nbOnagers + "]";
	}
	
}
