package SimpleGoal;

import DukesOfTheRealm.Castle;

public class SendOstGoal extends Goal
{
	private Castle destination;
	private final int nbPikers;
	private final int nbKnights;
	private final int nbOnagers;

	public SendOstGoal(final Castle destination, final int nbPikers, final int nbKnights, final int nbOnagers)
	{
		this.destination = destination;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return castle.createOst(this.destination, this.nbPikers, this.nbKnights, this.nbOnagers, false);
	}

	public void setDestination(final Castle castle)
	{
		this.destination = castle;
	}
}
