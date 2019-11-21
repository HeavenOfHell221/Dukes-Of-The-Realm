package DukesOfTheRealm;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.geometry.Point2D;

public abstract class Sprite {

    private Pane canvas;
    private Shape shape;
	
	private double x;
    private double y;
    private int cellX;
    private int cellY;

    private double width;
    private double height;
 
    
    public Sprite (Pane canvas, double x, double y)
    {
    	this.canvas = canvas;
    	this.x = x;
    	this.y = y;
    	Point2D p = Grid.GetCellWithCoordinates(x, y);
    	this.cellX = (int) p.getX();
    	this.cellY = (int) p.getX();
    }
    
    public Sprite (Pane canvas)
    {
    	this.canvas = canvas;
    }
    
    public Sprite (Pane canvas, Point2D p, double speed)
    {
    	this.canvas = canvas;
    	this.x = p.getX();
    	this.y = p.getY();
    	Point2D p2 = Grid.GetCellWithCoordinates(p.getX(), p.getY());
    	this.cellX = (int) p2.getX();
    	this.cellY = (int) p2.getX();
    }

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
    
    protected void AddRectangle(double width, double height)
	{
		Shape rectangle = new Rectangle(this.x, this.y, width, height);
		AddToLayerShape(rectangle);
		this.width = width;
		this.height = height;
		
	}
	
	protected void AddCircle(double r)
	{
		Shape circle = new Circle(this.x, this.y, r);
		circle.setFill(Color.DARKGOLDENROD);
		this.shape = circle;
		canvas.getChildren().add(circle);
		circle.toFront();
		this.width = 2 * r;
		this.height = 2 * r;
		
	}
    
    public void UpdateUIShape()
    {
    	shape.relocate(x, y);
    }
    
    public double GetX() 
    {
        return x;
    }
    public double GetY() 
    {
        return y;
    }
    
    public double getWidth() 
    {
        return width;
    }
    public double getHeight() 
    {
        return height;
    }
    public double getCenterX() 
    {
        return x + width * 0.5;
    }
    public double getCenterY() 
    {
        return y + height * 0.5;
    }
    public Pane getLayer() 
    {
        return canvas;
    }
    
    public void SetX(int x) 
    {
        this.x = x;
    }
    public void SetY(int y) 
    {
        this.y = y;
    }
    
    public void SetCellX(int cellX)
    {
    	this.cellX = cellX;
    }
    
    public void SetCellY(int cellY)
    {
    	this.cellY = cellY;
    }
    
    public void AddDx(double dx)
    {
    	this.x += dx;
    }
    
    public void AddDy(double dy)
    {
    	this.y += dy;
    }
    
    public int GetCellX()
    {
    	return cellX;
    }
    
    public int GetCellY()
    {
    	return cellY;
    }
}
