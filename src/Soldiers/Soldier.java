package Soldiers;

import java.io.Serializable;
import java.util.ArrayList;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Castle.Orientation;
import DukesOfTheRealm.Ost;
import DukesOfTheRealm.ReserveOfSoldiers;
import DukesOfTheRealm.Sprite;
import Enum.SoldierEnum;
import Path.Path;
import Interface.IUpdate;
import Utility.Point2D;
import Utility.Settings;
import Utility.Time;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Soldier extends Sprite implements Serializable, IUpdate
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	protected SoldierEnum type;
	protected Stats stats;
	protected Ost itsOst = null;
	public boolean onField = false;
	protected boolean isArrived = false;
	protected boolean isInPosition = false;
	public boolean isDead = false;
	protected Point2D attackLocation = null;
	protected boolean isWaitingForAttackLocation = false;
	protected Path path;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * 
	 * @param layer
	 * @param coord
	 * @param itsOst
	 */
	public Soldier(final Pane layer, final Point2D coord, final Ost itsOst)
	{
		this.canvas = layer;
		this.coordinate = coord;
		this.itsOst = itsOst;
	}

	/**
	 * 
	 */
	protected Soldier()
	{

	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	public void start()
	{
		if (this.attackLocation == null || this.isWaitingForAttackLocation)
		{
			//this.path = new Path(this.getCoordinate(), getSeparationPoint());
			this.canMove = true;
		}
	}

	public void Awake(final Color color)
	{
		getShape().setFill(color);
		getLayer().getChildren().add(getShape());
		this.onField = true;
		start();
	}

	public void startTransient(final Pane pane)
	{
		this.canvas = pane;
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		if (this.isDead)
		{
			return;
		}

		if (this.canMove && !this.isArrived)
		{
			Move(getSeparationPoint(), 1d); //Move(this.path.getPath().pop());
		}

		if (this.isArrived && !this.isInPosition)	// Arrived to castle but not in position to attack
		{
			if (this.attackLocation != null)
			{
				Move(this.attackLocation, 1d);
			}
			else
			{
				Move(getWaitingPoint(), 0.5d);
			}
		}

		if (this.isWaitingForAttackLocation)
		{
			SetAttackLocation();
		}

		if (this.isInPosition && !this.isWaitingForAttackLocation)
		{
			if (!this.itsOst.isBackup() || isStopAttack())
			{
				attack();
			}
			else
			{
				addInReserve(getDestination().getReserveOfSoldiers());
				this.isDead = true;
			}
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * 
	 * @param reserve
	 */
	protected abstract void addInReserve(ReserveOfSoldiers reserve);

	/**
	 * 
	 */
	private final void SetAttackLocation()
	{
		if (getDestination().isAvailableAttackLocation())
		{
			this.attackLocation = getDestination().getNextAttackLocation();
			this.isWaitingForAttackLocation = false;
		}
		else
		{
			this.isWaitingForAttackLocation = true;
		}
	}

	/**
	 * 
	 * @param dst
	 * @param factorSpeed
	 */
	private void Move(final Point2D dst, final double factorSpeed)
	{
		if (this.isWaitingForAttackLocation)
		{
			return;
		}

		isOutOfScreen();

		final int directionX = getX() < dst.getX() ? 1 : getX() == (int) dst.getX() || dst.delta(this.coordinate).getX() <= 0.5d ? 0 : -1;
		final int directionY = getY() < dst.getY() ? 1 : getY() == (int) dst.getY() || dst.delta(this.coordinate).getY() <= 0.5d ? 0 : -1;

		double offsetX = this.stats.speed * Time.deltaTime * directionX * factorSpeed;
		double offsetY = this.stats.speed * Time.deltaTime * directionY * factorSpeed;

		addMotion(offsetX, offsetY);
		updateUIShape();

		if (!this.isArrived)
		{
			isArrived();
		}
		else if (!this.isInPosition)
		{
			isInPosition();
		}
	}

	/**
	 * 
	 */
	private void isOutOfScreen()
	{
		if (getX() > Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.04) || getY() > Settings.SCENE_HEIGHT || getX() <= 0
				|| getY() <= 0)
		{
			this.isDead = true;
		}
	}

	private void isArrived()
	{
		if (getX() == getSeparationPoint().getX() && getY() == getSeparationPoint().getY()) //if (this.path.getPath().empty())
		{
			this.isArrived = true;
			SetAttackLocation();
			//Calculer chemin vers position d'attaque
		}
	}

	private void isInPosition()
	{
		if (!this.isWaitingForAttackLocation)
		{
			if (getX() == this.attackLocation.getX() && getY() == this.attackLocation.getY())
			{
				this.isInPosition = true;
			}
		}
	}

	/**
	 * 
	 */
	private void attack()
	{
		if (!isStopAttack())
		{
			// Il va vouloir faire 1 point de damage
			getDestination().randomRemoveHP();

			// Si le point de damage n'a pas reussi, la reserve bloque et stop l'attaque
			if (!getDestination().isStopAttack())
			{
				this.isDead = --this.stats.damage <= 0 ? true : false;
			}
			else
			{
				this.itsOst.win();
				this.isDead = true;
			}
		}
		else
		{
			this.isDead = true;
		}
	}

	/*************************************************/
	/*************** DELEGATES METHODS ***************/
	/*************************************************/
	
	/**
	 * @return
	 * @see    DukesOfTheRealm.Ost#getSeparationPoint()
	 */
	public Point2D getSeparationPoint()
	{
		return this.itsOst.getSeparationPoint();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.Ost#getWaitingPoint()
	 */
	public Point2D getWaitingPoint()
	{
		return this.itsOst.getWaitingPoint();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.Ost#getSoldiers()
	 */
	public ArrayList<Soldier> getSoldiers()
	{
		return this.itsOst.getSoldiers();
	}

	/**
	 *
	 * @see DukesOfTheRealm.Ost#win()
	 */
	public void win()
	{
		this.itsOst.win();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.Ost#isStopAttack()
	 */
	public boolean isStopAttack()
	{
		return this.itsOst.isStopAttack();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.Ost#getDestination()
	 */
	public Castle getDestination()
	{
		return this.itsOst.getDestination();
	}
	
	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	@Override
	public abstract double getProductionTime();
	
	/**
	 * 
	 */
	@Override
	public abstract int getProductionCost();

	/**
	 * @return the type
	 */
	public final SoldierEnum getType()
	{
		return type;
	}

	/**
	 * @return the onField
	 */
	public final boolean isOnField()
	{
		return this.onField;
	}

	/**
	 * @return the attackLocation
	 */
	public final Point2D getAttackLocation()
	{
		return this.attackLocation;
	}
}
