package node;

public class SelectorNode extends ControlFlowNode {
	
	public Node select() {
		int logicResult = this.checkLogic();
		if (logicResult < getChildren().size()) {
			return getChildren().get(logicResult);
		}
		else {
			return null;
		}
	}
}
