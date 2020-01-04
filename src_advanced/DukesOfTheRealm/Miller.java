package DukesOfTheRealm;

import java.io.Serializable;

import Interface.IBuilding;
import Interface.IProduction;

public class Miller implements Serializable, IBuilding, IProduction
{

	@Override
	public double getProductionTime()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getProductionCost(final Castle castle)
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
		// TODO Auto-generated method stub

	}

	@Override
	public int getLevel()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
