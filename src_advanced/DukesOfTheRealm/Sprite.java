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
import static Utility.Settings.ARCHER_REPRESENTATION_SIZE;
import static Utility.Settings.ARCHER_REPRESENTATION_HEIGHT_POSITION;
import static Utility.Settings.BERSERKER_REPRESENTATION_SIZE;
import static Utility.Settings.BERSERKER_REPRESENTATION_THICKNESS;
import static Utility.Settings.SPY_REPRESENTATION_OUTSIDE_RADIUS;
import static Utility.Settings.SPY_REPRESENTATION_INSIDE_RADIUS;
import static Utility.Settings.CONVEYOR_REPRESENTATION_RADIUS;
import static Utility.Settings.SOLDIER_SHADOW_COLOR;
import static Utility.Settings.SOLDIER_SHADOW_OFFSET;
import static Utility.Settings.SOLDIER_SHADOW_RADIUS;
import static Utility.Settings.SOLDIER_SHADOW_SIZE;
import static Utility.Settings.SOLDIER_STROKE_COLOR;
import static Utility.Settings.SOLDIER_STROKE_THICKNESS;
import static Utility.Settings.SOLDIER_STROKE_TYPE;

import java.io.Serializable;

import DukesOfTheRealm.Castle.Orientation;
import Utility.Point2D;
import Utility.Settings;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

/**
 * Repr�sente un �l�ment graphique � l'�cran
 */
