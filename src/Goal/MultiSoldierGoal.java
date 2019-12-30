package Goal;

import java.util.Random;

import DukesOfTheRealm.Castle;
import Enum.SoldierEnum;
import SimpleGoal.Goal;
import SimpleGoal.SoldierGoal;

/**
 * Objectif visant à produire des unités.
 */
public class MultiSoldierGoal extends Goal
{
	/**
	 * Queue des objectifs à accomplir pour accomplir cet objectif.
	 * @see GenericGoal
	 */
	private final GenericGoal goals;
	
	/**
	 * Nombre de Piker qu'on veut créer
	 */
	private final int nbPikers;
	
	/**
	 * Nombre de Knight qu'on veut créer.
	 */
	private final int nbKnights;
	
	/**
	 * Nombre de Onager qu'on veut créer.
	 */
	private final int nbOnagers;

	/**
	 * Constructeur De MultiSoldierGoal.
	 * <p>
	 * Va remplir la queue aléatoirement entre les différents type d'unité.
	 * </p>
	 * @param castle Le château sur lequel cet ojectif est à accomplir.
	 * @param nbPikers_ Le nombre de Piker à produire.
	 * @param nbKnights_ Le nombre de Knight à produire.
	 * @param nbOnagers_ Le nombre de Onager à produire.
	 * @see SimpleGoal.SoldierGoal
	 */
	public MultiSoldierGoal(final Castle castle, int nbPikers_, int nbKnights_, int nbOnagers_)
	{
		this.goals = new GenericGoal();
		Random rand = new Random();

		this.nbPikers = nbPikers_;
		this.nbKnights = nbKnights_;
		this.nbOnagers = nbOnagers_;

		int count = nbPikers_ + nbKnights_ + nbOnagers_;

		while (count > 0)
		{
			switch (rand.nextInt(3))
			{
				case 0:
					if (nbPikers_ > 0)
					{
						this.goals.addLast(new SoldierGoal(SoldierEnum.Piker));
						nbPikers_--;
					}
					break;

				case 1:
					if (nbKnights_ > 0)
					{
						this.goals.addLast(new SoldierGoal(SoldierEnum.Knight));
						nbKnights_--;
					}
					break;

				case 2:
					if (nbOnagers_ > 0)
					{
						this.goals.addLast(new SoldierGoal(SoldierEnum.Onager));
						nbOnagers_--;
					}
					break;

				default:
					break;
			}
			count = nbPikers_ + nbKnights_ + nbOnagers_;
		}
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return this.goals.goal(castle);
	}

	@Override
	public String toString()
	{
		return "MultiSoldierGoal [nbPikers= " + this.nbPikers + ", nbKnights= " + this.nbKnights + ", nbOnagers= " + this.nbOnagers + "]";
	}
}
