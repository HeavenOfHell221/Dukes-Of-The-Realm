package Goal;

import java.io.Serializable;
import java.util.ArrayDeque;
import Enum.SoldierEnum;
import Interface.IParameter;

public class ComplexeSoldierParameter implements IParameter, Serializable
{
	public final ArrayDeque<AtomicSoldierGoal> queue;
	
	public ComplexeSoldierParameter()
	{
		this.queue = new ArrayDeque<>();
	}
	
	/*
	 * Creer {number} {type} 
	 */
	public ComplexeSoldierParameter(final SoldierEnum type, final int number)
	{
		this();
		for(int i = 0; i < number; i++)
		{
			this.queue.add(new AtomicSoldierGoal(new AtomicSoldierParameter(type)));
		}
	}
	
	public ComplexeSoldierParameter(final ComplexeSoldierParameter parameter)
	{
		this();
		this.queue.addAll(parameter.queue);
	}
}
