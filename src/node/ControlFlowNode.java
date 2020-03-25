package node;

import routine.Routine;

public class ControlFlowNode extends Node {
	public Routine logicRoutine;
	
	public ControlFlowNode() {
		super();
	}
	
	public void setLogic(Routine routine) {
		//game.drawTextScreen(10, 70, "entered setLogic in cf node");
		this.logicRoutine = routine;
		//game.drawTextScreen(100, 70, "exited setLogic in cf node");
		//if (logicRoutine == null) {
			//game.drawTextScreen(10, 80, "logicRoutine was NOT set");
		//}
		//else {
			//game.drawTextScreen(10, 80, "logicRoutine was set");
		//}
	}
	
	public int checkLogic() {
		//if(this.game == null) {
			//game.drawTextScreen(100, 70, "Failed to find game in controlflow node");
		//}
		//if(this.logicRoutine == null) {
			//game.drawTextScreen(100, 80, "Failed to find logicRoutine in controlflow node");
		//}
		return this.logicRoutine.act(this.game, this.blackboard);
	}
}
