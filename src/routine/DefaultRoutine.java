package routine;

import blackboard.Blackboard;
import bwapi.Game;

public class DefaultRoutine extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		game.drawTextScreen(100, 200, "We've implemented a routine and now go to execution");
		return 0;
	}
}
