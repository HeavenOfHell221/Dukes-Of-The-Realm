package Duke;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Kingdom;
import SimpleGoal.Goal;
import Utility.Settings;

/**
 * Représente les IA qui jouent contre le joueur.
 *
 * <p>
 * Extends de la classe Actor.
 * </p>
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
	 * Référence au royaume auquel appartient ce Duke.
	 *
	 * @see DukesOfTheRealm.Kingdom
	 * @see Goals.GeneratorGoal#getNewGoalBattle(Castle)
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
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		super.update(now, pause);

		// Toutes les 1 seconde, l'IA va compléter ses objectifs et en prendre un nouveau
		// si sont objectif courant est compléter.
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
	 * @see          Goals.GeneratorGoal
	 */
	private void putNewGoal(final Castle castle)
	{
		// Goal g = getNewGoal(castle, castle.getCharacter());
		// this.goalMap.put(castle, g);
		// System.out.println(this.name + " -> niveau[" + castle.getLevel() + "] -> " + g);
	}

	@Override
	protected void addOrRemoveCastleList()
	{
		this.castlesWaitForDelete.forEach(castle -> this.goalMap.remove(castle));

		super.addOrRemoveCastleList();
	}

	/**
	 * Bloque une action durant un certain temps pour qu'elle s'effectue à un intervalle régulier
	 * (inférieur à 1 fois par image). Dans cas, elle s'effectue toutes les 1 seconde.
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
		if (now - this.lastTime > Settings.GAME_FREQUENCY)
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

	/**
	 * @param kingdom the kingdom to set
	 */
	public final void setKingdom(final Kingdom kingdom)
	{
		this.kingdom = kingdom;
	}
}
