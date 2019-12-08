package Interface;

import Duke.Actor;
import DukesOfTheRealm.Castle;
import javafx.scene.Node;

public interface IUI
{
	void addAllNodes();
	void addNode(final Node node);
	void relocateAllNodes();
	void setAllVisible(final boolean visible);
	void switchCastle(final Castle castle, final Actor actor, boolean productionVisible, boolean attackVisible);
	default void relocate(final Node node, final double x, final double y)
	{
		node.relocate(x, y);
	}
	default void setVisible(final Node node, final boolean visible)
	{
		node.setVisible(visible);
	}
}
