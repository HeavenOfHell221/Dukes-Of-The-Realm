package SaveSystem;

import java.io.Serializable;
import java.util.ArrayList;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Kingdom;

public class KingdomData implements Serializable
{
	private ArrayList<CastleData> castles;
	private final transient Kingdom kingdom;
	private transient ArrayList<Castle> newCastles;
		
	public KingdomData(final Kingdom kingdom)
	{
		this.castles = new ArrayList<>();
		this.kingdom = kingdom;
	}
	
	public void Save()
	{
		kingdom.GetCastles().forEach(
			castle ->
			{
				CastleData data = new CastleData(castle);
				data.Save();
				castles.add(data);
			});
	}
	
	public void Load()
	{
		this.newCastles = new ArrayList<>();
		castles.forEach( 
			castle ->
			{
				Castle newCastle = castle.Load();
				this.newCastles.add(newCastle);
			});
		
		//Main.kingdom.SetCastles(newCastles);
	}
}