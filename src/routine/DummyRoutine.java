package routine;

import blackboard.Blackboard;
import bwapi.Game;

public class DummyRoutine extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		game.drawTextScreen(100,200, "TODO: This is a dummy routine. Will replace soon.");
		return -1;

	}
}