package Interface;

public interface IBuilding
{
	/**
	 * Initialise le niveau de ce bâtiment au niveau voulu.
	 * @param level
	 */
	void setLevel(int level);

	/**
	 * @return Retourne le niveau de ce bâtiment.
	 */
	int getLevel();

	/**
	 * Augmente le niveau de ce bâtiment de 1 et actualise ses données s'il en a.
	 */
	void levelUp();
}