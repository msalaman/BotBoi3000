package routine;

import blackboard.Blackboard;
import bwapi.Game;

public class econZergMidRoutine01 extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		int supply = blackboard.getSupplyUsed();
		if(supply < 44) {
			game.drawTextScreen(100,200, "Mid stage 1: Cut SVC production. Make Medics");
			return 0;
		} else {
			return -1;
		}
	}
}
