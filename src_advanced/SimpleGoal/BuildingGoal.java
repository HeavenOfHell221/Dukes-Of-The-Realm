package SimpleGoal;

import DukesOfTheRealm.Castle;
import Enums.BuildingEnum;
import Interface.IBuilding;
import Interface.IProduction;

/**
 * Objectif visant à augmenter d'un niveau un bâtiment.
 */
public class BuildingGoal extends Goal
{

	/**
	 * Le bâtiment qu'on veut améliorer.
	 */
	private final BuildingEnum building;

	/**
	 * Constucteur de BuildingGoal
	 *
	 * @param building Le bâtiment qu'on veut améliorer.
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
