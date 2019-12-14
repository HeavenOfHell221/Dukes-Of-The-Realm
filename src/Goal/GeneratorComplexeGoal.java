package Goal;

import java.util.ArrayList;
import java.util.Random;

import Enum.SoldierEnum;

public class GeneratorComplexeGoal
{
	private final static GeneratorComplexeGoal instance = new GeneratorComplexeGoal();
	private final ArrayList<Goal> goalsPossible; 
	private final Random rand = new Random();
	
	public GeneratorComplexeGoal()
	{
		this.goalsPossible = new ArrayList<>();
		this.goalsPossible.add(new ComplexeSoldierGoal(new ComplexeSoldierParameter(SoldierEnum.Piker, rand.nextInt(20) + 1)));
		this.goalsPossible.add(new ComplexeSoldierGoal(new ComplexeSoldierParameter(SoldierEnum.Knight, rand.nextInt(20) + 1)));
		this.goalsPossible.add(new ComplexeSoldierGoal(new ComplexeSoldierParameter(SoldierEnum.Onager, rand.nextInt(20) + 1)));
	}
	
	public static final Goal getRandomGoal()
	{
		int index = instance.rand.nextInt(instance.goalsPossible.size());
		return instance.goalsPossible.get(index);
	}
}
