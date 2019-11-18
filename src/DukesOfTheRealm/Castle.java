package DukesOfTheRealm;

import Duke.Duke;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Castle extends Sprite {
	
	private int totalFlorin;
	private int level;
	private ReserveOfSoldiers reserveOfSoldiers;
	private Duke duke;
	
	Castle(Pane layer, Image image, double x, double y, int level, Duke duke)
	{
		super(layer, image, x, y);
		this.level = level;
		this.totalFlorin = 0;
		this.reserveOfSoldiers = new ReserveOfSoldiers();
		this.duke = duke;
	}
	

	public void AddRectangle()
	{
		AddRectangle((Settings.CASE_WIDTH * 5) - 2, (Settings.CASE_HEIGHT * 5) - 2);
	}
	
	public boolean CanLevelUp() 
	{
		if(totalFlorin >= Settings.LEVEL_UP_COST_FACTOR * (level + 1))
		{
			return true;
		}
		return false;
	}
	
	public double DistanceWith(Castle castle)
	{
		return 0;
	}
	
	public void LevelUp()
	{
		level++;
		RemoveFlorin(Settings.LEVEL_UP_COST_FACTOR * level);
	}
	
	private void UpdateFlorin()
	{
		AddFlorin(Settings.FLORIN_FACTOR * level);
	}
	
	public void Update()
	{
		UpdateFlorin();
	}
	
	public void AddFlorin(int amount)
	{
		System.out.println(totalFlorin);
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
