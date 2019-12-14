package Goal;

import java.util.ArrayList;
import java.util.Random;

import Enum.SoldierEnum;

public final class GeneratorAtomicGoal
{
	private final static GeneratorAtomicGoal instance = new GeneratorAtomicGoal();
	private final ArrayList<AtomicGoal> goalsPossible; 
	private final Random rand = new Random();
	
	private GeneratorAtomicGoal()
	{
		this.goalsPossible = new ArrayList<>();
		this.goalsPossible.add(new AtomicSoldierGoal(new AtomicSoldierParameter(SoldierEnum.Piker)));
		this.goalsPossible.add(new AtomicSoldierGoal(new AtomicSoldierParameter(SoldierEnum.Knight)));
		this.goalsPossible.add(new AtomicSoldierGoal(new AtomicSoldierParameter(SoldierEnum.Onager)));
		this.goalsPossible.add(new AtomicCastleGoal());
	}
	
	public static final AtomicGoal getRandomGoal()
	{
		int index = instance.rand.nextInt(instance.goalsPossible.size());
		return instance.goalsPossible.get(index);
	}
}
