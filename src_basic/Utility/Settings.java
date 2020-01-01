package Utility;

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

/**
 * Contient les param�tres du jeu (constantes ou non) en static.
 */
public class Settings
{

	/****************************************************/
	/*********************** GAME ***********************/
	/****************************************************/

	/**
	 * Largeur de la sc�ne.
	 */
	public static int SCENE_WIDTH = 1920;

	/**
	 * Hauteur de la sc�ne.
	 */
	public static int SCENE_HEIGHT = 1080;

	/**
	 * Constante repr�sentant 1 seconde dans JavaFX.
	 */
	public static final int GAME_FREQUENCY = 1000 * 1000 * 1000;

	/**
	 * Nombre de type d'unit�.
	 */
	public static final int NB_TYPES_OF_TROOPS = 3;

	/**
	 * Nombre d'unit�s qui apparaissent simultan�ment.
	 */
	public static final int SIMULTANEOUS_SPAWNS = 3;

	/****************************************************/
	/********************** CASTLE **********************/
	/****************************************************/

	/**
	 * Nombre de Florin par seconde pour chache niveau de ch�teau.
	 */
	public static final int FLORIN_PER_SECOND = 5;

	/**
	 * Nombre de Florin par seconde de base.
	 */
	public static final int FLORIN_PER_SECOND_OFFSET = 5;

	/**
	 * Pourcentage de r�duction des FLORIN_PER_SECOND pour les Baron.
	 */
	public static final float FLORIN_FACTOR_BARON = 0.1f;

	/**
	 * Augmentation du co�t d'am�lioration du ch�teau (Pour chaque niveau on ajoute LEVEL_UP_COST).
	 */
	public static final int LEVEL_UP_COST = 250;

	/**
	 * Offset pour le temps d'am�lioration du ch�teau.
	 */
	public static final int LEVEL_UP_DURATION_OFFSET = 1;

	/**
	 * Augmentation du temps d'am�lioration pour chaque niveau de ch�teau.
	 */
	public static final int LEVEL_UP_DURATION_FACTOR = 2;

	/**
	 * Nombre d'IA.
	 */
	public static final int AI_NUMBER = 6;

	/**
	 * Nombre de Baron.
	 */
	public static final int BARON_NUMBER = 5;

	/**
	 * Nombre de Knight de base pour le joueur et les IA.
	 */
	public static final int STARTER_KNIGHT = 15;

	/**
	 * Nombre de Piker de base pour le joueur et les IA.
	 */
	public static final int STARTER_PIKER = 30;

	/**
	 * Nombre de Onager de base pour le joueur et les IA.
	 */
	public static final int STARTER_ONAGER = 0;

	/**
	 * Nombre de position d'attaque possible autour d'un ch�teau.
	 */
	public static final int NB_ATTACK_LOCATIONS = 12;

	/**
	 * Nombre de position d'attaque pour chaque cot� d'un ch�teau.
	 */
	public static final int ATTACK_LOCATIONS_PER_SIDE = 3;

	/**
	 * Distance minimal entre un ch�teau et une unit�.
	 */
	public static final int GAP_WITH_SOLDIER = 10;

	/****************************************************/
	/************************ IA ************************/
	/****************************************************/

	/**
	 * Plage d'al�atoire pour le niveau du ch�teau des Baron
	 */
	public static final int RANDOM_LEVEL_CASTLE_BARON = 16;

	/**
	 * Offset du niveau du ch�teau des Baron (donc le minimum).
	 */
	public static final int OFFSET_LEVEL_CASTLE_BARON = 5;

	/****************************************************/
	/********************* SOLDIERS *********************/
	/****************************************************/

	/**
	 * Point de vie des Piker.
	 */
	public static final int PIKER_HP = 40;

	/**
	 * Point de vie des Knight.
	 */
	public static final int KNIGHT_HP = 50;

	/**
	 * Point de vie des Onager.
	 */
	public static final int ONAGER_HP = 24;

	/**
	 * D�g�ts des Piker.
	 */
	public static final int PIKER_DAMAGE = 20;

	/**
	 * D�g�ts des Knight.
	 */
	public static final int KNIGHT_DAMAGE = 72;

	/**
	 * D�g�ts des Onager.
	 */
	public static final int ONAGER_DAMAGE = 144;

	/**
	 * Co�t d'un Piker.
	 */
	public static final int PIKER_COST = 40;

