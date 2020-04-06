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
 * Classe représentant une unité de type Catapulte.
 *
 * @see Soldier
 */
public class Onager extends Soldier
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1008642104658799238L;

	/**
	 * Constructeur Onager
	 *
	 * @param layer  Le Pane pour afficher la réprésentation graphique de ce soldat.
	 * @param coord  Les coordonnées du soldat à son déploiement.
	 * @param itsOst La référence vers l'ost du soldat.
	 * @param speed  La vitesse de déplacement du soldat.
	 */
	public Onager(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Onager;
	}

	/**
	 * Constructeur par défaut.
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
	protected void applyDamage(final Castle destination)
	{
		Random rand = new Random();

		// Détruit le rempart
		if (rand.nextFloat() <= Settings.ONAGER_WALL - 0.02d * destination.getWall().getLevel())
		{
			destination.getReserveOfSoldiers().wallMultiplierReduction(destination.getWall().decreaseLevel());
		}

		super.applyDamage(destination);
	}
}
