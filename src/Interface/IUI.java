package Interface;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Interface utilis� pour le UI (User Interface).
 * Donne un certain nombre de m�thodes � impl�menter ainsi que des m�thodes par d�faut.
 */
public interface IUI
{
	/**
	 * Ajoute un noeud au pane.
	 * @param Node � ajouter au pane.
	 */
	void addNode(final Node node);

	/**
	 * Met � jour l'emplacement de tous les noeuds.
	 */
	void relocateAllNodes();

	/**
	 * Ajoute tous les noeuds au pane.
	 */
	void addAllNodes();

	/**
	 * Rend visible/invisible tous les noeuds.
	 * @param visible Boolean activant ou desactivant la visibilit� des noeuds.
	 */
	void setAllVisible(final boolean visible);

	/**
	 * Met � jour l'emplaement d'un noeud.
	 * @param node Le noeud � d�placer.
	 * @param x La coordonn�e x de nouvel emplacement.
	 * @param y La coordonn�e y du nouvel emplacement.
	 */
	default void relocate(final Node node, final double x, final double y)
	{
		node.relocate(x, y);
	}

	/**
	 * Change la visibilit� d'un noeud.
	 * @param node Le noeud auquel on veut changer la visibilit�.
	 * @param visible Boolean sp�cifiant s'il est visible ou non.
	 */
	default void setVisible(final Node node, final boolean visible)
	{
		node.setVisible(visible);
	}

	/**
	 * Charge une image en m�moire en allant la chercher dans un r�pertoire donn�.
	 * @param  path Le chemin permettant d'acc�der � l'image.
	 * @param width La largeur de l'image.
	 * @param height La hauteur de l'image.
	 * @return Un objet de tye ImageView pour utiliser ensuite l'image.
	 */
	default ImageView newImageView(final String path, final int width, final int height)
	{
		return new ImageView(new Image(getClass().getResource(path).toExternalForm(), width, height, true, true));
	}

}
