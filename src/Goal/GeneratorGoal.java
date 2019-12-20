package Goal;

import static Goal.GeneratorGoal.rand;

import java.io.Serializable;
import java.util.Random;

import Duke.Actor;
import Duke.DukeAI;
import DukesOfTheRealm.Castle;
import Enum.GoalEnum;
import Enum.SoldierEnum;
import SimpleGoal.*;

public class GeneratorGoal implements Serializable
{
	private final static GeneratorGoal instance = new GeneratorGoal();
	public final static Random rand = new Random();
	
	private GeneratorGoal()
	{
		
	}
	
	public static Goal getNewGoal(Castle castle)
	{
		switch(GoalEnum.getRandomType(rand))
		{
			case Backup: return getNewGoalProduction(castle);
			case Battle: return getNewGoalBattle(castle);
			case Finance: return getNewGoalFinance(castle);
			case Production: return getNewGoalProduction(castle);
			case Building: return getNewGoalBuilding(castle);
			default: break;
		}
		return null;
	}

	private static Goal getNewGoalBuilding(Castle castle)
	{
		return new CastleGoal();
	}

	private static Goal getNewGoalFinance(Castle castle)
	{
		final int lvl = castle.getLevel();
		switch(rand.nextInt(2))
		{
			case 0: return new SaveFlorinGoal(rand.nextInt(501) * castle.getLevel());
			case 1: return new SaveSoldierGoal(castle, rand.nextInt(lvl * 5) * lvl, rand.nextInt(lvl * 5) * lvl, rand.nextInt(lvl * 5) * lvl);
			default: break;
		}
		return null;
	}

	private static Goal getNewGoalBattle(Castle castle)
	{
		final int lvl = castle.getLevel();
		DukeAI actor = (DukeAI) castle.getActor();
		Actor actorTarget = actor.getKingdom().getRandomActor(actor);
		Castle castleTarget = actorTarget.getCastles().get(rand.nextInt(actorTarget.getCastles().size()));
		AttackGoal g = null;
		switch(rand.nextInt(2))
		{
			case 0: 
				g = new AttackGoal(castle, castle.getNbPikers(), castle.getNbKnights(), castle.getNbOnagers());
				  break;
			case 1: 
				g = new AttackGoal(castle, rand.nextInt(6 + lvl) + rand.nextInt(3) * lvl, 
				 rand.nextInt(11 + lvl) + rand.nextInt(4) * lvl, 
				 rand.nextInt(6 + lvl) + rand.nextInt(3) * lvl);
				break;
			default: break;
		}
		if(g != null)
			g.setGoal(castleTarget);
		return g;
	}

	private static Goal getNewGoalBackup(Castle castle)
	{
		return null;
	}

	private static Goal getNewGoalProduction(Castle castle)
	{
		final int lvl = castle.getLevel();
		switch(rand.nextInt(7))
		{
			// Piker
			case 0: return new MultiSoldierGoal(rand.nextInt(11 + lvl) + rand.nextInt(3) * lvl, 0, 0);
			// Knight
			case 1: return new MultiSoldierGoal(0, rand.nextInt(11 + lvl) + rand.nextInt(3) * lvl, 0);
			// Onager
			case 2: return new MultiSoldierGoal(0, 0, rand.nextInt(11 + lvl) + rand.nextInt(3) * lvl);
			// Piker + Knight
			case 3: return new MultiSoldierGoal(rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl, rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl, 0);
			// Piker + Onager
			case 4: return new MultiSoldierGoal(rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl, 0, rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl);
			// Knight + Onager
			case 5: return new MultiSoldierGoal(0, rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl, rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl);
			// Piker + Knight + Onager
			case 6: return new MultiSoldierGoal(rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl, rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl, rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl);
			default: break;
		}
		return null;
	}
}
