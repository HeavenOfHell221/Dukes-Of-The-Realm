package Enums;

import java.io.Serializable;

/**
 * Liste des points de caractère possible pour les IA.
 * @see Duke.DukeAI 
 */
public enum CharacterDukeEnum implements Serializable
{
	/**
	 * Duke ayant une probabilitée plus forte d'avoir des châteaux offensifs.
	 */
	Offensive,
	
	/**
	 * Duke ayant une probabilitée plus forte d'avoir des châteaux défensifs.
	 */
	Defensive,
	
	/**
	 * Duke n'ayant pas de caractère précis.
	 */
	Neural,
	
	/**
	 * Duke n'attaquant pas.
	 */
	Peaceful;
}
