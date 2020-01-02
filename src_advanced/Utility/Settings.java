package Utility;

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

/**
 * Contient les paramètres du jeu (constantes ou non) en static.
 */
public class Settings
{

	/****************************************************/
	/*********************** GAME ***********************/
	/****************************************************/

	/**
	 * Largeur de la scène.
	 */
	public static int SCENE_WIDTH = 1920;

	/**
	 * Hauteur de la scène.
	 */
	public static int SCENE_HEIGHT = 1080;

	/**
	 * Constante représentant 1 seconde dans JavaFX.
	 */
	public static final int GAME_FREQUENCY = 1000 * 1000 * 1000;

	/**
	 * Nombre de type d'unité.
	 */
	public static final int NB_TYPES_OF_TROOPS = 3;

	/**
	 * Nombre d'unités qui apparaissent simultanément.
	 */
	public static final int SIMULTANEOUS_SPAWNS = 3;

	/****************************************************/
	/********************** CASTLE **********************/
	/****************************************************/

	/**
	 * Nombre de Florin par seconde pour chache niveau de château.
	 */
	public static final int FLORIN_PER_SECOND = 5;

	/**
	 * Nombre de Florin par seconde de base.
	 */
	public static final int FLORIN_PER_SECOND_OFFSET = 5;

	/**
	 * Pourcentage de réduction des FLORIN_PER_SECOND pour les Baron.
	 */
	public static final float FLORIN_FACTOR_BARON = 0.1f;

	/**
	 * Augmentation du coût d'amélioration du château (Pour chaque niveau on ajoute LEVEL_UP_COST).
	 */
	public static final int LEVEL_UP_COST = 250;

	/**
	 * Offset pour le temps d'amélioration du château.
	 */
	public static final float LEVEL_UP_DURATION_OFFSET = 1f;

	/**
	 * Augmentation du temps d'amélioration pour chaque niveau de château.
	 */
	public static final float LEVEL_UP_DURATION_FACTOR = 2f;

	/**
	 * Nombre d'IA.
	 */
	public static final int AI_NUMBER = 5;

	/**
	 * Nombre de Baron.
	 */
	public static final int BARON_NUMBER = 6;

	/**
	 * Nombre de Knight de base pour le joueur et les IA.
	 */
	public static final int STARTER_KNIGHT = 10;

	/**
	 * Nombre de Piker de base pour le joueur et les IA.
	 */
	public static final int STARTER_PIKER = 20;

	/**
	 * Nombre de Onager de base pour le joueur et les IA.
	 */
	public static final int STARTER_ONAGER = 0;

	/**
	 * Nombre de position d'attaque possible autour d'un château.
	 */
	public static final int NB_ATTACK_LOCATIONS = 12;

	/**
	 * Nombre de position d'attaque pour chaque coté d'un château.
	 */
	public static final int ATTACK_LOCATIONS_PER_SIDE = 3;

	/**
	 * Distance minimal entre un château et une unité.
	 */
	public static final int GAP_WITH_SOLDIER = 10;

	/****************************************************/
	/************************ IA ************************/
	/****************************************************/

	/**
	 * Plage d'aléatoire pour le niveau du château des Baron
	 */
	public static final int RANDOM_LEVEL_CASTLE_BARON = 16;

	/**
	 * Offset du niveau du château des Baron (donc le minimum).
	 */
	public static final int OFFSET_LEVEL_CASTLE_BARON = 5;

	/****************************************************/
	/*********************** WALL ***********************/
	/****************************************************/
	
	public static final int WALL_LEVEL_MAX = 20;
	
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
	 * Point de vie des Spy.
	 */
	public static final int PSY_HP = 0;
	
	/**
	 * Point de vie des Berserker.
	 */
	public static final int BERSERKER_HP = 0;
	
	/**
	 * Point de vie des Archer
	 */
	public static final int ARCHER_HP = 0;

	/**
	 * Dégâts des Piker.
	 */
	public static final int PIKER_DAMAGE = 20;

	/**
	 * Dégâts des Knight.
	 */
	public static final int KNIGHT_DAMAGE = 72;

	/**
	 * Dégâts des Onager.
	 */
	public static final int ONAGER_DAMAGE = 144;
	
	/**
	 * Dégâts des Psy. 
	 */
	public static final int PSY_DAMAGE = 0;
	
	/**
	 * Dégâts des Berserker.
	 */
	public static final int BERSERKER_DAMAGE = 0;
	
	/**
	 * Dégâts des Archer.
	 */
	public static final int ARCHER_DAMAGE = 0;

