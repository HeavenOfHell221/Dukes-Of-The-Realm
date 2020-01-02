package Enums;

import java.io.Serializable;

/**
 * Liste des collisions possible entre une unité et un château.
 * <p>
 * Ce qui comprend : <br>
 * Les 4 cotés d'un château (Top, Right, Bottom, Left) <br>
 * Les 4 coins d'un château (LeftTop, TopRight, RightBottom, BottomLeft) L'intérieur du château
 * (Inside) Pas de collision (None)
 * </p>
 */
public enum CollisionEnum  implements Serializable
{
	/**
	 * Coté nord
	 */
	Top,

	/**
	 * Coté est
	 */
	Right,

	/**
	 * Coté ouest
	 */
	Left,

	/**
	 * Coté sud
	 */
	Bottom,

	/**
	 * Coin nord ouest
	 */
	LeftTop,

	/**
	 * Coin nord est
	 */
	TopRight,

	/**
	 * Coin sud est
	 */
	RightBottom,

	/**
	 * Coin sud ouest
	 */
	BottomLeft,

	/**
	 * A l'intérieur
	 */
	Inside,

	/**
	 * Aucune collision
	 */
	None;

}
