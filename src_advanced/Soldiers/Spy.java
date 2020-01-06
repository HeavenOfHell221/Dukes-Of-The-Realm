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
 * Représente une unité de type Spy.
 */
public class Spy extends Soldier
{
	/**
	 * Constructeur Spy
	 *
	 * @param layer  Le Pane pour afficher la réprésentation graphique de ce soldat.
	 * @param coord  Les coordonnées du soldat à son déploiement.
	 * @param itsOst La référence vers l'ost du soldat.
	 * @param speed  La vitesse de déplacement du soldat.
	 */
	public Spy(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Spy;
	}

	/**
	 * Constructeur par défaut de Spy.
	 */
	public Spy()
	{
		super();
		this.type = SoldierEnum.Spy;
	}

	@Override
	public void Awake(final Color color)
	{
		AddSpyRepresentation();
		super.Awake(color);
	}

	@Override
	protected void applyDamage(final Castle destination)
	{
		Random rand = new Random();

		// Espionnage
		if (rand.nextFloat() <= Settings.SPY_SPY - 0.01d * destination.getWall().getLevel() - 0.01d * destination.getLevel()
				- 0.001d * destination.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Spy))
		{
			destination.spiedOn();
		}

		super.applyDamage(destination);
	}
}
