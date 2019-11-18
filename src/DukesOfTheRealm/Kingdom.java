package DukesOfTheRealm;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

import Duke.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Kingdom {

	/* Attributs */
	private ArrayList<Castle> castles; // Liste des châteaux
	private Grid grid; // Grille de jeu
	private final double minimalDistanceBetweenTwoCastles = Settings.MIN_DISTANCE_BETWEEN_TWO_CASTLE * Settings.MIN_DISTANCE_BETWEEN_TWO_CASTLE; // Distance minimal autorisé entre deux châteaux
	
	public Kingdom()
	{
		this.castles = new ArrayList<Castle>();
		this.grid = new Grid((int)Settings.SCENE_WIDTH, (int)Settings.SCENE_HEIGHT);
	}
	
	
	public boolean CreateCastle(Pane layer, Image image, double x, double y, int level, Duke duke)
	{
		if(!IsCastleIsToCloser(x , y))
		{
			Castle newCastle = new Castle(layer, image, x, y, level, duke);
			newCastle.AddRectangle();
			return AddCastle(newCastle);
		}
		return false;
	}
	
	public void DeleteCastle(Castle castle)
	{
		if(castle != null)
		{
			castle.removeFromLayerShape();
			castles.remove(castle);
		}
	}
	
	public void DeleteAllCastle()
	{
		Iterator<Castle> it = castles.iterator();
		while(it.hasNext())
		{
			Castle castle = it.next();
			castle.removeFromLayerShape();
			castle.remove();
		}	
	}
	
	private boolean AddCastle(Castle castle)
	{
		return castles.add(castle);
	}
	
	private boolean IsCastleIsToCloser(double x, double y)
	{
		Castle closerCastle = null;
		double minimalDistance = Double.POSITIVE_INFINITY;
		
		Iterator<Castle> it = castles.iterator();
		
		while(it.hasNext())
		{
			Castle currentCastle = it.next();
			double d = DistanceBetween(currentCastle, x, y);
			if(d < minimalDistance)
			{
				minimalDistance = d;
				closerCastle = currentCastle;
			}			
		}
		return (minimalDistance < minimalDistanceBetweenTwoCastles);
	}

	private double DistanceBetween(Castle castle, double x, double y)
	{
		return Point2D.distanceSq(x, y, castle.getX(), castle.getY());
	}
	
	public boolean RemoveCastle(Castle castle)
	{
		return castles.remove(castle);
	}
	
	public ArrayList<Castle> GetCastlesOfThisDuke(Duke duke)
	{
		ArrayList<Castle> list = new ArrayList<Castle>();
		
		castles.forEach(
			castle -> 
			{ 
				if(castle.GetDuke().equals(duke)) 
					list.add(castle); 
			} 
		);
		
		return list;
	}
	
	public ArrayList<Castle> GetCastles()
	{
		return castles;
	}
	
	public Castle GetCastle(int i)
	{
		if(i < castles.size())
			return castles.get(i);
		return null;
	}
	
	public Grid GetGrid()
	{
		return grid;
	}
	
	public void Update()
	{
		castles.forEach(
			castle -> 
			{
				castle.Update();
			}
		);
	}
}
