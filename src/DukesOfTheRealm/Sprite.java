package DukesOfTheRealm;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.io.Serializable;

import Utility.Point2D;
import Utility.Settings;

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
    
    public Sprite (Pane canvas, double x, double y)
    {
    	this.canvas = canvas;
    	this.coordinate = new Point2D(x, y);
    }
    
    public Sprite (Pane canvas, Point2D point2D, double speed)
    {
    	this.canvas = canvas;
    	this.coordinate = new Point2D(point2D);
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

    protected void AddCastleRepresentation(double size)
	{
		Rectangle r = new Rectangle(GetX(), GetY(), size, size);
		this.shape = r;
		r.setCursor(Cursor.HAND);
		
		DropShadow e = new DropShadow();
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
		double r = Settings.PIKER_REPRESENTATION_RADIUS;
		Circle circle = new Circle(GetX(), GetY(), r);
		this.shape = circle;
		this.width = 2 * r;
		this.height = 2 * r;
		
		circle.setStroke(Color.BLACK);
		circle.setStrokeType(StrokeType.OUTSIDE);
		circle.setStrokeWidth(1.5);
	}
	
	protected void AddKnightRepresentation()
	{
		double s = Settings.KNIGHT_REPRESENTATION_SIZE;
		Rectangle r = new Rectangle(GetX(), GetY(), s, s);
		this.shape = r;
		this.width = s;
		this.height = s;
		
		r.setStroke(Color.BLACK);
		r.setStrokeType(StrokeType.OUTSIDE);
		r.setStrokeWidth(1.5);
	}
	
	protected void AddOnagerRepresentation()
	{
		double w = Settings.ONAGER_REPRESENTATION_WIDTH;
		double h = Settings.ONAGER_REPRESENTATION_HEIGHT;
		Rectangle r = new Rectangle(GetX(), GetY(), w, h);
		this.shape = r;
		this.width = w;
		this.height = h;
		
		r.setStroke(Color.BLACK);
		r.setStrokeType(StrokeType.OUTSIDE);
		r.setStrokeWidth(1.5);
	}
	
	public void RemoveShapeToLayer()
	{
		canvas.getChildren().remove(this.shape);
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
    
    public void SetX(int x) 
    {
    	this.coordinate.SetX(x);
    }
    public void SetY(int y) 
    {
    	this.coordinate.SetY(y);
    }
    
    public void AddDx(double dx)
    {
    	this.coordinate.AddDx(dx);
    }
    
    public void AddDy(double dy)
    {
    	this.coordinate.AddDy(dy);
    }
    
    public void AddMotion(double dx, double dy)
    {
    	this.coordinate.AddMotion(dx, dy);
    }
  
    public void SetCoordinate(Point2D coordinate)
    {
    	this.coordinate = coordinate;
    }
}
