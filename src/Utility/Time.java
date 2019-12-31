package Utility;

import DukesOfTheRealm.Main;
import Interface.IUpdate;

/**
 * Utilitaire pour calculer le nombre d'image par seconde et gérer le "temps réel" grâce à la variable deltaTime.
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
	 * Le temps à la dernière remise à zéro du counter.
	 * @see Time#counter
	 */
	private long lastUpdate;

	/**
	 * Spécifie si le nombre d'image par seconde doit être affiché dans la console.
	 */
	private final boolean print;

	/**
	 * La valeur de now à l'image précédente.
	 */
	private long oldTime;

	/**
	 * Durée de l'image précédente.
	 * <p>
	 * Très utile car elle permet d'avoir, peut importe le nombre d'image par seconde, un résultat constant.
	 * Exemple : Si je veux 10 Florin par seconde, je vais multiplier 10 par deltaTime. Ainsi, peut importe le nombre d'image par seconde, j'aurai toujours 10 Florin.
	 * Sans cela, je dois donner un nombre fixe de Florin à chaque image, donc nous n'avons pas la même quantité avec un jeu à 60 FPS et un jeu à 30FPS..
	 * </p>
	 */
	public static double deltaTime;

	/**
	 * Spécifie si c'est la première image du programme ou non.
	 */
	private boolean firstFrame;

	/**
	 * Nombre d'image calculé durant la dernière seconde.
	 */
	public static int FPS;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur de Time.
	 * @param print Spécifie si le nombre d'image par seconde doit être affiché dans la console. 
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
	 * Calcul la durée de l'image précédente.
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
		deltaTime = now - this.oldTime; // Calcul du temps de l'image précédente
		deltaTime /= Settings.GAME_FREQUENCY; // Obtenir en seconde.
		this.oldTime = now;
	}
}
