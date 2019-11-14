package DukesOfTheRealm;

public class Castle {

	final int factorCostLevelUp = 1000;
	
	Treasure treasure;
	int level;
	
	Castle(int level)
	{
		this.level = level;
		treasure = new Treasure(level);
	}
	
	public void UpdateTotalFlorin()
	{
		treasure.UpdateTotalFlorin();
	}
	
	public boolean canLevelUp() 
	{
		if(treasure.getFlorin() > factorCostLevelUp * (level + 1))
		{
			return true;
		}
		return false;
	}
	
	public void levelUp()
	{
		level++;
		treasure.UpdateFlorinPerTurn(level);
	}
	
	public void addFlorin(int amount)
	{
		treasure.addFlorin(amount);
	}
	
	public boolean removeFlorin(int amount)
	{
		return treasure.removeFlorin(amount);
	}
}
