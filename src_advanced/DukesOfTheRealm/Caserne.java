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
 * Gère la production des unités et l'amélioration du château.
 */
public class Caserne implements Serializable, IBuilding, IProduction
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Queue des productions en attente d'être prise par les unités de production.
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
	
	private int level;
	
	public int numberProductionUnitAdding = 0;
	
	public double ratio = 0;
	
	protected double sumCurrentTime = 0;
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

	public void updateProduction()
	{
		final Iterator<ProductionUnit> itUnit = this.productionUnitList.iterator();

		while(itUnit.hasNext())
		{
			final ProductionUnit p = itUnit.next();
			p.updateProduction();
		}
		
		while(this.numberProductionUnitAdding > 0)
		{
			this.productionUnitList.add(new ProductionUnit(this.castle, this));
			this.numberProductionUnitAdding--;
		}
		
		int count = 0;
		for(int i : this.buildingPack.values())
		{
			for(int k : this.soldierPack.values())
			{
				count += k;
			}
			count += i;
		}
		
		if(sumCurrentTime <= 0 || count == 0)
		{
			sumTotalTime = 0;
			sumCurrentTime = 0;
		}
		
		if(sumTotalTime > 0)
		{
			this.ratio = 1 - (sumCurrentTime / sumTotalTime);
		}
		else
		{
			this.ratio = 0;
		}
		
		/*if(this.castle.getActor().isPlayer())
			System.out.println(this.sumCurrentTime + " " + this.sumTotalTime);*/
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
		
		sumTotalTime += p.getProductionTime(getCastle(), p.getLevel());
		sumCurrentTime += p.getProductionTime(getCastle(), p.getLevel());
			
		p.productionStart(this);

		this.mainProductionQueue.addLast(p);
		return true;
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
	public double getProductionTime(Castle castle, int level)
	{
		return (Settings.CASERNE_PRODUCTION_OFFSET + Settings.CASERNE_PRODUCTION_TIME_PER_LEVEL * level) * castle.getProductionTimeMultiplier();
	}

	@Override
	public int getProductionCost(int level)
	{
		level += 2;
		return Settings.CASERNE_COST * level + (level * level * level * level) / 7;
	}
	
	public void levelUp()
	{
		if(this.level < Settings.CASERNE_LEVEL_MAX)
		{
			this.level += 1;
		
			if(this.level % 2 != 0 || this.level == Settings.CASERNE_LEVEL_MAX)
			{
				this.numberProductionUnitAdding++;
			}
		}
	}

	@Override
	public void productionFinished(final Castle castle, final boolean cancel)
	{
		if(!cancel)
		{
			((Caserne)castle.getBuilding(BuildingEnum.Caserne)).levelUp();
		}
		
		castle.getCaserne().getBuildingPack().replace(BuildingEnum.Caserne,
				castle.getCaserne().getBuildingPack().get(BuildingEnum.Caserne) - 1);
	}

	@Override
	public void productionStart(final Caserne caserne)
	{
		caserne.getBuildingPack().replace(BuildingEnum.Caserne, caserne.getBuildingPack().get(BuildingEnum.Caserne) + 1);
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
		return castle;
	}

	/**
	 * @return the ratio
	 */
	public final double getRatio()
	{
		return ratio;
	}
}
