package Duke;

import java.util.ArrayList;

import DukesOfTheRealm.Castle;
import UI.UIManager;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Actor
{
	private ArrayList<Castle> myCastles;
	private transient Color myColor;
	private String name = "";

	Actor()
	{

	}

	Actor(final String name)
	{
		this.myCastles = new ArrayList<>();
		this.name = name;
	}

	protected void castleHandle(final MouseEvent e)
	{
		if (e.getButton() == MouseButton.PRIMARY) // Clique gauche
		{
			final Rectangle rectangle = (Rectangle) e.getSource();

			getMyCastles().stream().filter(castle -> castle.getShape() == rectangle).limit(1).forEach(castle ->
			{
				UIManager.GetInstance().switchCastle(castle);
			});
		}
	}

	EventHandler<MouseEvent> CastleEventHandle = e -> castleHandle(e);

	public void addCastle(final Castle castle)
	{
		castle.getShape().setFill(this.myColor);
		castle.getShape().addEventFilter(MouseEvent.MOUSE_PRESSED, this.CastleEventHandle);
		this.myCastles.add(castle);
	}

	public boolean removeCastle(final Castle castle)
	{
		return this.myCastles.remove(castle);
	}

	public ArrayList<Castle> getMyCastles()
	{
		return this.myCastles;
	}

	public Color getMyColor()
	{
		return this.myColor;
	}

	public String getName()
	{
		return this.name;
	}

	public void setColor(final Color color)
	{
		this.myColor = color;
	}
}
