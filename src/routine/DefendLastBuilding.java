package routine;

import blackboard.Blackboard;
import bwapi.Game;

public class DefendLastBuilding extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		game.drawTextScreen(100, 200, "Defend last building!");
		//TODO: Send all troops to defend the last building
		return -1;
	}
}
