package Routine;

import Blackboard.*;
import bwapi.Race;

public class econZergStrat extends Routine {
	private Sequence sequence;
	
	public econZergStrat() {
		super();
	}
	
	public void start() {
		super.start();
	}
	
	public void reset() {
		sequence.reset();
		sequence.start();
	}
	
	public void act(Blackboard blackboard) {
		if(sequence == null) {
			sequence = new Sequence();
			//TODO: add stuff to sequence
			//sequence.addRoutine(new econZergEarly());
			//sequence.addRoutine(new econZergMid());
			//sequence.addRoutine(new econZergLate());
			//sequence.start();
			return;
		} else if(sequence.getState() == null) {
			sequence.start();
			return;
		} else if(sequence.isRunning()) {
			sequence.act(blackboard);
			return;
		} else if(sequence.isSuccess()) {
			this.state = sequence.state;
		} else if(sequence.isFailure()) {
			fail();
		}
	}
	
}
