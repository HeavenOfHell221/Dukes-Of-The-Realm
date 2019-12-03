package SaveSystem;

import java.io.Serializable;

import Soldiers.Soldier;
import Soldiers.SoldierEnum;
import Utility.Point2D;

public class SoldierData implements Serializable {

	public Point2D coordinate;
    public double width;
    public double height;
	public SoldierEnum type;
	public int health;
	public int damage;
	public int speed;
	private OstData itsOst;
	public boolean onField;
	public boolean canMove;
	public boolean isArrived;
	public boolean isInPosition;
	public boolean isDead;
	public Point2D attackLocation;
	public boolean isWaitingForAttackLocation;
	
	private transient final Soldier soldier;
	private transient final OstData ost;
	
	public SoldierData(final Soldier soldier, final OstData ost)
	{
		this.soldier = soldier;
		this.ost = ost;
	}
	
	public void Save()
	{
		soldier.ReceivedDataSave(this);
		itsOst = ost;
	}
	
	public void Load()
	{
		
	}
}
