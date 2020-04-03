package Routine;

import Blackboard.*;

public class StratZergStrat extends Routine {
	private Sequence sequence;
	
	public StratZergStrat() {
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
			sequence.addRoutine(new StratZergDefense(blackboard));
			sequence.addRoutine(new StratZergScout(blackboard));
			sequence.start();
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
