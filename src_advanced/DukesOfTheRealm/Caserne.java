package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

import Enums.BuildingEnum;
import Interface.IBuilding;
import Interface.IProduction;
import Utility.BuildingPack;
import Utility.Settings;
import Utility.SoldierPack;

/**
 * Gère la production des unités et l'amélioration des bâtiments.
 */
public class Caserne implements Serializable, IBuilding, IProduction
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Queue des productions en attente d'être prise par les unités de production.
	 * 
	 * @see ProductionUnit
	 */
	private ArrayDeque<IProduction> mainProductionQueue;

	/**
	 * Liste des unités de production.
	 */
	private ArrayList<ProductionUnit> productionUnitList;

	/**
	 * Le château à qui appartient cette caserne.
	 */
	private Castle castle;

	/**
	 * Nombre de chaque type d'unité dans la queue.
	 */
	private SoldierPack<Integer> soldierPack;

	/**
	 * Nombre de chaque type de bâtiment dans la queue.
	 */
	private BuildingPack<Integer> buildingPack;

	/**
	 * Niveau de ce bâtiment.
	 */
	private int level;

	/**
	 * Nombre d'unité de production en attente d'être ajouté dans cette caserne.
	 */
	public int numberProductionUnitAdding = 0;

	/**
	 * Ratio entre le temps de production restant et le temps total dans cette caserne.
	 */
	public double ratio = 0;

	/**
	 * Somme du temps restant pour terminer toutes les productions de cette caserne.
	 */
	protected double sumCurrentTime = 0;

	/**
	 * Somme du temps total de toutes les productions de cette caserne.
	 */
	protected double sumTotalTime = 0;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur de Caserne.
	 *
	 * @param castle Le château à qui appartient cette caserne.
	 */
	public Caserne(final Castle castle)
	{
		this.mainProductionQueue = new ArrayDeque<>();
		this.productionUnitList = new ArrayList<>();
		this.castle = castle;
		this.soldierPack = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);
		this.buildingPack = new BuildingPack<>(0, 0, 0, 0, 0);
		this.productionUnitList.add(new ProductionUnit(this.castle, this));
		this.level = 1;
	}

	public Caserne()
	{

	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	/**
	 * Met à jour les unités de production, ajoute les nouvelles unités de production et actualise le
	 * ratio.
	 */
	public void updateProduction()
	{
		final Iterator<ProductionUnit> itUnit = this.productionUnitList.iterator();

		while (itUnit.hasNext())
		{
			final ProductionUnit p = itUnit.next();
			p.updateProduction();
		}

		while (this.numberProductionUnitAdding > 0)
		{
			this.productionUnitList.add(new ProductionUnit(this.castle, this));
			this.numberProductionUnitAdding--;
		}

		int count = 0;
		for (int i : this.buildingPack.values())
		{
			for (int k : this.soldierPack.values())
			{
				count += k;
			}
			count += i;
		}

		if (this.sumCurrentTime <= 0 || count == 0)
		{
			this.sumTotalTime = 0;
			this.sumCurrentTime = 0;
		}

		if (this.sumTotalTime > 0)
		{
			this.ratio = 1 - this.sumCurrentTime / this.sumTotalTime;
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
	 * @param refoundFlorin Spécifie si on rend le coût en Florin de la production ou non.
	 */
	public void removeLastProduction(final boolean refoundFlorin)
	{
		IProduction i = this.mainProductionQueue.pollLast();
		if (i != null)
		{
			if (refoundFlorin)
			{
				this.castle.addFlorin(i.getProductionCost(i.getLevel()));
			}
			this.sumCurrentTime -= i.getProductionTime(getCastle(), i.getLevel());
			i.productionFinished(this.castle, true);
		}
	}

	/**
	 * Retire tout les éléments de la queue.
	 *
	 * @param refundFlorin Spécifie si on rend le coût en Florin de la production ou non.
	 */
	public void resetQueue(final boolean refundFlorin)
	{
		if (refundFlorin)
		{
			while (!this.mainProductionQueue.isEmpty())
			{
				IProduction i = this.mainProductionQueue.pollFirst();
				this.castle.addFlorin(i.getProductionCost(i.getLevel()));
				this.sumCurrentTime -= i.getProductionTime(getCastle(), i.getLevel());
				i.productionFinished(this.castle, true);
			}
		}
		else
		{
			this.mainProductionQueue.clear();
		}
		this.sumCurrentTime = 0;
		this.sumTotalTime = 0;
	}

	/**
	 * Ajoute une production à la fin de la queue si le château a assez de Florin pour la payer.
	 *
	 * @param  p La nouvelle production.
	 * @return   Retourne true si le production a bien été ajouté, false sinon.
	 */
	public boolean addProduction(final IProduction p)
	{
		if (!this.castle.removeFlorin(p.getProductionCost(p.getLevel())))
		{
			return false;
		}

		this.sumTotalTime += p.getProductionTime(getCastle(), p.getLevel());
		this.sumCurrentTime += p.getProductionTime(getCastle(), p.getLevel());

		p.productionStart(this);

		this.mainProductionQueue.addLast(p);
		return true;
	}

	@Override
	public void levelUp()
	{
		if (this.level < Settings.CASERNE_LEVEL_MAX)
		{
			this.level += 1;

			if (this.level % 2 != 0 || this.level == Settings.CASERNE_LEVEL_MAX)
			{
				this.numberProductionUnitAdding++;
			}
		}
	}

	@Override
	public void productionFinished(final Castle castle, final boolean cancel)
	{
		if (!cancel)
		{
			castle.getCaserne().levelUp();
		}

		castle.getCaserne().getBuildingPack().replace(BuildingEnum.Caserne,
				castle.getCaserne().getBuildingPack().get(BuildingEnum.Caserne) - 1);
	}

	@Override
	public void productionStart(final Caserne caserne)
	{
		caserne.getBuildingPack().replace(BuildingEnum.Caserne, caserne.getBuildingPack().get(BuildingEnum.Caserne) + 1);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return the mainProductionQueue
	 */
	public final ArrayDeque<IProduction> getMainProductionQueue()
	{
		return this.mainProductionQueue;
	}

	/**
	 * @return the soldierPack
	 */
	public final SoldierPack<Integer> getSoldierPack()
	{
		return this.soldierPack;
	}

	/**
	 * @return the buildingPack
	 */
	public final BuildingPack<Integer> getBuildingPack()
	{
		return this.buildingPack;
	}

	@Override
	public double getProductionTime(final Castle castle, final int level)
	{
		return (int) ((double) (Settings.CASERNE_PRODUCTION_OFFSET + Settings.CASERNE_PRODUCTION_TIME_PER_LEVEL * level)
				* castle.getProductionTimeMultiplier());
	}

	@Override
	public int getProductionCost(final int level)
	{
		double lvl = level + 2;
		return (int) ((double) (Settings.CASERNE_COST * lvl) + (double) (lvl * lvl * lvl * lvl / 7d));
	}

	@Override
	public void setLevel(final int level)
	{
		this.level = level;
	}

	@Override
	public int getLevel()
	{
		return this.level;
	}

	/**
	 * @return the castle
	 */
	public final Castle getCastle()
	{
		return this.castle;
	}

	/**
	 * @return the ratio
	 */
	public final double getRatio()
	{
		return this.ratio;
	}
}
