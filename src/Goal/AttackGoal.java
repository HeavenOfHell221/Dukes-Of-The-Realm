package Goal;

import DukesOfTheRealm.Castle;
import SimpleGoal.Goal;
import SimpleGoal.SendOstGoal;

/**
 * Objectif visant � envoyer une attaque vers un autre ch�teau d'un utre acteur.
 */
public class AttackGoal extends Goal
{
	/**
	 * Queue des objectifs � accomplir pour accomplir cet objectif.
	 * 
	 * @see GenericGoal
	 */
	private final GenericGoal goals;

	/**
	 * Le ch�teau origin de l'ost.
	 */
	private final Castle castleOrigin;

	/**
	 * Le ch�teau destination de l'ost.
	 */
	private final Castle castleDestination;

	/**
	 * Le nombre de Piker dans l'ost.
	 */
	private final int nbPikers;

	/**
	 * Le nombre de Knight dans l'ost.
	 */
	private final int nbKnights;

	/**
	 * Le nombre de Onager dans l'ost.
	 */
	private final int nbOnagers;

	/**
	 * Constructeur de AttackGoal.
	 *
	 * <p>
	 * Calcul le manque d'unit� pour cr�er l'ost et produit un objectif MultiSoldierGoal pour produire
	 * les unit�s manquante. Cr�� ensuite un objectif SendOstGoal pour envoyer l'ost.
	 * </p>
	 *
	 * @param castleOrigin      Le ch�teau d'o� va partir l'ost.
	 * @param castleDestination Le ch�teau destination de l'ost.
	 * @param nbPikers          Le nombre de Piker dans l'ost.
	 * @param nbKnights         Le nombre de Knight dans l'ost.
	 * @param nbOnagers         Le nombre de Onager dans l'ost.
	 * @see                     MultiSoldierGoal
	 * @see                     SimpleGoal.SendOstGoal
	 */
	public AttackGoal(final Castle castleOrigin, final Castle castleDestination, final int nbPikers, final int nbKnights,
			final int nbOnagers)
	{
		this.goals = new GenericGoal();
		this.castleOrigin = castleOrigin;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
		this.castleDestination = castleDestination;

		int nbPikers_ = this.castleOrigin.getNbPikers() + this.castleOrigin.getNbPikersInProduction();
		int nbOnagers_ = this.castleOrigin.getNbOnagers() + this.castleOrigin.getNbOnagersInProduction();
		int nbKnights_ = this.castleOrigin.getNbKnights() + this.castleOrigin.getNbKnightsInProduction();

		int realNbPikers = nbPikers_ < this.nbPikers ? this.nbPikers - nbPikers_ : 0;
		int realNbKnights = nbKnights_ < this.nbKnights ? this.nbKnights - nbKnights_ : 0;
		int realNbOnagers = nbOnagers_ < this.nbOnagers ? this.nbOnagers - nbOnagers_ : 0;

		this.goals.addLast(new MultiSoldierGoal(this.castleOrigin, realNbPikers, realNbKnights, realNbOnagers));
		this.goals.addLast(new SendOstGoal(castleDestination, this.nbPikers, this.nbKnights, this.nbOnagers));
	}

	@Override
	public boolean goal(final Castle castleOrigin)
	{
		// Au moment de lancer l'ost
		if (this.goals.size() == 1)
		{
			// Si le ch�teau qu'on veut attaquer est maintenant � nous
			if (this.castleOrigin.getActor() == this.castleDestination.getActor())
			{
				// On annule l'attaque
				this.goals.pollFirst();
			}

			// Si nous n'avons plus assez d'unit�s
			if (castleOrigin.getNbPikers() < this.nbPikers || castleOrigin.getNbKnights() < this.nbKnights
					|| castleOrigin.getNbOnagers() < this.nbOnagers)
			{
				int nbPikers_ = castleOrigin.getNbPikers() + castleOrigin.getNbPikersInProduction();
				int nbOnagers_ = castleOrigin.getNbOnagers() + castleOrigin.getNbOnagersInProduction();
				int nbKnights_ = castleOrigin.getNbKnights() + castleOrigin.getNbKnightsInProduction();

				int realNbPikers = nbPikers_ < this.nbPikers ? this.nbPikers - nbPikers_ : 0;
				int realNbKnights = nbKnights_ < this.nbKnights ? this.nbKnights - nbKnights_ : 0;
				int realNbOnagers = nbOnagers_ < this.nbOnagers ? this.nbOnagers - nbOnagers_ : 0;

				// On reproduit des unit�s avant de lancer l'ost
				this.goals.addFirst(new MultiSoldierGoal(castleOrigin, realNbPikers, realNbKnights, realNbOnagers));
				return false;
			}
		}

		return this.goals.goal(castleOrigin);
	}

	@Override
	public String toString()
	{
		return "AttackGoal [ nbPikers= " + this.nbPikers + ", nbKnights= " + this.nbKnights + ", nbOnagers= " + this.nbOnagers + "]";
	}
}
