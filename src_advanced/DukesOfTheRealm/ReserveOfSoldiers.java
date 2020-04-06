package DukesOfTheRealm;

import static Utility.Settings.ARCHER_HP;
import static Utility.Settings.BERSERKER_HP;
import static Utility.Settings.KNIGHT_HP;
import static Utility.Settings.ONAGER_HP;
import static Utility.Settings.PIKER_HP;
import static Utility.Settings.SPY_HP;

import java.io.Serializable;

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
	 * 
	 */
	private static final long serialVersionUID = -6076305320392608249L;

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
		this.soldierPack = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);
		this.HPPack = new SoldierPack<>(PIKER_HP * this.castle.getWallMultiplicator(), KNIGHT_HP * this.castle.getWallMultiplicator(),
				ONAGER_HP * this.castle.getWallMultiplicator(), ARCHER_HP * this.castle.getWallMultiplicator(),
				BERSERKER_HP * this.castle.getWallMultiplicator(), SPY_HP * this.castle.getWallMultiplicator(), 0d);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * Retire al�atoirement un point de vie � un type d'unit�.
	 *
	 * @param typeForce Le type d'unit� qui va perdre un point de vie.
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
	 * Teste si on peut encore retirer des points de vie (s'il reste des unit�s dans la r�serve).
	 */
	private void testRemoveHP()
	{
		if (getTotal() <= 0)
		{
			this.stopAttack = true;
		}
	}

	/**
	 * Met � jour la r�serne dans le cas o� un type d'unit� meurt.
	 * @param s Le type d'unit� qui perd une unit�.
	 */
	private void soldierDeath(final SoldierEnum s)
	{
		if (s == SoldierEnum.Conveyors)
		{
			return;
		}

		this.soldierPack.replace(s, this.soldierPack.get(s) - 1);
		this.castle.getMiller().addVillager(s.villager);
		this.HPPack.replace(s, s.HP * this.castle.getWallMultiplicator());
	}

	/**
	 * @return Retourne le nombre total d'unti� dans cette r�serve.
	 */
	private int getTotal()
	{
		int count = 0;
		for (SoldierEnum s : SoldierEnum.values())
		{
			if (s != SoldierEnum.Conveyors)
			{
				count += this.soldierPack.get(s);
			}
		}
		return count;
	}

	/**
	 * Retire, apr�s avoir lanc� une ost par exemple, un certain nombre d'unit� dans la r�serve.
	 *
	 * @param  s Le nombre d'unit� � retirer pour chaque type.
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
	 * Recalcul les points de vie des unit�s dans le cas o� le niveau du rempart est baiss� par des catapultes.
	 * @param reduction La r�duction du multiplicateur du rempart
	 * @see Wall#multiplier
	 */
	public void wallMultiplierReduction(final double reduction)
	{
		for (SoldierEnum s : SoldierEnum.values())
		{
			final double currentHP = s.HP * this.castle.getWallMultiplicator();
			final double oldHP = s.HP * (this.castle.getWallMultiplicator() + reduction);

			this.HPPack.replace(s, this.HPPack.get(s) - (oldHP - currentHP));

			if (this.HPPack.get(s) <= 0)
			{
				soldierDeath(s);
			}
		}

	}
	
	/**
	 * @param key   La cl�.
	 * @param value La nouvelle valeur.
	 * @see         Utility.Pack#replace(java.lang.Object, java.lang.Object)
	 */
	public void replace(final SoldierEnum key, final Integer value)
	{
		this.soldierPack.replace(key, value);
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
	 * @return the soldierPack
	 */
	public final SoldierPack<Integer> getSoldierPack()
	{
		return this.soldierPack;
	}
}
