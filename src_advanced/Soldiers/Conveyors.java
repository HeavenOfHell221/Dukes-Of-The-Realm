package Soldiers;

import DukesOfTheRealm.Ost;
import Enums.SoldierEnum;
import Utility.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Conveyors extends Soldier
{

	/**
	 * Constructeur Conveyors
	 *
	 * @param layer  Le Pane pour afficher la réprésentation graphique de ce soldat.
	 * @param coord  Les coordonnées du soldat à son déploiement.
	 * @param itsOst La référence vers l'ost du soldat.
	 * @param speed  La vitesse de déplacement du soldat.
	 */
	public Conveyors(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Conveyors;
	}

	/**
	 * Constructeur par défaut.
	 */
	public Conveyors()
	{
		super();
		this.type = SoldierEnum.Conveyors;
	}

	@Override
	public void Awake(final Color color)
	{
		AddConveyorRepresentation();
		super.Awake(color);
	}
}
