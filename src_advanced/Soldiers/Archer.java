package Soldiers;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Ost;
import Enums.SoldierEnum;
import Utility.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Archer extends Soldier
{
	/**
	 * Constructeur Archer
	 *
	 * @param layer  Le Pane pour afficher la réprésentation graphique de ce soldat.
	 * @param coord  Les coordonnées du soldat à son déploiement.
	 * @param itsOst La référence vers l'ost du soldat.
	 * @param speed  La vitesse de déplacement du soldat.
	 */
	public Archer(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Archer;
	}
	
	public Archer()
	{
		super();
		this.type = SoldierEnum.Archer;
	}

	@Override
	public void Awake(final Color color)
	{
		AddArcherRepresentation();
		super.Awake(color);
	}
}
