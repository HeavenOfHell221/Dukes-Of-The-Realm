package Interface;

/**
 * Interface utilis�e pour les classes ayant besoin d'�tre mise � jour � chaque image.
 */
public interface IUpdate
{
	/**
	 * M�thode appel�e pour initialiser un objet (ind�pendamment du constructeur).
	 */
	default void start()
	{

	}

	/**
	 * Met � jour l'objet en question.
	 * <p>
	 * Cette m�thode est appel� � chaque image. <br>
	 * Attention: Comme elles sont appel�es � chaque image et pour tout objet l'ayant, leur temps
	 * d'ex�cution impacte grandement le nombre de FPS en jeu.
	 * </p>
	 *
	 * @param now   Le temps �coul� depuis la cr�ation du programme.
	 * @param pause Boolean sp�cifiant si la pause est activ� ou non.
	 */
	void update(final long now, final boolean pause);
}
