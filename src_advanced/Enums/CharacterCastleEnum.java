package Enums;

import java.io.Serializable;

/**
 * Liste des points de caractère possible pour un château.
 */
public enum CharacterCastleEnum implements Serializable
{
	/**
	 * N'a pas de caractère précis.
	 */
	Neutral,
	
	/**
	 * Château ayant une plus forte probabilitée de produire des unités offensives et d'attaquer.
	 */
	Offensive,
	 
	/**
	 * Château ayant une plus forte probabilitée de produire des unités défensives.
	 */
	Defensive,
	
	/**
	 * Château ayant une plus forte probabilitée de produire des unités de soutien et d'envoyer des renforts.
	 */
	Support;
}
