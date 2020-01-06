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
 * Repr�sente une unit� de type Spy.
 */
public class Spy extends Soldier
{
	/**
	 * Constructeur Spy
	 *
	 * @param layer  Le Pane pour afficher la r�pr�sentation graphique de ce soldat.
	 * @param coord  Les coordonn�es du soldat � son d�ploiement.
	 * @param itsOst La r�f�rence vers l'ost du soldat.
	 * @param speed  La vitesse de d�placement du soldat.
	 */
	public Spy(final Pane layer, final Point2D coord, final Ost itsOst, final int speed)
	{
		super(layer, coord, itsOst);
		this.type = SoldierEnum.Spy;
	}

	/**
	 * Constructeur par d�faut de Spy.
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