	/**
	 * Coût d'un Piker.
	 */
	public static final int PIKER_COST = 64;

	/**
	 * Coût d'un Knight.
	 */
	public static final int KNIGHT_COST = 128;

	/**
	 * Coût d'un Onager.
	 */
	public static final int ONAGER_COST = 192;
	
	/**
	 * Coût d'un Psy.
	 */
	public static final int PSY_COST = 0;
	
	/**
	 * Coût d'un Berserker.
	 */
	public static final int BERSERKER_COST = 0;
	
	/**
	 * Coût d'un Archer.
	 */
	public static final int ARCHER_COST = 0;

	/**
	 * Vitesse de déplacement d'un Piker.
	 */
	public static final int PIKER_SPEED = 65;

	/**
	 * Vitesse de déplacement d'un Knight.
	 */
	public static final int KNIGHT_SPEED = 100;

	/**
	 * Vitesse de déplacement d'un Onager.
	 */
	public static final int ONAGER_SPEED = 35;
	
	/**
	 * Vitesse de déplacement d'un Psy.
	 */
	public static final int PSY_SPEED = 0;
	
	/**
	 * Vitesse de déplacement d'un Berserker.
	 */
	public static final int BERSERKER_SPEED = 0;
	
	/**
	 * Vetisse de déplacement d'un Archer.
	 */
	public static final int ARCHER_SPEED = 0;

	/**
	 * Temps de production d'un Piker (en seconde).
	 */
	public static final double PIKER_TIME_PRODUCTION = 0.6d;

	/**
	 * Temps de production d'un Knight (en seconde).
	 */
	public static final double KNIGHT_TIME_PRODUCTION = 1.5d;

	/**
	 * Temps de production d'un Onager (en seconde).
	 */
	public static final double ONAGER_TIME_PRODUCTION = 3d;
	
	/**
	 * Temps de production d'un Psy.
	 */
	public static final double PSY_TIME_PRODUCTION = 0d;
	
	/**
	 * Temps de production d'un Berserker.
	 */
	public static final double BERSERKER_TIME_PRODUCTION = 0d;
	
	/**
	 * Temps de production d'un Archer.
	 */
	public static final double ARCHER_TIME_PRODUCTION = 0d;

	/****************************************************/
	/********************* GRAPHICS *********************/
	/****************************************************/

	/**
	 * Taille en pixel d'un château.
	 */
	public static final int CASTLE_SIZE = 60;

	/**
	 * Taille en pixel d'un château divisé par 3.
	 */
	public static final int THIRD_OF_CASTLE = CASTLE_SIZE / 3;

	/**
	 * Fréquence d'apparition des unités lors de l'envoie d'une ost (en seconde).
	 */
	public static final int GAME_FREQUENCY_OST = (int) (GAME_FREQUENCY / 1.5f);

	/**
	 * Poucentage de la largeur du jeu à partir de laquelle on passe du terrain de jeu à l'interface
	 * utilisateur.
	 */
	public static final double MARGIN_PERCENTAGE = 0.75;

	/**
	 * Distance minimal entre deux châteaux lors de leur apparition (en pixel).
	 */
	public static final int MIN_DISTANCE_BETWEEN_TWO_CASTLE = (int) (CASTLE_SIZE * 5f);

	/**
	 * Taille (largeur) d'une porte d'un château (en pixel).
	 */
	public static final double DOOR_WIDTH = CASTLE_SIZE / 2;

	/**
	 * Taille (hauteur) d'une porte d'un château (en pixel).
	 */
	public static final double DOOR_HEIGHT = CASTLE_SIZE / 6;

	/**
	 * Position de la porte par rapport à son château (en pixel).
	 */
	public static final double DOOR_POSITION = CASTLE_SIZE / 4;

	/**
	 * Taille d'une unité (en pixel).
	 */
	public static final int SOLDIER_SIZE = 18;

	/**
	 * Taille de la représentation d'un Piker (en pixel).
	 */
	public static final int PIKER_REPRESENTATION_RADIUS = SOLDIER_SIZE / 2;

	/**
	 * Taille de la représentation d'un Knight (en pixel).
	 */
	public static final int KNIGHT_REPRESENTATION_SIZE = SOLDIER_SIZE;

	/**
	 * Taille de la représentation d'un Onager (en pixel).
	 */
	public static final int ONAGER_REPRESENTATION_SIZE = SOLDIER_SIZE;

	/* Paramètres interne pour le visuel des châteaux et des unités */

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
