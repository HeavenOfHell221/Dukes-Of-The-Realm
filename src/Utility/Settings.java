package Utility;

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

	/* Graphics */
	public static final double MARGIN_PERCENTAGE = 0.75;

	/* Other */
	public static final int X_DIRECTION = 1;
	public static final int Y_DIRECTION = 0;
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
	public static final int LEVEL_UP_COST_FACTOR = 10;
	public static final int LEVEL_UP_DURATION_OFFSET = 1; // en sec
	public static final int LEVEL_UP_DURATION_FACTOR = 5; // en sec

	/* Graphics */
	public static final int CASTLE_SIZE = 60;
	public static final int THIRD_OF_CASTLE = CASTLE_SIZE / 3;
	public static final int MIN_DISTANCE_BETWEEN_TWO_CASTLE = 250; /* Can be balanced */

	/* Castle number */
	public static final int AI_NUMBER = 3;
	public static final int BARON_NUMBER = 7;
	public static final int NB_TOTAL_TEST_CREATE_CASTLE = 1000 * 1000;

	/* Starter */ /* Can be balanced */
	public static final int STARTER_KNIGHT = 10;
	public static final int STARTER_PIKER = 30;
	public static final int STARTER_ONAGER = 5;

	/* Other */
	public static final int NB_ATTACK_LOCATIONS = 12;
	public static final int ATTACK_LOCATIONS_PER_SIDE = 3;
	public static final int GAP_WITH_SOLDIER = 10;

	/****************************************************/
	/********************* SOLDIERS *********************/
	/****************************************************/

	/* HP */ /* Can be balanced */
	public static final int PIKER_HP = 10;
	public static final int KNIGHT_HP = 20;
	public static final int ONAGER_HP = 50;

	/* Damage */ /* Can be balanced */
	public static final int PIKER_DAMAGE = 120;
	public static final int KNIGHT_DAMAGE = 300;
	public static final int ONAGER_DAMAGE = 600;

	/* Cost */ /* Can be balanced */
	public static final int PIKER_COST = 2;
	public static final int KNIGHT_COST = 4;
	public static final int ONAGER_COST = 8;

	/* Speed */ /* Can be balanced */
	public static final int PIKER_SPEED = 40;
	public static final int KNIGHT_SPEED = 60;
	public static final int ONAGER_SPEED = 20;

	/* Production time in frame */ /* Can be balanced */
	public static final int PIKER_TIME_PRODUCTION = 1;
	public static final int KNIGHT_TIME_PRODUCTION = 3;
	public static final int ONAGER_TIME_PRODUCTION = 6;

	/* Graphics */
	public static final int SOLDIER_SIZE = 16;
	public static final int PIKER_REPRESENTATION_RADIUS = SOLDIER_SIZE / 2;
	public static final int KNIGHT_REPRESENTATION_SIZE = SOLDIER_SIZE;
	public static final int ONAGER_REPRESENTATION_WIDTH = SOLDIER_SIZE * 2;
	public static final int ONAGER_REPRESENTATION_HEIGHT = SOLDIER_SIZE;
}
