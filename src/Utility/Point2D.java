package Utility;

import java.io.Serializable;

public class Point2D implements Serializable
{
	/**
	 * 
	 */
	private double x;
	
	/**
	 * 
	 */
	private double y;

	/**
	 * 
	 */
	public Point2D()
	{
		this.x = 0;
		this.y = 0;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Point2D(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @param p
	 */
	public Point2D(final Point2D p)
	{
		this(p.x, p.y);
	}

	/**
	 * 
	 * @return
	 */
	public double getX()
	{
		return this.x;
	}

	/**
	 * 
	 * @return
	 */
	public double getY()
	{
		return this.y;
	}

	/**
	 * 
	 * @param x
	 */
	public void setX(final double x)
	{
		this.x = x;
	}

	/**
	 * 
	 * @param y
	 */
	public void setY(final double y)
	{
		this.y = y;
	}

	/**
	 * 
	 * @param dx
	 */
	public void addDx(final double dx)
	{
		this.x += dx;
	}
	
	/**
	 * 
	 * @param dy
	 */
	public void addDy(final double dy)
	{
		this.y += dy;
	}

	/**
	 * 
	 * @param dx
	 * @param dy
	 */
	public void addMotion(final double dx, final double dy)
	{
		this.x += dx;
		this.y += dy;
	}
}
