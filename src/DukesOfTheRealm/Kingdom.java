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
	private final double minimalDistanceBetweenTwoCastles = 300; // Distance minimal autorisé entre deux châteaux
	
	public Kingdom()
	{
		this.castles = new ArrayList<Castle>();
		this.grid = new Grid((int)Settings.SCENE_WIDTH, (int)Settings.SCENE_HEIGHT);
	}
	
	
	public Boolean CreateCastle(Pane layer, Image image, double x, double y, int level, Duke duke)
	{
		if(!IsCastleIsToCloser(x , y))
		{
			Castle newCastle = new Castle(layer, image, x, y, level, duke);
			newCastle.AddRectangle();
			return AddCastle(newCastle);
		}
		return false;
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
		return Point2D.distance(x, y, castle.getX(), castle.getY());
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
