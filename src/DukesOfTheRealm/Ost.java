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
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Soldiers.Soldier;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Ost implements IUpdate, Serializable, ISave<OstData> {

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	private final Castle origin;
	private final Castle destination;
	private Point2D separationPoint = null;
	private Point2D waitingPoint = null;
	private final int nbPikers;
	private final int nbKnights;
	private final int nbOnagers;
	private final int nbSoldiers;
	private int speed;
	private final ArrayList<Soldier> soldiers;
	private int nbSoldiersSpawned;
	private boolean fullyDeployed = false;
	private transient Color color;
	private long lastTime;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	public Ost(final Castle origin, final Castle destination, final int nbPikers, final int nbKnights, final int nbOnagers, final Color color)
	{
		this.origin = origin;
		this.destination = destination;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
		nbSoldiers = this.nbPikers + this.nbKnights + this.nbOnagers;
		soldiers = new ArrayList<>();
		this.color = color;
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void Start()
	{
		speed = SetOstSpeed();
		separationPoint = SetSeparationPoint();
		waitingPoint = SetWaitingPoint();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void Update(final long now, final boolean pause)
	{
		if (!fullyDeployed && Time(now, pause)) {
			DeployOneSoldiersWave();
		}

		if(!pause)
		{
			if(soldiers.size() > 0)
			{
				final Iterator it = soldiers.iterator();

				while(it.hasNext())
				{
					final Soldier s = (Soldier) it.next();

					if(s.isDead)
					{
						s.RemoveShapeToLayer();
						destination.FreeAttackLocation(s.GetAttackLocation());
						it.remove();
					} else {
						s.Update(now, pause);
					}
				}
			} else {
				origin.RemoveOst();
			}
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public void ReceivedDataSave(final OstData data)
	{
		data.separationPoint = separationPoint;
		data.nbPikers = nbPikers;
		data.nbKnights = nbKnights;
		data.nbOnagers = nbOnagers;
		data.speed = speed;
		data.nbSoldiersSpawned = nbSoldiersSpawned;
		data.fullyDeployed = fullyDeployed;
	}

	@Override
	public void SendingDataSave(final OstData data)
	{

	}

	private int SetOstSpeed()
	{
		int minimalSpeed = Settings.KNIGHT_SPEED;
		minimalSpeed = (nbPikers > 0) ? Settings.PIKER_SPEED : minimalSpeed;
		minimalSpeed = (nbOnagers > 0) ? Settings.ONAGER_SPEED : minimalSpeed;
		return minimalSpeed;
	}

	private Point2D SetSeparationPoint()
	{
		final Orientation area = GetDestinationArea();
		final int offsetX = (area == Orientation.NE || area == Orientation.SE) ? (- Settings.GAP_WITH_SOLDIER - Settings.SOLDIER_SIZE) : (Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER);
		final int offsetY = (area == Orientation.SE || area == Orientation.SW) ? (- Settings.GAP_WITH_SOLDIER - Settings.SOLDIER_SIZE) : (Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER);
		final Point2D separationPoint = new Point2D(destination.GetX() + offsetX, destination.GetY() + offsetY);
		return separationPoint;
	}

	private Point2D SetWaitingPoint()
	{
		//new Point2D(x, y - 2 * (10 + Settings.SOLDIER_SIZE))   HAUT Gauche
		final Orientation area = GetDestinationArea();
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
		final Point2D waitingPoint = new Point2D(destination.GetX() + offsetX, destination.GetY() + offsetY);
		return waitingPoint;
	}

	private Orientation GetDestinationArea()
	{
		Orientation area = Orientation.None;
		if (origin.GetX() <= destination.GetX())
		{
			area = (origin.GetY() <= destination.GetY()) ? Orientation.SE : Orientation.NE;
		}
		else
		{
			area = (origin.GetY() <= destination.GetY()) ? Orientation.SW : Orientation.NW;
		}
		return area;
	}

	private void DeployOneSoldiersWave()
	{
		final int nbSpawn = (soldiers.size() <= (nbSoldiers - Settings.SIMULTANEOUS_SPAWNS)) ? Settings.SIMULTANEOUS_SPAWNS : (nbSoldiers - soldiers.size());

		switch(origin.GetOrientation())
		{
		case North:
		case South:
			for (int i = 0; i < nbSpawn; i++) {
				SpawnSoldier(origin.GetX() + (Settings.THIRD_OF_CASTLE * i), origin.GetY());
			}
			break;
		case West:
		case East:
			for (int i = 0; i < nbSpawn; i++) {
				SpawnSoldier(origin.GetX(), origin.GetY() + (Settings.THIRD_OF_CASTLE * i));
			}
			break;
		default:
			break;
		}

		if (nbSoldiersSpawned == nbSoldiers) {
			fullyDeployed = true;
		}
	}

	private void SpawnSoldier(final int x, final int y)
	{
		final AtomicReference<SoldierEnum> soldierType = GetNextAvailableSoldier();
		final Pane layer = origin.GetLayer();

		switch(origin.GetOrientation())
		{
		case North:
			switch (soldierType.get())
			{
			case Piker: final Piker piker = new Piker(layer, new Point2D(x, y - 10 - Settings.PIKER_REPRESENTATION_RADIUS * 2), this, speed); soldiers.add(piker); piker.Awake(color); nbSoldiersSpawned++; break;
			case Knight: final Knight knight = new Knight(layer, new Point2D(x, y - 10 - Settings.KNIGHT_REPRESENTATION_SIZE), this, speed); soldiers.add(knight); knight.Awake(color); nbSoldiersSpawned++; break;
			case Onager: final Onager onager = new Onager(layer, new Point2D(x, y - 10 - Settings.ONAGER_REPRESENTATION_HEIGHT), this, speed); soldiers.add(onager); onager.Awake(color); nbSoldiersSpawned++; break;
			default: break;
			}
			break;
		case South:
			switch (soldierType.get())
			{
			case Piker: final Piker piker = new Piker(layer, new Point2D(x, y + Settings.CASTLE_SIZE + 10), this, speed); soldiers.add(piker); piker.Awake(color); nbSoldiersSpawned++; break;
			case Knight: final Knight knight = new Knight(layer,new Point2D( x, y + Settings.CASTLE_SIZE + 10), this, speed); soldiers.add(knight); knight.Awake(color); nbSoldiersSpawned++; break;
			case Onager: final Onager onager = new Onager(layer,new Point2D( x, y + Settings.CASTLE_SIZE + 10), this, speed); soldiers.add(onager); onager.Awake(color); nbSoldiersSpawned++; break;
			default: break;
			}
			break;
		case West:
			switch (soldierType.get())
			{
			case Piker: final Piker piker = new Piker(layer, new Point2D(x - 10 - Settings.PIKER_REPRESENTATION_RADIUS * 2, y), this, speed); soldiers.add(piker); piker.Awake(color); nbSoldiersSpawned++; break;
			case Knight: final Knight knight = new Knight(layer, new Point2D(x - 10 - Settings.KNIGHT_REPRESENTATION_SIZE, y), this, speed); soldiers.add(knight); knight.Awake(color); nbSoldiersSpawned++; break;
			case Onager: final Onager onager = new Onager(layer, new Point2D(x - 10 - Settings.ONAGER_REPRESENTATION_WIDTH, y), this, speed); soldiers.add(onager); onager.Awake(color); nbSoldiersSpawned++; break;
			default: break;
			}
			break;
		case East:
			switch (soldierType.get())
			{
			case Piker: final Piker piker = new Piker(layer, new Point2D(x + Settings.CASTLE_SIZE + 10, y), this, speed); soldiers.add(piker); piker.Awake(color); nbSoldiersSpawned++; break;
			case Knight: final Knight knight = new Knight(layer, new Point2D(x + Settings.CASTLE_SIZE + 10, y), this, speed); soldiers.add(knight); knight.Awake(color); nbSoldiersSpawned++; break;
			case Onager: final Onager onager = new Onager(layer, new Point2D(x + Settings.CASTLE_SIZE + 10, y), this, speed); soldiers.add(onager); onager.Awake(color); nbSoldiersSpawned++; break;
			default: break;
			}
			break;

		default:
			break;
		}

	}

	private AtomicReference<SoldierEnum> GetNextAvailableSoldier()
	{
		final AtomicReference<SoldierEnum> slowestType = new AtomicReference<>();
		final long nbCreatedPikers = soldiers.stream()
				.filter(soldier -> soldier.GetType() == SoldierEnum.Piker)
				.count();
		final long nbCreatedOnagers = soldiers.stream()
				.filter(soldier -> soldier.GetType() == SoldierEnum.Onager)
				.count();

		if (nbCreatedOnagers < nbOnagers) {
			slowestType.set(SoldierEnum.Onager);
		} else if (nbCreatedPikers < nbPikers) {
			slowestType.set(SoldierEnum.Piker);
		} else {
			slowestType.set(SoldierEnum.Knight);
		}
		return slowestType;
	}

	private boolean Time(final long now, final boolean pause)
	{
		if(pause) {
			lastTime = now;
		}
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
