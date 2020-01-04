package Utility;

import java.util.HashMap;
import java.util.Random;

import Enums.SoldierEnum;

import static Utility.Settings.*;

/**
 * Structure de données utilisé pour stocker un nombre pour chaque type d'unité (nombre, point de vie, ..)
 */
public class SoldierPack<T2> extends Pack<SoldierEnum, T2>
{	
	
	/**
	 * Constructeur par défaut de SoldierPack.
	 */
	public SoldierPack()
	{
		super();
		for(SoldierEnum sEnum : SoldierEnum.values())
		{
			put(sEnum, null);
		}
	}
	
	/**
	 * Constructeur par recopie.
	 * @param soldierPack Le SoldierPack qu'on recopie.
	 */
	public SoldierPack(SoldierPack<T2> s)
	{
		super();
		putAll(s.map);
	}
	
	/**
	 * Constructeur de SoldierPack.
	 * @param piker Un nombre pour le Piker.
	 * @param knight Un nombre pour le Knight.
	 * @param onager Un nombre pour l'Onager.
	 * @param archer Un nombre pour l'Archer.
	 * @param berserker Un nombre pour le Berserker.
	 * @param spy un nombre pour le Spy.
	 */
	public SoldierPack(T2 piker, T2 knight, T2 onager, T2 archer, T2 berserker, T2 spy)
	{
		super();
		put(SoldierEnum.Piker, piker);
		put(SoldierEnum.Knight, knight);
		put(SoldierEnum.Onager, onager);
		put(SoldierEnum.Archer, archer);
		put(SoldierEnum.Berserker, archer);
		put(SoldierEnum.Spy, spy);
	}
	
	public void replace(SoldierEnum soldierEnum, T2 value)
	{
		super.replace(soldierEnum, value);
	}
	
	public T2 get(SoldierEnum soldierEnum)
	{
		return super.get(soldierEnum);
	}

	@Override
	public String toString()
	{
		return "SoldierPack [map=" + map + "]";
	}
}
