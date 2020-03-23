package node;

public class SelectorNode extends ControlFlowNode {
	
	public Node select() {
		int logicResult = this.checkLogic();
		if (logicResult < this.getChildren().size()) {
			return this.getChildren().get(logicResult);
		}
		else {
			return null;
		}
	}
}
