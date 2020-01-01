package Soldiers;

import DukesOfTheRealm.Ost;
import DukesOfTheRealm.ReserveOfSoldiers;
import Enums.SoldierEnum;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe représentant un soldat de type Piquier.
 * 
 * @see Soldier
 */
public class Piker extends Soldier
{

	/**
	 * Constructeur Piker
	 * 
	 * @param layer  Le Pane pour afficher la réprésentation graphique de ce soldat.
	 * @param coord  Les coordonnées du soldat à son déploiement.
	 * @param itsOst La référence vers l'ost du soldat.
	 * @param speed  La vitesse de déplacement du soldat.
	 */
	public Piker(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Piker;
		this.stats = new Stats(speed, Settings.PIKER_HP, Settings.PIKER_DAMAGE);
	}

	/**
	 * Constructeur par défaut.
	 */
	public Piker()
	{
		super();
	}

	@Override
	public void Awake(final Color color)
	{
		AddPikerRepresentation();
		super.Awake(color);
	}

	@Override
	public double getProductionTime()
	{
		return Settings.PIKER_TIME_PRODUCTION;
	}

	@Override
	public int getProductionCost()
	{
		return Settings.PIKER_COST;
	}

	@Override
	public void addInReserve(final ReserveOfSoldiers reserve)
	{
		reserve.addPiker();
	}
}
