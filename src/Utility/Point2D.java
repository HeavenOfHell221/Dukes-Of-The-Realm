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

	public double GetX()
	{
		return x;
	}

	public double GetY()
	{
		return y;
	}

	public void SetX(final double x)
	{
		this.x = x;
	}

	public void SetY(final double y)
	{
		this.y = y;
	}

	public void AddDx(final double x)
	{
		this.x += x;
	}

	public void AddDy(final double y)
	{
		this.y += y;
	}

	public void AddMotion(final double x, final double y)
	{
		this.x += x;
		this.y += y;
	}
}
