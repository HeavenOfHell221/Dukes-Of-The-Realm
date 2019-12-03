package Utility;

import java.io.Serializable;

public class Point2D implements Serializable {

	private double x;
	private double y;
	
	public Point2D()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public Point2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point2D(Point2D p)
	{
		this(p.x, p.y);
	}
	
	public double GetX()
	{
		return x;
	}
	
	public double GetY()
	{
		return y;
	}
	
	public void SetX(double x)
	{
		this.x = x;
	}
	
	public void SetY(double y)
	{
		this.y = y;
	}
	
	public void AddDx(double x)
	{
		this.x += x;
	}
	
	public void AddDy(double y)
	{
		this.y += y;
	}
	
	public void AddMotion(double x, double y)
	{
		this.x += x;
		this.y += y;
	}
}
