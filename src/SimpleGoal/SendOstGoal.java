package SimpleGoal;

import Duke.Actor;
import DukesOfTheRealm.Castle;

public class SendOstGoal extends Goal
{
	private Castle destination;
	private final int nbPikers;
	private final int nbKnights;
	private final int nbOnagers;
	
	public SendOstGoal(Castle destination, int nbPikers, int nbKnights, int nbOnagers)
	{
		this.destination = destination;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
	}

	@Override
	public boolean goal(Castle castle)
	{
		return castle.createOst(destination, nbPikers, nbKnights, nbOnagers);
	}
	
	public void setDestination(Castle castle)
	{
		this.destination = castle;
	}
}
