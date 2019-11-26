package DukesOfTheRealm;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Utility.Settings;
import javafx.geometry.Point2D;

public class Grid 
{
	/*private static int nbCellX = (Settings.SCENE_WIDTH / Settings.CELL_SIZE) + 4 * Settings.CASTLE_SIZE;
	private static int nbCellY = (Settings.SCENE_HEIGHT / Settings.CELL_SIZE) + 4 * Settings.CASTLE_SIZE;
	private AStar astar;
	public static ArrayList<ArrayList<Integer>> blockNodeList = new ArrayList<ArrayList<Integer>>();
	public static int[][] blockArray;
	
	public Grid() 
	{

	}
	
	private static Point2D GetCellWithCoordinate(Point2D coordinate)
	{
		return new Point2D(coordinate.getX() / Settings.CELL_SIZE, coordinate.getY() / Settings.CELL_SIZE);
	}
	
	public Deque<Point2D> GetPath(Point2D initialCoordinate, Point2D finalCoordinate)
	{
		Point2D initialCell = GetCellWithCoordinate(initialCoordinate);
		Point2D finalCell = GetCellWithCoordinate(finalCoordinate);
		
		Node initialNode = new Node((int)initialCell.getX(), (int)initialCell.getY());
		Node finalNode = new Node((int)finalCell.getX(), (int)finalCell.getY());
		astar = new AStar(nbCellX, nbCellY, initialNode, finalNode);
		astar.setBlocks(blockArray);
		
		List<Node> path = astar.findPath();
		
		for (Node node : path) {
            System.out.println(node);
        }
		
		return null;
	}
	
	public static void SetBlockArray() 
	{
		blockArray = new int[blockNodeList.size()][2];
		
		for(int i = 0; i < blockNodeList.size(); i++)
		{
			blockArray[i][0] = blockNodeList.get(i).get(0);
			blockArray[i][1] = blockNodeList.get(i).get(1);
		}
	}
	
	public static void AddCastle(Castle castle)
	{
		Point2D initialCoordinate = GetCellWithCoordinate(castle.GetCoordinate());
		
		for(int x = 0; x < Settings.CASTLE_SIZE; x += Settings.CELL_SIZE)
		{
			for(int y = 0; y < Settings.CASTLE_SIZE; y += Settings.CELL_SIZE)
			{
				ArrayList<Integer> l = new ArrayList<Integer>();
				blockNodeList.add(l);
				
				blockNodeList.get(blockNodeList.size() - 1).add((int)initialCoordinate.getX() + x);
				blockNodeList.get(blockNodeList.size() - 1).add((int)initialCoordinate.getY() + y);
			}
		}
	}*/
}
