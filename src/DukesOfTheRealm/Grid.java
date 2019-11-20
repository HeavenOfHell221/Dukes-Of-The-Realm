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
		this.cellWidth = Settings.CASE_WIDTH;
		this.cellHeight = Settings.CASE_HEIGHT;
		this.nbCellX = sizeX / cellWidth;
		this.nbCellY = sizeY / cellHeight;
		
		this.grid = new int[this.nbCellX][this.nbCellY];
		//System.out.println("sizeX:" + this.sizeX + ", sizeY:" + this.sizeY);
	}
	
	public Point2D GetCoordinatesWithCell(int x, int y)
	{
		return new Point2D(x * cellWidth, y * cellHeight);
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
