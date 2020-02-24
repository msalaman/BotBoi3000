package node;

import routine.Routine;

public class ExecutionNode extends Node {
	public void setRoutine(Routine myRoutine) {
		action = myRoutine;
	}
}
