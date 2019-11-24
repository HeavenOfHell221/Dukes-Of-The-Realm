package DukesOfTheRealm;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.Random;
import java.util.ArrayList;

import Duke.*;
import UI.CastleUI;
import Utility.Settings;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Kingdom extends Parent implements IUpdate{

	
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	
	private ArrayList<Castle> castles; // Liste des chateaux
	private ArrayList<Actor> actors; // Liste des acteurs
	private ArrayList<Color> colors;
	private static Player player;
	
	private final Pane playfieldLayer; // Le layer du jeu (groupe de base)
	
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	
	public Kingdom(Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
		this.castles = new ArrayList<Castle>();
		this.actors = new ArrayList<Actor>();
		this.colors = new ArrayList<Color>();
		colors.add(Color.DIMGRAY);
		colors.add(Color.DARKORANGE);
		colors.add(Color.HOTPINK);
		colors.add(Color.LIGHTSLATEGRAY);
		colors.add(Color.PERU);
		colors.add(Color.SILVER);
		colors.add(Color.TOMATO);
		colors.add(Color.AQUA);
		colors.add(Color.DEEPPINK);
		colors.add(Color.KHAKI);
		colors.add(Color.LINEN);
		colors.add(Color.OLIVE);
	}
	
	
	/*************************************************/
	/******************** METHODES *******************/
	/*************************************************/

	
	public boolean CreateCastle(Pane layer, Point2D coordinate, int level, Actor actor)
	{
		if(!IsCastleToClose(coordinate.getX(), coordinate.getY()))
		{
			Castle newCastle = new Castle(layer, coordinate.getX(), coordinate.getY(), level, actor);
			newCastle.AddRepresentation();
			this.getChildren().add(newCastle.GetShape());
			if (newCastle.GetActor() == player)
				newCastle.CreateOst(18, 18, 0);
			return AddCastle(newCastle);
		}
		return false;
	}
	
	public void CreateWorld(int AINumber, int baronNumber)
	{
		Random rand = new Random();
		
		Color c = colors.get(rand.nextInt(colors.size() - 1));
		Kingdom.player = new Player("Player", c);
		actors.add(Kingdom.player);
		colors.remove(c);
		
		int numberTest = 0;
		
		while(numberTest < Settings.NB_TOTAL_TEST_CREATE_CASTLE)
		{
			if(CreateCastle(playfieldLayer, GetRandomCoordinates(rand), 1, player))
			{
				break;
			}
			numberTest++;
		}
		
		c = colors.get(rand.nextInt(colors.size() - 1));
		for(int i = 0; i < AINumber; i++)
		{
			Actor a = new DukeAI("Duke " + (i+1), c);
			actors.add(a);
			colors.remove(c);
			
			numberTest = 0;
			
			while(numberTest < Settings.NB_TOTAL_TEST_CREATE_CASTLE)
			{
				if(CreateCastle(playfieldLayer, GetRandomCoordinates(rand), 1, a))
				{
					break;
				}
				numberTest++;
			}
		}
		
		c = colors.get(rand.nextInt(colors.size() - 1));
		for(int i = 0; i < baronNumber; i++)
		{
			Actor a = new Baron("Baron " + (i+1),c);
			actors.add(a);

			numberTest = 0;
			
			while(numberTest < 1000)
			{
				if(CreateCastle(playfieldLayer, GetRandomCoordinates(rand), rand.nextInt(6) + 1, a))
				{
					break;
				}
				numberTest++;
			}
		}
	}
	
	public void Start()
	{
		castles.forEach(castle -> castle.Start());
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

	public void Update(long now, boolean pause)
	{
		if(!pause)
			castles.forEach(castle -> castle.Update(now, pause));
		UpdateUI();
	}
	
	private void UpdateUI()
	{
		CastleUI instance = CastleUI.GetInstance();
		if(instance != null)
		{
			instance.Update();
		}
	}
	
	public Point2D GetRandomCoordinates(Random rand)
	{
		return new Point2D(rand.nextInt((int)(Settings.SCENE_WIDTH * Settings.MARGIN_PERCENTAGE)), rand.nextInt(Settings.SCENE_HEIGHT - (2 * Settings.CASTLE_SIZE)));
	}
	
	public static Player GetPlayer()
	{
		return player;
	}
}
