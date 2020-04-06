package Goals;

import java.util.ArrayList;
import java.util.Random;

import DukesOfTheRealm.Castle;
import Enums.SoldierEnum;
import SimpleGoal.Goal;
import Utility.SoldierPack;

/**
 * Objectif visant à envoyer du renfort à un château nous appartenant. Reproduit le même montant
 * d'unité que dans le nombre envoyé en renfort.
 */
public class BackupGoal extends Goal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -320381642236607431L;
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
	 * @param pack Le nombre d'unité pour chaque type à envoyer en renfort.
	 */
	public BackupGoal(final Castle origin, final SoldierPack<Integer> pack)
	{
		this.goals = new GenericGoal();
		Random rand = new Random();
		ArrayList<Castle> list = new ArrayList<>();
		list.addAll(origin.getActor().getCastles());
		list.remove(origin);
		if (!list.isEmpty()) // Si l'acteur a au moins 2 châteaux
		{
			Castle destination = list.get(rand.nextInt(list.size())); // On prend un château au hasard
			AttackGoal g = new AttackGoal(origin, destination, pack);
			this.goals.addLast(g);
			SoldierPack<Integer> production = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);
			for (SoldierEnum s : SoldierEnum.values())
			{
				if (pack.get(s) > 0)
				{
					production.replace(s, pack.get(s) / 2);
				}
			}
			this.goals.addLast(new MultiSoldierGoal(origin, production));
		}
	}

	@Override
	public boolean goal(final Castle castle)
	{
		return this.goals.goal(castle);
	}
}
