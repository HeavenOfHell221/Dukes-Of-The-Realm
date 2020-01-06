package Soldiers;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Ost;
import Enums.SoldierEnum;
import Utility.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Repr�sente une unit� de type Archer.
 */
public class Archer extends Soldier
{
	/**
	 * Constructeur Archer
	 *
	 * @param layer  Le Pane pour afficher la r�pr�sentation graphique de ce soldat.
	 * @param coord  Les coordonn�es du soldat � son d�ploiement.
	 * @param itsOst La r�f�rence vers l'ost du soldat.
	 * @param speed  La vitesse de d�placement du soldat.
	 */
	public Archer(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Archer;
	}
	
	/**
	 * Constructeur par d�faut de Archer.
	 */
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
