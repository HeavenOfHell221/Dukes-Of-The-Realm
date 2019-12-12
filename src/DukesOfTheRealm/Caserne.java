package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayDeque;

import Interface.IProductionUnit;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import UI.UIManager;
import Utility.Time;

public class Caserne implements Serializable
{
	private ArrayDeque<IProductionUnit> productionUnit;
	private double productionTime;
	private final Castle castle;
	private double ratio;
	
	public Caserne(Castle castle)
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

			ratio = 1 - (this.productionTime / this.productionUnit.getFirst().getProductionTime());
			
			if (this.productionTime <= 0)
			{
				final IProductionUnit p = this.productionUnit.pollFirst();
				
				if (p.getClass() == Castle.class)
				{
					castle.levelUp();
				}
				else if (p.getClass() == Piker.class)
				{
					castle.getReserveOfSoldiers().addPiker();
				}
				else if (p.getClass() == Onager.class)
				{
					castle.getReserveOfSoldiers().addOnager();
				}
				else if (p.getClass() == Knight.class)
				{
					castle.getReserveOfSoldiers().addKnight();
				}

				if (this.productionUnit.size() > 0)
				{
					this.productionTime = this.productionUnit.getFirst().getProductionTime();
				}
			}
		}
	}
	
	/**
	 * 
	 */
	public void removeLastProduction()
	{
		IProductionUnit i  = this.productionUnit.pollLast();
		this.castle.addFlorin(i.getProductionCost());
	}
	
	/**
	 * 
	 */
	public void resetQueue(boolean refundFlorin)
	{
		if(refundFlorin)
		{
			while(!this.productionUnit.isEmpty())
			{
				IProductionUnit i  = this.productionUnit.pollFirst();
				this.castle.addFlorin(i.getProductionCost());
			}
		}
		else
		{
			this.productionUnit.clear();
			this.productionTime = 0;
		}
	}
	
	/**
	 * 
	 * @param newProduction
	 * @return
	 */
	public boolean addProduction(final IProductionUnit newProduction)
	{
		if (newProduction == null || !castle.removeFlorin(newProduction.getProductionCost()))
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
		return productionUnit;
	}

	/**
	 * @return the productionTime
	 */
	public final double getProductionTime()
	{
		return productionTime;
	}

	/**
	 * @return the ratio
	 */
	public final double getRatio()
	{
		return ratio;
	}
}
