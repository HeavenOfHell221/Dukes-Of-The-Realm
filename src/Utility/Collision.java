package Utility;

import static Utility.Settings.CASTLE_SIZE;
import static Utility.Settings.GAP_WITH_SOLDIER;
import static Utility.Settings.SOLDIER_SIZE;

import java.util.ArrayList;
import java.util.Iterator;

import Enum.CollisionEnum;

/**
 * Module permetant de détecter les collisions entre les châteaux et les unités.
 */
public class Collision
{
	/**
	 * Instance static de la classe Collision. Utilisé comme singleton.
	 */
	private static final Collision instance = new Collision();

	/**
	 * Liste des coordonées des châteaux du royaume.
	 */
	private final ArrayList<Point2D> castlesCoord;

	/**
	 * Constructeur par défaut de Collision.
	 */
	private Collision()
	{
		this.castlesCoord = new ArrayList<>();
	}

	public static void addPoint(final Point2D p)
	{
		instance.castlesCoord.add(p);
	}

	/**
	 *
	 * @param  pS
	 * @param  pCastle
	 * @return         Retourne true si une collision à été détecté, false sinon.
	 */
	private static CollisionEnum testCollision(final Point2D pSoldier, final Point2D pCastle)
	{
		final Point2D leftTopA = new Point2D(pSoldier);
		final Point2D leftTopB = new Point2D(pCastle.getX() - GAP_WITH_SOLDIER + 1, pCastle.getY() - GAP_WITH_SOLDIER + 1);

		final double castleSize = CASTLE_SIZE + GAP_WITH_SOLDIER * 2 - 2;

		final double rightA = leftTopA.getX() + SOLDIER_SIZE;
		final double rightB = leftTopB.getX() + castleSize;

		final double bottomA = leftTopA.getY() + SOLDIER_SIZE;
		final double bottomB = leftTopB.getY() + castleSize;

		/* Les cas où une collision est impossible */

		if (bottomA < leftTopB.getY() || leftTopA.getY() > bottomB || rightA < leftTopB.getX() || leftTopA.getX() > rightB)
		{
			return CollisionEnum.None;
		}

		/* Il y a donc forcément une collision */

		/* Les cas où l'unité est en collision avec deux faces du châteaux */

		if (rightA == leftTopB.getX() && bottomA == leftTopB.getY() // La pointe touche
				|| rightA > leftTopB.getX() && bottomA > leftTopB.getY() && leftTopA.getX() < leftTopB.getX()
						&& leftTopA.getY() < leftTopB.getY())

		{
			return CollisionEnum.LeftTop;
		}

		if (bottomA == leftTopB.getY() && leftTopA.getX() == rightB
				|| bottomA > leftTopB.getY() && leftTopA.getX() < rightB && rightA > rightB && leftTopA.getY() < leftTopB.getY())
		{
			return CollisionEnum.TopRight;
		}

		if (leftTopA.getX() == rightB && leftTopA.getY() == bottomB
				|| leftTopA.getX() < rightB && leftTopA.getY() < bottomB && rightA > rightB && bottomA > bottomB)
		{
			return CollisionEnum.RightBottom;
		}

		if (rightA == leftTopB.getX() && leftTopA.getY() == bottomB
				|| rightA > leftTopB.getX() && leftTopA.getY() < bottomB && leftTopA.getX() < leftTopB.getX() && bottomA > bottomB)
		{
			return CollisionEnum.BottomLeft;
		}

		if (rightA >= leftTopB.getX() && leftTopA.getX() < leftTopB.getX())
		{
			return CollisionEnum.Left;
		}

		if (bottomA >= leftTopB.getY() && leftTopA.getY() < leftTopB.getY())
		{
			return CollisionEnum.Top;
		}

		if (leftTopA.getX() <= rightB && rightA > rightB)
		{
			return CollisionEnum.Right;
		}

		if (leftTopA.getY() <= bottomB && bottomA > bottomB)
		{
			return CollisionEnum.Bottom;
		}

		return CollisionEnum.Inside;
	}

	public static CollisionEnum testCollisionWithAllCastlesNearby(final Point2D pSoldier)
	{
		CollisionEnum isCollision = CollisionEnum.None;

		Iterator<Point2D> it = instance.castlesCoord.iterator();

		while (it.hasNext())
		{
			Point2D pNext = it.next();
			if (pSoldier.distance(pNext) <= CASTLE_SIZE + GAP_WITH_SOLDIER + SOLDIER_SIZE && isCollision == CollisionEnum.None)
			{
				isCollision = testCollision(pSoldier, pNext);
			}
		}
		return isCollision;
	}
}
