package Interface;

/**
 * Interface utilis� pour les classes ayant besoin d'�tre mise � jour � chaque image.
 */
public interface IUpdate
{
	/**
	 * M�thode appel� pour initialiser un objet (ind�pendamment du constructeur).
	 */
	default void start()
	{

	}

	/**
	 * Met � jour l'objet en question.
	 * <p>
	 * Cette m�thode est appel� � chaque image.
	 * </p>
	 * 
	 * @param now   Le temps �coul� depuis la cr�ation du programme.
	 * @param pause Boolean sp�cifiant si la pause est activ� ou non.
	 */
	void update(long now, boolean pause);
}
