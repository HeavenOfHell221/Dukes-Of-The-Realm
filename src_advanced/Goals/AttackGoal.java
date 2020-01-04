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
	 * @param soldierPack Contient le nombre d'unit� dans l'ost.
	 * @see                     MultiSoldierGoal
	 * @see                     SimpleGoal.SendOstGoal
	 */
	public AttackGoal(final Castle castleOrigin, final Castle castleDestination, SoldierPack<Integer> soldierPack)
	{
		this.goals = new GenericGoal();
		this.castleOrigin = castleOrigin;
		this.soldierPack = soldierPack;
		this.castleDestination = castleDestination;

		if (castleOrigin.getOst() != null)
		{
			return;
		}
		
		SoldierPack<Integer> nbSoldier = new SoldierPack<Integer>();
		SoldierPack<Integer> realNbSoldier = new SoldierPack<Integer>();
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			nbSoldier.replace(s, getReserveSoldier(s) + getCaserneSoldier(s));
		}
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			realNbSoldier.replace(s, nbSoldier.get(s) < soldierPack.get(s) ? (soldierPack.get(s) - nbSoldier.get(s)) : 0);
		}

		int count = 0;
		for(int i : realNbSoldier.values())
		{
			count += i;
		}
		
		if (count > 0)
		{
			this.goals.addLast(new MultiSoldierGoal(this.castleOrigin, realNbSoldier));
		}
		
		this.goals.addLast(new SendOstGoal(castleDestination, soldierPack));
	}

	@Override
	public boolean goal(final Castle castleOrigin)
	{
		// Au moment de lancer l'ost
		if (this.goals.size() == 1)
		{
			SoldierPack<Integer> nbSoldier = new SoldierPack<Integer>();
			
			for(SoldierEnum s : SoldierEnum.values())
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
			
			for(SoldierEnum s : SoldierEnum.values())
			{
				if(nbSoldier.get(s) < soldierPack.get(s))
				{
					canSendOst = false;
				}
			}

			// Si nous n'avons plus assez d'unit�s
			if (!canSendOst)
			{
				SoldierPack<Integer> realNbSoldier = new SoldierPack<Integer>();
				
				for(SoldierEnum s : SoldierEnum.values())
				{
					realNbSoldier.replace(s, nbSoldier.get(s) < soldierPack.get(s) ? (soldierPack.get(s) - nbSoldier.get(s)) : 0);
				}

				// On reproduit des unit�s avant de lancer l'ost
				this.goals.addFirst(new MultiSoldierGoal(castleOrigin, realNbSoldier));
				return false;
			}
		}

		return this.goals.goal(castleOrigin);
	}
	
	/**
	 * 
	 * @return
	 */
	private SoldierPack<Integer> getReserveSoldierPack()
	{
		return this.castleOrigin.getReserveOfSoldiers().getSoldierPack();
	}
	
	/**
	 * 
	 * @return
	 */
	private SoldierPack<Integer> getCaserneSoldierPack()
	{
		return this.castleOrigin.getCaserne().getSoldierPack();
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	private int getReserveSoldier(SoldierEnum s)
	{
		return getReserveSoldierPack().get(s);
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	private int getCaserneSoldier(SoldierEnum s)
	{
		return getCaserneSoldierPack().get(s);
	}
}
