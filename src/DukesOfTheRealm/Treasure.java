package DukesOfTheRealm;

public class Treasure {

	private int totalFlorin;
	private int florinPerTurn;
	final int florinFactor = 10;
	
	Treasure(int level)
	{
		totalFlorin = 0;
		florinPerTurn = level * florinFactor;
	}
	
	public int getFlorin()
	{
		return totalFlorin;
	}
	
	public void UpdateTotalFlorin()
	{
		totalFlorin += florinPerTurn;
	}
	
	public void UpdateFlorinPerTurn(int level)
	{
		florinPerTurn = level * florinFactor;
	}
	
	public void addFlorin(int amount)
	{
		totalFlorin += amount;
	}
	
	public boolean removeFlorin(int amount)
	{
		if(amount > totalFlorin)
		{
			return false;
		}
		
		totalFlorin -= amount;
		return true;
	}
}
