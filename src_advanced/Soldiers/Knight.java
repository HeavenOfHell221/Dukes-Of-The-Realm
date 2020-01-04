package Soldiers;

import DukesOfTheRealm.Caserne;
import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Ost;
import DukesOfTheRealm.ReserveOfSoldiers;
import Enums.SoldierEnum;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe repr�sentant un soldat de type Chevalier.
 *
 * @see Soldier
 */
public class Knight extends Soldier
{

	/**
	 * Constructeur Knight
	 *
	 * @param layer  Le Pane pour afficher la r�pr�sentation graphique de ce soldat.
	 * @param coord  Les coordonn�es du soldat � son d�ploiement.
	 * @param itsOst La r�f�rence vers l'ost du soldat.
	 * @param speed  La vitesse de d�placement du soldat.
	 */
	public Knight(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Knight;
	}

	/**
	 * Constructeur par d�faut.
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