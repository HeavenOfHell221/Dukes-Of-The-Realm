package Interface;

/**
 * Interface utilisé pour les classes ayant besoin d'être mise à jour à chaque image.
 */
public interface IUpdate
{
	/**
	 * Méthode appelé pour initialiser un objet (indépendamment du constructeur).
	 */
	default void start()
	{
		
	}

	/**
	 * Met à jour l'objet en question.
	 * <p>
	 * Cette méthode est appelé à chaque image.
	 * </p>
	 * @param now Le temps écoulé depuis la création du programme.
	 * @param pause Boolean spécifiant si la pause est activé ou non.
	 */
	void update(long now, boolean pause);
}
