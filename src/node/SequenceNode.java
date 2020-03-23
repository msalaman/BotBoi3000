package node;

public class SequenceNode extends ControlFlowNode {
	private int currentChild = 0;
	public void executeAll() {
		
		for (int i=this.currentChild; i < getChildren().size(); i++) {
			if(getChildren().get(i).getState() != 0) {
				this.currentChild++;
				continue;
			}
			if(getChildren().get(i).execute() < 0) {
				getChildren().get(i).setState(-1);
			}
			break;
		}
		if(this.currentChild >= getChildren().size()) {
			setState(-1);
		}
	}
}
