package SimpleGoal;

import DukesOfTheRealm.Castle;

/**
 * 
 *
 */
public class SendOstGoal extends Goal
{
	/**
	 * 
	 */
	private Castle destination;
	
	/**
	 * 
	 */
	private final int nbPikers;
	
	/**
	 * 
	 */
	private final int nbKnights;
	
	/**
	 * 
	 */
	private final int nbOnagers;

	/**
	 * 
	 * @param destination
	 * @param nbPikers
	 * @param nbKnights
	 * @param nbOnagers
	 */
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

	/**
	 * @param destination the destination to set
	 */
	public final void setDestination(Castle destination)
	{
		this.destination = destination;
	}

	
}
