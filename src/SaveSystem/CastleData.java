package SaveSystem;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Stack;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Castle.Orientation;
import DukesOfTheRealm.ReserveOfSoldiers;
import Interface.IProductionUnit;
import Utility.Point2D;

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
	public Orientation orientation;
	public Stack<Point2D> attackLocations;

	private final transient Castle castle;

	public CastleData(final Castle castle)
	{
		this.castle = castle;
	}

	public void Save()
	{
		castle.ReceivedDataSave(this);
		if(castle.GetOst() == null)
		{
		}
		else
		{
			final OstData data = new OstData(castle.GetOst(), this);
			data.Save();
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