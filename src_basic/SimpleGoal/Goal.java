package SimpleGoal;

import java.io.Serializable;

import DukesOfTheRealm.Castle;
import Interface.IGoal;

/**
 * Classe abstraite représentant un objectif et classe mère de tout les objectifs.
 */
public abstract class Goal implements IGoal, Serializable
{
	@Override
	public abstract boolean goal(final Castle castle);
}
