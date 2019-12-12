package Interface;

import Duke.Actor;
import DukesOfTheRealm.Castle;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author Utilisateur
 *
 */
public interface IUI
{
	/**
	 * 
	 * @param node
	 */
	void addNode(final Node node);

	/**
	 * 
	 */
	void relocateAllNodes();
	
	/**
	 * 
	 */
	void addAllNodes();
	
	/**
	 * 
	 * @param visible
	 */
	void setAllVisible(final boolean visible);

	/**
	 * 
	 * @param node
	 * @param x
	 * @param y
	 */
	default void relocate(final Node node, final double x, final double y)
	{
		node.relocate(x, y);
	}

	/**
	 * 
	 * @param node
	 * @param visible
	 */
	default void setVisible(final Node node, final boolean visible)
	{
		node.setVisible(visible);
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	default ImageView newImageView(final String path, int width, int height)
	{
		return new ImageView(new Image(getClass().getResource(path).toExternalForm(), width, height, true, true));
	}

}
