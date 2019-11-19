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
		
		if(this.sizeX * caseWidth < sizeX)
		{
			this.sizeX += 1;
		}
		
		if(this.sizeY * caseHeight < sizeY)
		{
			this.sizeY += 1;
		}
		
		this.grid = new int[this.sizeX][this.sizeY];
		//System.out.println("sizeX:" + this.sizeX + ", sizeY:" + this.sizeY);
	}
	
	public Point2D GetCoodinatesWithCase(int x, int y)
	{
		return new Point2D((x * caseWidth) + (((caseWidth - 1) / 2) + 1), (y * caseHeight) + (((caseHeight - 1) / 2) + 1));
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
