package Enums;

/**
 * Liste des collisions possible entre une unit� et un ch�teau.
 * <p>
 * Ce qui comprend : <br>
 * Les 4 cot�s d'un ch�teau (Top, Right, Bottom, Left) <br>
 * Les 4 coins d'un ch�teau (LeftTop, TopRight, RightBottom, BottomLeft) L'int�rieur du ch�teau
 * (Inside) Pas de collision (None)
 * </p>
 */
public enum CollisionEnum
{
	/**
	 * Cot� nord
	 */
	Top,

	/**
	 * Cot� est
	 */
	Right,

	/**
	 * Cot� ouest
	 */
	Left,

	/**
	 * Cot� sud
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
	 * A l'int�rieur
	 */
	Inside,

	/**
	 * Aucune collision
	 */
	None;

}
