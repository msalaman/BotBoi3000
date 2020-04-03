package Routine;

import Blackboard.*;

public class StratRoot extends Routine{
	public Selector selector;
	
	public StratRoot() {
		super();
	}
	
	public void start(Blackboard blackboard) {
		if(selector == null) {
			selector = new Selector();
		}
		selector.addRoutine(new StratZergStrat());
		/*selector.addRoutine(new stratTerranStrat(blackboard));
		selector.addRoutine(new stratProtossStrat(blackboard));
		*/
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
			this.start(blackboard);
			return;
		}
		if(selector.getState() == null) {
			this.start(blackboard);
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
				blackboard.game.drawTextScreen(10, 150, "StratRoot selector not in game..?");
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
