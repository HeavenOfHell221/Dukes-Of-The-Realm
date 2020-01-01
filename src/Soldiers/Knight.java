package Soldiers;

import DukesOfTheRealm.Caserne;
import DukesOfTheRealm.Ost;
import DukesOfTheRealm.ReserveOfSoldiers;
import Enum.SoldierEnum;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe représentant un soldat de type Chevalier.
 * @see Soldier
 */
public class Knight extends Soldier
{

	/**
	 * Constructeur Knight
	 * @param layer Le Pane pour afficher la réprésentation graphique de ce soldat.
	 * @param coord Les coordonnées du soldat à son déploiement.
	 * @param itsOst La référence vers l'ost du soldat.
	 * @param speed La vitesse de déplacement du soldat.
	 */
	public Knight(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Knight;
		this.stats = new Stats(speed, Settings.KNIGHT_HP, Settings.KNIGHT_DAMAGE);
	}

	/**
	 * Constructeur par défaut.
	 */
	public Knight()
	{
		super();
	}

	@Override
	public void Awake(final Color color)
	{
		AddKnightRepresentation();
		super.Awake(color);
	}

	@Override
	public double getProductionTime()
	{
		return Settings.KNIGHT_TIME_PRODUCTION;
	}

	@Override
	public int getProductionCost()
	{
		return Settings.KNIGHT_COST;
	}

	@Override
	public void addInReserve(final ReserveOfSoldiers reserve)
	{
		reserve.addKnight();
	}
}
