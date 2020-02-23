package node;

public class SequenceNode extends ControlFlowNode {
	
	public void executeAll() {
		for (int i=0; i < getChildren().size(); i++) {
			getChildren().get(i).execute();
		}
	}
}
