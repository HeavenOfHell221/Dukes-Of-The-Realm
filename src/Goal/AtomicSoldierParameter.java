package Goal;

import java.io.Serializable;

import DukesOfTheRealm.Castle;
import Enum.SoldierEnum;
import Interface.IParameter;

public class AtomicSoldierParameter implements IParameter, Serializable
{
	/**
	 * 
	 */
	public final SoldierEnum type;
	
	/**
	 * @param type
	 */
	public AtomicSoldierParameter(final SoldierEnum type)
	{
		this.type = type;
	}

	/**
	 * 
	 * @param parameter
	 */
	public AtomicSoldierParameter(final AtomicSoldierParameter parameter)
	{
		this.type = parameter.type;
	}
	
	
}
