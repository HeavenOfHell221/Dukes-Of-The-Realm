package DukesOfTheRealm;

import java.io.Serializable;

import Interface.IProduction;
import Utility.Time;

public class ProductionUnit implements Serializable
{
	/**
	 * Production en cours.
	 */
	private IProduction currentProduction;

	/**
	 * Le temps qu'il reste avant la fin de la production en cours.
	 */
	private double currentProductionTime;

	/**
	 * Le château à qui appartient cette unité de production.
	 */
	private final Castle castle;

	/**
	 * La caserne dans laquelle est cette unité de production.
	 */
	private final Caserne caserne;

	/**
	 *
	 */
	public ProductionUnit(final Castle castle, final Caserne caserne)
	{
		this.currentProductionTime = 0;
		this.castle = castle;
		this.caserne = caserne;
		this.currentProduction = null;
	}

	/**
	 * Met à jour à chaque update le temps qu'il reste pour la production en cours (si elle existe). Une
	 * fois la production terminé, si c'est une unité elle serra ajouté à la réserve, si c'est un
	 * bâtiment il serra amélioré.
	 */
	public void updateProduction()
	{
		if (this.currentProduction != null)
		{
			// On retire du temps
			this.currentProductionTime -= 1 * Time.deltaTime;
			this.caserne.sumCurrentTime -= 1 * Time.deltaTime;

			// Si la production est terminé
			if (this.currentProductionTime <= 0)
			{
				this.currentProduction.productionFinished(this.castle, false);
				this.currentProduction = null;
			}
		}
		else
		{
			if (this.caserne.getMainProductionQueue().size() > 0)
			{
				this.currentProduction = this.caserne.getMainProductionQueue().pollFirst();
				this.currentProductionTime = this.currentProduction.getProductionTime(this.castle, this.currentProduction.getLevel());
			}
		}
	}

	/**
	 * @return the currentProductionTime
	 */
	public final double getCurrentProductionTime()
	{
		return currentProductionTime;
	}

	/**
	 * @return the currentProduction
	 */
	public final IProduction getCurrentProduction()
	{
		return currentProduction;
	}
}
