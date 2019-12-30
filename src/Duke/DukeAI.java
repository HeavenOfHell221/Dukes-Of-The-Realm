package Duke;

import static Goal.GeneratorGoal.getNewGoal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Kingdom;
import SimpleGoal.Goal;
import Utility.Settings;

/**
 * Classe dérivée d'Actor, représente les IA qui jouent contre le joueur.
 * 
 * @see Actor
 */
public class DukeAI extends Actor implements Serializable
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Dernière fois que ce Duke à vérifié ses objectifs.
	 * 
	 * @see DukeAI#time(long, boolean)
	 * @see DukeAI#update(long, boolean)
	 */
	private long lastTime;

	/**
	 * Map des objectifs pour chaque château de ce Duke.
	 * <p>
	 * Clé: Un château, Valeur: Son objectif associé.
	 * </p>
	 * 
	 * @see DukeAI#update(long, boolean)
	 * @see DukeAI#putNewGoal(Castle)
	 */
	private final HashMap<Castle, Goal> goalMap;

	/**
	 * Référence au royaume auquel appartient ce DukeIA.
	 * 
	 * @see DukesOfTheRealm.Kingdom
	 * @see Goal.GeneratorGoal#getNewGoalBattle(Castle)
	 */
	private Kingdom kingdom;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par défaut de DukeIA.
	 */
	public DukeAI()
	{
		super();
		this.goalMap = new HashMap<>();
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	/**
	 *
	 * @param kingdom
	 */
	public void start(final Kingdom kingdom)
	{
		this.kingdom = kingdom;
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		super.update(now, pause);

		if (time(now, pause))
		{
			Iterator<Castle> it = this.castles.iterator();

			while (it.hasNext())
			{
				Castle castle = it.next();
				if (this.goalMap.containsKey(castle))
				{
					Goal g = this.goalMap.get(castle);
					if (g == null || g.isGoalIsCompleted(castle))
					{
						putNewGoal(castle);
					}
				}
				else
				{
					putNewGoal(castle);
				}
			}
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public void addFirstCastle(final Castle castle)
	{
		super.addFirstCastle(castle);
		castle.startSoldier();
	}

	/**
	 * Génère un nouvel objectif pour le château en paramètre et le place dans la map.
	 * 
	 * @param castle Le château courant.
	 * @see          Goal.GeneratorGoal
	 */
	private void putNewGoal(final Castle castle)
	{
		Goal g = getNewGoal(castle);
		this.goalMap.put(castle, g);
		//System.out.println(this.name + " -> niveau[" + castle.getLevel() + "] -> " + g);
	}

	/**
	 * Bloque une action durant un certain temps pour qu'elle s'effectue à un intervalle régulier
	 * (inférieur à 1 fois par image).
	 * 
	 * @param  now   Le temps écoulé depuis la création du programme.
	 * @param  pause Boolean spécifiant si la pause est activé ou non.
	 * @return       Retourne true si l'action est possible, false sinon.
	 */
	private boolean time(final long now, final boolean pause)
	{
		if (pause)
		{
			this.lastTime = now;
		}
		if (now - this.lastTime > Settings.GAME_FREQUENCY / 2)
		{
			this.lastTime = now;
			return true;
		}
		return false;
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return the kingdom
	 */
	public final Kingdom getKingdom()
	{
		return this.kingdom;
	}

}
