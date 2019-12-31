package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayDeque;

import Interface.IProductionUnit;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Utility.Settings;
import Utility.Time;

/**
 * Gère la production des unités et l'amélioration du château.
 */
public class Caserne implements Serializable
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Queue des productions. Celle en cours est la première de cette queue.
	 */
	private final ArrayDeque<IProductionUnit> productionUnit;

	/**
	 * Le temps qu'il reste avant la fin de la production en cours.
	 */
	private double productionTime;

	/**
	 * Le château à qui appartient cette caserne.
	 */
	private final Castle castle;

	/**
	 * Le ratio entre le temps restant et le temps total du production.
	 */
	private double ratio;

	/**
	 * Nombre de Piker dans la queue en attente.
	 */
	private int nbPikersInProduction;

	/**
	 * Nombre de Onager dans la queue en attente.
	 */
	private int nbOnagersInProduction;

	/**
	 * Nombre de Knight dans la queue en attente.
	 */
	private int nbKnightsInProduction;

	/**
	 * Nombre de Castle dans la queue en attente.
	 */
	private int nbCastleInProduction;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur de Caserne.
	 *
	 * @param castle Le château à qui appartient cette caserne.
	 */
	public Caserne(final Castle castle)
	{
		this.productionTime = 0;
		this.productionUnit = new ArrayDeque<>();
		this.castle = castle;
		this.nbPikersInProduction = 0;
		this.nbKnightsInProduction = 0;
		this.nbOnagersInProduction = 0;
		this.nbCastleInProduction = 0;
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	/**
	 * Met à jour à chaque image le temps qu'il reste pour la production en cours (si elle existe). Une
	 * fois la production terminé, si c'est une unité elle serra ajouté à la réserve, si c'est un
	 * bâtiment il serra amélioré.
	 */
	public void updateProduction()
	{
		//System.out.println(this.castle.getActor().getName() + " -> " + this.nbPikersInProduction + " " + this.nbKnightsInProduction + " " + this.nbOnagersInProduction);
		if (this.productionUnit.size() > 0)
		{
			// On retire du temps
			this.productionTime -= 1 * Time.deltaTime;

			// On calcul le ration pour le UI
			this.ratio = 1 - this.productionTime / this.productionUnit.getFirst().getProductionTime();

			// Si la production est terminé
			if (this.productionTime <= 0)
			{
				final IProductionUnit p = this.productionUnit.pollFirst();

				if (p.getClass() == Castle.class)
				{
					this.castle.levelUp();
					this.nbCastleInProduction--;
				}
				else if(p.getClass() == Onager.class)
				{
					this.nbOnagersInProduction--;
					this.castle.addOnager();
				}
				else if(p.getClass() == Piker.class)
				{
					this.nbPikersInProduction--;
					this.castle.addPiker();
				}
				else if(p.getClass() == Knight.class)
				{
					this.nbKnightsInProduction--;
					this.castle.addKnight();
				}
				

				if (this.productionUnit.size() > 0)
				{
					this.productionTime = this.productionUnit.getFirst().getProductionTime();
				}
			}
		}
		else
		{
			this.ratio = 0;
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * Retire la production en fin de queue.
	 *
	 * @param refoundFlorin Spécifie si on rend le coût en Florin de la production ou non.
	 */
	public void removeLastProduction(final boolean refoundFlorin)
	{
		IProductionUnit i = this.productionUnit.pollLast();
		if (i != null)
		{
			if (refoundFlorin)
			{
				this.castle.addFlorin(i.getProductionCost());
			}
		}
	}

	/**
	 * Retire tout les éléments de la queue.
	 *
	 * @param refundFlorin Spécifie si on rend le coût en Florin de la production ou non.
	 */
	public void resetQueue(final boolean refundFlorin)
	{
		if (refundFlorin)
		{
			while (!this.productionUnit.isEmpty())
			{
				IProductionUnit i = this.productionUnit.pollFirst();
				this.castle.addFlorin(i.getProductionCost());
			}
		}
		else
		{
			this.productionUnit.clear();
			
		}
		this.nbCastleInProduction = 0;
		this.nbKnightsInProduction = 0;
		this.nbPikersInProduction = 0;
		this.nbOnagersInProduction = 0;
	}

	/**
	 * Ajoute une production à la fin de la queue si le château a assez de Florin pour la payer.
	 *
	 * @param  p La nouvelle production.
	 * @return               Retourne true si le production a bien été ajouté, false sinon.
	 */
	public boolean addProduction(final IProductionUnit p)
	{
		if (p.getClass() == Castle.class)
		{
			if (!this.castle.removeFlorin(p.getProductionCost() + this.nbCastleInProduction * Settings.LEVEL_UP_COST))
			{
				return false;
			}
		}
		else
		{
			if (!this.castle.removeFlorin(p.getProductionCost()))
			{
				return false;
			}
		}
		
		if (p.getClass() == Castle.class)
		{
			this.nbCastleInProduction++;
		}
		else if(p.getClass() == Onager.class)
		{
			this.nbOnagersInProduction++;
		}
		else if(p.getClass() == Piker.class)
		{
			this.nbPikersInProduction++;
		}
		else if(p.getClass() == Knight.class)
		{
			this.nbKnightsInProduction++;
		}

		
		this.productionUnit.addLast(p);

		if (this.productionUnit.size() == 1)
		{
			this.productionTime = p.getProductionTime();
		}

		return true;
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return the productionUnit
	 */
	public final ArrayDeque<IProductionUnit> getProductionUnit()
	{
		return this.productionUnit;
	}

	/**
	 * @return the productionTime
	 */
	public final double getProductionTime()
	{
		return this.productionTime;
	}

	/**
	 * @return the ratio
	 */
	public final double getRatio()
	{
		return this.ratio;
	}

	/**
	 * @return the nbPikersInProduction
	 */
	public final int getNbPikersInProduction()
	{
		return this.nbPikersInProduction;
	}

	/**
	 * @return the nbOnagersInProduction
	 */
	public final int getNbOnagersInProduction()
	{
		return this.nbOnagersInProduction;
	}

	/**
	 * @return the nbKnightsInProduction
	 */
	public final int getNbKnightsInProduction()
	{
		return this.nbKnightsInProduction;
	}

	/**
	 * @param nbPikersInProduction the nbPikersInProduction to set
	 */
	public final void setNbPikersInProduction(final int nbPikersInProduction)
	{
		this.nbPikersInProduction = nbPikersInProduction;
	}

	/**
	 * @param nbOnagersInProduction the nbOnagersInProduction to set
	 */
	public final void setNbOnagersInProduction(final int nbOnagersInProduction)
	{
		this.nbOnagersInProduction = nbOnagersInProduction;
	}

	/**
	 * @param nbKnightsInProduction the nbKnightsInProduction to set
	 */
	public final void setNbKnightsInProduction(final int nbKnightsInProduction)
	{
		this.nbKnightsInProduction = nbKnightsInProduction;
	}

	/**
	 * @return the nbCastleInProduction
	 */
	public final int getNbCastleInProduction()
	{
		return this.nbCastleInProduction;
	}

	/**
	 * @param nbCastleInProduction the nbCastleInProduction to set
	 */
	public final void setNbCastleInProduction(final int nbCastleInProduction)
	{
		this.nbCastleInProduction = nbCastleInProduction;
	}

}
