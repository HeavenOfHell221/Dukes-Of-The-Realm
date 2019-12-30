package Interface;

import DukesOfTheRealm.Castle;

/**
 * Interface des objectifs pour les AI. Permet Le polymorhisme entre tout les objectifs.
 * 
 * @see Goal.GeneratorGoal
 */
public interface IGoal
{
	/**
	 * @return Retourne true si l'objectif est accomplie false sinon.
	 */
	boolean goal(final Castle castle);

	/**
	 * @return Retourne la valeur de retour de goal(Castle)
	 * @see    IGoal#goal(Castle)
	 */
	default boolean isGoalIsCompleted(final Castle castle)
	{
		return goal(castle);
	}
}
