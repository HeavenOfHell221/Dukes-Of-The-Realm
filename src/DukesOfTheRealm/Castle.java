package DukesOfTheRealm;

public class Castle {
	
	int totalFlorin;
	int level;
	
	Castle(int level)
	{
		this.level = level;
		totalFlorin = 0;
	}
	
	public boolean CanLevelUp() 
	{
		if(totalFlorin > Settings.LEVEL_UP_COST_FACTOR * (level + 1))
		{
			return true;
		}
		return false;
	}
	
	public void LevelUp()
	{
		level++;
	}
	
	public void UpdateFlorin()
	{
		AddFlorin(Settings.FLORIN_FACTOR * level);
	}
	
	public void AddFlorin(int amount)
	{
		totalFlorin += amount;
	}
	
	public boolean RemoveFlorin(int amount)
	{
		if(totalFlorin >= amount)
		{
			totalFlorin -= amount;
			return true;
		}
		return false;
	}
}
