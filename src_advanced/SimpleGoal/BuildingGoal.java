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
	private final BuildingEnum building;

	/**
	 * Constucteur de BuildingGoal
	 *
	 * @param building Le b�timent qu'on veut am�liorer.
	 * @see          Goals.GeneratorGoal
	 */
	public BuildingGoal(final BuildingEnum building)
	{
		this.building = building;
	}

	@Override
	public boolean goal(final Castle castle)
	{
		IBuilding b = this.building.getObject();
		final int level = (castle.getBuilding(this.building).getLevel() + castle.getCaserne().getBuildingPack().get(this.building));

		if (level < this.building.maxLevel)
		{
			b.setLevel(level);
			return castle.addProduction((IProduction) b);
		}
		else
		{
			return true;
		}
	}
}
