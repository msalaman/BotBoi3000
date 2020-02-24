package routine;

import blackboard.Blackboard;
import bwapi.Game;

public class econZergMidRoutine02 extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		int supply = blackboard.getSupplyUsed();
		if(supply < 61 && supply > 43) {
			game.drawTextScreen(100,200, "Mid stage 2: Build turrets and another sim city");
			return 0;
		} else {
			return 1;
		}
	}
}
