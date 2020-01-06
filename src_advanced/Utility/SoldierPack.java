package Utility;

import Enums.SoldierEnum;

/**
 * Structure de données utilisé pour stocker un nombre, un bouton, du texte... Tout type d'objet qui est lié à un type d'unité.
 * <p>
 * Clé : Le type d'une unité sous la forme de SoldierEnum.
 * Valeur: Un objet de type T2 (générique).
 * Cette structure de donnée permet de ne plus prendre en compte le nombre de type d'unité. <br>
 * En itérant sur toutes énumérations de SoldierEnum, on est sûr de parcourir la HashMap entière.
 * </p>
 * @see Enums.SoldierEnum
 */
public class SoldierPack<T2> extends Pack<SoldierEnum, T2>
{
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
	 * @param piker     Un objet pour le Piker.
	 * @param knight    Un objet pour le Knight.
	 * @param onager    Un objet pour l'Onager.
	 * @param archer    Un objet pour l'Archer.
	 * @param berserker Un objet pour le Berserker.
	 * @param spy       Un objet pour le Spy.
	 * @param conveyors Un objet pour le Conveyors.
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

	/**
	 * Récupère la valeur associé à la clé en paramètre.
	 * @param soldierEnum La clé associé à la valeur qu'on veut.
	 * @return La valeur.
	 */
	public T2 get(final SoldierEnum soldierEnum)
	{
		return super.get(soldierEnum);
	}
}
