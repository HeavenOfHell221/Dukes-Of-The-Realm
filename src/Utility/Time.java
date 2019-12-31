package Utility;

import DukesOfTheRealm.Main;
import Interface.IUpdate;

/**
 * Utilitaire pour calculer le nombre d'image par seconde et g�rer le "temps r�el" gr�ce � la variable deltaTime.
 */
public class Time implements IUpdate
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Compte le nombre d'image dans la seconde en cours.
	 */
	private int counter;

	/**
	 * Le temps � la derni�re remise � z�ro du counter.
	 * @see Time#counter
	 */
	private long lastUpdate;

	/**
	 * Sp�cifie si le nombre d'image par seconde doit �tre affich� dans la console.
	 */
	private final boolean print;

	/**
	 * La valeur de now � l'image pr�c�dente.
	 */
	private long oldTime;

	/**
	 * Dur�e de l'image pr�c�dente.
	 * <p>
	 * Tr�s utile car elle permet d'avoir, peut importe le nombre d'image par seconde, un r�sultat constant.
	 * Exemple : Si je veux 10 Florin par seconde, je vais multiplier 10 par deltaTime. Ainsi, peut importe le nombre d'image par seconde, j'aurai toujours 10 Florin.
	 * Sans cela, je dois donner un nombre fixe de Florin � chaque image, donc nous n'avons pas la m�me quantit� avec un jeu � 60 FPS et un jeu � 30FPS..
	 * </p>
	 */
	public static double deltaTime;

	/**
	 * Sp�cifie si c'est la premi�re image du programme ou non.
	 */
	private boolean firstFrame;

	/**
	 * Nombre d'image calcul� durant la derni�re seconde.
	 */
	public static int FPS;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur de Time.
	 * @param print Sp�cifie si le nombre d'image par seconde doit �tre affich� dans la console. 
	 */
	public Time(final boolean print)
	{
		this.lastUpdate = 0;
		this.print = print;
		deltaTime = 0;
		this.oldTime = 0;
		this.firstFrame = true;
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		newFrame(now);
		if (now - this.lastUpdate >= Settings.GAME_FREQUENCY)
		{
			this.lastUpdate = now;
			if (this.print)
			{
				System.out.println(this.counter + " fps");
				System.out.println(Main.nbSoldier + " soldiers" + "\n");
			}
			FPS = this.counter;
			this.counter = 0;
		}
		this.counter++;
	}

	/**
	 * Calcul la dur�e de l'image pr�c�dente.
	 * @param now Le temps actuel.
	 */
	public void newFrame(final long now)
	{
		if (this.firstFrame)
		{
			this.firstFrame = false;
			this.oldTime = now;
			return;
		}
		deltaTime = now - this.oldTime; // Calcul du temps de l'image pr�c�dente
		deltaTime /= Settings.GAME_FREQUENCY; // Obtenir en seconde.
		this.oldTime = now;
	}
}
