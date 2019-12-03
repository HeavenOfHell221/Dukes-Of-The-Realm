package DukesOfTheRealm;

import Utility.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import Duke.*;
import Interface.ISave;
import Interface.IUpdate;
import UI.UIManager;
import Utility.Settings;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Kingdom extends Parent implements IUpdate, ISave {

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	private ArrayList<Castle> castles; // Liste des chateaux
	private ArrayList<Actor> actors; // Liste des acteurs
	private ArrayList<Color> colors;
	private static Player player;
	
	private final Pane playfieldLayer; // Le layer du jeu (groupe de base)
	public UIManager castleUIInstance;
	private boolean canUpdate = false;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	
	public Kingdom(Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
		this.castles = new ArrayList<>();
		this.actors = new ArrayList<>();
		this.colors = new ArrayList<>();
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
		colors.add(Color.DARKOLIVEGREEN);
	}
	
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start()
	{
		castleUIInstance = UIManager.GetInstance();
		CreateWorld(Settings.AI_NUMBER, Settings.BARON_NUMBER);
		castles.forEach(castle -> castle.Start());
		castles.forEach(castle -> 
			{
				if (castle != castles.get(0))
					{
						castle.CreateOst(this.actors.get(0).GetMyCastles().get(0), 5, 5, 0);
					}
			});
		Kingdom.player.GetMyCastles().get(0).CreateOst(this.actors.get(1).GetMyCastles().get(0), 6, 9, 0);
		canUpdate = true;
	}
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/
	
	@Override
	public void Update(long now, boolean pause)
	{
		if(canUpdate)
		{
			castles.forEach(castle -> castle.Update(now, pause));
			UpdateUI(now, pause);
		}
	}
	
	private void UpdateUI(long now, boolean pause)
	{
		if(castleUIInstance != null)
		{
			castleUIInstance.Update(now, pause);
		}
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	public boolean CreateCastle(Pane layer, Point2D coordinate, int level, Actor actor)
	{
		if(!IsCastleToClose(coordinate.GetX(), coordinate.GetY()))
		{
			Castle newCastle = new Castle(layer, coordinate.GetX(), coordinate.GetY(), level, actor);
			newCastle.AddRepresentation();
			this.getChildren().add(newCastle.GetShape());
			this.getChildren().add(newCastle.GetDoor());
			return AddCastle(newCastle);
		}
		return false;
	}
	
	public void CreateWorld(int AINumber, int baronNumber)
	{
		Random rand = new Random();
		
		Color c = colors.get(rand.nextInt(colors.size() - 1));
		Kingdom.player = new Player("Player", Color.DARKRED);
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
	
	public Point2D GetRandomCoordinates(Random rand)
	{
		return new Point2D(rand.nextInt((int)(Settings.SCENE_WIDTH * Settings.MARGIN_PERCENTAGE - 2 * Settings.CASTLE_SIZE)) + Settings.CASTLE_SIZE, rand.nextInt(Settings.SCENE_HEIGHT - (4 * Settings.CASTLE_SIZE)) + Settings.CASTLE_SIZE);
	}
	
	@Override
	public void ReceivedDataSave(Object o) 
	{

	}

	@Override
	public void SendingDataSave(Object o) 
	{

	}
	
	
	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
	
	public static Player GetPlayer()
	{
		return player;
	}
	
	public ArrayList<Castle> GetCastles()
	{
		return castles;
	}
	
	public void SetCastles(ArrayList<Castle> castles)
	{
		this.castles = castles;
	}
}
