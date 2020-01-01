package Goal;

import java.io.Serializable;
import java.util.ArrayDeque;

import DukesOfTheRealm.Castle;
import Interface.IGoal;
import SimpleGoal.Goal;

/**
 * Objectif g�n�rique qui contient une queue contenant des objectifs du package SimpleGoal
 * (CastleGoal, SoldierGoal, ..). Permet la construction des objectifs plus complexe qui sont dans
 * le package Goal (AttackGoal, MultiSoldierGoal, ..).
 *
 * @see Goal
 * @see SimpleGoal
 */
public class GenericGoal implements Serializable, IGoal
{
	/**
	 * Queue des objectifs � r�ussir. Lorsqu'un objectif est r�ussi, il est retir� et on passe au
	 * suivant jusqu'� ce que la queue soit vide.
	 */
	private final ArrayDeque<Goal> goals;

	/**
	 * Constructeur par d�faut de GenericGoal.
	 */
	public GenericGoal()
	{
		this.goals = new ArrayDeque<>();
	}

	/**
	 * Tant que la queue n'est pas vide, on essai de faire l'objectif en t�te de queue. S'il est r�ussi,
	 * on passe au suivant etc. Il y a une limite d'objectif faisable dans la m�me update qui est le niveau du ch�teau * 2.
	 *
	 * @return Retourne true si la queue est vide, false sinon.
	 */
	@Override
	public boolean goal(final Castle castle)
	{
		final int goalCompletedMax = castle.getLevel() * 2;
		int goalCompleted = 0;
		while (!this.goals.isEmpty() && this.goals.getFirst().isGoalIsCompleted(castle) && goalCompleted < goalCompletedMax)
		{
			this.goals.pollFirst();
			goalCompleted++;
		}
		return this.goals.size() == 0;
	}

	/**
	 * Ajoute � la fin de la queue un objectif.
	 *
	 * @param goal L'objectif � ajouter.
	 */
	public void addLast(final Goal goal)
	{
		this.goals.addLast(goal);
	}

	/**
	 * Ajoute au d�but de la queue un objectif.
	 *
	 * @param goal L'objectif � ajouter.
	 */
	public void addFirst(final Goal goal)
	{
		this.goals.addFirst(goal);
	}

	/**
	 * @return Retourne le 1er �l�ment de la queue et le retire.
	 * @see    java.util.ArrayDeque#pollFirst()
	 */
	public Goal pollFirst()
	{
		return this.goals.pollFirst();
	}

	/**
	 * @return Retourne la taille de la queue.
	 * @see    java.util.ArrayDeque#size()
	 */
	public int size()
	{
		return this.goals.size();
	}

	/**
	 * @return Retourne le 1er �l�ment de la queue.
	 * @see    java.util.ArrayDeque#peekFirst()
	 */
	public Goal peekFirst()
	{
		return this.goals.peekFirst();
	}
}
