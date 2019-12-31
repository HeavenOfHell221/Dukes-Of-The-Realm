package SimpleGoal;

import DukesOfTheRealm.Castle;

/**
 * Objectif visant à envoyer une ost d'un château à un autre (attaque ou renfort).
 */
public class SendOstGoal extends Goal
{
	/**
	 * Le château destination de l'ost.
	 */
	private Castle destination;

	/**
	 * Le nombre de Piker à envoyer dans l'ost.
	 */
	private final int nbPikers;

	/**
	 * Le nombre de Knights à envoyer dans l'ost.
	 */
	private final int nbKnights;

	/**
	 * Le nombre de Onager à envoyer dans l'ost.
	 */
	private final int nbOnagers;

	/**
	 * Constructeur de SendOStGoal.
	 * 
	 * @param destination Le château destination de l'ost.
	 * @param nbPikers    Le nombre de Piker à envoyer dans l'ost.
	 * @param nbKnights   Le nombre de Knight à envoyer dans l'ost.
	 * @param nbOnagers   Le nombre de Onager à envoyer dans l'ost.
	 * @see               Goal.AttackGoal
	 * @see               Goal.BackupGoal
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
	public final void setDestination(final Castle destination)
	{
		this.destination = destination;
	}
}
