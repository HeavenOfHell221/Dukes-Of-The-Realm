package Interface;

import DukesOfTheRealm.Castle;

/**
 * Interface des objectifs pour les IA.
 *
 * @see Goal.GeneratorGoal
 */
public interface IGoal
{
	/**
	 * Essai d'accomplir l'objectif et retourne true si l'objectif est réussi, false sinon.
	 * 
	 * @param  castle Le château sur lequel l'objectif est à accomplir.
	 * @return        Retourne true si l'objectif est accomplie false sinon.
	 */
	boolean goal(final Castle castle);

	/**
	 * Appel la méthode goal et retourne sont résultat.
	 * 
	 * @return Retourne la valeur de retour de goal(Castle)
	 * @see    IGoal#goal(Castle)
	 */
	default boolean isGoalIsCompleted(final Castle castle)
	{
		return goal(castle);
	}
}
