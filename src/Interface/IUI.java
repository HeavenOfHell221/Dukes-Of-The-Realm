package Interface;

import DukesOfTheRealm.Castle;
import javafx.scene.Node;

public interface IUI {

	void AddAllNodes();
	void RelocateAllNodes();
	void Relocate(Node node, double x, double y);
	void AddNode(Node node);
	void SwitchCastle(Castle castle);
}
