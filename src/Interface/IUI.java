package Interface;

import DukesOfTheRealm.Castle;
import javafx.scene.Node;

public interface IUI
{

	void addAllNodes();

	void relocateAllNodes();

	void relocate(Node node, double x, double y);

	void addNode(Node node);

	void switchCastle(Castle castle);
}
