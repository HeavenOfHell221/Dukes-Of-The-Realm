package DukesOfTheRealm;

import java.io.Serializable;

import Interface.IProductionUnit;
import Interface.IUpdate;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public abstract class Sprite extends Parent implements IProductionUnit, Serializable
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	protected transient Pane canvas;
	protected transient Shape shape;

	protected Point2D coordinate;
	protected double width;
	protected double height;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	public Sprite(final Pane canvas, final double x, final double y)
	{
		this.canvas = canvas;
		this.coordinate = new Point2D(x, y);
	}

	public Sprite(final Pane canvas, final Point2D point2D)
	{
		this.canvas = canvas;
		this.coordinate = new Point2D(point2D);
	}

	protected Sprite()
	{

	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	public void updateUIShape()
	{
		if(this.shape != null)
			this.shape.relocate(getX(), getY());
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	protected void addCastleRepresentation(Pane pane, final double size)
	{
		final Rectangle r = new Rectangle(getX(), getY(), size, size);
		this.shape = r;
		pane.getChildren().add(this.shape);
		r.setCursor(Cursor.HAND);

		final DropShadow e = new DropShadow();
		e.setWidth(5);
		e.setHeight(5);
		e.setOffsetX(2);
		e.setOffsetY(2);
		e.setRadius(10);
		e.setColor(Color.BLACK);
		r.setEffect(e);

		this.width = size;
		this.height = size;

		r.setStroke(Color.BLACK);
		r.setStrokeType(StrokeType.OUTSIDE);
		r.setStrokeWidth(1.5);
	}

	protected void AddPikerRepresentation()
	{
		final double r = Settings.PIKER_REPRESENTATION_RADIUS;
		final Circle circle = new Circle(getX(), getY(), r);
		this.shape = circle;
		this.width = 2 * r;
		this.height = 2 * r;

		circle.setStroke(Color.BLACK);
		circle.setStrokeType(StrokeType.OUTSIDE);
		circle.setStrokeWidth(1.5);
	}

	protected void AddKnightRepresentation()
	{
		final double s = Settings.KNIGHT_REPRESENTATION_SIZE;
		final Rectangle r = new Rectangle(getX(), getY(), s, s);
		this.shape = r;
		this.width = s;
		this.height = s;

		r.setStroke(Color.BLACK);
		r.setStrokeType(StrokeType.OUTSIDE);
		r.setStrokeWidth(1.5);
	}

	protected void AddOnagerRepresentation()
	{
		final double w = Settings.ONAGER_REPRESENTATION_WIDTH;
		final double h = Settings.ONAGER_REPRESENTATION_HEIGHT;
		final Rectangle r = new Rectangle(getX(), getY(), w, h);
		this.shape = r;
		this.width = w;
		this.height = h;

		r.setStroke(Color.BLACK);
		r.setStrokeType(StrokeType.OUTSIDE);
		r.setStrokeWidth(1.5);
	}

	public void RemoveShapeToLayer()
	{
		if(this.shape != null)
			this.canvas.getChildren().remove(this.shape);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	public Shape getShape()
	{
		return this.shape;
	}

	public int getX()
	{
		return (int) this.coordinate.getX();
	}

	public int getY()
	{
		return (int) this.coordinate.getY();
	}

	public Point2D getCoordinate()
	{
		return this.coordinate;
	}

	public Point2D getCastleCenter()
	{
		return new Point2D(getX() + (((Settings.CASTLE_SIZE - 1) / 2) + 1), getY() + (((Settings.CASTLE_SIZE - 1) / 2) + 1));
	}

	public double getWidth()
	{
		return this.width;
	}

	public double getHeight()
	{
		return this.height;
	}

	public Pane getLayer()
	{
		return this.canvas;
	}

	public void setX(final int x)
	{
		this.coordinate.setX(x);
	}

	public void setY(final int y)
	{
		this.coordinate.setY(y);
	}

	public void addDx(final double dx)
	{
		this.coordinate.addDx(dx);
	}

	public void addDy(final double dy)
	{
		this.coordinate.addDy(dy);
	}

	public void addMotion(final double dx, final double dy)
	{
		this.coordinate.addMotion(dx, dy);
	}

	public void setCoordinate(final Point2D coordinate)
	{
		this.coordinate = coordinate;
	}
}
