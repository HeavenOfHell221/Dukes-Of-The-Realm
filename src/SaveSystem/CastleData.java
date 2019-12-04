package SaveSystem;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Stack;

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
	public Stack<Point2D> attackLocations;
	
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
		return null;
		/*Castle newCastle = new Castle(Main.playfieldLayer, coordinate.GetX(), coordinate.GetY());
		Ost newOst = this.ost.Load();
		newCastle.SetOst(newOst);
		return newCastle;*/
	}
}