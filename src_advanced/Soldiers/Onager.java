package Soldiers;

import java.util.Random;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Ost;
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
	}

	/**
	 * Constructeur par d�faut.
	 */
	public Onager()
	{
		super();
		this.type = SoldierEnum.Onager;
	}

	@Override
	public void Awake(final Color color)
	{
		AddOnagerRepresentation();
		super.Awake(color);
	}
	
	@Override
	protected void applyDamage(Castle destination)
	{
		Random rand = new Random();
		
		if(rand.nextFloat() <= Settings.ONAGER_WALL - 0.013d * destination.getWall().getLevel())
		{
			destination.getReserveOfSoldiers().wallMultiplierReduction(destination.getWall().decreaseLevel());
		}
		
		super.applyDamage(destination);
	}
}
