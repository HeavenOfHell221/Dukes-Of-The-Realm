package Utility;

import java.io.Serializable;

/**
 * Impl�mentation d'un point en 2D.
 */
public class Point2D implements Serializable
{
	/**
	 * Coordonn�e x.
	 */
	private double x;

	/**
	 * Coordonn�e y.
	 */
	private double y;

	/**
	 * Constructeur par d�faut de Point2D.
	 */
	public Point2D()
	{
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructeur de Point2D.
	 * @param x Coordonn�e x.
	 * @param y Coordonn�e y.
	 */
	public Point2D(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructeur par recopie de Point2D.
	 * @param p Le point2D � recopier.
	 */
	public Point2D(final Point2D p)
	{
		this(p.x, p.y);
	}
	
	/**
	 * @return the x
	 */
	public final double getX()
	{
		return x;
	}

	/**
	 * @return the y
	 */
	public final double getY()
	{
		return y;
	}

	/**
	 * @param x the x to set
	 */
	public final void setX(double x)
	{
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public final void setY(double y)
	{
		this.y = y;
	}

	/**
	 * D�place le point d'une certaine quantit� dx et dy.
	 * @param dx La quantit� en x.
	 * @param dy La quantit� en y.
	 */
	public void addMotion(final double dx, final double dy)
	{
		this.x += dx;
		this.y += dy;
	}

	/**
	 * Calcul un point2D en soustrayant les coordon�es du second avec le premier.
	 * @param p Le point qui serra soustrait.
	 * @return Retourne le resultat de la soustraction.
	 */
	public Point2D delta(final Point2D p)
	{
		if (p != null)
		{
			return new Point2D(Math.abs(this.x - p.x), Math.abs(this.y - p.y));
		}
		return null;
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public double distance(Point2D p)
	{
		return (this.x - p.getX())*(this.x - p.getX()) + (this.y - p.getY())*(this.y - p.getY());
	}
}
