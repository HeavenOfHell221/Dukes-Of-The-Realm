package Goal;

import java.io.Serializable;
import java.util.Random;

import Duke.Actor;
import Duke.DukeAI;
import DukesOfTheRealm.Castle;
import Enum.GoalEnum;
import SimpleGoal.CastleGoal;
import SimpleGoal.Goal;
import SimpleGoal.SaveFlorinGoal;

public class GeneratorGoal implements Serializable
{
	private final static GeneratorGoal instance = new GeneratorGoal();
	public final static Random rand = new Random();

	private GeneratorGoal()
	{

	}

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

	private static Goal getNewGoalBuilding(final Castle castle)
	{
		return new CastleGoal(castle);
	}

	private static Goal getNewGoalFinance(final Castle castle)
	{
		final int lvl = castle.getLevel();
		switch (rand.nextInt(2))
		{
			case 0:
			case 1:
				return new SaveFlorinGoal(rand.nextInt(201) * castle.getLevel());
			//case 1:
				//return new SaveSoldierGoal(castle, rand.nextInt(lvl * 5) + lvl, rand.nextInt(lvl * 5) + lvl, rand.nextInt(lvl * 5) + lvl);
			default:
				break;
		}
		return null;
	}

	private static Goal getNewGoalBattle(final Castle castle)
	{
		final int lvl = castle.getLevel();
		DukeAI actor = (DukeAI) castle.getActor();
		Actor actorTarget = actor.getKingdom().getRandomActor(actor);
		
		if(actorTarget.getCastles().size() <= 0)
		{
			return null;
		}
		
		Castle castleTarget = actorTarget.getCastles().get(rand.nextInt(actorTarget.getCastles().size()));
		AttackGoal g = null;
		
		switch (rand.nextInt(2))
		{
			case 0:
				g = new AttackGoal(castle, 0, rand.nextInt(10 + lvl) + rand.nextInt(4) * lvl, 0);
				break;
			case 1:
				g = new AttackGoal(castle, 0, rand.nextInt(6 + lvl) + rand.nextInt(3) * lvl,
						rand.nextInt(4 + lvl) + rand.nextInt(2) * lvl);
				break;
			default:
				break;
		}
		if (g != null)
		{
			g.setGoal(castleTarget);
		}
		return g;
	}

	private static Goal getNewGoalBackup(final Castle castle)
	{
		switch (rand.nextInt(2))
		{
			case 0:
				return new BackupGoal(castle, castle.getNbPikers()/2, castle.getNbKnights()/2, 0);
			case 1:
				return new BackupGoal(castle, 0, castle.getNbKnights(), 0);
			default:
				break;
		}
		return null;
	}

	private static Goal getNewGoalProduction(final Castle castle)
	{
		final int lvl = castle.getLevel();
		switch (rand.nextInt(7))
		{
			// Piker
			case 0:
				return new MultiSoldierGoal(castle, rand.nextInt(12 + lvl) + rand.nextInt(3) * lvl, 0, 0);
			// Knight
			case 1:
				return new MultiSoldierGoal(castle, 0, rand.nextInt(8 + lvl) + rand.nextInt(3) * lvl, 0);
			// Onager
			case 2:
				return new MultiSoldierGoal(castle, 0, 0, rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl);
			// Piker + Knight
			case 3:
				return new MultiSoldierGoal(castle, rand.nextInt(8 + lvl) + rand.nextInt(2) * lvl,
						rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl, 0);
			// Piker + Onager
			case 4:
				return new MultiSoldierGoal(castle, rand.nextInt(8 + lvl) + rand.nextInt(2) * lvl, 0,
						rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl);
			// Knight + Onager
			case 5:
				return new MultiSoldierGoal(castle, 0, rand.nextInt(8 + lvl) + rand.nextInt(2) * lvl,
						rand.nextInt(4 + lvl) + rand.nextInt(2) * lvl);
			// Piker + Knight + Onager
			case 6:
				return new MultiSoldierGoal(castle, rand.nextInt(6 + lvl) + rand.nextInt(2) * lvl,
						rand.nextInt(4 + lvl) + rand.nextInt(2) * lvl, rand.nextInt(4 + lvl) + rand.nextInt(2) * lvl);
			default:
				break;
		}
		return null;
	}
}
