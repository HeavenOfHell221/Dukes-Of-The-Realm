package Interface;

/**
 * Interface utilis� pour tout les objets pouvant �tre am�lior� via le syst�me de production. Permet
 * le polymorphisme entre les unit�s et les b�timents.
 */
public interface IProductionUnit
{
	/**
	 * @return Retourne le temps de production de l'objet.
	 */
	double getProductionTime();

	/**
	 * @return Retourne le co�t de l'am�lioration.
	 */
	int getProductionCost();
}
