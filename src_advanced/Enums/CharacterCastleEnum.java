package Enums;

import java.io.Serializable;

/**
 * Liste des points de caract�re possible pour un ch�teau.
 */
public enum CharacterCastleEnum implements Serializable
{
	/**
	 * N'a pas de caract�re pr�cis.
	 */
	Neutral,
	
	/**
	 * Ch�teau ayant une plus forte probabilit�e de produire des unit�s offensives et d'attaquer.
	 */
	Offensive,
	 
	/**
	 * Ch�teau ayant une plus forte probabilit�e de produire des unit�s d�fensives.
	 */
	Defensive,
	
	/**
	 * Ch�teau ayant une plus forte probabilit�e de produire des unit�s de soutien et d'envoyer des renforts.
	 */
	Support;
}
