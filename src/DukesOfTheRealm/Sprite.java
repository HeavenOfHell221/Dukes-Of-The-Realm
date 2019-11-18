package DukesOfTheRealm;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.geometry.Point2D;

public abstract class Sprite {

	private ImageView imageView;
    private Pane layer;
    
    private Shape shape;
	
	protected double x;
    protected double y;

	private boolean removable = false;

    private double w;
    private double h;
    
    public Sprite (Pane layer, Image image, double x, double y)
    {
    	this.layer = layer;
    	this.x = x;
    	this.y = y;
    	
    	//this.imageView = new ImageView(image);
    	//this.imageView.relocate(x , y);
    	
    	//this.w = image.getWidth();
    	//this.h = image.getHeight();
    	
    	//addToLayerImageView();
    }
    
    public Sprite (Pane layer, Image image, Point2D p, double speed)
    {
    	this.layer = layer;
    	this.x = p.getX();
    	this.y = p.getY();
    }
    
	private void AddToLayerImageView()
    {
    	layer.getChildren().add(this.imageView);
    }

    private void AddToLayerShape(Shape shape)
	{
    	this.shape = shape;
		layer.getChildren().add(shape);
	}
    
    public void removeFromLayerShape() 
    {
        this.layer.getChildren().remove(this.shape);
    }
    
    protected void AddRectangle(double width, double height)
	{
		Shape rectangle = new Rectangle(this.x, this.y, width, height);
		AddToLayerShape(rectangle);
		this.w = width;
		this.h = height;
		
	}
	
	protected void AddCircle(double r)
	{
		Shape circle = new Circle(this.x, this.y, r);
		AddToLayerShape(circle);
		this.w = 2 * r;
		this.h = 2 * r;
		
	}
    
    public void UpdateUIImageView() 
    {
        imageView.relocate(x, y);
    }
    
    public void updateUIShape()
    {
    	shape.relocate(x, y);
    }
    
    public double getX() 
    {
        return x;
    }
    public double getY() 
    {
        return y;
    }
    
    protected ImageView getView() 
    {
        return imageView;
    }
    
    public double getWidth() 
    {
        return w;
    }
    public double getHeight() 
    {
        return h;
    }
    public double getCenterX() 
    {
        return x + w * 0.5;
    }
    public double getCenterY() 
    {
        return y + h * 0.5;
    }
    
    public void setX(double x) 
    {
        this.x = x;
    }
    public void setY(double y) 
    {
        this.y = y;
    }  

    public boolean isRemovable() 
    {
        return removable;
    }

    public void remove()
    {
    	removable = true;
    }
    
    public boolean isCollisionWith(Sprite other)
    {
		double leftA = this.x;
	    double rightA = this.x + this.w;
	    double topA = this.y;
	    double bottomA = this.y + this.h;
	 
	    double leftB = other.x;
	    double rightB = other.x + other.w;
	    double topB = other.y;
	    double bottomB = other.y + other.h;
	    
	    if( bottomA <= topB )
	    {
	        return false;
	    }
	 
	    if( topA >= bottomB )
	    {
	        return false;
	    }
	 
	    if( rightA <= leftB )
	    {
	        return false;
	    }
	 
	    if( leftA >= rightB )
	    {
	        return false;
	    }
	    
	    return true;
    }
}
