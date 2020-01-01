package Interface;

/**
 * Interface utilisé pour tout les objets pouvant être amélioré via le système de production. Permet
 * le polymorphisme entre les unités et les bâtiments.
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
}
