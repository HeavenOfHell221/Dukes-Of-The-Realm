package Utility;

import Enums.BuildingEnum;

/**
 * Structure de données utilisé pour stocker un nombre, un bouton, du texte... Tout type d'objet qui
 * est lié à un type de bâtiment.
 * <p>
 * Clé : Le type du bâtiment sous la forme de BuildingEnum. <br>
 * Valeur: Un objet de type T2 (générique). <br>
 * Cette structure de donnée permet de ne plus prendre en compte le nombre de type de bâtiment. <br>
 * En itérant sur toutes énumérations de BuildingEnum, on est sûr de parcourir la HashMap entière.
 * </p>
 * 
 * @see Enums.BuildingEnum
 */
public class BuildingPack<T2> extends Pack<BuildingEnum, T2>
{
	/**
	 * Constructeur de BuildingPack.
	 * 
	 * @param castle Un objet pour le château.
	 * @param caserne Un objet pour la caserne.
	 * @param market Un objet pour le marché.
	 * @param miller Un objet pour le moulin.
	 * @param wall Un objet pour le rempart.
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
	 * @param  key La clé avec laquelle on obtient la valeur recherché.
	 * @return     Retourne la valeur lié à la clé en paramètre.
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
