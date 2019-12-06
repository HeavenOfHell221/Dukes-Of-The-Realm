package Utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import Enum.SoldierEnum;

public class Collisions {
	
	private ArrayList<Point2D> castlesCoordinates;
	
	public Collisions()
	{
		this.castlesCoordinates = new ArrayList<>();
	}
	
	public void addPoint(Point2D point)
	{
		this.castlesCoordinates.add(point);
	}
	
	
	public boolean isCollisionApproaching(Point2D soldierCoordinates, double offset, int direction)
	{
		Iterator<Point2D> it = castlesCoordinates.iterator();
		
		if (direction == Settings.X_DIRECTION)
		{
			while(it.hasNext())
			{
				if (XCollision(it.next(), soldierCoordinates.getX()) && YCollision(it.next(), soldierCoordinates.getY()))
				{
					return true;
				}
			};
		}
		else
		{
			while(it.hasNext())
			{
				if (XCollision(it.next(), soldierCoordinates.getX()) && YCollision(it.next(), soldierCoordinates.getY()))
				{
					return true;
				}
			};
		}
		
		return false;
	}
	
	private boolean XCollision(Point2D point, double coordinate)
	{
		return (coordinate >= (point.getX() - Settings.GAP_WITH_SOLDIER - Settings.SOLDIER_SIZE) && coordinate <= (point.getX() + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER));
	}
	
	private boolean YCollision(Point2D point, double coordinate)
	{
		return (coordinate >= (point.getY() - Settings.GAP_WITH_SOLDIER - Settings.SOLDIER_SIZE) && coordinate <= (point.getY() + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER));
	}
}
