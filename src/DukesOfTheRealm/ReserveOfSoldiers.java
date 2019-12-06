package DukesOfTheRealm;

import java.io.Serializable;

import Utility.Settings;

public class ReserveOfSoldiers implements Serializable
{

	private int nbPikers = 0;
	private int nbKnights = 0;
	private int nbOnagers = 0;
	private transient int pikersHPRemaining = Settings.PIKER_HP;
	private transient int knigtHPRemaining = Settings.KNIGHT_HP;
	private transient int onagerHPremaining = Settings.ONAGER_HP;

	public ReserveOfSoldiers()
	{

	}

	private boolean stopAttack = false;

	public void addPiker()
	{
		this.nbPikers++;
	}

	public void addKnight()
	{
		this.nbKnights++;
	}

	public void addOnager()
	{
		this.nbOnagers++;
	}

	public void randomRemoveHP(int x)
	{
		x = x % Settings.NB_TYPES_OF_TROOPS;

		if (this.stopAttack)
		{
			return;
		}

		switch (x)
		{
			case 0:
				if (removePikerHP())
				{
					break;
				}
				else
				{
					randomRemoveHP(x + 1);
				}
				break;
			case 1:
				if (removeKnightHP())
				{
					break;
				}
				else
				{
					randomRemoveHP(x + 1);
				}
				break;
			case 2:
				if (removeOnagerHP())
				{
					break;
				}
				else
				{
					randomRemoveHP(x + 1);
				}
				break;
			default:
				break;
		}

		switchActor();
	}

	private void switchActor()
	{
		if (this.nbKnights == 0 && this.nbPikers == 0 && this.nbOnagers == 0)
		{
			this.stopAttack = true;
		}
	}

	private boolean removePikerHP()
	{
		if (this.nbPikers > 0)
		{
			switch (this.pikersHPRemaining)
			{
				case 1:
					this.pikersHPRemaining = Settings.PIKER_HP;
					this.nbPikers--;
					break;
				default:
					this.pikersHPRemaining--;
					break;
			}
			return true;
		}
		return false;
	}

	private boolean removeKnightHP()
	{
		if (this.nbKnights > 0)
		{
			switch (this.knigtHPRemaining)
			{
				case 1:
					this.knigtHPRemaining = Settings.KNIGHT_HP;
					this.nbKnights--;
					break;
				default:
					this.knigtHPRemaining--;
					break;
			}
			return true;
		}
		return false;
	}

	private boolean removeOnagerHP()
	{
		if (this.nbOnagers > 0)
		{
			switch (this.onagerHPremaining)
			{
				case 1:
					this.onagerHPremaining = Settings.ONAGER_HP;
					this.nbOnagers--;
					break;
				default:
					this.onagerHPremaining--;
					break;
			}
			return true;
		}
		return false;
	}

	public int getNbPikers()
	{
		return this.nbPikers;
	}

	public int getNbKnights()
	{
		return this.nbKnights;
	}

	public int getNbOnagers()
	{
		return this.nbOnagers;
	}

	public void setNbPikers(final int nbPikers)
	{
		this.nbPikers = nbPikers;
	}

	public void setNbKnights(final int nbKnights)
	{
		this.nbKnights = nbKnights;
	}

	public void setNbOnagers(final int nbOnagers)
	{
		this.nbOnagers = nbOnagers;
	}
}
