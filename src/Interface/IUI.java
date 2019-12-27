package Interface;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Interface utilisé pour le UI (User Interface).
 * Donne un certain nombre de méthodes à implémenter ainsi que des méthodes par défaut.
 */
public interface IUI
{
	/**
	 * Ajoute un noeud au pane.
	 * @param Node à ajouter au pane.
	 */
	void addNode(final Node node);

	/**
	 * Met à jour l'emplacement de tous les noeuds.
	 */
	void relocateAllNodes();

	/**
	 * Ajoute tous les noeuds au pane.
	 */
	void addAllNodes();

	/**
	 * Rend visible/invisible tous les noeuds.
	 * @param visible Boolean activant ou desactivant la visibilité des noeuds.
	 */
	void setAllVisible(final boolean visible);

	/**
	 * Met à jour l'emplaement d'un noeud.
	 * @param node Le noeud à déplacer.
	 * @param x La coordonnée x de nouvel emplacement.
	 * @param y La coordonnée y du nouvel emplacement.
	 */
	default void relocate(final Node node, final double x, final double y)
	{
		node.relocate(x, y);
	}

	/**
	 * Change la visibilité d'un noeud.
	 * @param node Le noeud auquel on veut changer la visibilité.
	 * @param visible Boolean spécifiant s'il est visible ou non.
	 */
	default void setVisible(final Node node, final boolean visible)
	{
		node.setVisible(visible);
	}

	/**
	 * Charge une image en mémoire en allant la chercher dans un répertoire donné.
	 * @param  path Le chemin permettant d'accéder à l'image.
	 * @param width La largeur de l'image.
	 * @param height La hauteur de l'image.
	 * @return Un objet de tye ImageView pour utiliser ensuite l'image.
	 */
	default ImageView newImageView(final String path, final int width, final int height)
	{
		return new ImageView(new Image(getClass().getResource(path).toExternalForm(), width, height, true, true));
	}

}
