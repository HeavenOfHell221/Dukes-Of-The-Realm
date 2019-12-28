package Path;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

import Utility.Point2D;
import static Utility.Settings.*;

public class Path
{
	private Stack<Point2D> path;
	private Map<Point2D, Node> openList;
	private Map<Point2D, Node> closeList;
	private Point2D startPoint;
	private Point2D arrivalPoint;
	
	public Path(final Point2D startPoint, final Point2D arrivalPoint)
	{
		this.path = new Stack<>();
		this.openList = new HashMap<>();
		this.closeList = new HashMap<>();
		this.startPoint = new Point2D(startPoint);
		this.arrivalPoint = new Point2D(arrivalPoint);
		computePath();
	}
	
	private void computePath()
	{
		Node start = new Node(new Point2D(this.startPoint));
		Point2D current = new Point2D(this.startPoint);
		
		openList.put(current, start);
		addToCloseList(current);
		addAdjacents(current);

		while (!(current.getX() == this.arrivalPoint.getX() && current.getY() == this.arrivalPoint.getY())
				&&
				(!openList.isEmpty()))
		{
			current = getBestPoint();
			addToCloseList(current);
			addAdjacents(current);
		}

		if ((current.getX() == this.arrivalPoint.getX() && current.getY() == this.arrivalPoint.getY()))
		{
			retrievePath();
		}
		else
		{
			// No solution
		}
	}
	
	private void addToCloseList(final Point2D p)
	{
		Node node = openList.get(p);
		closeList.put(p, node);
		openList.remove(p);
	}
	
	private void addAdjacents(final Point2D p)
	{
		Node node;
		Point2D consideredPoint = new Point2D();
		for (double i = p.getX() - 1; i <= p.getX() + 1; i++)
		{
			if (i < 0 || i >= SCENE_WIDTH)	// Out of screen
				continue;
			for (double j = p.getY() - 1; j <= p.getY() + 1; j++)
			{
				if (j < 0 || j >= SCENE_HEIGHT)	 // Out of screen
					continue;
				if (i == p.getX() && j == p.getY())  // Current point
					continue;
				if (isPointInCastle(i, j))  // Point belongs to a castle
					continue;
				
				consideredPoint = new Point2D(i, j);
				if (!closeList.containsKey(consideredPoint))
				{
					node = new Node(p);
					node.setCostToStart(closeList.get(p).getCostToStart() + consideredPoint.distance(p));
					node.setCostToArrival(consideredPoint.distance(this.arrivalPoint));
					
					if (openList.containsKey(consideredPoint))
					{
						if (node.getTotalCost() < openList.get(consideredPoint).getTotalCost())
						{
							openList.put(consideredPoint, node);
						}
					}
					else
					{
						openList.put(consideredPoint, node);
					}
				}
			}
		}
	}
	
	private boolean isPointInCastle(final double x, final double y)
	{
		return false; //A compléter, pour l'instant elle agit comme s'il n'y avait aucun obstacle sur le route
	}
	
	private Point2D getBestPoint()
	{
		final AtomicReference<Point2D> bestPoint = new AtomicReference<>();
		double costMin = Double.POSITIVE_INFINITY;
		openList.forEach((k,v) -> 
		{
			if (v.getTotalCost() < costMin)
			{
				bestPoint.set(k);
			}
		});
		return bestPoint.get();
	}
	
	private void retrievePath()
	{
		Node node = closeList.get(this.arrivalPoint);
		Point2D prev = node.getParent();
		this.path.push(this.arrivalPoint);
		
		while(!(prev.getX() == this.startPoint.getX() && prev.getY() == this.startPoint.getY()))
		{
			this.path.push(prev);
			node = closeList.get(prev);
			prev = node.getParent();
		}
	}

	/**
	 * @return the path
	 */
	public Stack<Point2D> getPath()
	{
		return path;
	}
}
