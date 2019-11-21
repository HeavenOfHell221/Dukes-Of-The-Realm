package DukesOfTheRealm;

import Duke.Duke;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Ost extends Sprite {
	
	private Castle origin;
	private Castle destination;
	private int speed;
	private double movement;
	private boolean isArrived;
	private boolean canStart;
	
	public Ost(Pane layer, Image image, double x, double y, Castle origin, Castle destination, int speed)
	{
		super(layer, image, x + (Settings.CELL_SIZE-2)/4 +1, y + (Settings.CELL_SIZE-2)/4 +1);
//		super(layer, image, x, y);
		this.origin = origin;
		this.destination = destination;
		this.speed = 10;
		this.movement = (speed * Settings.CELL_SIZE) / Settings.TIME_FACTOR;
	}
	
	public void UpdateAtEachFrame()
	{	
		if(canStart && !isArrived)
		{
			Move();
			UpdateUIShape();
		}
			
	}
	
	private void Move()
	{
		int horizontalDirection = destination.getX() > origin.getX() ? 1 : -1;
		int verticalDirection = destination.getY() > origin.getX() ? 1 : -1;
		boolean toggleXMovement = true;
		boolean toggleYMovement = true;
		while(toggleXMovement)
		{
			this.x += this.movement * FPS.deltaTime * horizontalDirection;
			double x = Kingdom.grid.GetCellWithCoordinates((int) this.x, (int) this.y).getX();
			double xx = Kingdom.grid.GetCellWithCoordinates((int) destination.getX(), (int) destination.getY()).getX();
			if (x == xx)
			{
				toggleXMovement = false;
			}
		}
		while(toggleYMovement)
		{
			this.y += this.movement * FPS.deltaTime * verticalDirection;
			double y = Kingdom.grid.GetCellWithCoordinates((int) this.x, (int) this.y).getY();
			double yy = Kingdom.grid.GetCellWithCoordinates((int) destination.getX(), (int) destination.getY()).getY();
			if(y == yy)
			{
				toggleYMovement = false;
			}
		}
	}
	private void Start()
	{
		AddCircle(10);
		canStart = true;
	}
}
