package DukesOfTheRealm;

import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import Utility.Settings;
import javafx.geometry.Point2D;

public abstract class Sprite extends Parent{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
    private Pane canvas;
    private Shape shape;
	
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
    	this.coordinate = new Point2D(point2D.getX(), point2D.getY());
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
	
    private void AddToLayerShape(Shape shape)
	{
    	this.shape = shape;
		canvas.getChildren().add(shape);
		shape.toBack();
	}
    
    public void removeFromLayerShape() 
    {
        this.canvas.getChildren().remove(this.shape);
    }
    
    protected void AddCastleRepresentation(double size)
	{
		Rectangle rectangle = new Rectangle(GetX(), GetY(), size, size);
		this.shape = rectangle;
		
		 DropShadow e = new DropShadow();
	    e.setWidth(5);
	    e.setHeight(5);
	    e.setOffsetX(2);
	    e.setOffsetY(2);
	    e.setRadius(10);
	    e.setColor(Color.BLACK);
		    
		rectangle.setEffect(e);
		this.width = size;
		this.height = size;
		this.shape.toBack();
	}
	
	protected void AddPikerRepresentation()
	{
		double r = Settings.PIKER_REPRESENTATION_RADIUS;
		Circle circle = new Circle(GetX(), GetY(), r);
		circle.toFront();
		this.shape = circle;
		this.width = 2 * r;
		this.height = 2 * r;
	}
	
	protected void AddKnightRepresentation()
	{
		double s = Settings.KNIGHT_REPRESENTATION_SIZE;
		Rectangle rectangle = new Rectangle(GetX(), GetY(), s, s);
		rectangle.toFront();
		this.shape = rectangle;
		this.width = s;
		this.height = s;
	}
	
	protected void AddOnagerRepresentation()
	{
		double w = Settings.ONAGER_REPRESENTATION_WIDTH;
		double h = Settings.ONAGER_REPRESENTATION_HEIGHT;
		Rectangle rectangle = new Rectangle(GetX(), GetY(), w, h);
		rectangle.toFront();
		this.shape = rectangle;
		this.width = w;
		this.height = h;
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
        return (int) coordinate.getX();
    }
    public int GetY() 
    {
        return (int) coordinate.getY();
    }
    
    public Point2D GetCenter()
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

    public Pane getLayer() 
    {
        return canvas;
    }
    
    public void SetX(int x) 
    {
    	Point2D p = new Point2D(x, this.coordinate.getY());
    	this.coordinate = p;
    }
    public void SetY(int y) 
    {
    	Point2D p = new Point2D(this.coordinate.getX(), y);
    	this.coordinate = p;
    }
    
    public void AddDx(double dx)
    {
    	coordinate = coordinate.add(dx, 0);
    }
    
    public void AddDy(double dy)
    {
    	coordinate = coordinate.add(0, dy);
    }
  
    public void SetCoordinate(Point2D coordinate)
    {
    	this.coordinate = coordinate;
    }
}
