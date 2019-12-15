package SimpleGoal;

import Duke.Actor;
import DukesOfTheRealm.Castle;

public class SendOstGoal extends Goal
{
	private final Castle destination;
	private final int nbPikers;
	private final int nbKnights;
	private final int nbOnagers;
	private final Actor originActor;
	private final Actor destinationActor;
	
	public SendOstGoal(Castle destination, int nbPikers, int nbKnights, int nbOnagers, Actor originActor, Actor destinationActor)
	{
		this.destination = destination;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
		this.originActor = originActor;
		this.destinationActor = destinationActor;
	}

	@Override
	public boolean goal(Castle castle)
	{
		if(castle.createOst(destination, nbPikers, nbKnights, nbOnagers, originActor, destinationActor))
		{
			castle.removeSoldiers(nbPikers, nbKnights, nbOnagers);
			return true;
		}	
		return false;
	}
}
