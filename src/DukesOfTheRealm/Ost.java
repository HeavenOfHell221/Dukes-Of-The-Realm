package DukesOfTheRealm;

import java.util.ArrayList;
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
		this.soldiers = new ArrayList<Soldier>();
		this.color = color;
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	public void Start()
	{
		this.speed = SetOstSpeed();
	}
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/
	
	public void Update(long now, boolean pause)
	{
		if (!this.fullyDeployed && Time(now, pause))
			DeployOneSoldiersWave();
		
		if(!pause)
			soldiers.forEach(soldier -> soldier.Update(now, pause));
	}
	
	// Pour le moment les unités apparaissent toutes à droite du château
	private void DeployOneSoldiersWave()
	{
		int nbSoldiers = this.nbPikers + this.nbKnights + this.nbOnagers;
		int nbSpawn = (this.soldiers.size() <= (nbSoldiers - Settings.SIMULTANEOUS_SPAWNS)) ? Settings.SIMULTANEOUS_SPAWNS : (nbSoldiers - this.soldiers.size());
		int thirdOfCastle = Settings.CASTLE_SIZE / 3;
		
		for (int i = 0; i < nbSpawn; i++)
		{
			SpawnSoldier(this.origin.GetX() + Settings.CASTLE_SIZE, this.origin.GetY() + (thirdOfCastle * i));
		}
		
		if (this.soldiers.size() == nbSoldiers) {
			this.fullyDeployed = true;
		}
	}
	
	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
		
	private void SpawnSoldier(int x, int y)
	{
		//System.out.println("x = " + x + " et y = " + y);
		AtomicReference<SoldierEnum> soldierType = GetNextAvailableSoldier();
		Pane layer = this.origin.getLayer();
		switch (soldierType.get())
		{
		case Piker: Piker piker = new Piker(layer, x, y, speed); this.soldiers.add(piker); piker.Start(color); break;
		case Knight: Knight knight = new Knight(layer, x, y, speed); this.soldiers.add(knight); knight.Start(color); break;
		case Onager: Onager onager = new Onager(layer, x, y, speed); this.soldiers.add(onager); onager.Start(color); break;
		default: break;
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
