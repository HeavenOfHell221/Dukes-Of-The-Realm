package Utility;

import Enums.BuildingEnum;

/**
 * 
 */
public class BuildingPack<T2> extends Pack<BuildingEnum, T2>
{
	/**
	 * Constructeur par défaut de BuildingPack.
	 */
	public BuildingPack()
	{
		super();
		put(BuildingEnum.Castle, null);
		put(BuildingEnum.Wall, null);
		put(BuildingEnum.Caserne, null);
		put(BuildingEnum.Market, null);
		put(BuildingEnum.Miller, null);	
	}
	
	/**
	 * 
	 * @param castle
	 * @param caserne
	 * @param market
	 * @param miller
	 * @param wall
	 */
	public BuildingPack(T2 castle, T2 caserne, T2 market, T2 miller, T2 wall)
	{
		super();
		put(BuildingEnum.Castle, castle);
		put(BuildingEnum.Wall, wall);
		put(BuildingEnum.Caserne, caserne);
		put(BuildingEnum.Market, market);
		put(BuildingEnum.Miller, miller);	
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public T2 get(BuildingEnum key)
	{
		return super.get(key);
	}
	
	/**
	 * 
	 */
	public void replace(BuildingEnum key, T2 value)
	{
		super.replace(key, value);
	}
}