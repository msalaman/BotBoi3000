package Routine;

import Blackboard.*;
import bwapi.Race;

public class econZergStrat extends Routine {
	private Selector selector;
	
	public econZergStrat() {
		super();
	}
	
	public void start() {
		super.start();
		if(selector == null) {
			selector = new Selector();
		}
		//TODO: add stuff to selector
		
	}
	
	public void reset() {
		selector = new Selector();
		start();
	}
	
	public void act(Blackboard blackboard) {
		if(1 != 1) {//blackboard.enemyRace != Race.Terran ) {
			blackboard.game.drawTextScreen(10, 120, "This Not what should show");

			fail();
		} else {
			super.start();
			blackboard.game.drawTextScreen(10, 120, "This is in the econZergStrat");
		}
		
	}
	
}
