package Duke;

import java.io.Serializable;
import java.util.ArrayList;

import DukesOfTheRealm.Castle;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public abstract class Actor extends Parent implements Serializable {

	private final ArrayList<Castle> myCastles;
	private final Color myColor;
	static private Castle lastPlayerCastleClicked;
	static private Castle lastOtherCastleClicked;
	private String name = "";

	Actor(final String name, final Color myColor)
	{
		this.myColor = myColor;
		myCastles = new ArrayList<>();
		Actor.lastPlayerCastleClicked = null;
		this.name = name;
	}

	protected abstract void CastleHandle(MouseEvent e);

	EventHandler<MouseEvent> CastleEventHandle = e -> CastleHandle(e);

	public void AddCastle(final Castle castle)
	{
		castle.GetShape().setFill(GetMyColor());
		castle.GetShape().addEventFilter(MouseEvent.MOUSE_PRESSED, CastleEventHandle);
		myCastles.add(castle);
	}

	public boolean RemoveCastle(final Castle castle)
	{
		return myCastles.remove(castle);
	}

	public ArrayList<Castle> GetMyCastles()
	{
		return myCastles;
	}

	public Color GetMyColor()
	{
		return myColor;
	}

	public Castle GetLastPlayerCastleClicked()
	{
		return Actor.lastPlayerCastleClicked;
	}

	public void SetLastPlayerCastleClicked(final Castle castle)
	{
		Actor.lastPlayerCastleClicked = castle;
	}

	public Castle GetLastOtherCastleClicked()
	{
		return Actor.lastOtherCastleClicked;
	}

	public void SetLastOtherCastleClicked(final Castle lastOtherCastleClcked)
	{
		Actor.lastOtherCastleClicked = lastOtherCastleClcked;
	}

	public String GetName()
	{
		return name;
	}
}
