package DukesOfTheRealm;

import static Utility.Settings.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

import Enums.SoldierEnum;
import Interface.IBuilding;
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
	private Castle castle;
	
	/**
	 * Sp�cifie si les unit�s adverses doivent essayer de retirer des points de vie aux unit�s de la
	 * r�serve ou non.
	 */
	private boolean stopAttack = false;

	/**
	 * Nombre d'unit�, pour chaque type d'unit�, pr�sente dans cette r�serve.
	 */
	private SoldierPack<Integer> soldierPack;
	
	/**
	 * Nombre de point de vie restant pour chaque type d'unit�.
	 */
	private SoldierPack<Integer> HPPack;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur de ReserveOfSoldier.
	 * @param castle Le ch�teau qui contient cette r�serve.
	 */
	public ReserveOfSoldiers(Castle castle)
	{
		this.castle = castle;
		this.soldierPack = new SoldierPack<Integer>(0, 0, 0, 0, 0, 0);
		this.HPPack = new SoldierPack<Integer>(
				(int) (PIKER_HP * this.castle.getWallMultiplicator()), 
				(int) (KNIGHT_HP * this.castle.getWallMultiplicator()), 
				(int) (ONAGER_HP * this.castle.getWallMultiplicator()), 
				(int) (ARCHER_HP * this.castle.getWallMultiplicator()), 
				(int) (BERSERKER_HP * this.castle.getWallMultiplicator()), 
				(int) (SPY_HP * this.castle.getWallMultiplicator()));
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	public void start()
	{
		
	}
	
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
		
		if(this.soldierPack.get(typeForce) > 0)
		{
			int HPRemaining = this.HPPack.get(typeForce);
			if(--HPRemaining == 0)
			{
				this.soldierPack.replace(typeForce, this.soldierPack.get(typeForce) - 1);
				this.HPPack.replace(typeForce, (int)(typeForce.HP * this.castle.getWallMultiplicator()));
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
	
	private int getTotal()
	{
		int count = 0;
		for(int i : soldierPack.values())
		{
			count += i;
		}
		return count;
	}

	/**
	 * Retire, apr�s avoir lanc� une ost par exemple, un certain nombre d'unit� dans la r�serve.
	 *
	 * @param  soldierPack Le nombre d'unit� � retirer pour chaque type.
	 * @return           Retourne true si tout c'est bien pass�, retourne false si on ne peut pas
	 *                   retirer toutes les unit�s d�sir�es.
	 */
	public boolean removeSoldiers(SoldierPack<Integer> s)
	{
		for(SoldierEnum sEnum : SoldierEnum.values())
		{
			if(soldierPack.get(sEnum) < s.get(sEnum))
			{
				return false;
			}
		}
		
		for(SoldierEnum sEnum : SoldierEnum.values())
		{
			replace(sEnum, soldierPack.get(sEnum) - s.get(sEnum));
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
	 * @param key La cl�.
	 * @param value La valeur.
	 * @see Utility.Pack#replace(java.lang.Object, java.lang.Object)
	 */
	public void replace(SoldierEnum key, Integer value)
	{
		soldierPack.replace(key, value);
	}

	/**
	 * @return the soldierPack
	 */
	public final SoldierPack<Integer> getSoldierPack()
	{
		return soldierPack;
	}
}
