package DukesOfTheRealm;

import java.io.Serializable;

import Interface.IProductionUnit;
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

/**
 * 
 *
 */
public abstract class Sprite extends Parent implements IProductionUnit, Serializable
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * 
	 */
	protected transient Pane canvas;
	
	/**
	 * 
	 */
	protected transient Shape shape;

	/**
	 * 
	 */
	protected Point2D coordinate;
	
	/**
	 * 
	 */
	protected double width;
	
	/**
	 * 
	 */
	protected double height;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * 
	 * @param canvas
	 * @param point2D
	 */
	public Sprite(final Pane canvas, final Point2D point2D)
	{
		this.canvas = canvas;
		this.coordinate = new Point2D(point2D);
	}

	/**
	 * 
	 */
	protected Sprite()
	{

	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	/**
	 * 
	 */
	public final void updateUIShape()
	{
		if (this.shape != null)
		{
			this.shape.relocate(getX(), getY());
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * 
	 * @param pane
	 * @param size
	 */
	protected final void addCastleRepresentation(final Pane pane, final double size)
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

	/**
	 * 
	 */
	protected final void AddPikerRepresentation()
	{
		final double r = Settings.PIKER_REPRESENTATION_RADIUS;
		final Circle circle = new Circle(getX(), getY(), r);
		circle.setMouseTransparent(true);
		this.shape = circle;
		this.width = 2 * r;
		this.height = 2 * r;

		circle.setStroke(Color.BLACK);
		circle.setStrokeType(StrokeType.OUTSIDE);
		circle.setStrokeWidth(1.5);
	}

	/**
	 * 
	 */
	protected final void AddKnightRepresentation()
	{
		final double s = Settings.KNIGHT_REPRESENTATION_SIZE;
		final Rectangle r = new Rectangle(getX(), getY(), s, s);
		r.setMouseTransparent(true);
		this.shape = r;
		this.width = s;
		this.height = s;

		r.setStroke(Color.BLACK);
		r.setStrokeType(StrokeType.OUTSIDE);
		r.setStrokeWidth(1.5);
	}

	/**
	 * 
	 */
	protected final void AddOnagerRepresentation()
	{
		final double w = Settings.ONAGER_REPRESENTATION_WIDTH;
		final double h = Settings.ONAGER_REPRESENTATION_HEIGHT;
		final Rectangle r = new Rectangle(getX(), getY(), w, h);
		r.setMouseTransparent(true);
		this.shape = r;
		this.width = w;
		this.height = h;

		r.setStroke(Color.BLACK);
		r.setStrokeType(StrokeType.OUTSIDE);
		r.setStrokeWidth(1.5);
	}

	/**
	 * 
	 */
	public final void RemoveShapeToLayer()
	{
		if (this.shape != null)
		{
			this.canvas.getChildren().remove(this.shape);
		}
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * 
	 * @return
	 */
	public final Shape getShape()
	{
		return this.shape;
	}

	/**
	 * 
	 * @return
	 */
	public final int getX()
	{
		return (int) this.coordinate.getX();
	}

	/**
	 * 
	 * @return
	 */
	public final int getY()
	{
		return (int) this.coordinate.getY();
	}

	/**
	 * 
	 * @return
	 */
	public final Point2D getCoordinate()
	{
		return this.coordinate;
	}

	/**
	 * 
	 * @return
	 */
	public final Point2D getCastleCenter()
	{
		return new Point2D(getX() + (((Settings.CASTLE_SIZE - 1) / 2) + 1), getY() + (((Settings.CASTLE_SIZE - 1) / 2) + 1));
	}

	/**
	 * 
	 * @return
	 */
	public final double getWidth()
	{
		return this.width;
	}

	/**
	 * 
	 * @return
	 */
	public final double getHeight()
	{
		return this.height;
	}

	/**
	 * 
	 * @return
	 */
	public final Pane getLayer()
	{
		return this.canvas;
	}

	/**
	 * 
	 * @param x
	 */
	public final void setX(final int x)
	{
		this.coordinate.setX(x);
	}

	/**
	 * 
	 * @param y
	 */
	public final void setY(final int y)
	{
		this.coordinate.setY(y);
	}

	/**
	 * 
	 * @param dx
	 */
	public final void addDx(final double dx)
	{
		this.coordinate.addDx(dx);
	}
	
	/**
	 * 
	 * @param dy
	 */
	public final void addDy(final double dy)
	{
		this.coordinate.addDy(dy);
	}

	/**
	 * 
	 * @param dx
	 * @param dy
	 */
	public final void addMotion(final double dx, final double dy)
	{
		this.coordinate.addMotion(dx, dy);
	}

	/**
	 * 
	 * @param coordinate
	 */
	public final void setCoordinate(final Point2D coordinate)
	{
		this.coordinate = coordinate;
	}
	
	public void setColorShape(Color color)
	{
		this.shape.setFill(color);
	}
}
