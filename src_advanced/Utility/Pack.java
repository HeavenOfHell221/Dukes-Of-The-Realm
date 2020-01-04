package Utility;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Pack<T1, T2> implements Serializable
{

	protected final HashMap<T1, T2> map;	

	public Pack()
	{
		map = new HashMap<>();
	}

	/**
	 * @param key La clé.
	 * @return La valeur de get.
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public T2 get(Object key)
	{
		return map.get(key);
	}

	/**
	 * @param key La clé.
	 * @param value La valeur.
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	public void put(T1 key, T2 value)
	{
		this.map.put(key, value);
	}

	/**
	 * @param key La clé.
	 * @param value La valeur.
	 * @see java.util.HashMap#replace(java.lang.Object, java.lang.Object)
	 */
	public void replace(T1 key, T2 value)
	{
		this.map.replace(key, value);
	}

	/**
	 * @return
	 * @see java.util.HashMap#values()
	 */
	public Collection<T2> values()
	{
		return map.values();
	}

	/**
	 * @param m
	 * @see java.util.HashMap#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends T1, ? extends T2> m)
	{
		map.putAll(m);
	}	
	
	
}
