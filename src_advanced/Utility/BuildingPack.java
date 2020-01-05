package Utility;

import Enums.BuildingEnum;

/**
 * Structure de donn�es utilis� pour stocker un nombre, un bouton, du texte... Tout type d'objet qui
 * est li� � un type de b�timent.
 * <p>
 * Cl� : Le type du b�timent sous la forme de BuildingEnum. <br>
 * Valeur: Un objet de type T2 (g�n�rique). <br>
 * Cette structure de donn�e permet de ne plus prendre en compte le nombre de type de b�timent. <br>
 * En it�rant sur toutes �num�rations de BuildingEnum, on est s�r de parcourir la HashMap enti�re.
 * </p>
 * 
 * @see Enums.BuildingEnum
 */
public class BuildingPack<T2> extends Pack<BuildingEnum, T2>
{
	/**
	 * Constructeur par d�faut de BuildingPack.
	 */
	public BuildingPack()
	{
		super();
		for (BuildingEnum b : BuildingEnum.values())
		{
			put(b, null);
		}
	}

	/**
	 * Constructeur de BuildingPack.
	 * 
	 * @param castle
	 * @param caserne
	 * @param market
	 * @param miller
	 * @param wall
	 */
	public BuildingPack(final T2 castle, final T2 caserne, final T2 market, final T2 miller, final T2 wall)
	{
		super();
		put(BuildingEnum.Castle, castle);
		put(BuildingEnum.Wall, wall);
		put(BuildingEnum.Caserne, caserne);
		put(BuildingEnum.Market, market);
		put(BuildingEnum.Miller, miller);
	}

	/**
	 * @param  key La cl� avec laquelle on obtient la valeur recherch�.
	 * @return     Retourne la valeur li� � la cl� en param�tre.
	 */

	public T2 get(final BuildingEnum key)
	{
		return super.get(key);
	}

	@Override
	public void replace(final BuildingEnum key, final T2 value)
	{
		super.replace(key, value);
	}

}
