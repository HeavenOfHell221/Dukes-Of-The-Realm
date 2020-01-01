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
	 * @return Retourne le co�t de l'am�lioration.
	 */
	int getProductionCost();
	
	/**
	 * Fait l'action de fin de production (am�lioration ou ajout d'unit�) sur le ch�teau en param�tre.
	 * @param castle Le ch�teau � qui appartient la production termin�.
	 */
	void productionFinished(Castle castle);
	
	/**
	 * Ajoute 1 � l'am�lioration ou au type d'unit� qui va �tre produit.
	 * @param castle Le ch�teau � qui appartient la production qui commence.
	 */
	void productionStart(Castle castle);
}
