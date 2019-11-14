package DukesOfTheRealm;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Sprite {

	private ImageView imageView;
    private Pane layer;
	
	protected double x;
    protected double y;

    protected double dx;
    protected double dy;

	private boolean removable = false;

    private double w;
    private double h;
    
    public Sprite (Pane layer, Image image, double x, double y)
    {
    	this.layer = layer;
    	this.x = x;
    	this.y = y;
    	
    	this.imageView = new ImageView(image);
    	this.imageView.relocate(x , y);
    	
    	this.w = image.getWidth();
    	this.h = image.getHeight();
    	
    	addToLayer();
    }
    
    private void addToLayer()
    {
    	layer.getChildren().add(imageView);
    }
    
    public void move() 
    {
        x += dx;
        y += dy;
    }
    
    public void updateUI() 
    {
        imageView.relocate(x, y);
    }
    
    public double getX() 
    {
        return x;
    }
    public double getY() 
    {
        return y;
    }
    
    public double getDx() 
    {
        return dx;
    }
    public double getDy() 
    {
        return dy;
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
    public void setDx(double dx) 
    {
        this.dx = dx;
    }   
    public void setDy(double dy) 
    {
        this.dy = dy;
    }
    
    
    public boolean isRemovable() 
    {
        return removable;
    }

    public void remove()
    {
    	removable = true;
    }
}
