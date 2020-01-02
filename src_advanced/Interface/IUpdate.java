package Interface;

/**
 * Interface utilisée pour les classes ayant besoin d'être mise à jour à chaque image.
 */
public interface IUpdate
{
	/**
	 * Méthode appelée pour initialiser un objet (indépendamment du constructeur).
	 */
	default void start()
	{

	}

	/**
	 * Met à jour l'objet en question.
	 * <p>
	 * Cette méthode est appelé à chaque image. <br>
	 * Attention: Comme elles sont appelées à chaque image et pour tout objet l'ayant, leur temps
	 * d'exécution impacte grandement le nombre de FPS en jeu.
	 * </p>
	 *
	 * @param now   Le temps écoulé depuis la création du programme.
	 * @param pause Boolean spécifiant si la pause est activé ou non.
	 */
	void update(final long now, final boolean pause);
}
