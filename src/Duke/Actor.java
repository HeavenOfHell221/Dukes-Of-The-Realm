package Duke;

import java.util.ArrayList;

import DukesOfTheRealm.Castle;

public abstract class Actor {
	
	ArrayList<Castle> myCastles;
	
	Actor()
	{
		this.myCastles = new ArrayList<Castle>();
	}
	
	public boolean AddCastle(Castle castle)
	{
		return this.myCastles.add(castle);
	}
	
	public boolean RemoveCastle(Castle castle)
	{
		return this.myCastles.remove(castle);
	}
}
