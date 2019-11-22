package DukesOfTheRealm;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import Utility.Settings;
import javafx.geometry.Point2D;

public abstract class Sprite extends Parent{

    private Pane canvas;
    private Shape shape;
	
	private Point2D coordinate;
    private Point2D cell;

    private double width;
    private double height;
 
    
    public Sprite (Pane canvas, double x, double y)
    {
    	this.canvas = canvas;
    	this.coordinate = new Point2D(x, y);
    	this.cell = Grid.GetCellWithCoordinates(x, y);
    }
    
    public Sprite (Pane canvas, Point2D point2D, double speed)
    {
    	this.canvas = canvas;
    	this.coordinate = new Point2D(point2D.getX(), point2D.getY());
    	this.cell = Grid.GetCellWithCoordinates(this.coordinate.getX(), this.coordinate.getY());
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
		Rectangle rectangle = new Rectangle(GetX(), GetY(), width, height);
		this.shape = rectangle;
		this.width = width;
		this.height = height;
		this.shape.toBack();
	}
	
	protected void AddCircle(double r)
	{
		Circle circle = new Circle(GetY(), GetX(), r);
		circle.setFill(Color.DARKGOLDENROD);
		this.shape = circle;
		this.width = 2 * r;
		this.height = 2 * r;
	}
	
	public Shape GetShape()
	{
		return shape;
	}
    
    public void UpdateUIShape()
    {
    	shape.relocate(coordinate.getX(), coordinate.getY());
    }
    
    public int GetX() 
    {
        return (int) coordinate.getX();
    }
    public int GetY() 
    {
        return (int) coordinate.getY();
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
    
    public void SetCellX(int cellX)
    {
    	Point2D p = new Point2D(cellX, this.cell.getY());
    	this.cell = p;
    }
    
    public void SetCellY(int cellY)
    {
    	Point2D p = new Point2D(this.cell.getX(), cellY);
    	this.cell = p;
    }
    
    public void AddDx(double dx)
    {
    	coordinate.add(dx, 0);
    }
    
    public void AddDy(double dy)
    {
    	coordinate.add(0, dy);
    }
    
    public int GetCellX()
    {
    	return (int) this.cell.getX();
    }
    
    public int GetCellY()
    {
    	return (int) this.cell.getY();
    }
    
    public Point2D GetCell()
    {
    	return this.cell;
    }
    
    public Point2D GetCoordinate()
    {
    	return this.coordinate;
    }
    
    public void SetCell(Point2D cell)
    {
    	this.cell = cell;
    }
    
    public void SetCoordinate(Point2D coordinate)
    {
    	this.coordinate = coordinate;
    }
}
