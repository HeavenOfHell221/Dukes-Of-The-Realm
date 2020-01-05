package Goals;

import java.util.ArrayList;
import java.util.Random;

import DukesOfTheRealm.Castle;
import Enums.SoldierEnum;
import SimpleGoal.Goal;
import Utility.SoldierPack;

/**
 * Objectif visant � envoyer du renfort � un ch�teau nous appartenant. Reproduit le m�me montant
 * d'unit� que dans le nombre envoy� en renfort.
 */
public class BackupGoal extends Goal
{
	/**
	 * Queue des objectifs � accomplir pour accomplir cet objectif.
	 *
	 * @see GenericGoal
	 */
	private final GenericGoal goals;

	/**
	 * Constructeur de BackupGoal.
	 *
	 * <p>
	 * Cr�e une liste de ch�teau sans le ch�teau qui doit envoyer du renfort. Si la liste est vide alors
	 * l'objectif est infaisable et la queue reste vide.
	 * </p>
	 *
	 * @param origin    Le ch�teau qui va envoyer du renfort.
	 * @param nbPikers  Le nombre de Piker � envoyer.
	 * @param nbKnights Le nombre de Knight � envoyer.
	 * @param nbOnagers Le nombre de Onager � envoyer.
	 */
	public BackupGoal(final Castle origin, final SoldierPack<Integer> pack)
	{
		this.goals = new GenericGoal();
		Random rand = new Random();
		ArrayList<Castle> list = new ArrayList<>();
		list.addAll(origin.getActor().getCastles());
		list.remove(origin);
		if (!list.isEmpty()) // Si l'acteur a au moins 2 ch�teaux
		{
			Castle destination = list.get(rand.nextInt(list.size())); // On prend un ch�teau au hasard
			AttackGoal g = new AttackGoal(origin, destination, pack);
			this.goals.addLast(g);
			SoldierPack<Integer> production = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);
			for(SoldierEnum s : SoldierEnum.values())
			{
				if(pack.get(s) > 0)
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
