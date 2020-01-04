package DukesOfTheRealm;

import java.io.Serializable;

import Interface.IBuilding;
import Interface.IProduction;

public class Market implements Serializable, IBuilding, IProduction
{

	@Override
	public double getProductionTime()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getProductionCost(Castle castle)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void productionFinished(Castle castle, boolean cancel)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void productionStart(Caserne caserne)
	{
		// TODO Auto-generated method stub
		
	}

}
