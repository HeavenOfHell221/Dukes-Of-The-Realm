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
 * Classe d�riv�e d'Actor, repr�sente les IA qui jouent contre le joueur.
 * 
 * @see Actor
 */
public class DukeAI extends Actor implements Serializable
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Derni�re fois que ce Duke � v�rifi� ses objectifs.
	 * 
	 * @see DukeAI#time(long, boolean)
	 * @see DukeAI#update(long, boolean)
	 */
	private long lastTime;

	/**
	 * Map des objectifs pour chaque ch�teau de ce Duke.
	 * <p>
	 * Cl�: Un ch�teau, Valeur: Son objectif associ�.
	 * </p>
	 * 
	 * @see DukeAI#update(long, boolean)
	 * @see DukeAI#putNewGoal(Castle)
	 */
	private final HashMap<Castle, Goal> goalMap;

	/**
	 * R�f�rence au royaume auquel appartient ce DukeIA.
	 * 
	 * @see DukesOfTheRealm.Kingdom
	 * @see Goal.GeneratorGoal#getNewGoalBattle(Castle)
	 */
	private Kingdom kingdom;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par d�faut de DukeIA.
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
	 * G�n�re un nouvel objectif pour le ch�teau en param�tre et le place dans la map.
	 * 
	 * @param castle Le ch�teau courant.
	 * @see          Goal.GeneratorGoal
	 */
	private void putNewGoal(final Castle castle)
	{
		Goal g = getNewGoal(castle);
		this.goalMap.put(castle, g);
		//System.out.println(this.name + " -> niveau[" + castle.getLevel() + "] -> " + g);
	}

	/**
	 * Bloque une action durant un certain temps pour qu'elle s'effectue � un intervalle r�gulier
	 * (inf�rieur � 1 fois par image).
	 * 
	 * @param  now   Le temps �coul� depuis la cr�ation du programme.
	 * @param  pause Boolean sp�cifiant si la pause est activ� ou non.
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
