package DukesOfTheRealm;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

public abstract class Sprite {

	private ImageView imageView;
    private Pane layer;
    
    private Shape shape;
	
	protected double x;
    protected double y;

    protected double speed;

	private boolean removable = false;

    private double w;
    private double h;
    
    public Sprite (Pane layer, Image image, double x, double y, double speed)
    {
    	this.layer = layer;
    	this.x = x;
    	this.y = y;
    	this.speed = speed;
    	
    	//this.imageView = new ImageView(image);
    	//this.imageView.relocate(x , y);
    	
    	//this.w = image.getWidth();
    	//this.h = image.getHeight();
    	
    	//addToLayerImageView();
    }
    
    
    private void addToLayerImageView()
    {
    	layer.getChildren().add(imageView);
    }

    private void addToLayerShape(Shape shape)
	{
    	this.shape = shape;
		layer.getChildren().add(shape);
	}
    
    protected void addRectangle(double width, double height)
	{
		Shape rectangle = new Rectangle(this.x, this.y, width, height);
		addToLayerShape(rectangle);
		this.w = width;
		this.h = height;
		
	}
	
	protected void addCircle(double r)
	{
		Shape circle = new Circle(this.x, this.y, r);
		addToLayerShape(circle);
		this.w = 2 * r;
		this.h = 2 * r;
		
	}
    
    public void move() 
    {
        x += speed;
    }
    
    public void updateUIImageView() 
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
    
    public double getSpeed() 
    {
        return speed;
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
    public void setSpeed(double speed)
    {
    	this.speed = speed;
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
