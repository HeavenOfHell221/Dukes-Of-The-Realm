package Duke;

import java.util.Random;

import DukesOfTheRealm.Castle;

public class Baron extends Actor {

	public Baron()
	{

	}

	@Override
	public boolean AddCastle(Castle castle) {
		Random rand = new Random();
		castle.AddFlorin(rand.nextInt(801) + 200);
		return super.AddCastle(castle);
	}
	
	
}
