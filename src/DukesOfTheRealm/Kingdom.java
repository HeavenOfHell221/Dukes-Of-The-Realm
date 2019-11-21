package DukesOfTheRealm;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;

import Duke.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Kingdom implements IUpdateTurn, IUpdateAtEachFrame{

	
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	
	private ArrayList<Castle> castles; // Liste des chateaux
	private ArrayList<Actor> actors; // Liste des acteurs
	
	private final Pane playfieldLayer; // Le layer du jeu (groupe de base)
	
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	
	public Kingdom(Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
		this.castles = new ArrayList<Castle>();
		this.actors = new ArrayList<Actor>();
	}
	
	
	/*************************************************/
	/******************** METHODES *******************/
	/*************************************************/

	
	public boolean CreateCastle(Pane layer, Point2D p, int level, Duke duke)
	{
		if(!IsCastleToClose(p.getX() , p.getY()))
		{
			Castle newCastle = new Castle(layer, p.getX(), p.getY(), level, duke);
			newCastle.AddRectangle();
			return AddCastle(newCastle);
		}
		return false;
	}
	
	private boolean AddCastle(Castle castle)
	{
		return castles.add(castle);
	}
	
	private boolean IsCastleToClose(double x, double y)
	{		
		Iterator<Castle> it = castles.iterator();
		while(it.hasNext())
		{
			Castle currentCastle = it.next();
			double d = DistanceBetween(currentCastle, x, y);
			if(d < Settings.MIN_DISTANCE_BETWEEN_TWO_CASTLE)
			{
				return true;
			}		
		}
		return false;
	}

	private double DistanceBetween(Castle castle, double x, double y)
	{
		return Math.sqrt((y - castle.GetY()) * (y - castle.GetY()) + (x - castle.GetX()) * (x - castle.GetX()));
	}
	
	public boolean RemoveCastle(Castle castle)
	{
		return castles.remove(castle);
	}
	
	public ArrayList<Castle> GetAllCastlesOfThisDuke(Duke duke)
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
	
	public ArrayList<Castle> GetAllCastles()
	{
		return castles;
	}
	
	public Castle GetCastle(int i)
	{
		if(i < castles.size())
			return castles.get(i);
		return null;
	}
	
	public void UpdateTurn()
	{
		castles.forEach(castle -> castle.UpdateTurn());
	}
	
	public void UpdateAtEachFrame(long now)
	{
		castles.forEach(castle -> castle.UpdateAtEachFrame(now));
	}
	
	public Point2D GetRandomGridCell(Random rand)
	{
		Point2D p = new Point2D(rand.nextInt(Grid.GetSizeX()), rand.nextInt(Grid.GetSizeY()));
		return Grid.GetCoordinatesWithCell((int)p.getX(), (int)p.getY());
	}
}
