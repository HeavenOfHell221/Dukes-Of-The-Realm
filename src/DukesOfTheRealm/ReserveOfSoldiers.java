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
		nbPikers++;
	}

	public void AddKnight()
	{
		nbKnights++;
	}

	public void AddOnager()
	{
		nbOnagers++;
	}

	public void RandomRemoveHP(int x)
	{
		x = x % Settings.NB_TYPES_OF_TROOPS;

		if(stopAttack) {
			return;
		}

		switch(x)
		{
		case 0:
			if(RemovePikerHP()) {
				break;
			} else {
				RandomRemoveHP(x + 1);
			}
			break;
		case 1:
			if(RemoveKnightHP()) {
				break;
			} else {
				RandomRemoveHP(x + 1);
			}
			break;
		case 2:
			if(RemoveOnagerHP()) {
				break;
			} else {
				RandomRemoveHP(x + 1);
			}
			break;
		default:
			break;
		}

		SwitchActor();
	}

	private void SwitchActor()
	{
		if(nbKnights == 0 && nbPikers == 0 && nbOnagers == 0) {
			stopAttack = true;
		}
	}

	private boolean RemovePikerHP()
	{
		if(nbPikers > 0)
		{
			switch(pikersHPRemaining)
			{
			case 1:
				pikersHPRemaining = Settings.PIKER_HP;
				nbPikers--;
				break;
			default:
				pikersHPRemaining--;
				break;
			}
			return true;
		}
		return false;
	}

	private boolean RemoveKnightHP()
	{
		if(nbKnights > 0)
		{
			switch(knigtHPRemaining)
			{
			case 1:
				knigtHPRemaining = Settings.KNIGHT_HP;
				nbKnights--;
				break;
			default:
				knigtHPRemaining--;
				break;
			}
			return true;
		}
		return false;
	}

	private boolean RemoveOnagerHP()
	{
		if(nbOnagers > 0)
		{
			switch(onagerHPremaining)
			{
			case 1:
				onagerHPremaining = Settings.ONAGER_HP;
				nbOnagers--;
				break;
			default:
				onagerHPremaining--;
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

	public void SetNbPikers(final int nbPikers)
	{
		this.nbPikers = nbPikers;
	}

	public void SetNbKnights(final int nbKnights)
	{
		this.nbKnights = nbKnights;
	}

	public void SetNbOnagers(final int nbOnagers)
	{
		this.nbOnagers = nbOnagers;
	}
}
