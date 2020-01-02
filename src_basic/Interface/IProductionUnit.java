package Interface;

import DukesOfTheRealm.Castle;

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
	 * @param  castle Le ch�teau qui demande le co�t.
	 * @return        Retourne le co�t de l'am�lioration.
	 */
	int getProductionCost(Castle castle);

	/**
	 * Fait l'action de fin de production (am�lioration ou ajout d'unit�) sur le ch�teau en param�tre.
	 *
	 * @param castle Le ch�teau � qui appartient la production termin�.
	 * @param cancel Est ce que c'est une unit� annul� ou non.
	 */
	void productionFinished(Castle castle, boolean cancel);

	/**
	 * Ajoute 1 � l'am�lioration ou au type d'unit� qui va �tre produit.
	 *
	 * @param castle Le ch�teau � qui appartient la production qui commence.
	 */
	void productionStart(Castle castle);
}
