package Utility;

import Enums.SoldierEnum;

/**
 * Structure de donn�es utilis� pour stocker un nombre, un bouton, du texte... Tout type d'objet qui est li� � un type d'unit�.
 * <p>
 * Cl� : Le type d'une unit� sous la forme de SoldierEnum.
 * Valeur: Un objet de type T2 (g�n�rique).
 * Cette structure de donn�e permet de ne plus prendre en compte le nombre de type d'unit�. <br>
 * En it�rant sur toutes �num�rations de SoldierEnum, on est s�r de parcourir la HashMap enti�re.
 * </p>
 * @see Enums.SoldierEnum
 */
public class SoldierPack<T2> extends Pack<SoldierEnum, T2>
{

	/**
	 * Constructeur par d�faut de SoldierPack.
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
	 * @param spy       Un nombre pour le Spy.
	 * @param conveyors Un nombee pour le Conveyors.
	 */
	public SoldierPack(final T2 piker, final T2 knight, final T2 onager, final T2 archer, final T2 berserker, final T2 spy, final T2 conveyors)
	{
		super();
		put(SoldierEnum.Piker, piker);
		put(SoldierEnum.Knight, knight);
		put(SoldierEnum.Onager, onager);
		put(SoldierEnum.Archer, archer);
		put(SoldierEnum.Berserker, archer);
		put(SoldierEnum.Spy, spy);
		put(SoldierEnum.Conveyors, conveyors);
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
