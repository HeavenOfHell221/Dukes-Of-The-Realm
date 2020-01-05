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
		final int levelCastle = castle.getLevel();
		final int levelCaserne = castle.getCaserne().getLevel();
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
				soldierPack.replace(SoldierEnum.Berserker, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Berserker));
				return new AttackGoal(castle, destination, soldierPack);
			// Full Knight
			case 1:
				soldierPack.replace(SoldierEnum.Knight, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight));
				return new AttackGoal(castle, destination, soldierPack);
			// Berserker / Knight
			case 2:
				soldierPack.replace(SoldierEnum.Berserker, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Berserker));
				soldierPack.replace(SoldierEnum.Knight, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight));
				return new AttackGoal(castle, destination, soldierPack);
			// Berserker / Onager
			case 3:
				soldierPack.replace(SoldierEnum.Onager, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Onager));
				soldierPack.replace(SoldierEnum.Knight, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight));
				return new AttackGoal(castle, destination, soldierPack);
			// Knight / Onager
			case 4:
				soldierPack.replace(SoldierEnum.Onager, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Onager));
				soldierPack.replace(SoldierEnum.Knight, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight));
				return new AttackGoal(castle, destination, soldierPack);
			// Berserker / Knight / Onager
			case 5:
				soldierPack.replace(SoldierEnum.Berserker, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Berserker));
				soldierPack.replace(SoldierEnum.Knight, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight));
				soldierPack.replace(SoldierEnum.Onager, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Onager));
				return new AttackGoal(castle, destination, soldierPack);
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
				soldierPack.replace(SoldierEnum.Piker, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Piker));
				soldierPack.replace(SoldierEnum.Archer, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Archer));
			// Full Knight	
			case 1:
				soldierPack.replace(SoldierEnum.Knight, castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight));
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
		final SoldierPack<Boolean> soldierSelection = new SoldierPack<>(false, false, false, false, false, false, false);
		final SoldierPack<Integer> soldierProduction = new SoldierPack<>(0, 0, 0, 0, 0, 0, 0);
		final int levelCastle = castle.getLevel();
		final int levelCaserne = castle.getCaserne().getLevel();
		
		int nbVillagerOff = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Knight) * SoldierEnum.Knight.villager
				+ castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Berserker) * SoldierEnum.Berserker.villager
				+ castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Onager) * SoldierEnum.Onager.villager;
		
		int nbVillagerDef = castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Piker) * SoldierEnum.Piker.villager
				+ castle.getReserveOfSoldiers().getSoldierPack().get(SoldierEnum.Archer) * SoldierEnum.Archer.villager;
		
		
		switch(castle.getCharacter())
		{
			case Neutral:
				if(nbVillagerOff < nbVillagerDef)
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
				if(nbVillagerOff < nbVillagerDef * 1.5)
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
				if(nbVillagerOff * 1.5 < nbVillagerDef)
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
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			if(soldierSelection.get(s))
			{
				if(s.cost > 100)
				{
					soldierProduction.replace(s, 
							rand.nextInt(levelCastle * 2) 
							+ rand.nextInt(levelCaserne * 4)
							+ rand.nextInt(6) * levelCaserne
							+ rand.nextInt(2) * levelCastle);
				}
				else
				{
					soldierProduction.replace(s, 
						rand.nextInt(levelCastle * 4) 
						+ rand.nextInt(levelCaserne * 4)
						+ rand.nextInt(8) * levelCaserne
						+ rand.nextInt(4) * levelCastle);
				}
				
			}
			else
			{
				soldierProduction.replace(s, 0);
			}
		}
		return new MultiSoldierGoal(castle, soldierProduction);
	}
}
