package Utility;

import java.util.ArrayList;
import java.util.Iterator;
import static Utility.Settings.*;

public class Collisions
{
	
	private static final Collisions instance = new Collisions();
	private final ArrayList<Point2D> castlesCoordinates;

	private Collisions()
	{
		this.castlesCoordinates = new ArrayList<>();
	}

	public static void addPoint(final Point2D point)
	{
		instance.castlesCoordinates.add(point);
	}

	public static int isCollisionApproaching(final Point2D soldierCoordinates, final double offset)
	{
		Iterator<Point2D> it = instance.castlesCoordinates.iterator();
		while (it.hasNext())
		{
			Point2D castleCoordinates = it.next();
			if (XCollision(castleCoordinates, soldierCoordinates.getX() + offset) && YCollision(castleCoordinates, soldierCoordinates.getY()))
			{
				return X_COLLISION;
			}
			
			if (XCollision(castleCoordinates, soldierCoordinates.getX()) && YCollision(castleCoordinates, soldierCoordinates.getY() + offset))
			{
				return Y_COLLISION;
			}
		}

		return NO_COLLISION;
	}

	private static boolean XCollision(final Point2D castleCoordinates, final double coordinate)
	{
		return (coordinate > (castleCoordinates.getX() - GAP_WITH_SOLDIER - SOLDIER_SIZE) && coordinate < (castleCoordinates.getX() + CASTLE_SIZE + GAP_WITH_SOLDIER));
	}

	private static boolean YCollision(final Point2D castleCoordinates, final double coordinate)
	{
		return (coordinate > (castleCoordinates.getY() - GAP_WITH_SOLDIER - SOLDIER_SIZE) && coordinate < (castleCoordinates.getY() + CASTLE_SIZE + GAP_WITH_SOLDIER));
	}

	/**
	 * @return the instance
	 */
	public static Collisions getInstance()
	{
		return instance;
	}

	/**
	 * @return the castlesCoordinates
	 */
	public static ArrayList<Point2D> getCastlesCoordinates()
	{
		return instance.castlesCoordinates;
	}
}
