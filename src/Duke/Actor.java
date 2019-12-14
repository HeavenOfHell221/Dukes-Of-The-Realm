package Duke;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Main;
import Goal.AtomicGoal;
import Goal.GeneratorAtomicGoal;
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
	public ArrayList<Castle> castlesWaitForAdding;
	
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
		this.castlesWaitForAdding = new ArrayList<>();
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

		while (it.hasNext())
		{
			Castle castle = it.next();
			updateFlorin(castle);
			castle.updateProduction();
			castle.updateUIShape();
			castle.updateOst(now, pause);
		}

		if (this.castlesWaitForAdding.size() > 0)
		{
			this.castles.addAll(this.castlesWaitForAdding);
			this.castlesWaitForAdding.forEach(c -> addEvent(c));
			this.castlesWaitForAdding.clear();
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
			return Settings.FLORIN_PER_SECOND * castle.getLevel() + " Florin/s";
		}
		return " -- Florin/s";
	}

	public void addFirstCastle(final Castle castle)
	{
		this.castles.add(castle);
		addEvent(castle);
	}

	private void addEvent(final Castle castle)
	{
		castle.getShape().setOnMousePressed(e -> castleHandle(e));
	}

	public void addEventAllCastles()
	{
		this.castles.forEach(castle -> addEvent(castle));
	}

	protected void switchCastle(final Castle castle)
	{
		UIManager.getInstance().switchCastle(castle, this);
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
