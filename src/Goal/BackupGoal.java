package Goal;

import java.util.ArrayList;
import java.util.Random;

import DukesOfTheRealm.Castle;
import SimpleGoal.Goal;

/**
 * Objectif visant à envoyer du renfort à un château nous appartenant. Reproduit le même montant
 * d'unité que dans le nombre envoyé en renfort.
 */
public class BackupGoal extends Goal
{
	/**
	 * Queue des objectifs à accomplir pour accomplir cet objectif.
	 *
	 * @see GenericGoal
	 */
	private final GenericGoal goals;

	/**
	 * Constructeur de BackupGoal.
	 *
	 * <p>
	 * Crée une liste de château sans le château qui doit envoyer du renfort. Si la liste est vide alors
	 * l'objectif est infaisable et la queue reste vide.
	 * </p>
	 *
	 * @param origin    Le château qui va envoyer du renfort.
	 * @param nbPikers  Le nombre de Piker à envoyer.
	 * @param nbKnights Le nombre de Knight à envoyer.
	 * @param nbOnagers Le nombre de Onager à envoyer.
	 */
	public BackupGoal(final Castle origin, final int nbPikers, final int nbKnights, final int nbOnagers)
	{
		this.goals = new GenericGoal();
		Random rand = new Random();
		ArrayList<Castle> list = new ArrayList<>();
		list.addAll(origin.getActor().getCastles());
		list.remove(origin);
		if (!list.isEmpty()) // Si l'acteur a au moins 2 châteaux
		{
			Castle destination = list.get(rand.nextInt(list.size())); // On prend un château au hasard
			AttackGoal g = new AttackGoal(origin, destination, nbPikers, nbKnights, nbOnagers);
			this.goals.addLast(g);
			this.goals.addLast(new MultiSoldierGoal(origin, nbPikers, nbKnights, nbOnagers));
		}
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return this.goals.goal(castle);
	}
}
