package Interface;

import DukesOfTheRealm.Caserne;
import DukesOfTheRealm.Castle;
import DukesOfTheRealm.ReserveOfSoldiers;

/**
 * Interface utilis� pour tout les objects pouvant �tre am�liorer via le syst�me de production.
 * Permet le polymorphisme entre les unit�s et les b�timents.
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
	 * Ajoute une unit� dans la r�serve.
	 *
	 * @param reserveOfSoldiers La r�serve o� va �tre ajout� les unit�s.
	 */
	default void addProduction(final ReserveOfSoldiers reserveOfSoldiers)
	{

	}

	/**
	 * Am�liore le ch�teau en param�tre.
	 *
	 * @param castle Le ch�teau qui va augmenter de niveau.
	 */
	default void CastleUp(final Castle castle)
	{

	}

	/**
	 * Une fois la production termin�, on retire 1 au nombre de production dans la queue.
	 *
	 * @param caserne La caserne o� l'ont va retirer une production termin�.
	 */
	void removeInProduction(Caserne caserne);
}