	/**
	 * Co�t d'un Knight.
	 */
	public static final int KNIGHT_COST = 80;

	/**
	 * Co�t d'un Onager.
	 */
	public static final int ONAGER_COST = 120;

	/**
	 * Vitesse de d�placement d'un Piker.
	 */
	public static final int PIKER_SPEED = 40;

	/**
	 * Vitesse de d�placement d'un Knight.
	 */
	public static final int KNIGHT_SPEED = 80;

	/**
	 * Vitesse de d�placement d'un Onager.
	 */
	public static final int ONAGER_SPEED = 25;

	/**
	 * Temps de production d'un Piker (en seconde).
	 */
	public static final double PIKER_TIME_PRODUCTION = 0.6f;

	/**
	 * Temps de production d'un Knight (en seconde).
	 */
	public static final double KNIGHT_TIME_PRODUCTION = 1.5f;

	/**
	 * Temps de production d'un Onager (en seconde).
	 */
	public static final double ONAGER_TIME_PRODUCTION = 3f;

	/****************************************************/
	/********************* GRAPHICS *********************/
	/****************************************************/

	/**
	 * Taille en pixel d'un ch�teau.
	 */
	public static final int CASTLE_SIZE = 60;

	/**
	 * Taille en pixel d'un ch�teau divis� par 3.
	 */
	public static final int THIRD_OF_CASTLE = CASTLE_SIZE / 3;

	/**
	 * Fr�quence d'apparition des unit�s lors de l'envoie d'une ost (en seconde).
	 */
	public static final int GAME_FREQUENCY_OST = (int) (GAME_FREQUENCY / 1.5f);

	/**
	 * Poucentage de la largeur du jeu � partir de laquelle on passe du terrain de jeu � l'interface
	 * utilisateur.
	 */
	public static final double MARGIN_PERCENTAGE = 0.75;

	/**
	 * Distance minimal entre deux ch�teaux lors de leur apparition (en pixel).
	 */
	public static final int MIN_DISTANCE_BETWEEN_TWO_CASTLE = (int) (CASTLE_SIZE * 3.5f * 1.33f);

	/**
	 * Taille (largeur) d'une porte d'un ch�teau (en pixel).
	 */
	public static final double DOOR_WIDTH = CASTLE_SIZE / 2;

	/**
	 * Taille (hauteur) d'une porte d'un ch�teau (en pixel).
	 */
	public static final double DOOR_HEIGHT = CASTLE_SIZE / 6;

	/**
	 * Position de la porte par rapport � son ch�teau (en pixel).
	 */
	public static final double DOOR_POSITION = CASTLE_SIZE / 4;

	/**
	 * Taille d'une unit� (en pixel).
	 */
	public static final int SOLDIER_SIZE = 18;

	/**
	 * Taille de la repr�sentation d'un Piker (en pixel).
	 */
	public static final int PIKER_REPRESENTATION_RADIUS = SOLDIER_SIZE / 2;

	/**
	 * Taille de la repr�sentation d'un Knight (en pixel).
	 */
	public static final int KNIGHT_REPRESENTATION_SIZE = SOLDIER_SIZE;

	/**
	 * Taille de la repr�sentation d'un Onager (en pixel).
	 */
	public static final int ONAGER_REPRESENTATION_SIZE = SOLDIER_SIZE;

	/* Param�tres interne pour le visuel des ch�teaux et des unit�s */

	public static final double CASTLE_STROKE_THICKNESS = 2.0;
	public static final StrokeType CASTLE_STROKE_TYPE = StrokeType.INSIDE;
	public static final Color CASTLE_STROKE_COLOR = Color.BLACK;
	public static final int CASTLE_SHADOW_SIZE = 1;
	public static final int CASTLE_SHADOW_OFFSET = 6;
	public static final int CASTLE_SHADOW_RADIUS = 11;
	public static final Color CASTLE_SHADOW_COLOR = Color.BLACK;
	public static final double SOLDIER_STROKE_THICKNESS = 1.5;
	public static final StrokeType SOLDIER_STROKE_TYPE = StrokeType.INSIDE;
	public static final Color SOLDIER_STROKE_COLOR = Color.BLACK;
	public static final int SOLDIER_SHADOW_SIZE = 1;
	public static final int SOLDIER_SHADOW_OFFSET = 2;
	public static final int SOLDIER_SHADOW_RADIUS = 3;
	public static final Color SOLDIER_SHADOW_COLOR = Color.BLACK;
}
