package SaveSystem;

import java.io.Serializable;
import java.util.ArrayDeque;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Main;
import DukesOfTheRealm.Ost;
import DukesOfTheRealm.ReserveOfSoldiers;
import Utility.Point2D;
import DukesOfTheRealm.Castle.Orientation;
import Interface.IProductionUnit;

public class CastleData implements Serializable {	
	
	public Point2D coordinate;
    public double width;
    public double height;
	public double totalFlorin; 						
	public int level; 									
	public ReserveOfSoldiers reserveOfSoldiers; 		
	//public Actor actor; 								
	public ArrayDeque<IProductionUnit> productionUnit; 
	public int productionTime; 						
	private OstData ost;
	public Orientation orientation;
	public Point2D[] attackLocations;					
	public Point2D waitAttackLocation;
	public boolean occupiedAttackLocationsTab[];
	public int occupiedAttackLocations;
	
	private final transient Castle castle;
	
	public CastleData(final Castle castle)
	{
		this.castle = castle;
	}
	
	public void Save()
	{
		this.castle.ReceivedDataSave(this);
		if(castle.GetOst() == null)
		{
			this.ost = null;
		}
		else
		{
			OstData data = new OstData(castle.GetOst(), this);
			data.Save();
			this.ost = data;
		}
	}
	
	public Castle Load()
	{
		
		Castle newCastle = new Castle(Main.playfieldLayer, coordinate.GetX(), coordinate.GetY());
		Ost newOst = this.ost.Load();
		newCastle.SetOst(newOst);
		return newCastle;
	}
}