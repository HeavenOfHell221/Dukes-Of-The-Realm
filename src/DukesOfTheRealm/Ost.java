package DukesOfTheRealm;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import Soldiers.*;
import Utility.FPS;
import Utility.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Ost implements IUpdate{
	
	private Castle origin;
	private Castle destination;
	private int nb_piker;
	private int nb_knight;
	private int nb_onager;
	private ArrayList<Soldier> soldiers;
	private int speed;

	
	public Ost(Castle origin, Castle destination, int nb_piker, int nb_knight, int nb_onager)
	{
		this.origin = origin;
		this.destination = destination;
		this.nb_piker = nb_piker;
		this.nb_knight = nb_knight;
		this.nb_onager = nb_onager;
		this.soldiers = new ArrayList<Soldier>();
		this.speed = SetOstSpeed();
		//DeploySoldiers();
	}

	public void Update(long now, boolean pause)
	{
		//TO DO
	}
	
	private int SetOstSpeed()
	{
		int minimalSpeed = Settings.KNIGHT_SPEED;
		minimalSpeed = (this.nb_piker > 0) ? Settings.PIKER_SPEED : minimalSpeed;
		minimalSpeed = (this.nb_onager > 0) ? Settings.ONAGER_SPEED : minimalSpeed;
		return minimalSpeed;
	}
	
	
	private void DeploySoldiers()
	{
		int nb_soldier = this.nb_piker + this.nb_knight + this.nb_onager;
		int currentSpawn = 0;
		
		while (nb_soldier > 0 && currentSpawn < Settings.SIMULTANEOUS_SPAWNS)
		{
			switch (currentSpawn)
			{
			case 0:
				//SpawnSoldier(/** coord x **/, /** coord y **/);
				break;
			case 1:
				//SpawnSoldier(/** coord x **/, /** coord y **/);
				break;
			case 2:
				//SpawnSoldier(/** coord x **/, /** coord y **/);
				break;
			}
		}
	}
	
	private void SpawnSoldier(int x, int y)
	{
		AtomicReference<SoldierEnum> soldierType = GetNextAvailableSoldier();
		Pane layer = this.origin.getLayer();
		switch (soldierType.get())
		{
		case Piker: this.soldiers.add(new Piker(layer, x, y)); break;
		case Knight: this.soldiers.add(new Knight(layer, x, y)); break;
		case Onager: this.soldiers.add(new Onager(layer, x, y)); break;
		default: break;
		}
	}
	
	private AtomicReference<SoldierEnum> GetNextAvailableSoldier()
	{
		AtomicReference<SoldierEnum> slowestType = new AtomicReference<>();
		slowestType.set(SoldierEnum.Knight);
		this.soldiers.stream()
			.filter(soldier -> soldier.GetType() == SoldierEnum.Onager)
			.forEach(soldier -> {
				if (!soldier.isOnField())
					slowestType.set(SoldierEnum.Onager);
				});
		this.soldiers.stream()
		.filter(soldier -> soldier.GetType() == SoldierEnum.Piker)
		.forEach(soldier -> {
			if (!soldier.isOnField())
				slowestType.set(SoldierEnum.Piker);
			});
		return slowestType;
	}
	
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
