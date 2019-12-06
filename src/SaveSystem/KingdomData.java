package SaveSystem;

import java.io.Serializable;
import java.util.ArrayList;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Kingdom;

public class KingdomData implements Serializable
{
	private final ArrayList<CastleData> castles;
	private final transient Kingdom kingdom;
	private transient ArrayList<Castle> newCastles;

	public KingdomData(final Kingdom kingdom)
	{
		castles = new ArrayList<>();
		this.kingdom = kingdom;
	}

	public void Save()
	{
		kingdom.GetCastles().forEach(
				castle ->
				{
					final CastleData data = new CastleData(castle);
					data.Save();
					castles.add(data);
				});
	}

	public void Load()
	{
		newCastles = new ArrayList<>();
		castles.forEach(
				castle ->
				{
					final Castle newCastle = castle.Load();
					newCastles.add(newCastle);
				});

		//Main.kingdom.SetCastles(newCastles);
	}
}