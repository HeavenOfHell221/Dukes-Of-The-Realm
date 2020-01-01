package Soldiers;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Ost;
import DukesOfTheRealm.ReserveOfSoldiers;
import Enums.SoldierEnum;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe repr�sentant un soldat de type Catapulte.
 *
 * @see Soldier
 */
public class Onager extends Soldier
{
	/**
	 * Constructeur Onager
	 *
	 * @param layer  Le Pane pour afficher la r�pr�sentation graphique de ce soldat.
	 * @param coord  Les coordonn�es du soldat � son d�ploiement.
	 * @param itsOst La r�f�rence vers l'ost du soldat.
	 * @param speed  La vitesse de d�placement du soldat.
	 */
	public Onager(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Onager;
		this.stats = new Stats(speed, Settings.ONAGER_HP, Settings.ONAGER_DAMAGE);
	}

	/**
	 * Constructeur par d�faut.
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
	public int getProductionCost(final Castle castle)
	{
		return Settings.ONAGER_COST;
	}

	@Override
	public void addInReserve(final ReserveOfSoldiers reserve)
	{
		reserve.addOnager();
	}

	@Override
	public void productionFinished(final Castle castle)
	{
		castle.addOnager();
		castle.getCaserne().nbOnagersInProduction--;
	}

	@Override
	public void productionStart(final Castle castle)
	{
		castle.getCaserne().nbOnagersInProduction++;
	}
}
