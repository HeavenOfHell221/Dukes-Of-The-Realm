package Duke;

import java.util.ArrayList;

import DukesOfTheRealm.Castle;
import javafx.scene.paint.Color;

public abstract class Actor {
	
	ArrayList<Castle> myCastles;
	Color myColor;
	
	Actor(Color myColor)
	{
		this.myColor = myColor;
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
