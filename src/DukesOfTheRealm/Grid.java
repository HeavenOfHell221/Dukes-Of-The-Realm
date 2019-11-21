package DukesOfTheRealm;

import javafx.geometry.Point2D;

public class Grid {
	private int[][] grid;
	private int nbCellX;
	private int nbCellY;
	private final int cellWidth;
	private final int cellHeight;
	
	public Grid(int sizeX, int sizeY)
	{
		this.cellWidth = Settings.CELL_SIZE;
		this.cellHeight = Settings.CELL_SIZE;
		this.nbCellX = sizeX / cellWidth;
		this.nbCellY = sizeY / cellHeight;
		
		this.grid = new int[this.nbCellX][this.nbCellY];
	}
	
	public Point2D GetCoordinatesWithCell(int x, int y)
	{
		return new Point2D(x * cellWidth, y * cellHeight);
	}
	
	public Point2D GetCellWithCoordinates(int x, int y)
	{
		//System.out.println("x: "+ (x/cellWidth) + ", y: " + (y/cellWidth));
		return new Point2D(x / cellWidth, y / cellHeight);
	}

	public int[][] GetGrid() 
	{
		return grid;
	}

	public int GetSizeX() 
	{
		return nbCellX;
	}

	public int GetSizeY() 
	{
		return nbCellY;
	}

	public int GetCaseWidth() 
	{
		return cellWidth;
	}

	public int GetCaseHeight() 
	{
		return cellHeight;
	}
}
