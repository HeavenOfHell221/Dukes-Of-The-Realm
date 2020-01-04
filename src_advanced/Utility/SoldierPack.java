package Utility;

import Enums.SoldierEnum;

/**
 * Structure de données utilisé pour stocker un nombre pour chaque type d'unité (nombre, point de
 * vie, ..)
 */
public class SoldierPack<T2> extends Pack<SoldierEnum, T2>
{

	/**
	 * Constructeur par défaut de SoldierPack.
	 */
	public SoldierPack()
	{
		super();
		for (SoldierEnum sEnum : SoldierEnum.values())
		{
			put(sEnum, null);
		}
	}

	/**
	 * Constructeur par recopie.
	 * 
	 * @param soldierPack Le SoldierPack qu'on recopie.
	 */
	public SoldierPack(final SoldierPack<T2> s)
	{
		super();
		putAll(s.map);
	}

	/**
	 * Constructeur de SoldierPack.
	 * 
	 * @param piker     Un nombre pour le Piker.
	 * @param knight    Un nombre pour le Knight.
	 * @param onager    Un nombre pour l'Onager.
	 * @param archer    Un nombre pour l'Archer.
	 * @param berserker Un nombre pour le Berserker.
	 * @param spy       un nombre pour le Spy.
	 */
	public SoldierPack(final T2 piker, final T2 knight, final T2 onager, final T2 archer, final T2 berserker, final T2 spy)
	{
		super();
		put(SoldierEnum.Piker, piker);
		put(SoldierEnum.Knight, knight);
		put(SoldierEnum.Onager, onager);
		put(SoldierEnum.Archer, archer);
		put(SoldierEnum.Berserker, archer);
		put(SoldierEnum.Spy, spy);
	}

	@Override
	public void replace(final SoldierEnum soldierEnum, final T2 value)
	{
		super.replace(soldierEnum, value);
	}

	public T2 get(final SoldierEnum soldierEnum)
	{
		return super.get(soldierEnum);
	}
}
