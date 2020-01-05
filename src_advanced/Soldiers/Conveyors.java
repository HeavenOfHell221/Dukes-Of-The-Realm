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
	 * @param layer  Le Pane pour afficher la r�pr�sentation graphique de ce soldat.
	 * @param coord  Les coordonn�es du soldat � son d�ploiement.
	 * @param itsOst La r�f�rence vers l'ost du soldat.
	 * @param speed  La vitesse de d�placement du soldat.
	 */
	public Conveyors(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Conveyors;
	}

	/**
	 * Constructeur par d�faut.
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
