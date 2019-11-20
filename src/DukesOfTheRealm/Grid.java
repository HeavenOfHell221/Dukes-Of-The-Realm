package DukesOfTheRealm;

import javafx.geometry.Point2D;

public class Grid {
	private int[][] grid;
	private int sizeX;
	private int sizeY;
	private final int caseWidth;
	private final int caseHeight;
	
	public Grid(int sizeX, int sizeY)
	{
		this.caseWidth = Settings.CASE_WIDTH;
		this.caseHeight = Settings.CASE_HEIGHT;
		this.sizeX = sizeX / caseWidth;
		this.sizeY = sizeY / caseHeight;
		
		this.grid = new int[this.sizeX][this.sizeY];
		//System.out.println("sizeX:" + this.sizeX + ", sizeY:" + this.sizeY);
	}
	
	public Point2D GetCoodinatesWithCase(int x, int y)
	{
		int centerX = ((caseWidth - 1) / 2) + 1;
		int centerY = ((caseHeight - 1) / 2) + 1;
		return new Point2D((x * caseWidth) + centerX, (y * caseHeight) + centerY);
	}

	public int[][] GetGrid() 
	{
		return grid;
	}

	public int GetSizeX() 
	{
		return sizeX;
	}

	public int GetSizeY() 
	{
		return sizeY;
	}

	public int GetCaseWidth() 
	{
		return caseWidth;
	}

	public int GetCaseHeight() 
	{
		return caseHeight;
	}
}
