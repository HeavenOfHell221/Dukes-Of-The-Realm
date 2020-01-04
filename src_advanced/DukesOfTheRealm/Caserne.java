package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

import Interface.IBuilding;
import Interface.IProduction;
import Utility.BuildingPack;
import Utility.SoldierPack;
import Utility.Time;

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
	private final ArrayDeque<IProduction> mainProductionQueue;

	/**
	 * Liste des unités de production.
	 */
	private ArrayList<ProductionUnit> productionUnitList;

	/**
	 * Le château à qui appartient cette caserne.
	 */
	private final Castle castle;
	
	/**
	 * Nombre de chaque type d'unité dans la queue.
	 */
	private SoldierPack<Integer> soldierPack;

	
	/**
	 * Nombre de chaque type de bâtiment dans la queue.
	 */
	private BuildingPack<Integer> buildingPack;
	

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
		this.soldierPack = new SoldierPack<Integer>(0, 0, 0, 0, 0, 0);
		this.buildingPack = new BuildingPack<Integer>(0, 0, 0, 0, 0);
		this.productionUnitList.add(new ProductionUnit(this.castle, this));
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	public void updateProduction()
	{
		this.productionUnitList.forEach(unit -> unit.updateProduction());
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
				this.castle.addFlorin(i.getProductionCost(this.castle));
			}
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
				this.castle.addFlorin(i.getProductionCost(this.castle));
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
		if (!this.castle.removeFlorin(p.getProductionCost(this.castle)))
		{
			return false;
		}
		
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
		return mainProductionQueue;
	}

	/**
	 * @return the soldierPack
	 */
	public final SoldierPack<Integer> getSoldierPack()
	{
		return soldierPack;
	}

	/**
	 * @return the buildingPack
	 */
	public final BuildingPack<Integer> getBuildingPack()
	{
		return buildingPack;
	}

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