public abstract class Sprite extends Parent implements Serializable
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Le Pane sur lequel afficher le sprite.
	 */
	protected transient Pane canvas;

	/**
	 * La forme associ�e � la repr�sentation graphique de l'objet.
	 */
	protected transient Shape shape;

	/**
	 * Les coordonn�es de l'objet repr�sent�.
	 */
	protected Point2D coordinate;

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	/**
	 * Met � jour la position du sprite � l'�cran.
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
	 * Ajoute la repr�sentation graphique d'un ch�teau � l'�cran.
	 *
	 * @param pane Le Pane sur lequel afficher le sprite.
	 */
	protected final void addCastleRepresentation(final Pane pane)
	{
		final Rectangle r = new Rectangle(getX(), getY(), CASTLE_SIZE, CASTLE_SIZE);
		this.shape = r;
		pane.getChildren().add(this.shape);
		r.setCursor(Cursor.HAND);

		addShadow(r, CASTLE_SHADOW_SIZE, CASTLE_SHADOW_SIZE, CASTLE_SHADOW_OFFSET, CASTLE_SHADOW_OFFSET, CASTLE_SHADOW_RADIUS,
				CASTLE_SHADOW_COLOR);
		addStroke(r, CASTLE_STROKE_THICKNESS, CASTLE_STROKE_TYPE, CASTLE_STROKE_COLOR);
	}

	/**
	 * Cr�e et ajoute la repr�sentation graphique de la porte d'un ch�teau � l'�cran.
	 *
	 * @param  pane        Le Pane sur lequel afficher le sprite.
	 * @param  orientation L'orientation de la porte
	 * @return             la porte cr��e.
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
	 * Cr�e et ajoute � l'�cran une figure de cercle pour repr�senter graphiquement un Piquier.
	 */
	protected final void AddPikerRepresentation()
	{
		final double r = PIKER_REPRESENTATION_RADIUS;
		final Circle c = new Circle(getX(), getY(), r);
		c.setMouseTransparent(true);
		this.shape = c;
		updateUIShape();
		addStroke(c, SOLDIER_STROKE_THICKNESS, SOLDIER_STROKE_TYPE, SOLDIER_STROKE_COLOR);
		addShadow(c, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_RADIUS,
				SOLDIER_SHADOW_COLOR);
	}

	/**
	 * Cr�e et ajoute � l'�cran une figure de triangle pour repr�senter graphiquement un Chevalier.
	 */
	protected final void AddKnightRepresentation()
	{
		final double s = KNIGHT_REPRESENTATION_SIZE;
		Polygon t = new Polygon();
		t.getPoints().addAll(getX() + s / 2, (double) getY(), (double) getX(), getY() + s, getX() + s, getY() + s);

		t.setMouseTransparent(true);
		this.shape = t;
		updateUIShape();
		addStroke(t, SOLDIER_STROKE_THICKNESS, SOLDIER_STROKE_TYPE, SOLDIER_STROKE_COLOR);
		addShadow(t, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_RADIUS,
				SOLDIER_SHADOW_COLOR);
	}

	/**
	 * Cr�e et ajoute � l'�cran une figure de carr� pour repr�senter graphiquement une Catapulte.
	 */
	protected final void AddOnagerRepresentation()
	{
		final double s = ONAGER_REPRESENTATION_SIZE;
		final Rectangle r = new Rectangle(getX(), getY(), s, s);
		r.setMouseTransparent(true);
		this.shape = r;
		updateUIShape();
		addStroke(r, SOLDIER_STROKE_THICKNESS, SOLDIER_STROKE_TYPE, SOLDIER_STROKE_COLOR);
		addShadow(r, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_RADIUS,
				SOLDIER_SHADOW_COLOR);
	}
	
	/**
	 * Cr�e et ajoute � l'�cran une figure de cerf-volant pour repr�senter graphiquement un Archer.
	 */
	protected final void AddArcherRepresentation()
	{
		final double s = ARCHER_REPRESENTATION_SIZE;
		final double hp = ARCHER_REPRESENTATION_HEIGHT_POSITION;
		Path k = new Path(new MoveTo(getX() + s/2, getY()),
    			new LineTo(getX(), getY() + hp),
    			new LineTo(getX() + s/2, getY() + s),
    			new LineTo(getX() + s, getY() + hp),
    			new ClosePath()
    			);
		k.setMouseTransparent(true);
		this.shape = k;
		updateUIShape();
		addStroke(k, SOLDIER_STROKE_THICKNESS, SOLDIER_STROKE_TYPE, SOLDIER_STROKE_COLOR);
		addShadow(k, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_RADIUS,
				SOLDIER_SHADOW_COLOR);
	}
	
	/**
	 * Cr�e et ajoute � l'�cran une figure de croix pour repr�senter graphiquement un Berserker.
	 */
	protected final void AddBerserkerRepresentation()
	{
		final double s = BERSERKER_REPRESENTATION_SIZE;
		final double t = BERSERKER_REPRESENTATION_THICKNESS;
		Rectangle r1 = new Rectangle(getX() + t, getY(), t, s);
    	Rectangle r2 = new Rectangle(getX(), getY() + t, s, t);
    	Shape p = Shape.union(r1, r2);
		p.setMouseTransparent(true);
		this.shape = p;
		updateUIShape();
		addStroke(p, SOLDIER_STROKE_THICKNESS, SOLDIER_STROKE_TYPE, SOLDIER_STROKE_COLOR);
		addShadow(p, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_RADIUS,
				SOLDIER_SHADOW_COLOR);
	}
	
	/**
	 * Cr�e et ajoute � l'�cran une figure de lune pour repr�senter graphiquement un Espion.
	 */
	protected final void AddSpyRepresentation()
	{
		final double r = CONVEYOR_REPRESENTATION_RADIUS;
    	Circle c1 = new Circle(getX(), getY(), r);
    	Circle c2 = new Circle(getX() + r, getY(), r);
    	Shape m = Shape.subtract(c1, c2);
    	m.setMouseTransparent(true);
		this.shape = m;
		updateUIShape();
		addStroke(m, SOLDIER_STROKE_THICKNESS, SOLDIER_STROKE_TYPE, SOLDIER_STROKE_COLOR);
		addShadow(m, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_RADIUS,
				SOLDIER_SHADOW_COLOR);
	}
	
	/**
	 * Cr�e et ajoute � l'�cran une figure d'anneau pour repr�senter graphiquement un Convoyeur de fonds.
	 */
	protected final void AddConveyorRepresentation()
	{
		final double r_out = SPY_REPRESENTATION_OUTSIDE_RADIUS;
		final double r_in = SPY_REPRESENTATION_INSIDE_RADIUS;
		Circle c1 = new Circle(getX(), getY(), r_out);
    	Circle c2 = new Circle(getX(), getY(), r_in);
    	Shape r = Shape.subtract(c1, c2);
    	r.setMouseTransparent(true);
		this.shape = r;
		updateUIShape();
		addStroke(r, SOLDIER_STROKE_THICKNESS, SOLDIER_STROKE_TYPE, SOLDIER_STROKE_COLOR);
		addShadow(r, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_SIZE, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_OFFSET, SOLDIER_SHADOW_RADIUS,
				SOLDIER_SHADOW_COLOR);
	}

	/**
	 * Ajoute une ombre � la forme repr�sentant un objet � l'�cran.
	 *
	 * @param shape   La forme sur laquelle ajouter l'ombre.
	 * @param width   La largeur de l'ombre.
	 * @param height  La hauteur de l'ombre.
	 * @param offsetX Le d�calage de l'ombre selon l'axe des abscisses.
	 * @param offsetY Le d�calage de l'ombre selon l'axe des ordoon�es.
	 * @param radius  Le rayon associ� aux coins de l'ombres
	 * @param color   La couleur de l'ombre.
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
	 * Ajoute un contour � la forme repr�sentant un objet � l'�cran.
	 *
	 * @param shape      La forme sur laquelle ajouter le contour.
	 * @param thickness  L'�paisseur du contour.
	 * @param strokeType Le type de contour, int�rieur, ext�rieur ou � cheval.
	 * @param color      La couleur du contour.
	 */
	private final void addStroke(final Shape shape, final double thickness, final StrokeType strokeType, final Color color)
	{
		shape.setStrokeWidth(thickness);
		shape.setStrokeType(strokeType);
		shape.setStroke(color);
	}

	/**
	 * Retire de l'�cran la repr�sentation d'un objet.
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
	 * R�cup�re la forme associ�e � l'objet.
	 *
	 * @return La forme.
	 */
	public final Shape getShape()
	{
		return this.shape;
	}

	/**
	 * R�cup�re la coordonn�e en abscisse de l'objet.
	 *
	 * @return La coordonn�e en abscisse
	 */
	public final int getX()
	{
		return (int) this.coordinate.getX();
	}

	/**
	 * R�cup�re la coordonn�e en ordonn�e de l'objet.
	 *
	 * @return La coordonn�e en ordonn�e.
	 */
	public final int getY()
	{
		return (int) this.coordinate.getY();
	}

	/**
	 * R�cup�re les coordonn�es de l'objet.
	 *
	 * @return Les coordonn�es de l'objet.
	 */
	public final Point2D getCoordinate()
	{
		return this.coordinate;
	}

	/**
	 * R�cup�re le Pane sur lequel l'objet est affich�.
	 *
	 * @return Le Pane.
	 */
	public final Pane getLayer()
	{
		return this.canvas;
	}

	/**
	 * Ajoute une valeur donn�e aux coordonn�es de l'objet.
	 *
	 * @param dx La valeur ajout�e sur l'axe des abscisses.
	 * @param dy La valeur ajout�e sur l'axe des ordonn�es.
	 */
	public final void addMotion(final double dx, final double dy)
	{
		this.coordinate.addMotion(dx, dy);
		updateUIShape();
	}

	/**
	 * Met en place les coordonn�es de l'objet.
	 *
	 * @param coordinate Les coordonn�es de l'objet.
	 */
	public final void setCoordinate(final Point2D coordinate)
	{
		this.coordinate = coordinate;
		updateUIShape();
	}

	/**
	 * Met en place la couleur de l'objet.
	 *
	 * @param color La couleur de l'objet.
	 */
	public void setColorShape(final Color color)
	{
		this.shape.setFill(color);
	}
}
