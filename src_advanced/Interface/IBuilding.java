package Interface;

public interface IBuilding
{
	/**
	 * Initialise le niveau de ce b�timent au niveau voulu.
	 * @param level
	 */
	void setLevel(int level);

	/**
	 * @return Retourne le niveau de ce b�timent.
	 */
	int getLevel();

	/**
	 * Augmente le niveau de ce b�timent de 1 et actualise ses donn�es s'il en a.
	 */
	void levelUp();
}