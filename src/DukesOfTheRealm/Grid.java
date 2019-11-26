package DukesOfTheRealm;

import java.util.Deque;


import Utility.Settings;
import javafx.geometry.Point2D;

public class Grid 
{
	private int nbCellX;
	private int nbCellY;
	AStar astar;
	
	private static Grid instance = new Grid();
	
	private Grid() 
	{
		this.nbCellX = (int)((Settings.SCENE_WIDTH / Settings.CELL_SIZE) * Settings.MARGIN_PERCENTAGE);
		this.nbCellY = Settings.SCENE_HEIGHT / Settings.CASTLE_SIZE;
		this.astar = new AStar(nbCellX, nbCellY, null, null);
	}
	
	public static Deque<Point2D> GetPath(Point2D coordinate)
	{
		
		return null;
	}
}
