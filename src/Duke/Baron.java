package Duke;

import java.io.Serializable;
import java.util.Random;

import DukesOfTheRealm.Castle;

public class Baron extends Actor implements Serializable
{
	public Baron(final String name)
	{
		super(name);
	}

	public Baron()
	{
		super();
	}

	@Override
	public void addCastle(final Castle castle)
	{
		final Random rand = new Random();
		castle.AddFlorin(rand.nextInt(801) + 200);
		super.addCastle(castle);
	}
}
