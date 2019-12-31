package DukesOfTheRealm;

import static Utility.Settings.KNIGHT_HP;
import static Utility.Settings.ONAGER_HP;
import static Utility.Settings.PIKER_HP;

import java.io.Serializable;
import java.util.Random;

import Enum.SoldierEnum;

/**
 * Réserve qui contient le nombre de chaque unité pour un château.
 */
public class ReserveOfSoldiers implements Serializable
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Spécifie si les unités adverses doivent essayer de retirer des points de vie aux unités de la
	 * réserve ou non.
	 */
	private boolean stopAttack = false;

	/**
	 * Nombre de Piker dans la réserve.
	 */
	private int nbPikers = 0;

	/**
	 * Nombre de Knight dans la réserve.
	 */
	private int nbKnights = 0;

	/**
	 * Nombre de Onager dans la réserve.
	 */
	private int nbOnagers = 0;

	/**
	 * Nombre point de vie courant des Piker. Si égale à 0, le château perd 1 Piker.
	 */
	private int pikersHPRemaining = PIKER_HP;

	/**
	 * Nombre point de vie courant des Knight. Si égale à 0, le château perd 1 Knight.
	 */
	private int knigtHPRemaining = KNIGHT_HP;

	/**
	 * Nombre de point de vie courant des Onager. si égale à 0, le château perd 1 Onager.
	 */
	private int onagerHPremaining = ONAGER_HP;

	/**
	 * Object Random pour gérer l'aléatoire.
	 */
	private final Random rand = new Random();

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

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

		switch (typeForce)
		{
			case Knight:
				if (this.nbKnights > 0)
				{
					if (--this.knigtHPRemaining == 0)
					{
						this.nbKnights--;
						this.knigtHPRemaining = KNIGHT_HP;
					}
				}
				else
				{
					randomRemoveHP(SoldierEnum.Onager);
				}
				break;
			case Onager:
				if (this.nbOnagers > 0)
				{
					if (--this.onagerHPremaining == 0)
					{
						this.nbOnagers--;
						this.onagerHPremaining = ONAGER_HP;
					}
				}
				else
				{
					randomRemoveHP(SoldierEnum.Piker);
				}
				break;
			case Piker:
				if (this.nbPikers > 0)
				{
					if (--this.pikersHPRemaining == 0)
					{
						this.nbPikers--;
						this.pikersHPRemaining = PIKER_HP;
					}
				}
				else
				{
					randomRemoveHP(SoldierEnum.Knight);
				}
				break;
			default:
				break;

		}
	}

	/**
	 * Teste si on peut encore retirer des points de vie (si il reste des unités dans la réserve).
	 */
	private void testRemoveHP()
	{
		boolean piker = this.nbPikers <= 0;
		boolean knight = this.nbKnights <= 0;
		boolean onager = this.nbOnagers <= 0;

		if (piker && onager && knight)
		{
			this.stopAttack = true;
		}
	}

	/**
	 * Ajoute un Piker à la réserve.
	 */
	public void addPiker()
	{
		this.nbPikers++;
	}

	/**
	 * Ajoute un Knight à la réserve.
	 */
	public void addKnight()
	{
		this.nbKnights++;
	}

	/**
	 * Ajoute un Onager à la réserve.
	 */
	public void addOnager()
	{
		this.nbOnagers++;
	}

	/**
	 * Retire, après avoir lancé une ost par exemple, un certain nombre d'unité dans la réserve.
	 * 
	 * @param  nbPikers  Le nombre de Piker à retirer.
	 * @param  nbKnights Le nombre de Knight à retirer.
	 * @param  nbOnagers Le nombre de Onager à retirer.
	 * @return           Retourne true si tout c'est bien passé, retourne false si on ne peut pas
	 *                   retirer toutes les unités désirées.
	 */
	public boolean removeSoldiers(final int nbPikers, final int nbKnights, final int nbOnagers)
	{
		if (this.nbPikers < nbPikers || this.nbKnights < nbKnights || this.nbOnagers < nbOnagers)
		{
			return false;
		}

		this.nbKnights -= nbKnights;
		this.nbPikers -= nbPikers;
		this.nbOnagers -= nbOnagers;

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
	 * @return the nbPikers
	 */
	public final int getNbPikers()
	{
		return this.nbPikers;
	}

	/**
	 * @return the stopAttack
	 */
	public final boolean isStopAttack()
	{
		return this.stopAttack;
	}

	/**
	 * @return the nbKnights
	 */
	public final int getNbKnights()
	{
		return this.nbKnights;
	}

	/**
	 * @return the nbOnagers
	 */
	public final int getNbOnagers()
	{
		return this.nbOnagers;
	}

	/**
	 * @param nbPikers the nbPikers to set
	 */
	public final void setNbPikers(final int nbPikers)
	{
		this.nbPikers = nbPikers;
	}

	/**
	 * @param nbKnights the nbKnights to set
	 */
	public final void setNbKnights(final int nbKnights)
	{
		this.nbKnights = nbKnights;
	}

	/**
	 * @param nbOnagers the nbOnagers to set
	 */
	public final void setNbOnagers(final int nbOnagers)
	{
		this.nbOnagers = nbOnagers;
	}
}
