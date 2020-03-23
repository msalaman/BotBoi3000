package node;

public class SelectorNode extends ControlFlowNode {
	
	public Node select() {
		//game.drawTextScreen(100, 170, "1This means that there is hope!");
		int logicResult = this.checkLogic();
		//game.drawTextScreen(100, 180, "2This means that there is hope!");
		if (logicResult < this.getChildren().size()) {
			//game.drawTextScreen(100, 190, "3This means that there is hope!");
			return this.getChildren().get(logicResult);
		}
		else {
			//game.drawTextScreen(100, 190, "This means that there is NO hope!");
			return null;
		}
	}
}
