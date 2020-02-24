package node;

import routine.Routine;

public class ControlFlowNode extends Node {
	public Routine logicRoutine;
	
	public void setLogic(Routine routine) {
		this.logicRoutine = routine;
	}
	
	public int checkLogic() {
		if(this.game == null) {
			game.drawTextScreen(100, 50, "Failed to find game in controlflow node");
		}
		if(this.logicRoutine == null) {
			game.drawTextScreen(100, 20, "Failed to find logicRoutine in controlflow node");
		}
		return this.logicRoutine.act(this.game, this.blackboard);
	}
}
