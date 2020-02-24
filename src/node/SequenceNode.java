package node;

public class SequenceNode extends ControlFlowNode {
	private int currentChild = 0;
	public void executeAll() {
		
		for (int i=currentChild; i < getChildren().size(); i++) {
			if(getChildren().get(i).getState() != 0) {
				currentChild++;
				continue;
			}
			if(getChildren().get(i).execute() < 0) {
				getChildren().get(i).setState(-1);
			}
			break;
		}
		if(currentChild >= getChildren().size()) {
			this.setState(-1);
		}
	}
}
