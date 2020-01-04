package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayDeque;
import Interface.IProduction;
import Utility.BuildingPack;
import Utility.SoldierPack;
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
	 * Le ratio entre le temps restant et le temps total du production.
	 */
	private double ratio;

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
	public ProductionUnit(Castle castle, Caserne caserne)
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
		if(this.currentProduction != null)
		{
			// On retire du temps
			this.currentProductionTime -= 1 * Time.deltaTime;
			
			// On calcul le ration pour le UI
			this.ratio = 1 - this.currentProductionTime / this.currentProduction.getProductionTime();
			
			// Si la production est terminé
			if (this.currentProductionTime <= 0)
			{
				this.currentProduction.productionFinished(this.castle, false);
				this.currentProduction = null;
			}
		}
		else
		{
			this.ratio = 0;
			if(this.caserne.getMainProductionQueue().size() > 0)
			{
				this.currentProduction = this.caserne.getMainProductionQueue().pollFirst();
				this.currentProductionTime = this.currentProduction.getProductionTime();
			}
		}
	}
}
