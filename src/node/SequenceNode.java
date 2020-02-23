package node;

public class SequenceNode extends ControlFlowNode {
	private int currentChild = 0;
	public void executeAll() {
		
		for (int i=currentChild; i < getChildren().size(); i++) {
			if(getChildren().get(i).getState() != 0) {
				currentChild++;
				continue;
			}
			getChildren().get(i).execute();
			break;
		}
		if(currentChild >= getChildren().size()) {
			this.setState(1);
		}
	}
}
