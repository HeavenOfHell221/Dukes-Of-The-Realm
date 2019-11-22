package DukesOfTheRealm;

import Duke.Duke;
import Utility.FPS;
import Utility.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Ost extends Sprite {
	
	private Castle origin;
	private Castle destination;
	private double speed;
	private double movement;
	private boolean isArrived;
	private boolean newTurn;
	private boolean canMove = false;;
	private long timeForMove = Settings.GAME_FREQUENCY * 2;
	private long lastTurn = 0;

	
	public Ost(Pane layer, double x, double y, Castle origin, Castle destination, int speed)
	{
		super(layer, x + (Settings.CELL_SIZE-2)/4 +1, y + (Settings.CELL_SIZE-2)/4 +1);
		this.origin = origin;
		this.destination = destination;
		this.speed = speed;
		this.movement = (this.speed * (double) Settings.CELL_SIZE) / (double) this.timeForMove; //Settings.TIME_FACTOR;
		Start();
	}
	
	public void UpdateAtEachFrame(long now)
	{
//		if(newTurn)
//		{
//			lastTurn = now;
//			newTurn = false;
//		}
//		
//		if(now - lastTurn < timeForMove)
//		{
//			Move();
//			UpdateUIShape();
//		}
		
		if (now - lastTurn >= Settings.TURN_DURATION)
		{
			canMove = true;
			lastTurn = now;
		}
		
		if (canMove)
		{
			Move();
			UpdateUIShape();
		}
	}
	
	private void Move()
	{
		int horizontalDirection = destination.GetX() > origin.GetX() ? 1 : -1;
		int verticalDirection = destination.GetY() > origin.GetX() ? 1 : -1;
		boolean toggleXMovement = true;
		boolean toggleYMovement = true;
		
		toggleXMovement = Grid.GetCellWithCoordinates(GetX(), GetY()).getX() 
				== Grid.GetCellWithCoordinates(destination.GetX(), destination.GetY()).getX() ? false : true;
		 
		
		toggleYMovement = Grid.GetCellWithCoordinates(GetX(), GetY()).getY()
				== Grid.GetCellWithCoordinates(destination.GetX(), destination.GetY()).getY() ? false : true;
		
		if(toggleXMovement)
		{
			AddDx(this.movement * FPS.deltaTime * horizontalDirection);
		}
		
		else if(toggleYMovement)
		{
			AddDy(this.movement * FPS.deltaTime * verticalDirection);
		}
	}
	
	private void Start()
	{
		AddCircle(10);
		newTurn = true;
	}
}
