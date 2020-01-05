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
	public static final int NB_TYPES_OF_TROOPS = 6;

	/**
	 * Nombre d'unit�s qui apparaissent simultan�ment.
	 */
	public static final int SIMULTANEOUS_SPAWNS = 3;

	/**
	 * Nombre d'IA.
	 */
	public static final int AI_NUMBER = 5;

	/**
	 * Nombre de Baron.
	 */
	public static final int BARON_NUMBER = 6;

	/****************************************************/
	/********************** CASTLE **********************/
	/****************************************************/

	/**
	 * Nombre de Florin par seconde pour chache niveau de ch�teau.
	 */
	public static final int FLORIN_PER_SECOND = 10; // 5

	/**
	 * Nombre de Florin par seconde de base.
	 */
	public static final int FLORIN_PER_SECOND_OFFSET = 5;

	/**
	 * Pourcentage de r�duction des FLORIN_PER_SECOND pour les Baron.
	 */
	public static final float FLORIN_FACTOR_BARON = 0.1f;

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
	/********************* STARTER **********************/
	/****************************************************/

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
	public static final int STARTER_ONAGER = 0; // 0

	/**
	 * Nombre de Archer de base pour le joueur et les IA.
	 */
	public static final int STARTER_ARCHER = 10; 

	/**
	 * Nombre de Berserker de base pour le joueur et les IA.
	 */
	public static final int STARTER_BERSERKER = 20;

	/**
	 * Nombte de Spy de base pour le joueur et les IA.
	 */
	public static final int STARTER_SPY = 0;

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
	/******************** Buildings *********************/
	/****************************************************/

	/**
	 * Augmentation du co�t d'am�lioration du ch�teau (Pour chaque niveau on ajoute LEVEL_UP_COST).
	 */
	public static final int CASTLE_COST = 250;

	/**
	 * Offset pour le temps d'am�lioration du ch�teau.
	 */
	public static final float CASTLE_PRODUCTION_OFFSET = 1f;

	/**
	 * Augmentation du temps d'am�lioration pour chaque niveau de ch�teau.
	 */
	public static final float CASTLE_PRODUCTION_TIME_PER_LEVEL = 3f;

	public static final int CASTLE_LEVEL_MAX = 20;

	public static final int WALL_COST = 200;
	
	public static final float WALL_PRODUCTION_OFFSET = 6f;
	
	public static final float WALL_PRODUCTION_TIME_PER_LEVEL = 5f;
	
	public static final int WALL_LEVEL_MAX = 20;

	public static final int CASERNE_COST = 200;
	
	public static final float CASERNE_PRODUCTION_OFFSET = 0f;
	
	public static final float CASERNE_PRODUCTION_TIME_PER_LEVEL = 4f;
	
	public static final int CASERNE_LEVEL_MAX = 10;

	public static final int MILLER_COST = 300;
	
	public static final float MILLER_PRODUCTION_OFFSET = 1f;
	
	public static final float MILLER_PRODUCTION_TIME_PER_LEVEL = 4f;
	
	public static final int MILLER_LEVEL_MAX = 10;
	
	public static final int MILLER_VILLAGER_BASE = 80; // 80
	
	public static final int MILLER_VILLAGER_PER_LEVEL = 12;

	public static final int MARKET_COST = 100;
	
	public static final float MARKET_PRODUCTION_OFFSET = 2f;
	
	public static final float MARKET_PRODUCTION_TIME_PER_LEVEL = 2f;
	
	public static final int MARKET_LEVEL_MAX = 10;

	/****************************************************/
	/********************* SOLDIERS *********************/
	/****************************************************/

	/**
	 * Point de vie des Piker.
	 */
	public static final int PIKER_HP = 32;

	/**
	 * Point de vie des Knight.
	 */
	public static final int KNIGHT_HP = 62;

	/**
	 * Point de vie des Onager.
	 */
	public static final int ONAGER_HP = 19;

	/**
	 * Point de vie des Spy.
	 */
	public static final int SPY_HP = 6;

	/**
	 * Point de vie des Berserker.
	 */
	public static final int BERSERKER_HP = 16;

	/**
	 * Point de vie des Archer
	 */
	public static final int ARCHER_HP = 60;

	/**
	 * D�g�ts des Piker.
	 */
	public static final int PIKER_DAMAGE = 24;

	/**
	 * D�g�ts des Knight.
	 */
	public static final int KNIGHT_DAMAGE = 102;

	/**
	 * D�g�ts des Onager.
	 */
	public static final int ONAGER_DAMAGE = 10;

	/**
	 * D�g�ts des Psy.
	 */
	public static final int SPY_DAMAGE = 6;

	/**
	 * D�g�ts des Berserker.
	 */
	public static final int BERSERKER_DAMAGE = 72;

	/**
	 * D�g�ts des Archer.
	 */
	public static final int ARCHER_DAMAGE = 42;

	/**
	 * Co�t d'un Piker.
	 */
	public static final int PIKER_COST = 64;

	/**
	 * Co�t d'un Knight.
	 */
	public static final int KNIGHT_COST = 164;

	/**
	 * Co�t d'un Onager.
	 */
	public static final int ONAGER_COST = 192;
	
	/**
	 * Probabilit� d'un Onager de baisser de 1 le niveau d'un rempart pour chacun de ses points d'attaque.
	 */
	public static final float ONAGER_WALL = 0.05f; 
	
	/**
	 * Probabilit� d'un Spy d'espionner le ch�teau qu'il attaque pour chacun de ses points d'attaque.
	 */
	public static final float SPY_SPY = 0.05f;

	/**
	 * Co�t d'un Psy.
	 */
	public static final int SPY_COST = 80;

	/**
	 * Co�t d'un Berserker.
	 */
	public static final int BERSERKER_COST = 80;

	/**
	 * Co�t d'un Archer.
	 */
	public static final int ARCHER_COST = 128;

	/**
	 * Vitesse de d�placement d'un Piker.
	 */
	public static final int PIKER_SPEED = 65;

	/**
	 * Vitesse de d�placement d'un Knight.
	 */
	public static final int KNIGHT_SPEED = 100;

	/**
	 * Vitesse de d�placement d'un Onager.
	 */
	public static final int ONAGER_SPEED = 95; // 35

	/**
	 * Vitesse de d�placement d'un Psy.
	 */
	public static final int SPY_SPEED = 80;

	/**
	 * Vitesse de d�placement d'un Berserker.
	 */
	public static final int BERSERKER_SPEED = 65;

	/**
	 * Vetisse de d�placement d'un Archer.
	 */
	public static final int ARCHER_SPEED = 55;

	/**
	 * Temps de production de base d'un Piker (en seconde).
	 */
	public static final double PIKER_TIME_PRODUCTION = 0.68d;

	/**
	 * Temps de production de base d'un Knight (en seconde).
	 */
	public static final double KNIGHT_TIME_PRODUCTION = 3.08d;

	/**
	 * Temps de production de base d'un Onager (en seconde).
	 */
	public static final double ONAGER_TIME_PRODUCTION = 4.5d;

	/**
	 * Temps de production de base d'un Psy.
	 */
	public static final double SPY_TIME_PRODUCTION = 1.31d;

	/**
	 * Temps de production de base d'un Berserker.
	 */
	public static final double BERSERKER_TIME_PRODUCTION = 1.13d;

	/**
	 * Temps de production de base d'un Archer.
	 */
	public static final double ARCHER_TIME_PRODUCTION = 2.25d;

	/**
	 *
	 */
	public static final int PIKER_VILLAGER = 1;

	/**
	 *
	 */
	public static final int KNIGHT_VILLAGER = 2;

	/**
	 *
	 */
	public static final int ONAGER_VILLAGER = 3;

	/**
	 *
	 */
	public static final int ARCHER_VILLAGER = 1;

	/**
	 *
	 */
	public static final int BERSERKER_VILLAGER = 1;

	/**
	 *
	 */
	public static final int SPY_VILLAGER = 2;
	
	/**
	 * 
	 */
	public static final int CONVEYORS_FLORIN = 200;
	
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
	public static final int MIN_DISTANCE_BETWEEN_TWO_CASTLE = (int) (CASTLE_SIZE * 5f);

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

	/* Taille de la repr�sentation d'un Archer (en pixel). */

	public static final int ARCHER_REPRESENTATION_SIZE = SOLDIER_SIZE;
	public static final int ARCHER_REPRESENTATION_HEIGHT_POSITION = 7*SOLDIER_SIZE/10;

	/* Taille de la repr�sentation d'un Berserker (en pixel). */
	
	public static final int BERSERKER_REPRESENTATION_SIZE = SOLDIER_SIZE;
	public static final int BERSERKER_REPRESENTATION_THICKNESS = SOLDIER_SIZE/3;

	/* Taille de la repr�sentation d'un Spy (en pixel) */
	
	public static final int SPY_REPRESENTATION_OUTSIDE_RADIUS = SOLDIER_SIZE/2;
	public static final int SPY_REPRESENTATION_INSIDE_RADIUS = SOLDIER_SIZE/4;

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
