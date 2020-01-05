package SimpleGoal;

import DukesOfTheRealm.Castle;
import Enums.BuildingEnum;
import Interface.IBuilding;
import Interface.IProduction;

/**
 * Objectif visant � augmenter d'un niveau un b�timent.
 */
public class BuildingGoal extends Goal
{

	/**
	 * Le b�timent qu'on veut am�liorer.
	 */
	private BuildingEnum building;
	
	/**
	 * Constucteur de BuildingGoal
	 *
	 * @param castle Le ch�teau qu'on veut am�liorer.
	 * @see          Goals.GeneratorGoal
	 */
	public BuildingGoal(BuildingEnum building)
	{
		this.building = building;
	}

	@Override
	public boolean goal(final Castle castle)
	{
		IBuilding b = building.getObject();
		final int level = castle.getBuilding(building).getLevel();
		
		if(level < building.maxLevel)
		{
			b.setLevel(level);
			return castle.addProduction((IProduction)b);
		}
		else
		{
			return true;
		}
	}

	@Override
	public String toString()
	{
		return "BuildingGoal [building=" + building + "]";
	}
}
