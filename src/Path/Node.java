package Path;

import Utility.Point2D;

public class Node
{
	private double costToStart = 0;
	private double costToArrival = 0;
	private double totalCost = 0;
	private Point2D parent;
	
	/**
	 * @param parent
	 */
	public Node(final Point2D parent)
	{
		this.parent = new Point2D(parent);
	}
	
	
	
	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost()
	{
		this.totalCost = this.costToStart + this.costToArrival;
	}
	
	
	
	/**
	 * @return the costToStart
	 */
	public double getCostToStart()
	{
		return costToStart;
	}
	
	/**
	 * @param costToStart the costToStart to set
	 */
	public void setCostToStart(double costToStart)
	{
		this.costToStart = costToStart;
	}
	
	/**
	 * @return the costToArrival
	 */
	public double getCostToArrival()
	{
		return costToArrival;
	}
	
	/**
	 * @param costToArrival the costToArrival to set
	 */
	public void setCostToArrival(double costToArrival)
	{
		this.costToArrival = costToArrival;
	}
	
	/**
	 * @return the totalCost
	 */
	public double getTotalCost()
	{
		return totalCost;
	}
	
	/**
	 * @return the parent
	 */
	public Point2D getParent()
	{
		return parent;
	}
	
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Point2D parent)
	{
		this.parent = parent;
	}
}
