package DukesOfTheRealm;

import static Utility.Settings.KNIGHT_HP;
import static Utility.Settings.ONAGER_HP;
import static Utility.Settings.PIKER_HP;

import java.io.Serializable;
import java.util.Random;

import Enums.SoldierEnum;

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
	 * Nombre de Piker dans la r�serve.
	 */
	private int nbPikers = 0;

	/**
	 * Nombre de Knight dans la r�serve.
	 */
	private int nbKnights = 0;

	/**
	 * Nombre de Onager dans la r�serve.
	 */
	private int nbOnagers = 0;

	/**
	 * Nombre point de vie courant des Piker. Si �gale � 0, le ch�teau perd 1 Piker.
	 */
	private int pikersHPRemaining;

	/**
	 * Nombre point de vie courant des Knight. Si �gale � 0, le ch�teau perd 1 Knight.
	 */
	private int knigtHPRemaining;

	/**
	 * Nombre de point de vie courant des Onager. si �gale � 0, le ch�teau perd 1 Onager.
	 */
	private int onagerHPremaining;

	/**
	 * Object Random pour g�rer l'al�atoire.
	 */
	private final Random rand = new Random();
	
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
		this.knigtHPRemaining = (int) (KNIGHT_HP * this.castle.getWallMultiplicator());
		this.pikersHPRemaining = (int) (PIKER_HP * this.castle.getWallMultiplicator());
		this.knigtHPRemaining = (int) (ONAGER_HP * this.castle.getWallMultiplicator());
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

		switch (typeForce)
		{
			case Knight:
				if (this.nbKnights > 0)
				{
					if (--this.knigtHPRemaining == 0)
					{
						this.nbKnights--;
						this.knigtHPRemaining = (int) (KNIGHT_HP * this.castle.getWallMultiplicator());
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
						this.onagerHPremaining = (int) (ONAGER_HP * this.castle.getWallMultiplicator());
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
						this.pikersHPRemaining = (int) (PIKER_HP * this.castle.getWallMultiplicator());
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
	 * Teste si on peut encore retirer des points de vie (si il reste des unit�s dans la r�serve).
	 */
	private void testRemoveHP()
	{
		if (this.nbPikers <= 0 && this.nbKnights <= 0 && this.nbOnagers <= 0)
		{
			this.stopAttack = true;
		}
	}

	/**
	 * Ajoute un Piker � la r�serve.
	 */
	public void addPiker()
	{
		this.nbPikers++;
	}

	/**
	 * Ajoute un Knight � la r�serve.
	 */
	public void addKnight()
	{
		this.nbKnights++;
	}

	/**
	 * Ajoute un Onager � la r�serve.
	 */
	public void addOnager()
	{
		this.nbOnagers++;
	}

	/**
	 * Retire, apr�s avoir lanc� une ost par exemple, un certain nombre d'unit� dans la r�serve.
	 *
	 * @param  nbPikers  Le nombre de Piker � retirer.
	 * @param  nbKnights Le nombre de Knight � retirer.
	 * @param  nbOnagers Le nombre de Onager � retirer.
	 * @return           Retourne true si tout c'est bien pass�, retourne false si on ne peut pas
	 *                   retirer toutes les unit�s d�sir�es.
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
