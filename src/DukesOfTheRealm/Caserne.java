package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayDeque;

import Interface.IProductionUnit;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Utility.Time;

public class Caserne implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final ArrayDeque<IProductionUnit> productionUnit;
	private double productionTime;
	private final Castle castle;
	private double ratio;

	public Caserne(final Castle castle)
	{
		this.productionTime = 0;
		this.productionUnit = new ArrayDeque<>();
		this.castle = castle;
	}

	public void updateProduction()
	{
		if (this.productionUnit.size() > 0)
		{
			this.productionTime -= (1 * Time.deltaTime);

			this.ratio = 1 - (this.productionTime / this.productionUnit.getFirst().getProductionTime());

			if (this.productionTime <= 0)
			{
				final IProductionUnit p = this.productionUnit.pollFirst();

				if (p.getClass() == Castle.class)
				{
					this.castle.levelUp();
				}
				else if (p.getClass() == Piker.class)
				{
					this.castle.addPiker();
				}
				else if (p.getClass() == Onager.class)
				{
					this.castle.addOnager();
				}
				else if (p.getClass() == Knight.class)
				{
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

	/**
	 *
	 */
	public void removeLastProduction(boolean refoundFlorin)
	{
		IProductionUnit i = this.productionUnit.pollLast();
		if(refoundFlorin && i != null)
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
		if (newProduction == null || !this.castle.removeFlorin(newProduction.getProductionCost()))
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
}
