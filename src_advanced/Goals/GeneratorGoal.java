package Goals;

import java.io.Serializable;
import java.util.Random;
import Duke.Actor;
import Duke.DukeAI;
import DukesOfTheRealm.Castle;
import Enums.GoalEnum;
import SimpleGoal.CastleGoal;
import SimpleGoal.Goal;
import SimpleGoal.SaveFlorinGoal;
import Utility.SoldierPack;

/**
 * Génère des objectifs aléatoire pour les IA.
 */
public class GeneratorGoal implements Serializable
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Instance de la classe GeneratorGoal. Utilisé comme singleton.
	 */
	private final static GeneratorGoal instance = new GeneratorGoal();

	/**
	 * Objet Random utilisé pour l'aléatoire.
	 */
	public final static Random rand = new Random();

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par défaut de GeneratorGoal.
	 */
	private GeneratorGoal()
	{

	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * Prend au hasard un type d'objectif et retoune un objectif de ce type.
	 *
	 * @param  castle    Le château qui aurra l'objectif à accomplir.
	 * @param  character Le caractère du château qui aurra l'objectif.
	 * @return           Un objectif pour l'IA.
	 * @see              Enums.GoalEnum
	 */
	public static Goal getNewGoal(final Castle castle)
	{
		switch (GoalEnum.getRandomType())
		{
			case Backup:
				return getNewGoalBackup(castle);
			case Battle:
				return getNewGoalBattle(castle);
			case Finance:
				return getNewGoalFinance(castle);
			case Production:
				return getNewGoalProduction(castle);
			case Building:
				return getNewGoalBuilding(castle);
			default:
				break;
		}
		return null;
	}

	/**
	 * Génère un objectif lié aux bâtiments (amélioration de niveau).
	 *
	 * @param  castle Le château qui aurra l'objectif à accomplir.
	 * @return        Un objectif.
	 */
	private static Goal getNewGoalBuilding(final Castle castle)
	{
		return new CastleGoal(castle);
	}

	/**
	 * Génère un objectif lié au Florin.
	 *
	 * @param  castle Le château qui aurra l'objectif à accomplir.
	 * @return        Un ojectif.
	 */
	private static Goal getNewGoalFinance(final Castle castle)
	{
		// lvl.1 -> entre 100 et 300
		// lvl.10 -> entre 100 et 2100
		return new SaveFlorinGoal(rand.nextInt(201) * castle.getLevel() + 100);
	}

	/**
	 * Génère un objectif lié aux attaques avec une ost.
	 * <p>
	 * Récupère la liste des acteurs en supprimant l'acteur qui demande la liste. <br>
	 * Les attaques ne contiennent pas de Piker.
	 * </p>
	 *
	 * @param  castle Le château qui aurra l'objectif à accomplir.
	 * @return        Un objectif.
	 */
	private static Goal getNewGoalBattle(final Castle castle)
	{
		castle.getLevel();
		final DukeAI actor = (DukeAI) castle.getActor();
		final Actor actorTarget = actor.getKingdom().getRandomActor(actor);

		if (actorTarget.getCastles().size() <= 0)
		{
			return null;
		}

		actorTarget.getCastles().get(rand.nextInt(actorTarget.getCastles().size()));
		AttackGoal g = null;

		switch (rand.nextInt(2))
		{
			case 0:
				// Lvl.1 -> entre [0, 0, 0] et [0, 13, 0]
				// Lvl.10 -> entre [0, 0, 0] et [0, 49, 0]
				// g = new AttackGoal(castle, castleTarget, 0, rand.nextInt(10 + lvl) + rand.nextInt(4) * lvl, 0);
				break;
			case 1:
				// Lvl.1 -> entre [0, 0, 0] et [0, 8, 5]
				// Lvl.10 -> entre [0, 0, 0] et [0, 35, 23]
				// g = new AttackGoal(castle, castleTarget, 0, rand.nextInt(6 + lvl) + rand.nextInt(3) * lvl,
				// rand.nextInt(4 + lvl) + rand.nextInt(2) * lvl);
				break;
			default:
				break;
		}
		return g;
	}

	/**
	 * Génère un objectif lié aux renforts.
	 *
	 * @param  castle Le château qui aurra l'objectif à accomplir.
	 * @return        Un objectif.
	 */
	private static Goal getNewGoalBackup(final Castle castle)
	{
		switch (rand.nextInt(2))
		{
			case 0:
				// La moitié des Pikers et des Knights
				// return new BackupGoal(castle, castle.getNbPikers() / 2, castle.getNbKnights() / 2, 0);
			case 1:
				// Tout les Knights
				// return new BackupGoal(castle, 0, castle.getNbKnights(), 0);
			default:
				break;
		}
		return null;
	}

	/**
	 * Génère un objectif visant à produire des unités.
	 *
	 * @param  castle    Le château qui aurra l'objectif à accomplir.
	 * @param  character Le caractère du château qui aura cette objectif.
	 * @return           Un objectif.
	 */
	private static Goal getNewGoalProduction(final Castle castle)
	{

		/*
		 * switch (rand.nextInt(7)) {
		 * 
		 * // Piker case 0: return new MultiSoldierGoal(castle, rand.nextInt(12 + lvl) + rand.nextInt(3) *
		 * lvl, 0, 0); // Knight case 1: return new MultiSoldierGoal(castle, 0, rand.nextInt(8 + lvl) +
		 * rand.nextInt(3) * lvl, 0); // Onager case 2: return new MultiSoldierGoal(castle, 0, 0,
		 * rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl); // Piker + Knight case 3: return new
		 * MultiSoldierGoal(castle, rand.nextInt(8 + lvl) + rand.nextInt(2) * lvl, rand.nextInt(6 + lvl) +
		 * rand.nextInt(2) * lvl, 0); // Piker + Onager case 4: return new MultiSoldierGoal(castle,
		 * rand.nextInt(8 + lvl) + rand.nextInt(2) * lvl, 0, rand.nextInt(5 + lvl) + rand.nextInt(2) * lvl);
		 * // Knight + Onager case 5: return new MultiSoldierGoal(castle, 0, rand.nextInt(8 + lvl) +
		 * rand.nextInt(2) * lvl, rand.nextInt(4 + lvl) + rand.nextInt(2) * lvl); // Piker + Knight + Onager
		 * case 6: return new MultiSoldierGoal(castle, rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl,
		 * rand.nextInt(4 + lvl) + rand.nextInt(2) * lvl, rand.nextInt(4 + lvl) + rand.nextInt(2) * lvl);
		 * default: break;
		 * 
		 * }
		 */
		return null;
	}
}
