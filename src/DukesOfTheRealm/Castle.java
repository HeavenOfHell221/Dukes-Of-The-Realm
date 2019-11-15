package DukesOfTheRealm;

public class Castle {
	
	private int totalFlorin;
	private int level;
	private ReserveOfSoldiers reserveOfSoldiers;
	private Duke duke;
	
	Castle(int level, Duke duke)
	{
		this.level = level;
		this.totalFlorin = 0;
		this.reserveOfSoldiers = new ReserveOfSoldiers();
		this.duke = duke;
	}
	
	public boolean CanLevelUp() 
	{
		if(totalFlorin >= Settings.LEVEL_UP_COST_FACTOR * (level + 1))
		{
			return true;
		}
		return false;
	}
	
	public void LevelUp()
	{
		level++;
		RemoveFlorin(Settings.LEVEL_UP_COST_FACTOR * level);
	}
	
	public void UpdateFlorin()
	{
		AddFlorin(Settings.FLORIN_FACTOR * level);
	}
	
	public void AddFlorin(int amount)
	{
		totalFlorin += amount;
	}
	
	public void RemoveFlorin(int amount)
	{
		if(EnoughOfFlorin(amount))
		{
			totalFlorin -= amount;
		}
	}
	
	public boolean EnoughOfFlorin(int amount)
	{
		return (amount <= totalFlorin);
	}

	public int GetTotalFlorin() {
		return totalFlorin;
	}

	public ReserveOfSoldiers GetReserveOfSoldiers() {
		return reserveOfSoldiers;
	}

	public Duke GetDuke() {
		return duke;
	}

	public void SetDuke(Duke duke) {
		this.duke = duke;
	}
	
	
}
