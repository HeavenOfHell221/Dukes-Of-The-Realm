package Utility;

import java.io.Serializable;

/**
 * Implémentation d'un point en 2D.
 */
public class Point2D implements Serializable
{
	/**
	 * Coordonnée x.
	 */
	private double x;

	/**
	 * Coordonnée y.
	 */
	private double y;

	/**
	 * Constructeur par défaut de Point2D.
	 */
	public Point2D()
	{
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructeur de Point2D.
	 * @param x Coordonnée x.
	 * @param y Coordonnée y.
	 */
	public Point2D(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructeur par recopie de Point2D.
	 * @param p Le point2D à recopier.
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
	 * Déplace le point d'une certaine quantité dx et dy.
	 * @param dx La quantité en x.
	 * @param dy La quantité en y.
	 */
	public void addMotion(final double dx, final double dy)
	{
		this.x += dx;
		this.y += dy;
	}

	/**
	 * Calcul un point2D en soustrayant les coordonées du second avec le premier.
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
	 * Calcul la distance entre ce point et le point p en paramètre.
	 * @param p Le second point.
	 * @return La distance entre ce point et le point p.
	 */
	public double distance(Point2D p)
	{
		if(p != null)
		{
			double xbxa = this.x - p.getX();
			double ybya = this.y - p.getY();
			return Math.sqrt(xbxa * xbxa + ybya * ybya);
		}
		
		return Double.POSITIVE_INFINITY;
	}
	
	

	@Override
	public String toString()
	{
		return "Point2D [x=" + x + ", y=" + y + "]";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Point2D other = (Point2D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
		{
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
		{
			return false;
		}
		return true;
	}
	
	
}
