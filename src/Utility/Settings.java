package Utility;

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

/**
 *
 *
 */
public class Settings
{

	/****************************************************/
	/*********************** GAME ***********************/
	/****************************************************/

	/* Screen size */
	public static int SCENE_WIDTH = 1920;
	public static int SCENE_HEIGHT = 1080;

	/* Time */
	public static final int GAME_FREQUENCY = 1000 * 1000 * 1000; // 1 second

	/* Collisions */
	public static final int NO_COLLISION = 0;
	public static final int X_COLLISION = 1;
	public static final int Y_COLLISION = 2;

	/* Other */
	public static final int NB_TYPES_OF_TROOPS = 3;
	public static final int SIMULTANEOUS_SPAWNS = 3;
	public static final int SLODIERS_MAX_PER_OST = 12;

	/****************************************************/
	/********************** CASTLE **********************/
	/****************************************************/

	/* Florin */ /* Can be balanced */
	public static final int FLORIN_PER_SECOND = 10;
	public static final float FLORIN_FACTOR_BARON = 0.1f;

	/* Levels */ /* Can be balanced */
	public static final int LEVEL_UP_COST_FACTOR = 100;
	public static final int LEVEL_UP_DURATION_OFFSET = 1; // en sec
	public static final int LEVEL_UP_DURATION_FACTOR = 5; // en sec

	/* Castle number */
	public static final int AI_NUMBER = 4;
	public static final int BARON_NUMBER = 7;
	public static final int NB_TOTAL_TEST_CREATE_CASTLE = 1000 * 1000;

	/* Starter */ /* Can be balanced */
	public static final int STARTER_KNIGHT = 15;
	public static final int STARTER_PIKER = 15;
	public static final int STARTER_ONAGER = 15;

	/* Other */
	public static final int NB_ATTACK_LOCATIONS = 12;
	public static final int ATTACK_LOCATIONS_PER_SIDE = 3;
	public static final int GAP_WITH_SOLDIER = 10;

	/****************************************************/
	/********************* SOLDIERS *********************/
	/****************************************************/

	/* HP */ /* Can be balanced */
	public static final int PIKER_HP = 20;
	public static final int KNIGHT_HP = 50;
	public static final int ONAGER_HP = 10;

	/* Damage */ /* Can be balanced */
	public static final int PIKER_DAMAGE = 25;
	public static final int KNIGHT_DAMAGE = 50;
	public static final int ONAGER_DAMAGE = 75;

	/* Cost */ /* Can be balanced */
	public static final int PIKER_COST = 20;
	public static final int KNIGHT_COST = 40;
	public static final int ONAGER_COST = 70;

	/* Speed */ /* Can be balanced */ /* Not above 80 */
	public static final int PIKER_SPEED = 60;	//60
	public static final int KNIGHT_SPEED = 80;	//80
	public static final int ONAGER_SPEED = 40;	//40

	/* Production time in frame */ /* Can be balanced */
	public static final double PIKER_TIME_PRODUCTION = 0.01f;
	public static final double KNIGHT_TIME_PRODUCTION = 0.01f;
	public static final double ONAGER_TIME_PRODUCTION = 0.01f;
	
	/****************************************************/
	/********************* GRAPHICS *********************/
	/****************************************************/

	/* Game */
	public static final double MARGIN_PERCENTAGE = 0.75;
	public static final int MIN_DISTANCE_BETWEEN_TWO_CASTLE = 250; /* Can be balanced */

	/* Castle */
	public static final int CASTLE_SIZE = 60;
	public static final int THIRD_OF_CASTLE = CASTLE_SIZE / 3;
	public static final double CASTLE_STROKE_THICKNESS = 2.0;
	public static final StrokeType CASTLE_STROKE_TYPE = StrokeType.INSIDE;
	public static final Color CASTLE_STROKE_COLOR = Color.BLACK;
	public static final int CASTLE_SHADOW_SIZE = 1;
	public static final int CASTLE_SHADOW_OFFSET = 7;
	public static final int CASTLE_SHADOW_RADIUS = 11;
	public static final Color CASTLE_SHADOW_COLOR = Color.BLACK;
	
	/* Door */
	public static final double DOOR_WIDTH = CASTLE_SIZE / 2;
	public static final double DOOR_HEIGHT = CASTLE_SIZE / 6;
	public static final double DOOR_POSITION = CASTLE_SIZE / 4;

	/* Soldiers */
	public static final int SOLDIER_SIZE = 18;
	public static final int PIKER_REPRESENTATION_RADIUS = SOLDIER_SIZE / 2;
	public static final int KNIGHT_REPRESENTATION_SIZE = SOLDIER_SIZE;
	public static final int ONAGER_REPRESENTATION_SIZE = SOLDIER_SIZE;
	public static final double SOLDIER_STROKE_THICKNESS = 1.5;
	public static final StrokeType SOLDIER_STROKE_TYPE = StrokeType.INSIDE;
	public static final Color SOLDIER_STROKE_COLOR = Color.BLACK;
	public static final int SOLDIER_SHADOW_SIZE = 1;
	public static final int SOLDIER_SHADOW_OFFSET = 2;
	public static final int SOLDIER_SHADOW_RADIUS = 3;
	public static final Color SOLDIER_SHADOW_COLOR = Color.BLACK;
}
