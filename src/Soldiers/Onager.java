package Soldiers;

import DukesOfTheRealm.Ost;
import DukesOfTheRealm.ReserveOfSoldiers;
import Enums.SoldierEnum;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe représentant un soldat de type Catapulte.
 * 
 * @see Soldier
 */
public class Onager extends Soldier
{
	/**
	 * Constructeur Onager
	 * 
	 * @param layer  Le Pane pour afficher la réprésentation graphique de ce soldat.
	 * @param coord  Les coordonnées du soldat à son déploiement.
	 * @param itsOst La référence vers l'ost du soldat.
	 * @param speed  La vitesse de déplacement du soldat.
	 */
	public Onager(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Onager;
		this.stats = new Stats(speed, Settings.ONAGER_HP, Settings.ONAGER_DAMAGE);
	}

	/**
	 * Constructeur par défaut.
	 */
	public Onager()
	{
		super();
	}

	@Override
	public void Awake(final Color color)
	{
		AddOnagerRepresentation();
		super.Awake(color);
	}

	@Override
	public double getProductionTime()
	{
		return Settings.ONAGER_TIME_PRODUCTION;
	}

	@Override
	public int getProductionCost()
	{
		return Settings.ONAGER_COST;
	}

	@Override
	public void addInReserve(final ReserveOfSoldiers reserve)
	{
		reserve.addOnager();
	}
}
