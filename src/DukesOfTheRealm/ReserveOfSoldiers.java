package DukesOfTheRealm;

import Utility.Settings;

public class ReserveOfSoldiers {

	private int nbPikers = 0;
	private int nbKnights = 0;
	private int nbOnagers = 0;
	private int pikersHPRemaining = Settings.PIKER_HP;
	private int knigtHPRemaining = Settings.KNIGHT_HP;
	private int onagerHPremaining = Settings.ONAGER_HP;
	
	public void AddPiker()
	{
		this.nbPikers++;
	}
	
	public void AddKnight()
	{
		this.nbKnights++;	
	}
	
	public void AddOnager()
	{
		this.nbOnagers++;
	}
	
	public void RemovePikerHP()
	{
		if(RemoveHP(this.nbPikers, this.pikersHPRemaining) && this.nbPikers > 0)
		{
			this.pikersHPRemaining = Settings.PIKER_HP;
		}
	}
	
	public void RemoveKnightHP()
	{
		if(RemoveHP(this.nbKnights, this.knigtHPRemaining) && this.nbKnights > 0)
		{
			this.knigtHPRemaining = Settings.KNIGHT_HP;
		}
	}
	
	public void RemoveOnagerHP()
	{
		if(RemoveHP(this.nbOnagers, this.onagerHPremaining) && this.nbOnagers > 0)
		{
			this.onagerHPremaining = Settings.ONAGER_HP;
		}
	}
	
	private boolean RemoveHP(int nb, int hp)
	{
		if(nb > 0)
		{
			if(hp > 0)
			{
				hp--;
				if(hp == 0)
				{
					nb--;
					return true;
				}
			}
		}
		return false;
	}

	public int getNbPikers() {
		return nbPikers;
	}

	public int getNbKnights() {
		return nbKnights;
	}

	public int getNbOnagers() {
		return nbOnagers;
	}

	public void setNbPikers(int nbPikers) {
		this.nbPikers = nbPikers;
	}

	public void setNbKnights(int nbKnights) {
		this.nbKnights = nbKnights;
	}

	public void setNbOnagers(int nbOnagers) {
		this.nbOnagers = nbOnagers;
	}
}
