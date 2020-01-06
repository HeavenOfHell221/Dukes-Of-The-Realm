package DukesOfTheRealm;

import java.io.Serializable;

import Interface.IProduction;
import Utility.Time;

/**
 * Repr�sente une unit� de production qui est capable de produire les productions de la caserne une par une.
 */
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
	 * Le ch�teau � qui appartient cette unit� de production.
	 */
	private final Castle castle;

	/**
	 * La caserne dans laquelle est cette unit� de production.
	 */
	private final Caserne caserne;

	/**
	 * Constructeur de ProductionUnit.
	 * @param castle Le ch�teau � qui appartient cette unit� de production.
	 * @param caserne La caserne qui contient cette unit� de production.
	 */
	public ProductionUnit(final Castle castle, final Caserne caserne)
	{
		this.currentProductionTime = 0;
		this.castle = castle;
		this.caserne = caserne;
		this.currentProduction = null;
	}

	/**
	 * Met � jour � chaque update le temps qu'il reste pour la production en cours (si elle existe). <br>
	 * Une fois la production termin�, si c'est une unit� elle serra ajout� � la r�serve, si c'est un
	 * b�timent il serra am�lior�.
	 */
	public void updateProduction()
	{
		if (this.currentProduction != null)
		{
			// On retire du temps
			this.currentProductionTime -= 1 * Time.deltaTime;
			this.caserne.sumCurrentTime -= 1 * Time.deltaTime;

			// Si la production est termin�
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
		return this.currentProductionTime;
	}

	/**
	 * @return the currentProduction
	 */
	public final IProduction getCurrentProduction()
	{
		return this.currentProduction;
	}
}
