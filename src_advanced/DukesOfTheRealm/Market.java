package DukesOfTheRealm;

import java.io.Serializable;

import Interface.IBuilding;
import Interface.IProduction;

public class Market implements Serializable, IBuilding, IProduction
{
	@Override
	public double getProductionTime(final Castle castle, int level)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getProductionCost(final int level)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void productionFinished(final Castle castle, final boolean cancel)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void productionStart(final Caserne caserne)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setLevel(final int level)
	{

	}

	@Override
	public int getLevel()
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
