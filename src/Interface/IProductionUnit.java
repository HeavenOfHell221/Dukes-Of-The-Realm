package Interface;

import DukesOfTheRealm.Caserne;
import DukesOfTheRealm.Castle;
import DukesOfTheRealm.ReserveOfSoldiers;

/**
 * Interface utilisé pour tout les objects pouvant être améliorer via le système de production.
 * Permet le polymorphisme entre les unités et les bâtiments.
 */
public interface IProductionUnit
{
	/**
	 * @return Retourne le temps de production de l'objet.
	 */
	double getProductionTime();

	/**
	 * @return Retourne le coût de l'amélioration.
	 */
	int getProductionCost();

	/**
	 * Ajoute une unité dans la réserve.
	 *
	 * @param reserveOfSoldiers La réserve où va être ajouté les unités.
	 */
	default void addProduction(final ReserveOfSoldiers reserveOfSoldiers)
	{

	}

	/**
	 * Améliore le château en paramètre.
	 *
	 * @param castle Le château qui va augmenter de niveau.
	 */
	default void CastleUp(final Castle castle)
	{

	}

	/**
	 * Une fois la production terminé, on retire 1 au nombre de production dans la queue.
	 *
	 * @param caserne La caserne où l'ont va retirer une production terminé.
	 */
	void removeInProduction(Caserne caserne);
}
