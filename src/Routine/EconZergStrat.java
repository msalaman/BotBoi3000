package Routine;

import Blackboard.*;

public class EconZergStrat extends Routine {
	private Sequence sequence;
	
	public EconZergStrat() {
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
			blackboard.game.drawTextScreen(150, 100, "We are generating the sequence");
			sequence = new Sequence();
			//TODO: add stuff to sequence
			sequence.addRoutine(new EconZergEarly(blackboard));
			sequence.addRoutine(new EconZergMid(blackboard));
			sequence.addRoutine(new EconZergLate(blackboard));
			sequence.start();
			blackboard.game.drawTextScreen(150, 110, "We finished starting the sequence");
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
