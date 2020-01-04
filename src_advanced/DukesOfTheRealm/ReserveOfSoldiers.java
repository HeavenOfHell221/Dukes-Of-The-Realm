package DukesOfTheRealm;

import static Utility.Settings.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

import Enums.SoldierEnum;
import Interface.IBuilding;
import Utility.SoldierPack;

/**
 * Réserve qui contient le nombre de chaque unité pour un château.
 */
public class ReserveOfSoldiers implements Serializable
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Référence sur le château qui contient cette réserve.
	 */
	private Castle castle;
	
	/**
	 * Spécifie si les unités adverses doivent essayer de retirer des points de vie aux unités de la
	 * réserve ou non.
	 */
	private boolean stopAttack = false;

	/**
	 * Nombre d'unité, pour chaque type d'unité, présente dans cette réserve.
	 */
	private SoldierPack<Integer> soldierPack;
	
	/**
	 * Nombre de point de vie restant pour chaque type d'unité.
	 */
	private SoldierPack<Integer> HPPack;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur de ReserveOfSoldier.
	 * @param castle Le château qui contient cette réserve.
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
	 * Retire aléatoirement un point de vie à un type d'unité.
	 *
	 * @param typeForce Le type d'unité qu iva perdre un point de vie, aléatoire la 1ère fois et forcé
	 *                  si on tombe sur un type d'unité où il y en a 0 dans le château attaqué.
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
	 * Teste si on peut encore retirer des points de vie (si il reste des unités dans la réserve).
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
	 * Retire, après avoir lancé une ost par exemple, un certain nombre d'unité dans la réserve.
	 *
	 * @param  soldierPack Le nombre d'unité à retirer pour chaque type.
	 * @return           Retourne true si tout c'est bien passé, retourne false si on ne peut pas
	 *                   retirer toutes les unités désirées.
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
	 * Réactive les attaques de cette réserve.
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
	 * @param key La clé.
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
