package DukesOfTheRealm;

import static Utility.Settings.KNIGHT_HP;
import static Utility.Settings.ONAGER_HP;
import static Utility.Settings.PIKER_HP;

import java.io.Serializable;
import java.util.Random;

import Enum.SoldierEnum;

/**
 *
 *
 */
public class ReserveOfSoldiers implements Serializable
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 *
	 */
	private boolean stopAttack = false;

	/**
	 *
	 */
	private int nbPikers = 0;

	/**
	 *
	 */
	private int nbKnights = 0;

	/**
	 *
	 */
	private int nbOnagers = 0;

	/**
	 *
	 */
	private int pikersHPRemaining = PIKER_HP;

	/**
	 *
	 */
	private int knigtHPRemaining = KNIGHT_HP;

	/**
	 *
	 */
	private int onagerHPremaining = ONAGER_HP;

	/**
	 *
	 */
	private final Random rand = new Random();

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 *
	 */
	public ReserveOfSoldiers()
	{

	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 *
	 * @param typeForce
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
	 *
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
	 *
	 */
	public void addPiker()
	{
		this.nbPikers++;
	}

	/**
	 *
	 */
	public void addKnight()
	{
		this.nbKnights++;
	}

	/**
	 *
	 */
	public void addOnager()
	{
		this.nbOnagers++;
	}

	/**
	 *
	 * @param  nbPikers
	 * @param  nbKnights
	 * @param  nbOnagers
	 * @return
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
		if (this.nbKnights < 0 || this.nbOnagers < 0 || this.nbPikers < 0)
		{
			System.out.println("Reserve -> " + this.nbKnights + " " + this.nbOnagers + " " + this.nbPikers);
		}
		return true;
	}

	/**
	 *
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
