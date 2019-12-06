package Utility;

import java.io.Serializable;

public class Point2D implements Serializable {

	private double x;
	private double y;

	public Point2D()
	{
		x = 0;
		y = 0;
	}

	public Point2D(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}

	public Point2D(final Point2D p)
	{
		this(p.x, p.y);
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public void setX(final double x)
	{
		this.x = x;
	}

	public void setY(final double y)
	{
		this.y = y;
	}

	public void addDx(final double x)
	{
		this.x += x;
	}

	public void addDy(final double y)
	{
		this.y += y;
	}

	public void addMotion(final double x, final double y)
	{
		this.x += x;
		this.y += y;
	}
}
