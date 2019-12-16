package Utility;

import static Utility.Settings.CASTLE_SIZE;
import static Utility.Settings.GAP_WITH_SOLDIER;
import static Utility.Settings.NO_COLLISION;
import static Utility.Settings.SOLDIER_SIZE;
import static Utility.Settings.X_COLLISION;
import static Utility.Settings.Y_COLLISION;

import java.util.ArrayList;
import java.util.Iterator;

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

	public static int isCollisionApproaching(final Point2D soldierCoordinates, final double offsetX, final double offsetY)
	{
		Iterator<Point2D> it = instance.castlesCoordinates.iterator();
		while (it.hasNext())
		{
			Point2D castleCoordinates = it.next();
			if (XCollision(castleCoordinates, soldierCoordinates.getX() + offsetX)
					&& YCollision(castleCoordinates, soldierCoordinates.getY()))
			{
				return X_COLLISION;
			}

			if (XCollision(castleCoordinates, soldierCoordinates.getX())
					&& YCollision(castleCoordinates, soldierCoordinates.getY() + offsetY))
			{
				return Y_COLLISION;
			}
		}

		return NO_COLLISION;
	}

	private static boolean XCollision(final Point2D castleCoordinates, final double coordinate)
	{
		return (coordinate + SOLDIER_SIZE > (castleCoordinates.getX() - GAP_WITH_SOLDIER)
				&& coordinate < (castleCoordinates.getX() + CASTLE_SIZE + GAP_WITH_SOLDIER));
	}

	private static boolean YCollision(final Point2D castleCoordinates, final double coordinate)
	{
		return (coordinate + SOLDIER_SIZE > (castleCoordinates.getY() - GAP_WITH_SOLDIER)
				&& coordinate < (castleCoordinates.getY() + CASTLE_SIZE + GAP_WITH_SOLDIER));
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
