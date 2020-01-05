package DukesOfTheRealm;

import static Utility.Settings.ARCHER_HP;
import static Utility.Settings.BERSERKER_HP;
import static Utility.Settings.KNIGHT_HP;
import static Utility.Settings.ONAGER_HP;
import static Utility.Settings.PIKER_HP;
import static Utility.Settings.SPY_HP;

import java.io.Serializable;

import Enums.BuildingEnum;
import Enums.SoldierEnum;
import Utility.SoldierPack;

/**
 * R�serve qui contient le nombre de chaque unit� pour un ch�teau.
 */
public class ReserveOfSoldiers implements Serializable
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	/**
	 * R�f�rence sur le ch�teau qui contient cette r�serve.
	 */
	private final Castle castle;

	/**
	 * Sp�cifie si les unit�s adverses doivent essayer de retirer des points de vie aux unit�s de la
	 * r�serve ou non.
	 */
	private boolean stopAttack = false;

	/**
	 * Nombre d'unit�, pour chaque type d'unit�, pr�sente dans cette r�serve.
	 */
	private final SoldierPack<Integer> soldierPack;

	/**
	 * Nombre de point de vie restant pour chaque type d'unit�.
	 */
	private final SoldierPack<Double> HPPack;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur de ReserveOfSoldier.
	 * 
	 * @param castle Le ch�teau qui contient cette r�serve.
	 */
	public ReserveOfSoldiers(final Castle castle)
	{
		this.castle = castle;
		this.soldierPack = new SoldierPack<>(0, 0, 0, 0, 0, 0);
		this.HPPack = new SoldierPack<>((PIKER_HP * this.castle.getWallMultiplicator()),
				(KNIGHT_HP * this.castle.getWallMultiplicator()), (ONAGER_HP * this.castle.getWallMultiplicator()),
				(ARCHER_HP * this.castle.getWallMultiplicator()), (BERSERKER_HP * this.castle.getWallMultiplicator()),
				(SPY_HP * this.castle.getWallMultiplicator()));
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	/**
	 * Retire al�atoirement un point de vie � un type d'unit�.
	 *
	 * @param typeForce Le type d'unit� qu iva perdre un point de vie, al�atoire la 1�re fois et forc�
	 *                  si on tombe sur un type d'unit� o� il y en a 0 dans le ch�teau attaqu�.
	 */
	public void randomRemoveHP(final SoldierEnum typeForce)
	{
		testRemoveHP();
		
		if (this.stopAttack)
		{
			return;
		}

		if (this.soldierPack.get(typeForce) > 0)
		{
			double HPRemaining = this.HPPack.get(typeForce);
			if (--HPRemaining <= 0)
			{
				soldierDeath(typeForce);
			}
			else
			{
				this.HPPack.replace(typeForce, HPRemaining);
			}
		}
		else
		{
			randomRemoveHP(SoldierEnum.getRandomTypeWithDefense());
		}
	}

	/**
	 * Teste si on peut encore retirer des points de vie (si il reste des unit�s dans la r�serve).
	 */
	private void testRemoveHP()
	{
		if (getTotal() <= 0)
		{
			this.stopAttack = true;
		}
	}
	
	/**
	 * 
	 * @param s
	 */
	private void soldierDeath(SoldierEnum s)
	{
		this.soldierPack.replace(s, this.soldierPack.get(s) - 1);
		this.castle.getMiller().addVillager(s.villager);
		this.HPPack.replace(s, s.HP * this.castle.getWallMultiplicator());
	}

	/**
	 * 
	 * @return
	 */
	private int getTotal()
	{
		int count = 0;
		for (int i : this.soldierPack.values())
		{
			count += i;
		}
		return count;
	}

	/**
	 * Retire, apr�s avoir lanc� une ost par exemple, un certain nombre d'unit� dans la r�serve.
	 *
	 * @param  soldierPack Le nombre d'unit� � retirer pour chaque type.
	 * @return             Retourne true si tout c'est bien pass�, retourne false si on ne peut pas
	 *                     retirer toutes les unit�s d�sir�es.
	 */
	public boolean removeSoldiers(final SoldierPack<Integer> s)
	{
		for (SoldierEnum sEnum : SoldierEnum.values())
		{
			if (this.soldierPack.get(sEnum) < s.get(sEnum))
			{
				return false;
			}
		}

		for (SoldierEnum sEnum : SoldierEnum.values())
		{
			replace(sEnum, this.soldierPack.get(sEnum) - s.get(sEnum));
		}

		return true;
	}

	/**
	 * R�active les attaques de cette r�serve.
	 */
	public void reactivateAttack()
	{
		this.stopAttack = false;
	}
	
	/**
	 * 
	 * @param reduction
	 */
	public void wallMultiplierReduction(final double reduction)
	{
		for (SoldierEnum s : SoldierEnum.values())
		{		
			final double currentHP = s.HP * this.castle.getWallMultiplicator();
			final double oldHP = s.HP * (this.castle.getWallMultiplicator() + reduction);

			this.HPPack.replace(s, this.HPPack.get(s) - (oldHP - currentHP));
			
			if(this.HPPack.get(s) <= 0)
			{
				soldierDeath(s);	
			}
		}

	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return the stopAttack
	 */
	public final boolean isStopAttack()
	{
		return this.stopAttack;
	}

	/**
	 * @param key   La cl�.
	 * @param value La valeur.
	 * @see         Utility.Pack#replace(java.lang.Object, java.lang.Object)
	 */
	public void replace(final SoldierEnum key, final Integer value)
	{
		this.soldierPack.replace(key, value);
	}

	/**
	 * @return the soldierPack
	 */
	public final SoldierPack<Integer> getSoldierPack()
	{
		return this.soldierPack;
	}
}
