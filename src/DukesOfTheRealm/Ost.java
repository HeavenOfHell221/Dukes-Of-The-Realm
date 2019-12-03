package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

import DukesOfTheRealm.Castle.Orientation;
import Enum.SoldierEnum;
import Interface.ISave;
import Interface.IUpdate;
import SaveSystem.OstData;
import Soldiers.*;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Ost implements IUpdate, Serializable, ISave<OstData> {

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	private Castle origin;
	private Castle destination;
	private Point2D separationPoint = null;
	private Point2D waitingPoint = null;
	private int nbPikers;
	private int nbKnights;
	private int nbOnagers;
	private final int nbSoldiers;
	private int speed;
	private ArrayList<Soldier> soldiers;
	private int nbSoldiersSpawned;
	private boolean fullyDeployed = false;
	private transient Color color;
	private long lastTime;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	public Ost(Castle origin, Castle destination, int nbPikers, int nbKnights, int nbOnagers, Color color)
	{
		this.origin = origin;
		this.destination = destination;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
		this.nbSoldiers = this.nbPikers + this.nbKnights + this.nbOnagers;
		this.soldiers = new ArrayList<>();
		this.color = color;
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start()
	{
		this.speed = SetOstSpeed();
		this.separationPoint = SetSeparationPoint();
		this.waitingPoint = SetWaitingPoint();
	}
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/
	
	@Override
	public void Update(long now, boolean pause)
	{
		if (!this.fullyDeployed && Time(now, pause))
			DeployOneSoldiersWave();

		if(!pause)
		{
			if(soldiers.size() > 0)
			{
				Iterator it = soldiers.iterator();
			
				while(it.hasNext())
				{
					Soldier s = (Soldier) it.next();
					
					if(s.isDead)
					{
						s.RemoveShapeToLayer();
						this.destination.FreeAttackLocation(s.GetAttackLocation());
						it.remove();
				}	
				else
					s.Update(now, pause);	
				}
			}
			else
				origin.RemoveOst();
		}
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	@Override
	public void ReceivedDataSave(OstData data) 
	{
		data.separationPoint = this.separationPoint;
		data.nbPikers = this.nbPikers;
		data.nbKnights = this.nbKnights;
		data.nbOnagers = this.nbOnagers;
		data.speed = this.speed;
		data.nbSoldiersSpawned = this.nbSoldiersSpawned;
		data.fullyDeployed = this.fullyDeployed;
	}

	@Override
	public void SendingDataSave(OstData data) 
	{
		
	}

	private int SetOstSpeed()
	{
		int minimalSpeed = Settings.KNIGHT_SPEED;
		minimalSpeed = (this.nbPikers > 0) ? Settings.PIKER_SPEED : minimalSpeed;
		minimalSpeed = (this.nbOnagers > 0) ? Settings.ONAGER_SPEED : minimalSpeed;
		return minimalSpeed;
	}

	private Point2D SetSeparationPoint()
	{
		Orientation area = GetDestinationArea();
		int offsetX = (area == Orientation.NE || area == Orientation.SE) ? (- Settings.GAP_WITH_SOLDIER - Settings.SOLDIER_SIZE) : (Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER);
		int offsetY = (area == Orientation.SE || area == Orientation.SW) ? (- Settings.GAP_WITH_SOLDIER - Settings.SOLDIER_SIZE) : (Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER);
		Point2D separationPoint = new Point2D(this.destination.GetX() + offsetX, this.destination.GetY() + offsetY);
		return separationPoint;
	}

	private Point2D SetWaitingPoint()
	{
		//new Point2D(x, y - 2 * (10 + Settings.SOLDIER_SIZE))   HAUT Gauche
		Orientation area = GetDestinationArea();
		int offsetX = 0;
		int offsetY = 0;
		switch (area)
		{
		case NW: offsetX = 2 * Settings.THIRD_OF_CASTLE; offsetY = Settings.CASTLE_SIZE + 2 * Settings.GAP_WITH_SOLDIER + Settings.SOLDIER_SIZE; break;
		case NE: offsetX = - 2 * (Settings.GAP_WITH_SOLDIER + Settings.SOLDIER_SIZE); offsetY = 2 * Settings.THIRD_OF_CASTLE; break;
		case SE: offsetY = - 2 * (Settings.GAP_WITH_SOLDIER + Settings.SOLDIER_SIZE); break;
		case SW: offsetX = Settings.CASTLE_SIZE + 2 * Settings.GAP_WITH_SOLDIER + Settings.SOLDIER_SIZE; break;
		default: break;
		}
		Point2D waitingPoint = new Point2D(this.destination.GetX() + offsetX, this.destination.GetY() + offsetY);
		return waitingPoint;
	}
	
	private Orientation GetDestinationArea()
	{
		Orientation area = Orientation.None;
		if (this.origin.GetX() <= this.destination.GetX())
		{
			area = (this.origin.GetY() <= this.destination.GetY()) ? Orientation.SE : Orientation.NE;
		}
		else
		{
			area = (this.origin.GetY() <= this.destination.GetY()) ? Orientation.SW : Orientation.NW;
		}
		return area;
	}
	
	private void DeployOneSoldiersWave()
	{
		int nbSpawn = (this.soldiers.size() <= (nbSoldiers - Settings.SIMULTANEOUS_SPAWNS)) ? Settings.SIMULTANEOUS_SPAWNS : (nbSoldiers - this.soldiers.size());
		
		switch(origin.GetOrientation())
		{
			case North:
			case South:
				for (int i = 0; i < nbSpawn; i++)
					SpawnSoldier(this.origin.GetX() + (Settings.THIRD_OF_CASTLE * i), this.origin.GetY());
				break;
			case West:
			case East:
				for (int i = 0; i < nbSpawn; i++)
					SpawnSoldier(this.origin.GetX(), this.origin.GetY() + (Settings.THIRD_OF_CASTLE * i));
				break;
			default:
				break;
		}
		
		if (this.nbSoldiersSpawned == nbSoldiers) {
			this.fullyDeployed = true;
		}
	}
		
	private void SpawnSoldier(int x, int y)
	{
		AtomicReference<SoldierEnum> soldierType = GetNextAvailableSoldier();
		Pane layer = this.origin.GetLayer();
		
		switch(origin.GetOrientation())
		{
			case North:
				switch (soldierType.get())
				{
					case Piker: Piker piker = new Piker(layer, x, y - 10 - Settings.PIKER_REPRESENTATION_RADIUS * 2, this, speed); this.soldiers.add(piker); piker.Awake(color); this.nbSoldiersSpawned++; break;
					case Knight: Knight knight = new Knight(layer, x, y - 10 - Settings.KNIGHT_REPRESENTATION_SIZE, this, speed); this.soldiers.add(knight); knight.Awake(color); this.nbSoldiersSpawned++; break;
					case Onager: Onager onager = new Onager(layer, x, y - 10 - Settings.ONAGER_REPRESENTATION_HEIGHT, this, speed); this.soldiers.add(onager); onager.Awake(color); this.nbSoldiersSpawned++; break;
					default: break;
				}
				break;
			case South:
				switch (soldierType.get())
				{
					case Piker: Piker piker = new Piker(layer, x, y + Settings.CASTLE_SIZE + 10, this, speed); this.soldiers.add(piker); piker.Awake(color); this.nbSoldiersSpawned++; break;
					case Knight: Knight knight = new Knight(layer, x, y + Settings.CASTLE_SIZE + 10, this, speed); this.soldiers.add(knight); knight.Awake(color); this.nbSoldiersSpawned++; break;
					case Onager: Onager onager = new Onager(layer, x, y + Settings.CASTLE_SIZE + 10, this, speed); this.soldiers.add(onager); onager.Awake(color); this.nbSoldiersSpawned++; break;
					default: break;
				}
				break;
			case West:
				switch (soldierType.get())
				{
					case Piker: Piker piker = new Piker(layer, x - 10 - Settings.PIKER_REPRESENTATION_RADIUS * 2, y, this, speed); this.soldiers.add(piker); piker.Awake(color); this.nbSoldiersSpawned++; break;
					case Knight: Knight knight = new Knight(layer, x - 10 - Settings.KNIGHT_REPRESENTATION_SIZE, y, this, speed); this.soldiers.add(knight); knight.Awake(color); this.nbSoldiersSpawned++; break;
					case Onager: Onager onager = new Onager(layer, x - 10 - Settings.ONAGER_REPRESENTATION_WIDTH, y, this, speed); this.soldiers.add(onager); onager.Awake(color); this.nbSoldiersSpawned++; break;
					default: break;
				}
				break;
			case East:
				switch (soldierType.get())
				{
					case Piker: Piker piker = new Piker(layer, x + Settings.CASTLE_SIZE + 10, y, this, speed); this.soldiers.add(piker); piker.Awake(color); this.nbSoldiersSpawned++; break;
					case Knight: Knight knight = new Knight(layer, x + Settings.CASTLE_SIZE + 10, y, this, speed); this.soldiers.add(knight); knight.Awake(color); this.nbSoldiersSpawned++; break;
					case Onager: Onager onager = new Onager(layer, x + Settings.CASTLE_SIZE + 10, y, this, speed); this.soldiers.add(onager); onager.Awake(color); this.nbSoldiersSpawned++; break;
					default: break;
				}
				break;
				
			default:
				break;
		}
		
	}
	
	private AtomicReference<SoldierEnum> GetNextAvailableSoldier()
	{
		AtomicReference<SoldierEnum> slowestType = new AtomicReference<>();
		long nbCreatedPikers = this.soldiers.stream()
				.filter(soldier -> soldier.GetType() == SoldierEnum.Piker)
				.count();
		long nbCreatedOnagers = this.soldiers.stream()
				.filter(soldier -> soldier.GetType() == SoldierEnum.Onager)
				.count();
		
		if (nbCreatedOnagers < this.nbOnagers)
			slowestType.set(SoldierEnum.Onager);
		else if (nbCreatedPikers < this.nbPikers)
			slowestType.set(SoldierEnum.Piker);
		else
			slowestType.set(SoldierEnum.Knight);
		return slowestType;
	}
	
	private boolean Time(long now, boolean pause)
	{
		if(pause)
			lastTime = now;
		if(now - lastTime > Settings.GAME_FREQUENCY)
		{
			lastTime = now;
			return true;
		}
		return false;
	}
	
	/*public void SetOstData(OstData ostData)
	{
		
	}*/
	
	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
	
	public Castle GetOrigin()
	{
		return origin;
	}

	public Castle GetDestination()
	{
		return destination;
	}

	public Point2D GetSeparationPoint()
	{
		return separationPoint;
	}

	public Point2D GetWaitingPoint()
	{
		return waitingPoint;
	}

	public double GetSpeed()
	{
		return speed;
	}
	
	public ArrayList<Soldier> GetSoldiers()
	{
		return soldiers;
	}
}
