package DukesOfTheRealm;

import static Utility.Settings.CASTLE_SHADOW_COLOR;
import static Utility.Settings.CASTLE_SHADOW_OFFSET;
import static Utility.Settings.CASTLE_SHADOW_RADIUS;
import static Utility.Settings.CASTLE_SHADOW_SIZE;
import static Utility.Settings.CASTLE_SIZE;
import static Utility.Settings.CASTLE_STROKE_COLOR;
import static Utility.Settings.CASTLE_STROKE_THICKNESS;
import static Utility.Settings.CASTLE_STROKE_TYPE;
import static Utility.Settings.DOOR_HEIGHT;
import static Utility.Settings.DOOR_POSITION;
import static Utility.Settings.DOOR_WIDTH;
import static Utility.Settings.KNIGHT_REPRESENTATION_SIZE;
import static Utility.Settings.ONAGER_REPRESENTATION_SIZE;
import static Utility.Settings.PIKER_REPRESENTATION_RADIUS;
import static Utility.Settings.SOLDIER_SHADOW_COLOR;
import static Utility.Settings.SOLDIER_SHADOW_OFFSET;
import static Utility.Settings.SOLDIER_SHADOW_RADIUS;
import static Utility.Settings.SOLDIER_SHADOW_SIZE;
import static Utility.Settings.SOLDIER_STROKE_COLOR;
import static Utility.Settings.SOLDIER_STROKE_THICKNESS;
import static Utility.Settings.SOLDIER_STROKE_TYPE;

import java.io.Serializable;

import DukesOfTheRealm.Castle.Orientation;
import Interface.IProductionUnit;
import Utility.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
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
	private final void updateUIShape()
	{
		if (this.shape != null)
		{
			this.shape.relocate(this.coordinate.getX(), this.coordinate.getY());
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 *
	 * @param pane
	 */
	protected final void addCastleRepresentation(final Pane pane)
	{
		final Rectangle r = new Rectangle(getX(), getY(), CASTLE_SIZE, CASTLE_SIZE);
		this.shape = r;
		pane.getChildren().add(this.shape);
		r.setCursor(Cursor.HAND);

		this.width = CASTLE_SIZE;
		this.height = CASTLE_SIZE;

		addShadow(r, CASTLE_SHADOW_SIZE, CASTLE_SHADOW_SIZE, CASTLE_SHADOW_OFFSET, CASTLE_SHADOW_OFFSET, CASTLE_SHADOW_RADIUS,
				CASTLE_SHADOW_COLOR);
		addStroke(r, CASTLE_STROKE_THICKNESS, CASTLE_STROKE_TYPE, CASTLE_STROKE_COLOR);
	}

	/**
	 *
	 * @param pane
	 */
	protected Rectangle addDoorRepresentation(final Pane pane, final Orientation orientation)
	{
		Rectangle door;
		switch (orientation)
		{
			case North:
				door = new Rectangle(getX() + DOOR_POSITION, getY(), DOOR_WIDTH, DOOR_HEIGHT);
				break;
			case South:
				door = new Rectangle(getX() + DOOR_POSITION, getY() + CASTLE_SIZE - DOOR_HEIGHT, DOOR_WIDTH, DOOR_HEIGHT);
				break;
			case East:
				door = new Rectangle(getX() + CASTLE_SIZE - DOOR_HEIGHT, getY() + DOOR_POSITION, DOOR_HEIGHT, DOOR_WIDTH);
				break;
			case West:
				door = new Rectangle(getX(), getY() + DOOR_POSITION, DOOR_HEIGHT, DOOR_WIDTH);
				break;
			default:
				door = new Rectangle(0, 0, 0, 0);
				break;
		}
		door.setMouseTransparent(true);
		pane.getChildren().add(door);
		return door;
	}

	/**
	 *
	 */
	protected final void AddPikerRepresentation()
	{
		final double r = PIKER_REPRESENTATION_RADIUS;
		final Circle c = new Circle(getX(), getY(), r);
		c.setMouseTransparent(true);
		this.shape = c;
		this.width = 2 * r;
		this.height = 2 * r;
		updateUIShape();
		addStroke(c, SOLDIER_STROKE_THICKNESS, SOLDIER_STROKE_TYPE, SOLDIER_STROKE_COLOR);
		addShadow(c, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_RADIUS,
				SOLDIER_SHADOW_COLOR);
	}

	/**
	 *
	 */
	protected final void AddKnightRepresentation()
	{
		final double s = KNIGHT_REPRESENTATION_SIZE;
		Polygon t = new Polygon();
		t.getPoints().addAll(getX() + s / 2, (double) getY(), (double) getX(), getY() + s, getX() + s, getY() + s);

		t.setMouseTransparent(true);
		this.shape = t;
		this.width = s;
		this.height = s;
		updateUIShape();
		addStroke(t, SOLDIER_STROKE_THICKNESS, SOLDIER_STROKE_TYPE, SOLDIER_STROKE_COLOR);
		addShadow(t, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_RADIUS,
				SOLDIER_SHADOW_COLOR);
	}

	/**
	 *
	 */
	protected final void AddOnagerRepresentation()
	{
		final double s = ONAGER_REPRESENTATION_SIZE;
		final Rectangle r = new Rectangle(getX(), getY(), s, s);
		r.setMouseTransparent(true);
		this.shape = r;
		this.width = s;
		this.height = s;
		updateUIShape();
		addStroke(r, SOLDIER_STROKE_THICKNESS, SOLDIER_STROKE_TYPE, SOLDIER_STROKE_COLOR);
		addShadow(r, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_RADIUS,
				SOLDIER_SHADOW_COLOR);
	}

	/**
	 *
	 * @param shape
	 * @param width
	 * @param height
	 * @param offsetX
	 * @param offsetY
	 * @param radius
	 * @param color
	 */
	private final void addShadow(final Shape shape, final int width, final int height, final int offsetX, final int offsetY,
			final int radius, final Color color)
	{
		final DropShadow e = new DropShadow();
		e.setWidth(width);
		e.setHeight(height);
		e.setOffsetX(offsetX);
		e.setOffsetY(offsetY);
		e.setRadius(radius);
		e.setColor(color);
		shape.setEffect(e);
	}

	/**
	 *
	 * @param shape
	 * @param thickness
	 * @param strokeType
	 * @param color
	 */
	private final void addStroke(final Shape shape, final double thickness, final StrokeType strokeType, final Color color)
	{
		shape.setStrokeWidth(thickness);
		shape.setStrokeType(strokeType);
		shape.setStroke(color);
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
		return new Point2D(getX() + (CASTLE_SIZE - 1) / 2 + 1, getY() + (CASTLE_SIZE - 1) / 2 + 1);
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
		updateUIShape();
	}

	/**
	 *
	 * @param y
	 */
	public final void setY(final int y)
	{
		this.coordinate.setY(y);
		updateUIShape();
	}

	/**
	 *
	 * @param dx
	 * @param dy
	 */
	public final void addMotion(final double dx, final double dy)
	{
		this.coordinate.addMotion(dx, dy);
		updateUIShape();
	}

	/**
	 *
	 * @param coordinate
	 */
	public final void setCoordinate(final Point2D coordinate)
	{
		this.coordinate = coordinate;
		updateUIShape();
	}

	public void setColorShape(final Color color)
	{
		this.shape.setFill(color);
	}
}
