package Goal;

import DukesOfTheRealm.Castle;
import Enum.SoldierEnum;
import Interface.IParameter;

public class AtomicSoldierParameter implements IParameter
{
	/**
	 * 
	 */
	public final SoldierEnum type;
	
	/*
	 * 
	 */
	public final Castle castle;
	
	/**
	 * @param number
	 * @param type
	 */
	public AtomicSoldierParameter(final Castle castle, final SoldierEnum type)
	{
		this.type = type;
		this.castle = castle;
	}
	
	
}
