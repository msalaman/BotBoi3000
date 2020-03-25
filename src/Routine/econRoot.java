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
		blackboard.game.drawTextScreen(10, 140, "EconRoot selector");
		if(selector == null) {
			blackboard.game.drawTextScreen(10, 150, "EconRoot selector==null");
			this.start(blackboard);
			return;
		}
		blackboard.game.drawTextScreen(10, 160, "1EconRoot selector");
		if(selector.getState() == null) {
			blackboard.game.drawTextScreen(10, 160, "69EconRoot selector");
			this.start(blackboard);
			return;
		}
		if(selector.isFailure()) {
			blackboard.game.drawTextScreen(10, 150, "EconRoot selector failed");
			fail();
			this.reset();
			return;
		}
		blackboard.game.drawTextScreen(10, 160, "2EconRoot selector");
		if(selector.isRunning()) {
			blackboard.game.drawTextScreen(10, 150, "EconRoot selector running");
			//if(!blackboard.game.isInGame()) {
			//	blackboard.game.drawTextScreen(10, 150, "EconRoot selector not in game..?");
			//	fail();
			//}
			//else {
			blackboard.game.drawTextScreen(10, 150, "EconRoot selector act");
			selector.act(blackboard);
			return;
			//}
		}
		blackboard.game.drawTextScreen(10, 160, "3EconRoot selector");
		if(this.selector.isSuccess()) {
			blackboard.game.drawTextScreen(10, 150, "EconRoot selector success");
			succeed();
			this.reset();
		} 		
		blackboard.game.drawTextScreen(10, 160, "4EconRoot selector");

		
	}
	
}
