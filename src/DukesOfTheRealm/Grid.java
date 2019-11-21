package DukesOfTheRealm;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Point2D;

public class Grid {
	
	private ArrayList<ArrayList<ArrayList<Sprite>>> grid;
	
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

		this.grid = new ArrayList<ArrayList<ArrayList<Sprite>>>();
		
		for(int x = 0; x < nbCellX; x++)
		{
			grid.add(new ArrayList<ArrayList<Sprite>>());
			for(int y = 0; y < nbCellY; y++)
			{
				grid.get(x).add(new ArrayList<Sprite>());
			}
		}
	}
	
	public static Point2D GetCoordinatesWithCell(int x, int y)
	{
		return new Point2D(x * instance.cellWidth, y * instance.cellHeight);
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
	}
	
	public static boolean AddSpriteInGrid(Sprite sprite, int cellX, int cellY)
	{
		if(cellX >= instance.nbCellX || cellY >= instance.nbCellY || cellX < 0 || cellY < 0)
			return false;
		
		if(!instance.grid.get(cellX).get(cellY).add(sprite))
			return false;
		
		sprite.SetCellX(cellX);
		sprite.SetCellY(cellY);
		
		return true;
	}
	
	public static boolean RemoveSpriteInGrid(Sprite sprite, int cellX, int cellY)
	{
		if(cellX >= instance.nbCellX || cellY >= instance.nbCellY || cellX < 0 || cellY < 0)
			return false;
		
		if(!instance.grid.get(cellX).get(cellY).remove(sprite))
			return false;
		
		sprite.SetCellX(-1);
		sprite.SetCellY(-1);
		
		return true;
	}
	
	public static ArrayList<Sprite> GetSprites(int cellX, int cellY)
	{
		if(cellX >= instance.nbCellX || cellY >= instance.nbCellY || cellX < 0 || cellY < 0) // Probleme avec les indices des cells
			return null;
			
		return instance.grid.get(cellX).get(cellY);
	}
	
	public static ArrayList<Sprite> GetNextSpritesX(int cellX, int cellY)
	{
		return GetSprites(cellX + 1, cellY);
	}
	
	public static ArrayList<Sprite> GetNextSpritesY(int cellX, int cellY)
	{
		return GetSprites(cellX, cellY + 1);
	}
	
	public static ArrayList<Sprite> GetPreviousSpritesX(int cellX, int cellY)
	{
		return GetSprites(cellX - 1, cellY);
	}
	
	public static ArrayList<Sprite> GetPreviousSpritesY(int cellX, int cellY)
	{
		return GetSprites(cellX, cellY - 1);
	}
	
	public static boolean MoveSpriteInGrid(Sprite sprite, Direction direction, Sprite destination)
	{
		int oldCellX = sprite.GetCellX();
		int oldCellY = sprite.GetCellY();
		int newCellX;
		int newCellY;
		
		switch(direction)
		{
			case Up: newCellX = oldCellX; newCellY = oldCellY - 1; break;
			case Down: newCellX = oldCellX; newCellY = oldCellY + 1; break;
			case Left: newCellX = oldCellX - 1; newCellY = oldCellY; break;
			case Right: newCellX = oldCellX + 1; newCellY = oldCellY; break;
			default: return false;
		}
		
		ArrayList<Sprite> spritesInNewCell = GetSprites(newCellX, newCellY);
		int size = spritesInNewCell.size();
		
		if(size > 0)
		{
			for(int i = 0; i < size; i++)
				if(spritesInNewCell.get(i).getClass() == Castle.class && spritesInNewCell.get(i) != destination)
					return false;
		}
		
		return RemoveSpriteInGrid(sprite, oldCellX, oldCellY) && AddSpriteInGrid(sprite, newCellX, newCellY);
	}
}
