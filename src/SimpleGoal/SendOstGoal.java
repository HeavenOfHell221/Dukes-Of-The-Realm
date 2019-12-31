package SimpleGoal;

import DukesOfTheRealm.Castle;

/**
 * Objectif visant � envoyer une ost d'un ch�teau � un autre (attaque ou renfort).
 */
public class SendOstGoal extends Goal
{
	/**
	 * Le ch�teau destination de l'ost.
	 */
	private Castle destination;

	/**
	 * Le nombre de Piker � envoyer dans l'ost.
	 */
	private final int nbPikers;

	/**
	 * Le nombre de Knights � envoyer dans l'ost.
	 */
	private final int nbKnights;

	/**
	 * Le nombre de Onager � envoyer dans l'ost.
	 */
	private final int nbOnagers;

	/**
	 * Constructeur de SendOStGoal.
	 * 
	 * @param destination Le ch�teau destination de l'ost.
	 * @param nbPikers    Le nombre de Piker � envoyer dans l'ost.
	 * @param nbKnights   Le nombre de Knight � envoyer dans l'ost.
	 * @param nbOnagers   Le nombre de Onager � envoyer dans l'ost.
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
