package routine;

import blackboard.Blackboard;
import bwapi.Game;

public class econZergEarlyRoutine02 extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		int supply = blackboard.getSupplyUsed();
		if(supply < 26 && supply > 9) {
			game.drawTextScreen(100,200, "Early stage 2: focus on marines and SVCs");
			return 0;
		} else {
			return -1;
		}
	}
}