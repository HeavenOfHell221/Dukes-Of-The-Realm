package Enums;

import static Utility.Settings.CASERNE_COST;
import static Utility.Settings.CASERNE_LEVEL_MAX;
import static Utility.Settings.CASERNE_PRODUCTION_OFFSET;
import static Utility.Settings.CASERNE_PRODUCTION_TIME_PER_LEVEL;
import static Utility.Settings.CASTLE_COST;
import static Utility.Settings.CASTLE_LEVEL_MAX;
import static Utility.Settings.CASTLE_PRODUCTION_OFFSET;
import static Utility.Settings.CASTLE_PRODUCTION_TIME_PER_LEVEL;
import static Utility.Settings.MARKET_COST;
import static Utility.Settings.MARKET_LEVEL_MAX;
import static Utility.Settings.MARKET_PRODUCTION_OFFSET;
import static Utility.Settings.MARKET_PRODUCTION_TIME_PER_LEVEL;
import static Utility.Settings.MILLER_COST;
import static Utility.Settings.MILLER_LEVEL_MAX;
import static Utility.Settings.MILLER_PRODUCTION_OFFSET;
import static Utility.Settings.MILLER_PRODUCTION_TIME_PER_LEVEL;
import static Utility.Settings.WALL_COST;
import static Utility.Settings.WALL_LEVEL_MAX;
import static Utility.Settings.WALL_PRODUCTION_OFFSET;
import static Utility.Settings.WALL_PRODUCTION_TIME_PER_LEVEL;

import java.util.Random;

import DukesOfTheRealm.Caserne;
import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Market;
import DukesOfTheRealm.Miller;
import DukesOfTheRealm.Wall;
import Interface.IBuilding;

/**
 * Enumération contenant les bâtiments et leurs constantes.
 */
public enum BuildingEnum
{
	/**
	 * Représente le château.
	 */
	Castle(CASTLE_COST, CASTLE_PRODUCTION_OFFSET, CASTLE_PRODUCTION_TIME_PER_LEVEL, CASTLE_LEVEL_MAX),

	/**
	 * Représente le moulin.
	 */
	Miller(MILLER_COST, MILLER_PRODUCTION_OFFSET, MILLER_PRODUCTION_TIME_PER_LEVEL, MILLER_LEVEL_MAX),

	/**
	 * Représente le rempart.
	 */
	Wall(WALL_COST, WALL_PRODUCTION_OFFSET, WALL_PRODUCTION_TIME_PER_LEVEL, WALL_LEVEL_MAX),

	/**
	 * Représente la caserne.
	 */
	Caserne(CASERNE_COST, CASERNE_PRODUCTION_OFFSET, CASERNE_PRODUCTION_TIME_PER_LEVEL, CASERNE_LEVEL_MAX),

	/**
	 * Représente le marché.
	 */
	Market(MARKET_COST, MARKET_PRODUCTION_OFFSET, MARKET_PRODUCTION_TIME_PER_LEVEL, MARKET_LEVEL_MAX);

	/**
	 * Coût de base du bâtiment.
	 */
	public final int cost;
	
	/**
	 * Niveau maximum du bâtiment.
	 */
	public final int maxLevel;
	
	/**
	 * Temps de production de base du bâtiment.
	 */
	public final float productionTimeOffset;
	
	/**
	 * Temps de production additionnel par niveau du bâtiment.
	 */
	public final float productionTimePerLevel;

	/**
	 * Constructeur de BuildingEnum.
	 * @param cost Le coût.
	 * @param productionTimeOffset Le temps de production de base.
	 * @param productionTimePerLevel Le temps de production additionnel par niveau.
	 * @param maxLevel Le niveau maximum.
	 */
	private BuildingEnum(final int cost, final float productionTimeOffset, final float productionTimePerLevel, final int maxLevel)
	{
		this.cost = cost;
		this.maxLevel = maxLevel;
		this.productionTimeOffset = productionTimeOffset;
		this.productionTimePerLevel = productionTimePerLevel;
	}

	/**
	 * @return Retourne une instance du bâtiment lié à l'énumération.
	 */
	public IBuilding getObject()
	{
		switch (this)
		{
			case Caserne:
				return new Caserne();
			case Castle:
				return new Castle();
			case Market:
				return new Market();
			case Miller:
				return new Miller();
			case Wall:
				return new Wall();
			default:
				return null;

		}
	}

	/**
	 * @return Retourne un bâtiment aléatoire pour l'objectif de production des bâtiments des IA.
	 * <p>
	 * Caserne: 3/15
	 * Castle: 4/15
	 * Miller: 2/15
	 * Market: 1/15
	 * Wall: 5/15
	 * </p>
	 * @see Goals.GeneratorGoal
	 */
	public static BuildingEnum getRandomTypeForAI()
	{
		Random rand = new Random();
		switch (rand.nextInt(15))
		{
			case 0:
			case 1:
			case 2:
				return Caserne;
			case 3:
			case 4:
			case 5:
			case 6:
				return Castle;
			case 7:
			case 8:
				return Miller;
			case 9:
				return Market;
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				return Wall;
			default:
				return Castle;
		}
	}
}
