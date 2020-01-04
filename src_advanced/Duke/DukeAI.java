package Duke;

import static Goals.GeneratorGoal.getNewGoal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Kingdom;
import Enums.CharacterCastleEnum;
import Enums.CharacterDukeEnum;
import SimpleGoal.Goal;
import Utility.Settings;

/**
 * Repr�sente les IA qui jouent contre le joueur.
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
	 * Le caract�re de ce Duke.
	 * @see Enums.CharacterDukeEnum
	 */
	private CharacterDukeEnum character;
	
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
	 * R�f�rence au royaume auquel appartient ce Duke.
	 *
	 * @see DukesOfTheRealm.Kingdom
	 * @see Goals.GeneratorGoal#getNewGoalBattle(Castle)
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
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		super.update(now, pause);

		// Toutes les 1 seconde, l'IA va compl�ter ses objectifs et en prendre un nouveau
		// si sont objectif courant est compl�ter.
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
		addNewCharacterInThisNewCastle(castle);
	}

	/**
	 * G�n�re un nouvel objectif pour le ch�teau en param�tre et le place dans la map.
	 *
	 * @param castle Le ch�teau courant.
	 * @see          Goals.GeneratorGoal
	 */
	private void putNewGoal(final Castle castle)
	{
		//Goal g = getNewGoal(castle, castle.getCharacter());
		//this.goalMap.put(castle, g);
		// System.out.println(this.name + " -> niveau[" + castle.getLevel() + "] -> " + g);
	}

	@Override
	protected void addOrRemoveCastleList()
	{
		this.castlesWaitForAdding.forEach(castle -> addNewCharacterInThisNewCastle(castle));
		this.castlesWaitForDelete.forEach(castle -> this.goalMap.remove(castle));

		super.addOrRemoveCastleList();
	}

	/**
	 * Bloque une action durant un certain temps pour qu'elle s'effectue � un intervalle r�gulier
	 * (inf�rieur � 1 fois par image). Dans cas, elle s'effectue toutes les 1 seconde.
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

	/**
	 * @param character the character to set
	 */
	public final void setCharacter(CharacterDukeEnum character)
	{
		this.character = character;
	}
	
	/**
	 * Change le caract�re de ce nouveau ch�teau.
	 * @param castle Le ch�teau que ce Duke vient d'acqu�rir.
	 */
	private void addNewCharacterInThisNewCastle(Castle castle)
	{
		CharacterCastleEnum character = CharacterCastleEnum.getRandomType(this.character);
		castle.setCharacter(character);
	}
}
