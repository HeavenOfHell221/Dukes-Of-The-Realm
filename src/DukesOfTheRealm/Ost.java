package DukesOfTheRealm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

import DukesOfTheRealm.Castle.Orientation;
import Enum.SoldierEnum;
import Interface.IUpdate;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Soldiers.Soldier;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Ost implements IUpdate, Serializable
{
	private static final long serialVersionUID = 1L;

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
	private transient long lastTime;

	private boolean isBackup;

	private boolean stopAttack = false;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	public Ost(final Castle origin, final Castle destination, final int nbPikers, final int nbKnights, final int nbOnagers,
			final Color color, final boolean isBackup)
	{
		this.origin = origin;
		this.destination = destination;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
		this.nbSoldiers = this.nbPikers + this.nbKnights + this.nbOnagers;
		this.soldiers = new ArrayList<>();
		this.color = color;
		this.isBackup = isBackup;
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		this.speed = SetOstSpeed();
		this.separationPoint = SetSeparationPoint();
		this.waitingPoint = SetWaitingPoint();
	}

	public void startTransient(final Color color)
	{
		this.color = color;
		this.soldiers.forEach(soldier ->
		{
			soldier.startTransient(this.origin.getLayer());
			soldier.Awake(this.color);
		});
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		this.isBackup = this.origin.getActor() == this.destination.getActor();

		if (!this.fullyDeployed && Time(now, pause))
		{
			DeployOneSoldiersWave();
		}

		if (!pause)
		{
			if (this.soldiers.size() > 0)
			{
				final Iterator<Soldier> it = this.soldiers.iterator();

				while (it.hasNext())
				{
					Soldier s = it.next();

					if (s.isDead)
					{
						s.RemoveShapeToLayer();
						this.destination.freeAttackLocation(s.getAttackLocation());
						it.remove();
						Main.nbSoldier--;
					}
					else
					{
						s.update(now, pause);
					}
				}
			}
			else
			{
				this.origin.removeOst();
			}
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	private int SetOstSpeed()
	{
		int minimalSpeed = Settings.KNIGHT_SPEED;
		minimalSpeed = this.nbPikers > 0 ? Settings.PIKER_SPEED : minimalSpeed;
		minimalSpeed = this.nbOnagers > 0 ? Settings.ONAGER_SPEED : minimalSpeed;
		return minimalSpeed;
	}

	private Point2D SetSeparationPoint()
	{
		final Orientation area = GetDestinationArea();
		final int offsetX = area == Orientation.NE || area == Orientation.SE ? -Settings.GAP_WITH_SOLDIER - Settings.SOLDIER_SIZE
				: Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER;
		final int offsetY = area == Orientation.SE || area == Orientation.SW ? -Settings.GAP_WITH_SOLDIER - Settings.SOLDIER_SIZE
				: Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER;
		final Point2D separationPoint = new Point2D(this.destination.getX() + offsetX, this.destination.getY() + offsetY);
		return separationPoint;
	}

	private Point2D SetWaitingPoint()
	{
		// new Point2D(x, y - 2 * (10 + Settings.SOLDIER_SIZE)) HAUT Gauche
		final Orientation area = GetDestinationArea();
		int offsetX = 0;
		int offsetY = 0;
		switch (area)
		{
			case NW:
				offsetX = 2 * Settings.THIRD_OF_CASTLE;
				offsetY = Settings.CASTLE_SIZE + 2 * Settings.GAP_WITH_SOLDIER + Settings.SOLDIER_SIZE;
				break;
			case NE:
				offsetX = -2 * (Settings.GAP_WITH_SOLDIER + Settings.SOLDIER_SIZE);
				offsetY = 2 * Settings.THIRD_OF_CASTLE;
				break;
			case SE:
				offsetY = -2 * (Settings.GAP_WITH_SOLDIER + Settings.SOLDIER_SIZE);
				break;
			case SW:
				offsetX = Settings.CASTLE_SIZE + 2 * Settings.GAP_WITH_SOLDIER + Settings.SOLDIER_SIZE;
				break;
			default:
				break;
		}
		final Point2D waitingPoint = new Point2D(this.destination.getX() + offsetX, this.destination.getY() + offsetY);
		return waitingPoint;
	}

	private Orientation GetDestinationArea()
	{
		Orientation area = Orientation.None;
		if (this.origin.getX() <= this.destination.getX())
		{
			area = this.origin.getY() <= this.destination.getY() ? Orientation.SE : Orientation.NE;
		}
		else
		{
			area = this.origin.getY() <= this.destination.getY() ? Orientation.SW : Orientation.NW;
		}
		return area;
	}

	private void DeployOneSoldiersWave()
	{
		final int nbSpawn = this.nbSoldiersSpawned <= this.nbSoldiers - Settings.SIMULTANEOUS_SPAWNS ? Settings.SIMULTANEOUS_SPAWNS
				: this.nbSoldiers - this.nbSoldiersSpawned;
		Main.nbSoldier += nbSpawn;
		switch (this.origin.getOrientation())
		{
			case North:
			case South:
				for (int i = 0; i < nbSpawn; i++)
				{
					SpawnSoldier(this.origin.getX() + Settings.THIRD_OF_CASTLE * i, this.origin.getY());
				}
				break;
			case West:
			case East:
				for (int i = 0; i < nbSpawn; i++)
				{
					SpawnSoldier(this.origin.getX(), this.origin.getY() + Settings.THIRD_OF_CASTLE * i);
				}
				break;
			default:
				break;
		}

		if (this.nbSoldiersSpawned == this.nbSoldiers)
		{
			this.fullyDeployed = true;
		}
	}

	private void SpawnSoldier(final int x, final int y)
	{
		final AtomicReference<SoldierEnum> soldierType = getNextAvailableSoldier();
		final Pane layer = this.origin.getLayer();

		switch (this.origin.getOrientation())
		{
			case North:
				switch (soldierType.get())
				{
					case Piker:
						final Piker piker = new Piker(layer,
								new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.PIKER_REPRESENTATION_RADIUS * 2), this, this.speed);
						this.soldiers.add(piker);
						piker.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Knight:
						final Knight knight = new Knight(layer,
								new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.KNIGHT_REPRESENTATION_SIZE), this, this.speed);
						this.soldiers.add(knight);
						knight.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Onager:
						final Onager onager = new Onager(layer,
								new Point2D(x, y - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE), this, this.speed);
						this.soldiers.add(onager);
						onager.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					default:
						break;
				}
				break;
			case South:
				switch (soldierType.get())
				{
					case Piker:
						final Piker piker = new Piker(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this,
								this.speed);
						this.soldiers.add(piker);
						piker.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Knight:
						final Knight knight = new Knight(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this,
								this.speed);
						this.soldiers.add(knight);
						knight.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Onager:
						final Onager onager = new Onager(layer, new Point2D(x, y + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER), this,
								this.speed);
						this.soldiers.add(onager);
						onager.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					default:
						break;
				}
				break;
			case West:
				switch (soldierType.get())
				{
					case Piker:
						final Piker piker = new Piker(layer,
								new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.PIKER_REPRESENTATION_RADIUS * 2, y), this, this.speed);
						this.soldiers.add(piker);
						piker.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Knight:
						final Knight knight = new Knight(layer,
								new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.KNIGHT_REPRESENTATION_SIZE, y), this, this.speed);
						this.soldiers.add(knight);
						knight.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Onager:
						final Onager onager = new Onager(layer,
								new Point2D(x - Settings.GAP_WITH_SOLDIER - Settings.ONAGER_REPRESENTATION_SIZE, y), this, this.speed);
						this.soldiers.add(onager);
						onager.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					default:
						break;
				}
				break;
			case East:
				switch (soldierType.get())
				{
					case Piker:
						final Piker piker = new Piker(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this,
								this.speed);
						this.soldiers.add(piker);
						piker.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Knight:
						final Knight knight = new Knight(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this,
								this.speed);
						this.soldiers.add(knight);
						knight.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					case Onager:
						final Onager onager = new Onager(layer, new Point2D(x + Settings.CASTLE_SIZE + Settings.GAP_WITH_SOLDIER, y), this,
								this.speed);
						this.soldiers.add(onager);
						onager.Awake(this.color);
						this.nbSoldiersSpawned++;
						break;
					default:
						break;
				}
				break;

			default:
				break;
		}

	}

	private AtomicReference<SoldierEnum> getNextAvailableSoldier()
	{
		final AtomicReference<SoldierEnum> slowestType = new AtomicReference<>();
		final long nbCreatedPikers = this.soldiers.stream().filter(soldier -> soldier.getType() == SoldierEnum.Piker).count();
		final long nbCreatedOnagers = this.soldiers.stream().filter(soldier -> soldier.getType() == SoldierEnum.Onager).count();

		if (nbCreatedOnagers < this.nbOnagers)
		{
			slowestType.set(SoldierEnum.Onager);
		}
		else if (nbCreatedPikers < this.nbPikers)
		{
			slowestType.set(SoldierEnum.Piker);
		}
		else
		{
			slowestType.set(SoldierEnum.Knight);
		}
		return slowestType;
	}

	private boolean Time(final long now, final boolean pause)
	{
		if (pause)
		{
			this.lastTime = now;
		}
		if (now - this.lastTime > Settings.GAME_FREQUENCY_OST)
		{
			this.lastTime = now;
			return true;
		}
		return false;
	}

	public void win()
	{
		if (!this.stopAttack && !this.origin.getActor().isDead)
		{
			this.stopAttack = true;
			if (!this.isBackup)
			{
				this.origin.getActor().castlesWaitForAdding.add(this.destination);
				this.destination.getActor().castlesWaitForDelete.add(this.destination);

				this.destination.setActor(this.origin.getActor());

				this.destination.switchColor(this.origin.getActor().getColor());
				this.destination.resetQueue(false);
				this.destination.reactivateAttack();
			}
		}
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return the origin
	 */
	public final Castle getOrigin()
	{
		return this.origin;
	}

	/**
	 * @return the destination
	 */
	public final Castle getDestination()
	{
		return this.destination;
	}

	/**
	 * @return the separationPoint
	 */
	public final Point2D getSeparationPoint()
	{
		return this.separationPoint;
	}

	/**
	 * @return the waitingPoint
	 */
	public final Point2D getWaitingPoint()
	{
		return this.waitingPoint;
	}

	/**
	 * @return the fullyDeployed
	 */
	public final boolean isFullyDeployed()
	{
		return this.fullyDeployed;
	}

	/**
	 * @return the isBackup
	 */
	public final boolean isBackup()
	{
		return this.isBackup;
	}

	/**
	 * @return the stopAttack
	 */
	public final boolean isStopAttack()
	{
		return this.stopAttack;
	}

	/**
	 * @return the soldiers
	 */
	public final ArrayList<Soldier> getSoldiers()
	{
		return this.soldiers;
	}

}
