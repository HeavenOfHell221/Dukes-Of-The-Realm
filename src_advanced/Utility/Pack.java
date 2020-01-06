package Utility;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Structure de donn�es utilis� pour stocker divers type de variable li� aux unit�s ou aux
 * b�timents.
 * <p>
 * Cette structure ne contient qu'une HashMap g�n�rique qui permet une libert� total dans le choix
 * de la cl� et des valeurs pour chaque object instanci�.
 * </p>
 * 
 * @param <T1> Type g�n�rique. Souvent une �num�ration.
 * @param <T2> Type g�n�rique.
 */
public abstract class Pack<T1, T2> implements Serializable
{
	/**
	 * HashMap g�n�rique permettant de mettre n'importe quel type comme cl� ou comme valeur.
	 */
	protected final HashMap<T1, T2> map;

	/**
	 * Constructeur par d�faut de Pack.
	 */
	public Pack()
	{
		this.map = new HashMap<>();
	}

	/**
	 * @param  key La cl�.
	 * @return     La valeur de get.
	 * @see        java.util.HashMap#get(java.lang.Object)
	 */
	public T2 get(final Object key)
	{
		return this.map.get(key);
	}

	/**
	 * @param key   La cl�.
	 * @param value La valeur qu'on veut associer � la cl�.
	 * @see         java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	public void put(final T1 key, final T2 value)
	{
		this.map.put(key, value);
	}

	/**
	 * @param key   La cl�.
	 * @param value La nouvelle valeur qu'on souhaite mettre.
	 * @see         java.util.HashMap#replace(java.lang.Object, java.lang.Object)
	 */
	public void replace(final T1 key, final T2 value)
	{
		this.map.replace(key, value);
	}

	/**
	 * @return Retourne une collection contenant toutes les valeurs de la map.
	 * @see    java.util.HashMap#values()
	 */
	public Collection<T2> values()
	{
		return this.map.values();
	}

	/**
	 * @param m Une HashMap.
	 * @see     java.util.HashMap#putAll(java.util.Map)
	 */
	public void putAll(final Map<? extends T1, ? extends T2> m)
	{
		this.map.putAll(m);
	}

	@Override
	public String toString()
	{
		return "Pack [map=" + map + "]";
	}
}
