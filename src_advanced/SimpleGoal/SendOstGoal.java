package SimpleGoal;

import DukesOfTheRealm.Castle;
import Utility.SoldierPack;

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
	 * Contient le nombre d'unité dans l'ost;
	 */
	private final SoldierPack soldierPack;

	/**
	 * Constructeur de SendOStGoal.
	 *
	 * @param destination Le château destination de l'ost.
	 * @param nbPikers    Le nombre de Piker à envoyer dans l'ost.
	 * @param nbKnights   Le nombre de Knight à envoyer dans l'ost.
	 * @param nbOnagers   Le nombre de Onager à envoyer dans l'ost.
	 * @see               Goals.AttackGoal
	 * @see               Goals.BackupGoal
	 */
	public SendOstGoal(final Castle destination, final SoldierPack soldierPack)
	{
		this.destination = destination;
		this.soldierPack = soldierPack;
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return castle.createOst(this.destination, this.soldierPack);
	}

	/**
	 * @param destination the destination to set
	 */
	public final void setDestination(final Castle destination)
	{
		this.destination = destination;
	}
}
