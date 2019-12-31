package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayDeque;

import Interface.IProductionUnit;
import Utility.Settings;
import Utility.Time;

/**
 * G�re la production des unit�s et l'am�lioration du ch�teau.
 */
public class Caserne implements Serializable
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Queue des productions. Celle en cours est la premi�re de cette queue.
	 */
	private final ArrayDeque<IProductionUnit> productionUnit;

	/**
	 * Le temps qu'il reste avant la fin de la production en cours.
	 */
	private double productionTime;

	/**
	 * Le ch�teau � qui appartient cette caserne.
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
	 * @param castle Le ch�teau � qui appartient cette caserne.
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
	 * Met � jour � chaque image le temps qu'il reste pour la production en cours (si elle existe). Une
	 * fois la production termin�, si c'est une unit� elle serra ajout� � la r�serve, si c'est un
	 * b�timent il serra am�lior�.
	 */
	public void updateProduction()
	{
		if (this.productionUnit.size() > 0)
		{
			// On retire du temps
			this.productionTime -= 1 * Time.deltaTime;

			// On calcul le ration pour le UI
			this.ratio = 1 - this.productionTime / this.productionUnit.getFirst().getProductionTime();

			// Si la production est termin�
			if (this.productionTime <= 0)
			{
				final IProductionUnit p = this.productionUnit.pollFirst();

				if (p.getClass() == Castle.class)
				{
					p.CastleUp(this.castle);
				}
				else
				{
					p.addProduction(this.castle.getReserveOfSoldiers());
				}

				p.removeInProduction(this);

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
	 * @param refoundFlorin Sp�cifie si on rend le co�t en Florin de la production ou non.
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
			i.removeInProduction(this);
		}
	}

	/**
	 * Retire tout les �l�ments de la queue.
	 *
	 * @param refundFlorin Sp�cifie si on rend le co�t en Florin de la production ou non.
	 */
	public void resetQueue(final boolean refundFlorin)
	{
		if (refundFlorin)
		{
			while (!this.productionUnit.isEmpty())
			{
				IProductionUnit i = this.productionUnit.pollFirst();
				this.castle.addFlorin(i.getProductionCost());
				i.removeInProduction(this);
			}
		}
		else
		{
			this.productionUnit.clear();
			this.nbCastleInProduction = 0;
			this.nbKnightsInProduction = 0;
			this.nbPikersInProduction = 0;
			this.nbOnagersInProduction = 0;
		}
	}

	/**
	 * Ajoute une production � la fin de la queue si le ch�teau a assez de Florin pour la payer.
	 *
	 * @param  newProduction La nouvelle production.
	 * @return               Retourne true si le production a bien �t� ajout�, false sinon.
	 */
	public boolean addProduction(final IProductionUnit newProduction)
	{
		if (newProduction.getClass() == Castle.class)
		{
			if (!this.castle.removeFlorin(newProduction.getProductionCost() + this.nbCastleInProduction * Settings.LEVEL_UP_COST))
			{
				return false;
			}
		}
		else
		{
			if (!this.castle.removeFlorin(newProduction.getProductionCost()))
			{
				return false;
			}
		}

		this.productionUnit.addLast(newProduction);

		if (this.productionUnit.size() == 1)
		{
			this.productionTime = newProduction.getProductionTime();
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
