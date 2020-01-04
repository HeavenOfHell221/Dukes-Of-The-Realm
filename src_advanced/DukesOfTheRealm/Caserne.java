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
 * G�re la production des unit�s et l'am�lioration du ch�teau.
 */
public class Caserne implements Serializable, IBuilding, IProduction
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Queue des productions en attente d'�tre prise par les unit�s de production.
	 */
	private ArrayDeque<IProduction> mainProductionQueue;

	/**
	 * Liste des unit�s de production.
	 */
	private ArrayList<ProductionUnit> productionUnitList;

	/**
	 * Le ch�teau � qui appartient cette caserne.
	 */
	private Castle castle;
	
	/**
	 * Nombre de chaque type d'unit� dans la queue.
	 */
	private SoldierPack<Integer> soldierPack;

	
	/**
	 * Nombre de chaque type de b�timent dans la queue.
	 */
	private BuildingPack<Integer> buildingPack;
	

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur de Caserne.
	 *
	 * @param castle Le ch�teau � qui appartient cette caserne.
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
	
	public Caserne()
	{
		
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
	 * @param refoundFlorin Sp�cifie si on rend le co�t en Florin de la production ou non.
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
	 * Retire tout les �l�ments de la queue.
	 *
	 * @param refundFlorin Sp�cifie si on rend le co�t en Florin de la production ou non.
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
	 * Ajoute une production � la fin de la queue si le ch�teau a assez de Florin pour la payer.
	 *
	 * @param  p La nouvelle production.
	 * @return   Retourne true si le production a bien �t� ajout�, false sinon.
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

	@Override
	public void setLevel(int level)
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
