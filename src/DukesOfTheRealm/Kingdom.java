package DukesOfTheRealm;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;

import Duke.*;
import Utility.Settings;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Kingdom extends Parent implements IUpdate{

	
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

	
	public boolean CreateCastle(Pane layer, Point2D coordinate, int level, Actor actor)
	{
		System.out.println(coordinate);
		if(!IsCastleToClose(coordinate.getX(), coordinate.getY()))
		{
			Castle newCastle = new Castle(layer, coordinate.getX(), coordinate.getY(), level, actor);
			newCastle.AddRectangle();
			this.getChildren().add(newCastle.GetShape());
			
			return AddCastle(newCastle);
		}
		return false;
	}
	
	public void CreateWorld(int AINumber, int baronNumber)
	{
		Random rand = new Random();
		
		/*for(int i = 0; i < AINumber; i++)
		{
			Actor a = new DukeAI();
			actors.add(a);
			
			int numberTest = 0;
			
			while(numberTest < 100)
			{
				if(CreateCastle(playfieldLayer, GetRandomGridCell(rand), 1, a))
				{
					break;
				}
				numberTest++;
			}
		}
		
		for(int i = 0; i < baronNumber; i++)
		{
			Actor a = new Baron();
			actors.add(a);
			
			int numberTest = 0;
			
			while(numberTest < 100)
			{
				if(CreateCastle(playfieldLayer, GetRandomGridCell(rand), rand.nextInt(6) + 1, a))
				{
					break;
				}
				numberTest++;
			}
		}*/
		
		Actor a = new DukeAI();
		actors.add(a);
		CreateCastle(playfieldLayer, Grid.GetCoordinatesWithCell(0, 0), rand.nextInt(6) + 1, a);
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

	public void Update(long now)
	{
		castles.forEach(castle -> castle.Update(now));
	}
	
	public Point2D GetRandomGridCell(Random rand)
	{
		Point2D p = new Point2D(rand.nextInt(Grid.GetSizeX() - 2) + 1, rand.nextInt(Grid.GetSizeY() - 2) + 1);
		return Grid.GetCoordinatesWithCell((int)p.getX(), (int)p.getY());
	}
}
