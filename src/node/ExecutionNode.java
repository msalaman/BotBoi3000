package node;

import routine.Routine;

public class ExecutionNode extends Node {
	
	public ExecutionNode() {}
	
	public void setRoutine(Routine myRoutine) {
		action = myRoutine;
	}
}
