package Soldiers;

import DukesOfTheRealm.Ost;
import Enums.SoldierEnum;
import Utility.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe représentant une unité de type Piquier.
 *
 * @see Soldier
 */
public class Piker extends Soldier
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1963637020994006321L;

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
	}

	/**
	 * Constructeur par défaut.
	 */
	public Piker()
	{
		super();
		this.type = SoldierEnum.Piker;
	}

	@Override
	public void Awake(final Color color)
	{
		AddPikerRepresentation();
		super.Awake(color);
	}
}
