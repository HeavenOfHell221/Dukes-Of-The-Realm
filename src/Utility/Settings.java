package Utility;

public class Settings {

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
	public static final int NB_TYPES_OF_TROOPS = 3;
	public static final int SIMULTANEOUS_SPAWNS = 3;
	
	/* Grid */
	public static final int CELL_SIZE = 61;
	
	/****************************************************/
	/********************** CASTLE **********************/
	/****************************************************/
	
	/* Florin */
	public static final int FLORIN_PER_SECOND = 2;
	
	/* Levels */
	public static final int LEVEL_UP_COST_FACTOR = 1000;
	public static final int LEVEL_UP_DURATION_OFFSET = 100;
	public static final int LEVEL_UP_DURATION_FACTOR = 50;
	
	/* Graphics*/
	public static final int CASTLE_SIZE = 60;
	public static final int MIN_DISTANCE_BETWEEN_TWO_CASTLE = 350;
	
	/* Castle number */
	public static final int AI_NUMBER = 3;
	public static final int BARON_NUMBER = 6;
	public static final int NB_TOTAL_TEST_CREATE_CASTLE = 1000 * 1000;
	
	
	/****************************************************/
	/********************* SOLDIERS *********************/
	/****************************************************/
	
	/* HP */
	public static final int PIKER_HP = 0;
	public static final int KNIGHT_HP = 0;
	public static final int ONAGER_HP = 0;
	
	/* Damage */
	public static final int PIKER_DAMAGE = 0;
	public static final int KNIGHT_DAMAGE = 0;
	public static final int ONAGER_DAMAGE = 0;
	
	/* Cost */
	public static final int PIKER_COST = 0;
	public static final int KNIGHT_COST = 0;
	public static final int ONAGER_COST = 0;
	
	/* Speed */
	public static final int PIKER_SPEED = 40;
	public static final int KNIGHT_SPEED = 60;
	public static final int ONAGER_SPEED = 20;
	
	/* Production time in second */
	public static final int PIKER_TIME_PRODUCTION = 15;
	public static final int KNIGHT_TIME_PRODUCTION = 30;
	public static final int ONAGER_TIME_PRODUCTION = 60;

	/* Graphics */
	public static final int PIKER_REPRESENTATION_RADIUS = 8;
	public static final int KNIGHT_REPRESENTATION_SIZE = 16;
	public static final int ONAGER_REPRESENTATION_WIDTH = 32;
	public static final int ONAGER_REPRESENTATION_HEIGHT = 16;	
}
