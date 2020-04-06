package Soldiers;

import DukesOfTheRealm.Ost;
import Enums.SoldierEnum;
import Utility.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe représentant une unité de type Chevalier.
 *
 * @see Soldier
 */
public class Knight extends Soldier
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7623963236950027731L;

	/**
	 * Constructeur Knight
	 *
	 * @param layer  Le Pane pour afficher la réprésentation graphique de ce soldat.
	 * @param coord  Les coordonnées du soldat à son déploiement.
	 * @param itsOst La référence vers l'ost du soldat.
	 * @param speed  La vitesse de déplacement du soldat.
	 */
	public Knight(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Knight;
	}

	/**
	 * Constructeur par défaut.
	 */
	public Knight()
	{
		super();
		this.type = SoldierEnum.Knight;
	}

	@Override
	public void Awake(final Color color)
	{
		AddKnightRepresentation();
		super.Awake(color);
	}
}
