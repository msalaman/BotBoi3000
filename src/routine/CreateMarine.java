package routine;

import blackboard.Blackboard;
import bwapi.Game;

public class CreateMarine extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		game.drawTextScreen(100, 200, "Create marine");
		//TODO: Functionality to create marine
		return 0;
	}
}
