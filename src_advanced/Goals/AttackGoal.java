package Goals;

import DukesOfTheRealm.Castle;
import Enums.SoldierEnum;
import SimpleGoal.Goal;
import SimpleGoal.SendOstGoal;
import Utility.SoldierPack;

/**
 * Objectif visant � envoyer une attaque vers un autre ch�teau d'un autre acteur.
 */
public class AttackGoal extends Goal
{
	/**
	 * Queue des objectifs � accomplir pour accomplir cet objectif.
	 *
	 * @see GenericGoal
	 */
	private final GenericGoal goals;

	/**
	 * Le ch�teau origine de l'ost.
	 */
	private final Castle castleOrigin;

	/**
	 * Le ch�teau destination de l'ost.
	 */
	private final Castle castleDestination;

	/**
	 * Contient le nombre d'unit� � envoyer dans l'ost.
	 */
	private SoldierPack<Integer> soldierPack;

	/**
	 * Constructeur de AttackGoal.
	 *
	 * <p>
	 * Calcul le manque d'unit� pour cr�er l'ost et produit un objectif MultiSoldierGoal pour produire
	 * les unit�s manquante. Cr�� ensuite un objectif SendOstGoal pour envoyer l'ost.
	 * </p>
	 *
	 * @param castleOrigin      Le ch�teau d'o� va partir l'ost.
	 * @param castleDestination Le ch�teau destination de l'ost.
	 * @param soldierPack       Contient le nombre d'unit� dans l'ost.
	 * @see                     MultiSoldierGoal
	 * @see                     SimpleGoal.SendOstGoal
	 */
	public AttackGoal(final Castle castleOrigin, final Castle castleDestination, final SoldierPack<Integer> soldierPack)
	{
		this.goals = new GenericGoal();
		this.castleOrigin = castleOrigin;
		this.soldierPack = soldierPack;
		this.castleDestination = castleDestination;

		if (castleOrigin.getOst() != null)
		{
			return;
		}

		SoldierPack<Integer> nbSoldier = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);
		SoldierPack<Integer> realNbSoldier = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);

		// Nombre d'unit� dans la caserne + dans la r�serve
		for (SoldierEnum s : SoldierEnum.values())
		{
			nbSoldier.replace(s, getReserveSoldier(s) + getCaserneSoldier(s));
		}

		// Nombre d'unit� � produire s'il y en a pas assez
		for (SoldierEnum s : SoldierEnum.values())
		{
			realNbSoldier.replace(s, nbSoldier.get(s) < soldierPack.get(s) ? soldierPack.get(s) - nbSoldier.get(s) : 0);
		}

		// On compte le nombre d'unit� � produire
		int count = 0;
		for (int i : realNbSoldier.values())
		{
			count += i;
		}

		// Si y'a des unit�s � produire avant d'envoyer l'ost
		if (count > 0)
		{
			this.goals.addLast(new MultiSoldierGoal(this.castleOrigin, realNbSoldier));
		}

		this.goals.addLast(new SendOstGoal(castleDestination, soldierPack));

		SoldierPack<Integer> production = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);
		for (SoldierEnum s : SoldierEnum.values())
		{
			if (soldierPack.get(s) > 0)
			{
				production.replace(s, soldierPack.get(s) / 2);
			}
		}
		this.goals.addLast(new MultiSoldierGoal(this.castleOrigin, production));
	}

	@Override
	public boolean goal(final Castle castleOrigin)
	{
		// Au moment de lancer l'ost
		if (this.goals.size() == 1)
		{
			SoldierPack<Integer> nbSoldier = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);

			for (SoldierEnum s : SoldierEnum.values())
			{
				nbSoldier.replace(s, getReserveSoldier(s) + getCaserneSoldier(s));
			}

			// Si le ch�teau qu'on veut attaquer est maintenant � nous
			if (this.castleOrigin.getActor() == this.castleDestination.getActor())
			{
				// On annule l'attaque
				this.goals.pollFirst();
			}

			boolean canSendOst = true;

			// Si l'un des unit�s n'est plus suffisante
			for (SoldierEnum s : SoldierEnum.values())
			{
				if (nbSoldier.get(s) < this.soldierPack.get(s))
				{
					canSendOst = false;
				}
			}

			// Si nous n'avons plus assez d'unit�s
			if (!canSendOst)
			{
				SoldierPack<Integer> realNbSoldier = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);

				for (SoldierEnum s : SoldierEnum.values())
				{
					realNbSoldier.replace(s, nbSoldier.get(s) < this.soldierPack.get(s) ? this.soldierPack.get(s) - nbSoldier.get(s) : 0);
				}

				// On reproduit des unit�s avant de lancer l'ost
				this.goals.addFirst(new MultiSoldierGoal(castleOrigin, realNbSoldier));
				return false;
			}
		}

		return this.goals.goal(castleOrigin);
	}

	/**
	 * @param  s Le type d'unit� qu'on veut.
	 * @return Retourne le nombre d'unit� de type s de la r�serve du ch�teau d'origine.
	 */
	private int getReserveSoldier(final SoldierEnum s)
	{
		return this.castleOrigin.getReserveOfSoldiers().getSoldierPack().get(s);
	}

	/**
	 *
	 * @param  s Le type d'unit� qu'on veut.
	 * @return Retourne le nombre d'unit� de type s de la caserne du ch�teau d'origine.
	 */
	private int getCaserneSoldier(final SoldierEnum s)
	{
		return this.castleOrigin.getCaserne().getSoldierPack().get(s);
	}
}
