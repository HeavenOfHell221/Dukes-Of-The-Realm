package DukesOfTheRealm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

import Soldiers.*;
import Utility.Time;
import Utility.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Ost implements IUpdate{
	
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	private Castle origin;
	private Castle destination;
	private int nbPikers;
	private int nbKnights;
	private int nbOnagers;
	private ArrayList<Soldier> soldiers;
	private boolean fullyDeployed = false;
	private int speed;
	private Color color;
	private long lastTime;
	private int nbSoldiersSpawned;
	private final int nbSoldiers;

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
		this.soldiers = new ArrayList<>();
		this.color = color;
		this.nbSoldiers = this.nbPikers + this.nbKnights + this.nbOnagers;
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start()
	{
		this.speed = SetOstSpeed();
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
	
	// Pour le moment les unit�s apparaissent toutes � droite du ch�teau
	private void DeployOneSoldiersWave()
	{
		int nbSpawn = (this.soldiers.size() <= (nbSoldiers - Settings.SIMULTANEOUS_SPAWNS)) ? Settings.SIMULTANEOUS_SPAWNS : (nbSoldiers - this.soldiers.size());
		int thirdOfCastle = Settings.CASTLE_SIZE / 3;
		
		
		switch(origin.GetOrientation())
		{
			case North:
			case South:
				for (int i = 0; i < nbSpawn; i++)
					SpawnSoldier(this.origin.GetX() + (thirdOfCastle * i), this.origin.GetY());
				break;
			case West:
			case East:
				for (int i = 0; i < nbSpawn; i++)
					SpawnSoldier(this.origin.GetX(), this.origin.GetY() + (thirdOfCastle * i));
				break;
			default:
				break;
		}
		
		if (this.nbSoldiersSpawned == nbSoldiers) {
			this.fullyDeployed = true;
		}
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
		
	private void SpawnSoldier(int x, int y)
	{
		AtomicReference<SoldierEnum> soldierType = GetNextAvailableSoldier();
		Pane layer = this.origin.GetLayer();
		
		switch(origin.GetOrientation())
		{
			case North:
				switch (soldierType.get())
				{
					case Piker: Piker piker = new Piker(layer, x, y - 10 - Settings.PIKER_REPRESENTATION_RADIUS * 2, speed); this.soldiers.add(piker); piker.Awake(color); this.nbSoldiersSpawned++; break;
					case Knight: Knight knight = new Knight(layer, x, y - 10 - Settings.KNIGHT_REPRESENTATION_SIZE, speed); this.soldiers.add(knight); knight.Awake(color); this.nbSoldiersSpawned++; break;
					case Onager: Onager onager = new Onager(layer, x, y - 10 - Settings.ONAGER_REPRESENTATION_HEIGHT, speed); this.soldiers.add(onager); onager.Awake(color); this.nbSoldiersSpawned++; break;
					default: break;
				}
				break;
			case South:
				switch (soldierType.get())
				{
					case Piker: Piker piker = new Piker(layer, x, y + Settings.CASTLE_SIZE + 10, speed); this.soldiers.add(piker); piker.Awake(color); this.nbSoldiersSpawned++; break;
					case Knight: Knight knight = new Knight(layer, x, y + Settings.CASTLE_SIZE + 10, speed); this.soldiers.add(knight); knight.Awake(color); this.nbSoldiersSpawned++; break;
					case Onager: Onager onager = new Onager(layer, x, y + Settings.CASTLE_SIZE + 10, speed); this.soldiers.add(onager); onager.Awake(color); this.nbSoldiersSpawned++; break;
					default: break;
				}
				break;
			case West:
				switch (soldierType.get())
				{
					case Piker: Piker piker = new Piker(layer, x - 10 - Settings.PIKER_REPRESENTATION_RADIUS * 2, y, speed); this.soldiers.add(piker); piker.Awake(color); this.nbSoldiersSpawned++; break;
					case Knight: Knight knight = new Knight(layer, x - 10 - Settings.KNIGHT_REPRESENTATION_SIZE, y, speed); this.soldiers.add(knight); knight.Awake(color); this.nbSoldiersSpawned++; break;
					case Onager: Onager onager = new Onager(layer, x - 10 - Settings.ONAGER_REPRESENTATION_WIDTH, y, speed); this.soldiers.add(onager); onager.Awake(color); this.nbSoldiersSpawned++; break;
					default: break;
				}
				break;
			case East:
				switch (soldierType.get())
				{
					case Piker: Piker piker = new Piker(layer, x + Settings.CASTLE_SIZE + 10, y, speed); this.soldiers.add(piker); piker.Awake(color); this.nbSoldiersSpawned++; break;
					case Knight: Knight knight = new Knight(layer, x + Settings.CASTLE_SIZE + 10, y, speed); this.soldiers.add(knight); knight.Awake(color); this.nbSoldiersSpawned++; break;
					case Onager: Onager onager = new Onager(layer, x + Settings.CASTLE_SIZE + 10, y, speed); this.soldiers.add(onager); onager.Awake(color); this.nbSoldiersSpawned++; break;
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
	
	private int SetOstSpeed()
	{
		int minimalSpeed = Settings.KNIGHT_SPEED;
		minimalSpeed = (this.nbPikers > 0) ? Settings.PIKER_SPEED : minimalSpeed;
		minimalSpeed = (this.nbOnagers > 0) ? Settings.ONAGER_SPEED : minimalSpeed;
		return minimalSpeed;
	}
	
	private boolean Time(long now, boolean pause)
	{
		if(pause)
			lastTime = now;
		if(now - lastTime > Settings.GAME_FREQUENCY * 2)
		{
			lastTime = now;
			return true;
		}
		return false;
	}
	
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

	public double GetSpeed()
	{
		return speed;
	}
}
