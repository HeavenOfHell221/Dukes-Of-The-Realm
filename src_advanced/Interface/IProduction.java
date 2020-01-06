package Interface;

import DukesOfTheRealm.Caserne;
import DukesOfTheRealm.Castle;

/**
 * Interface utilis� pour tout les objets pouvant �tre am�lior� via le syst�me de production. Permet
 * le polymorphisme entre les unit�s et les b�timents.
 */
public interface IProduction
{
	/**
	 * @param  castle Le ch�teau qui demande cette production.
	 * @param  level  Le niveau de la production.
	 * @return        Retourne le temps de production de l'objet.
	 */
	double getProductionTime(Castle castle, int level);

	/**
	 *
	 * @param  level Le niveau du b�timent.
	 * @return       Retourne le co�t de l'am�lioration.
	 */
	int getProductionCost(int level);

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
	 * @param caserne La caserne qui produit cette production.
	 */
	void productionStart(Caserne caserne);

	/**
	 * @return Retourne le niveau de la production.
	 */
	int getLevel();
}
