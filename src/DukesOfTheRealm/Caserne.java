package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayDeque;

import Interface.IProductionUnit;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Utility.Settings;
import Utility.Time;

public class Caserne implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final ArrayDeque<IProductionUnit> productionUnit;
	private double productionTime;
	private final Castle castle;
	private double ratio;

	private int nbPikersInProduction;
	private int nbOnagersInProduction;
	private int nbKnightsInProduction;
	private int nbCastleInProduction;

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

	public void updateProduction()
	{
		if (this.productionUnit.size() > 0)
		{
			this.productionTime -= 1 * Time.deltaTime;

			this.ratio = 1 - this.productionTime / this.productionUnit.getFirst().getProductionTime();

			if (this.productionTime <= 0)
			{
				final IProductionUnit p = this.productionUnit.pollFirst();

				if (p.getClass() == Castle.class)
				{
					this.castle.levelUp();
					this.nbCastleInProduction--;
				}
				else if (p.getClass() == Piker.class)
				{
					this.castle.addPiker();
					this.nbPikersInProduction--;
				}
				else if (p.getClass() == Onager.class)
				{
					this.castle.addOnager();
					this.nbOnagersInProduction--;
				}
				else if (p.getClass() == Knight.class)
				{
					this.castle.addKnight();
					this.nbKnightsInProduction--;
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

	/**
	 *
	 */
	public void removeLastProduction(final boolean refoundFlorin)
	{
		IProductionUnit i = this.productionUnit.pollLast();
		if (refoundFlorin && i != null)
		{
			this.castle.addFlorin(i.getProductionCost());
		}
	}

	/**
	 *
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
	}

	/**
	 *
	 * @param  newProduction
	 * @return
	 */
	public boolean addProduction(final IProductionUnit newProduction)
	{
		if (newProduction == null 
				|| !this.castle.removeFlorin(newProduction.getProductionCost() + this.nbCastleInProduction * Settings.LEVEL_UP_COST_FACTOR))
		{
			return false;
		}

		this.productionUnit.addLast(newProduction);

		if (this.productionUnit.size() == 1)
		{
			this.productionTime = newProduction.getProductionTime();
		}

		return true;
	}

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
		return nbCastleInProduction;
	}

	/**
	 * @param nbCastleInProduction the nbCastleInProduction to set
	 */
	public final void setNbCastleInProduction(int nbCastleInProduction)
	{
		this.nbCastleInProduction = nbCastleInProduction;
	}

	
}
