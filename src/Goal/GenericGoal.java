package Goal;

import java.io.Serializable;
import java.util.ArrayDeque;

import DukesOfTheRealm.Castle;
import Interface.IGoal;
import SimpleGoal.Goal;

/**
 * Objectif générique qui contient une queue contenant des objectifs du package SimpleGoal (CastleGoal, SoldierGoal, ..). Permet la construction des objectifs
 * plus complexe qui sont dans le package Goal (AttackGoal, MultiSoldierGoal, ..).
 * @see Goal
 * @see SimpleGoal
 */
public class GenericGoal implements Serializable, IGoal
{
	/**
	 * Queue des objectifs à réussir. Lorsqu'un objectif est réussi, il est retiré et on passe au suivant jusqu'à ce que la queue soit vide.
	 */
	private final ArrayDeque<Goal> goals;

	/**
	 * Constructeur par défaut de GenericGoal.
	 */
	public GenericGoal()
	{
		this.goals = new ArrayDeque<>();
	}

	/**
	 * Tant que la queue n'est pas vide, on essai de faire l'objectif en tête de queue. S'il est réussi, on passe au suivant etc.
	 * @return Retourne true si la queue est vide, false sinon.
	 */
	@Override
	public boolean goal(final Castle castle)
	{
		final int goalCompletedMax = castle.getLevel() * 5;
		int goalCompleted = 0;
		while (!this.goals.isEmpty() && this.goals.getFirst().isGoalIsCompleted(castle) && goalCompleted < goalCompletedMax)
		{
			this.goals.pollFirst();
			goalCompleted++;
		}

		return this.goals.size() == 0;
	}

	/**
	 * Ajoute à la fin de la queue un objectif.
	 * @param goal L'objectif à ajouter.
	 */
	public void addLast(final Goal goal)
	{
		this.goals.addLast(goal);
	}

	/**
	 * Ajoute au début de la queue un objectif.
	 * @param goal L'objectif à ajouter.
	 */
	public void addFirst(final Goal goal)
	{
		this.goals.addFirst(goal);
	}

	/**
	 * @return Retourne le 1er élément de la queue et le retire.
	 */
	public Goal pollFirst()
	{
		return this.goals.pollFirst();
	}

	/**
	 * @return Retourne la taille de la queue.
	 */
	public int size()
	{
		return this.goals.size();
	}

	/**
	 * @return Retourne le 1er élément de la queue.
	 * @see    java.util.ArrayDeque#peekFirst()
	 */
	public Goal peekFirst()
	{
		return this.goals.peekFirst();
	}
}
