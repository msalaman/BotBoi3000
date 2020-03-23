package node;

public class SequenceNode extends ControlFlowNode {
	private int currentChild = 0;
	public void executeAll() {
		for (int i=this.currentChild; i < getChildren().size(); i++) {
			game.drawTextScreen(200, 60, "In exec All for loop");
			if(getChildren().get(i).getState() != 0) {
				game.drawTextScreen(200, 70, "Check child state");
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
