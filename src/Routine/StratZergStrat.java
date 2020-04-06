package Routine;

import Blackboard.*;

public class StratZergStrat extends Routine {
	private Selector selector;
	
	public StratZergStrat() {
		super();
	}
	
	public void start() {
		if(selector == null) {
			selector = new Selector();
		}
		selector.addRoutine(new StratZergDefense());
		selector.addRoutine(new StratZergOffense());
		selector.addRoutine(new StratZergScout());
		super.start();
	}
	
	public void reset() {
		this.state = null;
		this.selector = null;
		super.start();
	}
	
	@Override
	public void act(Blackboard blackboard) {
		if(selector == null) {
			this.start();
			return;
		}
		if(selector.getState() == null) {
			this.start();
			selector.start();
			return;
		}
		if(selector.isFailure()) {
			fail();
			this.reset();
			return;
		}
		if(selector.isRunning()) {
			if(!blackboard.game.isInGame()) {
				blackboard.game.drawTextScreen(10, 150, "StratZergStrat selector not in game..?");
				fail();
			}
			else {
				selector.act(blackboard);
				return;
			}
		}
		if(this.selector.isSuccess()) {
			succeed();
			this.reset();
		} 
	}
	
}
