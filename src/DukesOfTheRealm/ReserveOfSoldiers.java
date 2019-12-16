package DukesOfTheRealm;

import java.io.Serializable;
import java.util.Random;

import Enum.SoldierEnum;

import static Utility.Settings.*;

/**
 *
 *
 */
public class ReserveOfSoldiers implements Serializable
{
	private boolean stopAttack = false;
	private int nbPikers = 0;
	private int nbKnights = 0;
	private int nbOnagers = 0;
	private int pikersHPRemaining = PIKER_HP;
	private int knigtHPRemaining = KNIGHT_HP;
	private int onagerHPremaining = ONAGER_HP;
	private Random rand = new Random();
	
	/**
	 *
	 */
	public ReserveOfSoldiers()
	{

	}
	
	public void randomRemoveHP(SoldierEnum typeForce)
	{
		testRemoveHP();
		
		if(this.stopAttack) return;

		switch(typeForce)
		{
			case Knight:
				if(this.nbKnights > 0)
				{
					if(--this.knigtHPRemaining == 0)
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
				if(this.nbOnagers > 0)
				{
					if(--this.onagerHPremaining == 0)
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
				if(this.nbPikers > 0)
				{
					if(--this.pikersHPRemaining == 0)
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
	
	private void testRemoveHP()
	{
		boolean piker = false;
		boolean knight = false;
		boolean onager = false;
		
		if(this.nbKnights <= 0)
		{
			this.nbKnights = 0;
			knight = true;
		}
		if(this.nbPikers <= 0)
		{
			this.nbPikers = 0;
			piker = true;
		}
		if(this.nbOnagers <= 0)
		{
			this.nbOnagers = 0;
			onager = true;
		}
		
		if(piker && onager && knight)
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
	 * @param nbPikers
	 * @param nbKnights
	 * @param nbOnagers
	 * @return
	 */
	public boolean removeSoldiers(final int nbPikers, final int nbKnights, final int nbOnagers)
	{
		if(this.nbPikers < nbPikers || this.nbKnights < nbKnights || this.nbOnagers < nbOnagers)
			return false;
		
		this.nbKnights -= nbKnights;
		this.nbPikers -= nbPikers;
		this.nbOnagers -= nbOnagers;
		if(this.nbKnights < 0 || this.nbOnagers < 0 || this.nbPikers < 0)
			System.out.println("Reserve -> " + this.nbKnights + " " + this.nbOnagers + " " + this.nbPikers);
		return true;
	}

	public void reactivateAttack()
	{
		this.stopAttack = false;
	}

	/**
	 *
	 * @return
	 */
	public int getNbPikers()
	{
		return this.nbPikers;
	}

	/**
	 *
	 * @return
	 */
	public int getNbKnights()
	{
		return this.nbKnights;
	}

	/**
	 *
	 * @return
	 */
	public int getNbOnagers()
	{
		return this.nbOnagers;
	}

	/**
	 *
	 * @param nbPikers
	 */
	public void setNbPikers(final int nbPikers)
	{
		this.nbPikers = nbPikers;
	}

	/**
	 *
	 * @param nbKnights
	 */
	public void setNbKnights(final int nbKnights)
	{
		this.nbKnights = nbKnights;
	}

	/**
	 *
	 * @param nbOnagers
	 */
	public void setNbOnagers(final int nbOnagers)
	{
		this.nbOnagers = nbOnagers;
	}

	/**
	 *
	 * @return
	 */
	public boolean isStopAttack()
	{
		return this.stopAttack;
	}

	@Override
	public String toString()
	{
		return "ReserveOfSoldiers [stopAttack=" + this.stopAttack + ", nbPikers=" + this.nbPikers + ", nbKnights=" + this.nbKnights
				+ ", nbOnagers=" + this.nbOnagers + "]";
	}
}
