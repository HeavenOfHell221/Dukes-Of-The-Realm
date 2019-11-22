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
	
	private Point2D coordinate;
    private Point2D cell;

    private double width;
    private double height;
 
    
    public Sprite (Pane canvas, double x, double y)
    {
    	this.canvas = canvas;
    	//this.x = x;
    	//this.y = y;
    	this.coordinate = new Point2D(x, y);
    	this.cell = Grid.GetCellWithCoordinates(x, y);
    	//this.cellX = (int) p.getX();
    	//this.cellY = (int) p.getX();
    }
    
    public Sprite (Pane canvas, Point2D point2D, double speed)
    {
    	this.canvas = canvas;
    	//this.x = p.getX();
    	//this.y = p.getY();
    	this.coordinate = new Point2D(point2D.getX(), point2D.getY());
    	this.cell = Grid.GetCellWithCoordinates(this.coordinate.getX(), this.coordinate.getY());
    	//this.cellX = (int) p2.getX();
    	//this.cellY = (int) p2.getX();
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
		Shape rectangle = new Rectangle(GetX(), GetY(), width, height);
		AddToLayerShape(rectangle);
		this.width = width;
		this.height = height;
		
	}
	
	protected void AddCircle(double r)
	{
		Shape circle = new Circle(GetY(), GetX(), r);
		circle.setFill(Color.DARKGOLDENROD);
		this.shape = circle;
		canvas.getChildren().add(circle);
		circle.toFront();
		this.width = 2 * r;
		this.height = 2 * r;
		
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
    	double oldX = coordinate.getX();
    	coordinate.add(x - oldX, 0);
    }
    public void SetY(int y) 
    {
    	double oldY = coordinate.getY();
    	coordinate.add(0, y - oldY);
    }
    
    public void SetCellX(int cellX)
    {
    	double oldCellX = cell.getX();
    	cell.add(cellX - oldCellX, 0);
    }
    
    public void SetCellY(int cellY)
    {
    	double oldCellY = cell.getY();
    	cell.add(0, cellY - oldCellY);
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
