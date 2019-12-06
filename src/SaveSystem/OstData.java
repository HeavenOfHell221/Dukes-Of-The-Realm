package SaveSystem;

import java.io.Serializable;
import java.util.ArrayList;

import DukesOfTheRealm.Ost;
import Utility.Point2D;

public class OstData implements Serializable
{
	public Point2D separationPoint;
	public int nbPikers;
	public int nbKnights;
	public int nbOnagers;
	public int speed;
	private ArrayList<SoldierData> soldiers;
	public int nbSoldiersSpawned;
	public boolean fullyDeployed;

	private transient final Ost ost;
	private transient final CastleData castledata;

	public OstData(final Ost ost, final CastleData castledata)
	{
		this.ost = ost;
		this.castledata = castledata;
	}

	public void Save()
	{
		soldiers = new ArrayList<>();
		ost.GetSoldiers().forEach(
				soldier ->
				{
					final SoldierData data = new SoldierData(soldier, this);
					data.Save();
					soldiers.add(data);
				});

		ost.receivedDataSave(this);
	}

	public Ost Load()
	{
		return null;
	}
}
