package DukesOfTheRealm;

import java.io.Serializable;

import Utility.Settings;

public class ReserveOfSoldiers implements Serializable {

	private int nbPikers = 0;
	private int nbKnights = 0;
	private int nbOnagers = 0;
	private int pikersHPRemaining = Settings.PIKER_HP;
	private int knigtHPRemaining = Settings.KNIGHT_HP;
	private int onagerHPremaining = Settings.ONAGER_HP;
	
	public ReserveOfSoldiers()
	{
		
	}
	
	private boolean stopAttack = false;
	
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
	
	public void RandomRemoveHP(int x)
	{
		x = x % Settings.NB_TYPES_OF_TROOPS;
		
		if(stopAttack)
			return;
		
		switch(x)
		{
			case 0: 
				if(RemovePikerHP()) 
					break;
				else
					RandomRemoveHP(x + 1);
				break;
			case 1: 
				if(RemoveKnightHP())
					break;
				else
					RandomRemoveHP(x + 1);
				break;
			case 2: 
				if(RemoveOnagerHP())
					break;
				else
					RandomRemoveHP(x + 1);
				break;
				default:
					break;
		}
		
		SwitchActor();
	}
	
	private void SwitchActor()
	{
		if(this.nbKnights == 0 && this.nbPikers == 0 && this.nbOnagers == 0)
			this.stopAttack = true;
	}
	
	private boolean RemovePikerHP()
	{
		if(this.nbPikers > 0)
		{
			switch(this.pikersHPRemaining)
			{
				case 1:
					this.pikersHPRemaining = Settings.PIKER_HP;
					System.out.println("before " + this.nbPikers);
					this.nbPikers--;
					System.out.println("before " + this.nbPikers);
					break;
				default:
					this.pikersHPRemaining--;
					break;
			}
			return true;
		}
		return false;
	}
	
	private boolean RemoveKnightHP()
	{
		if(this.nbKnights > 0)
		{
			switch(this.knigtHPRemaining)
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
	
	private boolean RemoveOnagerHP()
	{
		if(this.nbOnagers > 0)
		{
			switch(this.onagerHPremaining)
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

	public int GetNbPikers() {
		return nbPikers;
	}

	public int GetNbKnights() {
		return nbKnights;
	}

	public int GetNbOnagers() {
		return nbOnagers;
	}

	public void SetNbPikers(int nbPikers) 
	{
		this.nbPikers = nbPikers;
	}

	public void SetNbKnights(int nbKnights) 
	{
		this.nbKnights = nbKnights;
	}

	public void SetNbOnagers(int nbOnagers) 
	{
		this.nbOnagers = nbOnagers;
	}
}
