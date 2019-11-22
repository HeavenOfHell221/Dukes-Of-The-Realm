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
    
    protected void AddCastleRepresentation(double width, double height)
	{
		Rectangle rectangle = new Rectangle(GetX(), GetY(), width, height);
		this.shape = rectangle;
		this.width = width;
		this.height = height;
		this.shape.toBack();
	}
	
	protected void AddPikerRepresentation()
	{
		double r = Settings.PIKER_REPRESENTATION_RADIUS;
		Circle circle = new Circle(GetX(), GetY(), r);
		circle.setFill(Color.DARKGOLDENROD);
		circle.toFront();
		this.shape = circle;
		this.width = 2 * r;
		this.height = 2 * r;
	}
	
	protected void AddKnightRepresentation()
	{
		double s = Settings.KNIGHT_REPRESENTATION_SIZE;
		Rectangle rectangle = new Rectangle(GetX(), GetY(), s, s);
		rectangle.setFill(Color.DARKGOLDENROD);
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
		rectangle.setFill(Color.DARKGOLDENROD);
		rectangle.toFront();
		this.shape = rectangle;
		this.width = w;
		this.height = h;
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
