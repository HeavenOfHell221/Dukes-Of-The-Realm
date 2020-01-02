package Enums;

import java.io.Serializable;

/**
 * Liste des points de caract�re possible pour les IA.
 * @see Duke.DukeAI 
 */
public enum CharacterDukeEnum implements Serializable
{
	/**
	 * Duke ayant une probabilit�e plus forte d'avoir des ch�teaux offensifs.
	 */
	Offensive,
	
	/**
	 * Duke ayant une probabilit�e plus forte d'avoir des ch�teaux d�fensifs.
	 */
	Defensive,
	
	/**
	 * Duke n'ayant pas de caract�re pr�cis.
	 */
	Neural,
	
	/**
	 * Duke n'attaquant pas.
	 */
	Peaceful;
}
