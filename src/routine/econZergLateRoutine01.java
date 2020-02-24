package routine;

import blackboard.Blackboard;
import bwapi.Game;

public class econZergLateRoutine01 extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		game.drawTextScreen(100,200, "Late stage 1: Vultures and tanks");
		game.drawTextScreen(100,200, "This is the last stage and where the econ will stay");
		return -1;
	}
}
