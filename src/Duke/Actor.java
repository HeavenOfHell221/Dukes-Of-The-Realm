package Duke;

import DukesOfTheRealm.Castle;

public abstract class Actor {

	Castle mainCastle;
	
	Actor(Castle mainCastle)
	{
		this.mainCastle = mainCastle;
	}
}
