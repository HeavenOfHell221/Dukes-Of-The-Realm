package Duke;

import static Utility.Settings.FLORIN_PER_SECOND;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Main;
import Interface.IUpdate;
import UI.UIManager;
import Utility.Settings;
import Utility.Time;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Actor implements Serializable, IUpdate
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	protected String name = "";
	protected ArrayList<Castle> castles;
	protected transient Color color;
	protected transient Pane pane;
	public ArrayDeque<Castle> castlesWaitForAdding;
	public ArrayDeque<Castle> castlesWaitForDelete;
	public boolean isDead = false;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	Actor()
	{

	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		this.castles = new ArrayList<>();
		this.castlesWaitForAdding = new ArrayDeque<>();
		this.castlesWaitForDelete = new ArrayDeque<>();
	}

	public void startTransient(final Color color, final Pane pane)
	{
		this.color = color;
		if (!Main.isNewGame)
		{
			this.castles.forEach(castle -> castle.setColor(color));
		}
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		Iterator<Castle> it = this.castles.iterator();
		// System.out.println(this.name + " -> " + this.castles.size());
		while (it.hasNext())
		{
			Castle castle = it.next();
			updateFlorin(castle);
			castle.updateProduction();
			castle.updateOst(now, pause);
		}
		addOrRemoveCastleList();
	}

	protected void addOrRemoveCastleList()
	{
		if (this.castlesWaitForAdding.size() > 0)
		{
			// System.out.println("ADD | " + this.name + " | size before : " + this.castles.size() + " | size
			// list AD: " + this.castlesWaitForAdding.size());
			this.castles.addAll(this.castlesWaitForAdding);
			// System.out.println("ADD | " + this.name + " | size after : " + this.castles.size());
			this.castlesWaitForAdding.forEach(c -> addEvent(c));
			this.castlesWaitForAdding.clear();
		}

		if (this.castlesWaitForDelete.size() > 0)
		{
			// System.out.println("DELETE | " + this.name + " | size before : " + this.castles.size() + " | size
			// list DEL: " + this.castlesWaitForDelete.size());
			this.castles.removeAll(this.castlesWaitForDelete);
			// System.out.println("DELETE | " + this.name + " | size after : " + this.castles.size() + "\n");
			this.castlesWaitForDelete.clear();
		}

		if (this.castles.size() <= 0)
		{
			this.isDead = true;
		}
	}

	protected void updateFlorin(final Castle castle)
	{
		castle.addFlorin(Settings.FLORIN_PER_SECOND * castle.getLevel() * Time.deltaTime);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	protected void castleHandle(final MouseEvent e)
	{
		if (e.getButton() == MouseButton.PRIMARY)
		{
			final Rectangle r = (Rectangle) e.getSource();

			getCastles().stream().filter(castle -> castle.getShape() == r).limit(1).forEach(castle ->
			{
				switchCastle(castle);
			});

		}
	}

	public String florinIncome(final Castle castle)
	{
		if (this.castles.contains(castle))
		{
			String tmp = String.format("%.1f", (float) (FLORIN_PER_SECOND * castle.getLevel()));
			return tmp + " Florin/s";
		}
		return " -- Florin/s";
	}

	public void addFirstCastle(final Castle castle)
	{
		this.castles.add(castle);
		addEvent(castle);
	}

	protected void addEvent(final Castle castle)
	{
		castle.getShape().setOnMousePressed(e -> castleHandle(e));
	}

	public void addEventAllCastles()
	{
		this.castles.forEach(castle -> addEvent(castle));
	}

	protected void switchCastle(final Castle castle)
	{
		UIManager.getInstance().switchCastle(castle);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	public void setName(final String name)
	{
		this.name = name;
	}

	public void setColor(final Color color)
	{
		this.color = color;
	}

	public String getName()
	{
		return this.name;
	}

	public Color getColor()
	{
		return this.color;
	}

	public boolean isPlayer()
	{
		return false;
	}

	public String getName(final Castle caslte)
	{
		if (this.castles.contains(caslte))
		{
			return this.name;
		}
		return "--";
	}

	public ArrayList<Castle> getCastles()
	{
		return this.castles;
	}
}
