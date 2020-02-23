package node;

import routine.Routine;

public class ControlFlowNode extends Node {
	public Routine logicRoutine;
	
	public void setLogic(Routine routine) {
		this.logicRoutine = routine;
	}
	
	public int checkLogic() {
		return logicRoutine.act(game, blackboard);
	}
}
