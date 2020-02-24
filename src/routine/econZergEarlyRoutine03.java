package routine;

import blackboard.Blackboard;
import bwapi.Game;

public class econZergEarlyRoutine03 extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		int supply = blackboard.getSupplyUsed();
		if(supply > 25 && supply < 39) {
			game.drawTextScreen(100,200, "Early stage 3: build up Barraks and Refinery");
			return 0;
		} else {
			return 1;
		}

	}
}
