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
	 * Essai d'accomplir l'objectif et retourne true si l'objectif est r�ussi, false sinon.
	 * 
	 * @param  castle Le ch�teau sur lequel l'objectif est � accomplir.
	 * @return        Retourne true si l'objectif est accomplie false sinon.
	 */
	boolean goal(final Castle castle);

	/**
	 * Appel la m�thode goal et retourne sont r�sultat.
	 * 
	 * @return Retourne la valeur de retour de goal(Castle)
	 * @see    IGoal#goal(Castle)
	 */
	default boolean isGoalIsCompleted(final Castle castle)
	{
		return goal(castle);
	}
}
