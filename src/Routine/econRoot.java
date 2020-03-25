package Routine;

import Blackboard.*;

public class econRoot extends Routine{
	public Selector selector;
	
	public econRoot() {
		super();
	}
	
	public void start(Blackboard blackboard) {
		if(selector == null) {
			selector = new Selector();
		}
		selector.addRoutine(new econZergStrat());
		/*selector.addRoutine(new econTerranStrat(blackboard));
		selector.addRoutine(new econProtossStrat(blackboard));
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
				blackboard.game.drawTextScreen(10, 150, "EconRoot selector not in game..?");
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
