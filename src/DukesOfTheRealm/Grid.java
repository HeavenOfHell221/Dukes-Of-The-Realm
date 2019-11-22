package DukesOfTheRealm;

import java.util.ArrayList;
import java.util.HashMap;

import Utility.Settings;
import javafx.geometry.Point2D;

public class Grid {
	
	/*private Point2D[][] grid;
	private HashMap<Point2D, Castle> castleHashMap;
	
	private final int nbCellX;
	private final int nbCellY;
	
	private final double cellWidth;
	private final double cellHeight;
	
	private static Grid instance = new Grid(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
	
	
	private Grid(int sizeX, int sizeY)
	{
		this.cellWidth = Settings.CELL_SIZE;
		this.cellHeight = Settings.CELL_SIZE;
		this.nbCellX = (int) (sizeX / cellWidth);
		this.nbCellY = (int) (sizeY / cellHeight);
		this.grid = new Point2D[nbCellX][nbCellY];
		
		for(int x = 0; x < nbCellX; x++)
		{
			for(int y = 0; y < nbCellY; y++)
			{
				grid[x][y] = new Point2D(x, y);
			}
		}
		
		this.castleHashMap = new HashMap<Point2D, Castle>();
	}
	
	public static void AddCastle(Castle castle)
	{	
		instance.castleHashMap.put(instance.grid[castle.GetCellX()][castle.GetCellY()], castle);
		
		instance.castleHashMap.put(instance.grid[castle.GetCellX()][castle.GetCellY() - 1], castle);
		instance.castleHashMap.put(instance.grid[castle.GetCellX()][castle.GetCellY() + 1], castle);
		instance.castleHashMap.put(instance.grid[castle.GetCellX() - 1][castle.GetCellY()], castle);
		instance.castleHashMap.put(instance.grid[castle.GetCellX() + 1][castle.GetCellY()], castle);
		
		instance.castleHashMap.put(instance.grid[castle.GetCellX() - 1][castle.GetCellY() + 1], castle);
		instance.castleHashMap.put(instance.grid[castle.GetCellX() - 1][castle.GetCellY() - 1], castle);
		
		instance.castleHashMap.put(instance.grid[castle.GetCellX() + 1][castle.GetCellY() + 1], castle);
		instance.castleHashMap.put(instance.grid[castle.GetCellX() + 1][castle.GetCellY() - 1], castle);
	}
	
	public static Castle GetCastleAtThisCellIfExist(int cellX, int cellY)
	{
		return instance.castleHashMap.get(instance.grid[cellX][cellY]);
	}
	
	public static Point2D GetCoordinatesWithCell(int x, int y)
	{
		return new Point2D(x * instance.cellWidth + Settings.CELL_SIZE, y * instance.cellHeight + Settings.CELL_SIZE);
	}
	
	public static Point2D GetCellWithCoordinates(double x, double y)
	{
		return new Point2D((int) (x / instance.cellWidth),(int) (y / instance.cellHeight));
	}

	public static int GetSizeX() 
	{
		return instance.nbCellX;
	}

	public static int GetSizeY() 
	{
		return instance.nbCellY;
	}

	public static double GetCaseWidth() 
	{
		return instance.cellWidth;
	}

	public static double GetCaseHeight() 
	{
		return instance.cellHeight;
	}*/
}
