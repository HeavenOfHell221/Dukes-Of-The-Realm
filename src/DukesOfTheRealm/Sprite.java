package DukesOfTheRealm;

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

public abstract class Sprite extends Parent implements IUpdate, IProductionUnit {

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	private transient Pane canvas;
	private transient Shape shape;

	private Point2D coordinate;
	private double width;
	private double height;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	public Sprite (final Pane canvas, final double x, final double y)
	{
		this.canvas = canvas;
		coordinate = new Point2D(x, y);
	}

	public Sprite (final Pane canvas, final Point2D point2D)
	{
		this.canvas = canvas;
		coordinate = new Point2D(point2D);
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

	public void UpdateUIShape()
	{
		shape.relocate(GetX(), GetY());
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	protected void AddCastleRepresentation(final double size)
	{
		final Rectangle r = new Rectangle(GetX(), GetY(), size, size);
		shape = r;
		r.setCursor(Cursor.HAND);

		final DropShadow e = new DropShadow();
		e.setWidth(5);
		e.setHeight(5);
		e.setOffsetX(2);
		e.setOffsetY(2);
		e.setRadius(10);
		e.setColor(Color.BLACK);
		r.setEffect(e);

		width = size;
		height = size;

		r.setStroke(Color.BLACK);
		r.setStrokeType(StrokeType.OUTSIDE);
		r.setStrokeWidth(1.5);
	}

	protected void AddPikerRepresentation()
	{
		final double r = Settings.PIKER_REPRESENTATION_RADIUS;
		final Circle circle = new Circle(GetX(), GetY(), r);
		shape = circle;
		width = 2 * r;
		height = 2 * r;

		circle.setStroke(Color.BLACK);
		circle.setStrokeType(StrokeType.OUTSIDE);
		circle.setStrokeWidth(1.5);
	}

	protected void AddKnightRepresentation()
	{
		final double s = Settings.KNIGHT_REPRESENTATION_SIZE;
		final Rectangle r = new Rectangle(GetX(), GetY(), s, s);
		shape = r;
		width = s;
		height = s;

		r.setStroke(Color.BLACK);
		r.setStrokeType(StrokeType.OUTSIDE);
		r.setStrokeWidth(1.5);
	}

	protected void AddOnagerRepresentation()
	{
		final double w = Settings.ONAGER_REPRESENTATION_WIDTH;
		final double h = Settings.ONAGER_REPRESENTATION_HEIGHT;
		final Rectangle r = new Rectangle(GetX(), GetY(), w, h);
		shape = r;
		width = w;
		height = h;

		r.setStroke(Color.BLACK);
		r.setStrokeType(StrokeType.OUTSIDE);
		r.setStrokeWidth(1.5);
	}

	public void RemoveShapeToLayer()
	{
		canvas.getChildren().remove(shape);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	public Shape GetShape()
	{
		return shape;
	}

	public int GetX()
	{
		return (int) coordinate.GetX();
	}
	public int GetY()
	{
		return (int) coordinate.GetY();
	}

	public Point2D GetCoordinate()
	{
		return coordinate;
	}

	public Point2D GetCastleCenter()
	{
		return new Point2D(GetX() + (((Settings.CASTLE_SIZE - 1) / 2) + 1), GetY() + (((Settings.CASTLE_SIZE - 1) / 2) + 1));
	}

	public double getWidth()
	{
		return width;
	}
	public double getHeight()
	{
		return height;
	}

	public Pane GetLayer()
	{
		return canvas;
	}

	public void SetX(final int x)
	{
		coordinate.SetX(x);
	}
	public void SetY(final int y)
	{
		coordinate.SetY(y);
	}

	public void AddDx(final double dx)
	{
		coordinate.AddDx(dx);
	}

	public void AddDy(final double dy)
	{
		coordinate.AddDy(dy);
	}

	public void AddMotion(final double dx, final double dy)
	{
		coordinate.AddMotion(dx, dy);
	}

	public void SetCoordinate(final Point2D coordinate)
	{
		this.coordinate = coordinate;
	}
}
