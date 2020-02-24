package routine;

import blackboard.Blackboard;
import bwapi.Game;

public class econZergEarlyRoutine01 extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		int supply = blackboard.getSupplyUsed();
		if(supply < 10) {
			game.drawTextScreen(100,200, "Early stage 1: focus on SVCs");
		}
		return 0;
	}
}