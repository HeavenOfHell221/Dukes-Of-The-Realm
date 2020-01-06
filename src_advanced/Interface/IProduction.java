package Interface;

import DukesOfTheRealm.Caserne;
import DukesOfTheRealm.Castle;

/**
 * Interface utilisé pour tout les objets pouvant être amélioré via le système de production. Permet
 * le polymorphisme entre les unités et les bâtiments.
 */
public interface IProduction
{
	/**
	 * @param  castle Le château qui demande cette production.
	 * @param  level  Le niveau de la production.
	 * @return        Retourne le temps de production de l'objet.
	 */
	double getProductionTime(Castle castle, int level);

	/**
	 *
	 * @param  level Le niveau du bâtiment.
	 * @return       Retourne le coût de l'amélioration.
	 */
	int getProductionCost(int level);

	/**
	 * Fait l'action de fin de production (amélioration ou ajout d'unité) sur le château en paramètre.
	 *
	 * @param castle Le château à qui appartient la production terminé.
	 * @param cancel Est ce que c'est une unité annulé ou non.
	 */
	void productionFinished(Castle castle, boolean cancel);

	/**
	 * Ajoute 1 à l'amélioration ou au type d'unité qui va être produit.
	 *
	 * @param castle  Le château à qui appartient la production qui commence.
	 * @param caserne La caserne qui produit cette production.
	 */
	void productionStart(Caserne caserne);

	/**
	 * @return Retourne le niveau de la production.
	 */
	int getLevel();
}
