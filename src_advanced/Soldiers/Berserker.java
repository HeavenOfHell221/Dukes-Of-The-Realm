package Soldiers;

import DukesOfTheRealm.Ost;
import Enums.SoldierEnum;
import Utility.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Représente une unité de type Berserker.
 */
public class Berserker extends Soldier
{
	/**
	 * Constructeur Berserker
	 *
	 * @param layer  Le Pane pour afficher la réprésentation graphique de ce soldat.
	 * @param coord  Les coordonnées du soldat à son déploiement.
	 * @param itsOst La référence vers l'ost du soldat.
	 * @param speed  La vitesse de déplacement du soldat.
	 */
	public Berserker(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Berserker;
	}
	
	/**
	 * Constructeur par défaut de Berserker.
	 */
	public Berserker()
	{
		super();
		this.type = SoldierEnum.Berserker;
	}

	@Override
	public void Awake(final Color color)
	{
		AddBerserkerRepresentation();
		super.Awake(color);
	}
}
