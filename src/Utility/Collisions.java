package Utility;

import java.util.ArrayList;
import java.util.Iterator;

public class Collisions
{

	private final ArrayList<Point2D> castlesCoordinates;

	public Collisions()
	{
		this.castlesCoordinates = new ArrayList<>();
	}

	public void addPoint(final Point2D point)
	{
		this.castlesCoordinates.add(point);
	}

	public boolean isCollisionApproaching(final Point2D soldierCoordinates, final double offset, final int direction)
	{
		Iterator<Point2D> it = this.castlesCoordinates.iterator();

		if (direction == Settings.X_DIRECTION)
		{
			while (it.hasNext())
				{
				if (XCollision(it.next(), soldierCoordinates.getX()) && YCollision(it.next(), soldierCoordinates.getY()))
				{
					return true;
				}
			}
		}
		else
		{
			while (it.hasNext())
			{
				if (XCollision(it.next(), soldierCoordinates.getX()) && YCollision(it.next(), soldierCoordinates.getY()))
				{
					return true;
				}
			}
		}

		return false;
	}

	private boolean XCollision(final Point2D point, final double coordinate)
	{
		return (coordinate >= (point.getX() - Settings.GAP_WITH_SOLDIER - Settings.SOLDIER_SIZE)
				&& coordinate <= (point.getX() + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER));
	}

	private boolean YCollision(final Point2D point, final double coordinate)
	{
		return (coordinate >= (point.getY() - Settings.GAP_WITH_SOLDIER - Settings.SOLDIER_SIZE)
				&& coordinate <= (point.getY() + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER));
	}
}
