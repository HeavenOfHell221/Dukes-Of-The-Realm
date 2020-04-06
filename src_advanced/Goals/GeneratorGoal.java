package Goals;

import java.io.Serializable;
import java.util.Random;

import Duke.Actor;
import Duke.DukeAI;
import DukesOfTheRealm.Castle;
import Enums.BuildingEnum;
import Enums.GoalEnum;
import Enums.SoldierEnum;
import SimpleGoal.BuildingGoal;
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
	 * 
	 */
	private static final long serialVersionUID = 4895815065254712313L;

	/**
	 * Instance de la classe GeneratorGoal. Utilisé comme singleton.
	 */
	@SuppressWarnings("unused")
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
		return new BuildingGoal(BuildingEnum.getRandomTypeForAI());
	}

	/**
	 * Génère un objectif lié au Florin.
	 *
	 * @param  castle Le château qui aurra l'objectif à accomplir.
	 * @return        Un ojectif.
	 */
	private static Goal getNewGoalFinance(final Castle castle)
	{
		return new SaveFlorinGoal(rand.nextInt(1001) * castle.getLevel() + 200);
	}

	/**
	 * Génère un objectif lié aux attaques avec une ost.
	 * <p>
	 * Récupère la liste des acteurs en supprimant l'acteur qui demande la liste. <br>
	 * Les attaques ne contiennent pas d'unité défensive.
	 * </p>
	 *
	 * @param  castle Le château qui aurra l'objectif à accomplir.
	 * @return        Un objectif.
	 */
	private static Goal getNewGoalBattle(final Castle castle)
	{
		final DukeAI actor = (DukeAI) castle.getActor();
		final Actor actorTarget = actor.getKingdom().getRandomActor(actor);

		if (actorTarget.getCastles().size() <= 0)
		{
			return null;
		}

		Castle destination = actorTarget.getCastles().get(rand.nextInt(actorTarget.getCastles().size()));
		AttackGoal g = null;

		final SoldierPack<Integer> soldierPack = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);

		switch (rand.nextInt(6))
		{
			// Full Berserker
			case 0:
				final int berserker = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Berserker);
				if(berserker >= 15)
				{
					soldierPack.replace(SoldierEnum.Berserker, berserker);
					g = new AttackGoal(castle, destination, soldierPack);
				}
				break;
			// Full Knight
			case 1:
				final int knight = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight);
				if(knight >= 9)
				{
					soldierPack.replace(SoldierEnum.Knight, knight);
					g = new AttackGoal(castle, destination, soldierPack);
				}
				break;
			// Berserker / Knight
			case 2:
				final int berserker1 = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Berserker);
				final int knight1 = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight);
				
				if(berserker1 >= 12 && knight1 >= 6)
				{
					soldierPack.replace(SoldierEnum.Berserker, berserker1);
					soldierPack.replace(SoldierEnum.Knight, knight1);
					g = new AttackGoal(castle, destination, soldierPack);
				}
				break;
			// Berserker / Onager
			case 3:
				final int berserker2 = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Berserker);
				final int onager = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Onager);
				
				if(berserker2 >= 12 && onager >= 6)
				{
					soldierPack.replace(SoldierEnum.Onager, onager);
					soldierPack.replace(SoldierEnum.Berserker, berserker2);
					g = new AttackGoal(castle, destination, soldierPack);
				}
				break;
			// Knight / Onager
			case 4:
				final int knight2 = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight);
				final int onager2 = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Onager);
				
				if(knight2 >= 9 && onager2 >= 6)
				{
					soldierPack.replace(SoldierEnum.Onager, onager2);
					soldierPack.replace(SoldierEnum.Knight, knight2);
					g = new AttackGoal(castle, destination, soldierPack);
				}
				break;
			// Berserker / Knight / Onager
			case 5:
				final int berserker3 = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Berserker);
				final int knight3 = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight);
				final int onager3 = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Onager);
				
				if(berserker3 >= 12 && knight3 >= 9 && onager3 >= 6)
				{
					soldierPack.replace(SoldierEnum.Berserker, berserker3);
					soldierPack.replace(SoldierEnum.Knight, knight3);
					soldierPack.replace(SoldierEnum.Onager, onager3);
					g = new AttackGoal(castle, destination, soldierPack);
				}
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
		final SoldierPack<Integer> soldierPack = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);

		switch (rand.nextInt(2))
		{
			// Piker / Archer
			case 0:
				soldierPack.replace(SoldierEnum.Piker, (int) (castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Piker) / 2d));
				soldierPack.replace(SoldierEnum.Archer, (int) (castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Archer) / 2d));
				// Full Knight
			case 1:
				soldierPack.replace(SoldierEnum.Knight, (int) (castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight) / 2d));
			default:
				break;
		}
		return null;
	}

	/**
	 * Génère un objectif visant à produire des unités.
	 *
	 * @param  castle    Le château qui aurra l'objectif à accomplir.
	 * @return           Un objectif.
	 */
	private static Goal getNewGoalProduction(final Castle castle)
	{
		final SoldierPack<Boolean> soldierSelection = new SoldierPack<>(false, false, false, false, false, false, false);
		final SoldierPack<Integer> soldierProduction = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);
		final int levelCastle = castle.getLevel();
		final int levelCaserne = castle.getCaserne().getLevel();

		int nbVillagerOff = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight) * SoldierEnum.Knight.villager
				+ castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Berserker) * SoldierEnum.Berserker.villager
				+ castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Onager) * SoldierEnum.Onager.villager;

		int nbVillagerDef = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Piker) * SoldierEnum.Piker.villager
				+ castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Archer) * SoldierEnum.Archer.villager;

		switch (castle.getCharacter())
		{
			case Neutral:
				if (nbVillagerOff < nbVillagerDef)
				{

					soldierSelection.replace(SoldierEnum.Knight, true);
					soldierSelection.replace(SoldierEnum.Berserker, true);
					soldierSelection.replace(SoldierEnum.Onager, true);
				}
				else
				{
					soldierSelection.replace(SoldierEnum.Piker, true);
					soldierSelection.replace(SoldierEnum.Archer, true);
				}
			case Offensive:
				if (nbVillagerOff < nbVillagerDef * 1.66)
				{
					soldierSelection.replace(SoldierEnum.Knight, true);
					soldierSelection.replace(SoldierEnum.Berserker, true);
					soldierSelection.replace(SoldierEnum.Onager, true);
				}
				else
				{
					soldierSelection.replace(SoldierEnum.Piker, true);
					soldierSelection.replace(SoldierEnum.Archer, true);
				}
			case Defensive:
				if (nbVillagerOff * 1.66 < nbVillagerDef)
				{
					soldierSelection.replace(SoldierEnum.Knight, true);
					soldierSelection.replace(SoldierEnum.Berserker, true);
					soldierSelection.replace(SoldierEnum.Onager, true);
				}
				else
				{
					soldierSelection.replace(SoldierEnum.Piker, true);
					soldierSelection.replace(SoldierEnum.Archer, true);
				}
		}

		for (SoldierEnum s : SoldierEnum.values())
		{
			if (soldierSelection.get(s))
			{
				int value = rand.nextInt(levelCastle + 1) 
						+ rand.nextInt(levelCaserne * 2 + 1)
						+ rand.nextInt(2) * levelCaserne 
						+ rand.nextInt(2) * levelCastle;
				
				while(s.villager * value > 40)
				{
					value = (int)((double) value * 0.75d);
				}
				
				soldierProduction.replace(s, value);
			}
			else
			{
				soldierProduction.replace(s, 0);
			}
		}
		return new MultiSoldierGoal(castle, soldierProduction);
	}
}
