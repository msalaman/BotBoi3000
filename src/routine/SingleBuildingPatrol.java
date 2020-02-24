package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Race;

public class SingleBuildingPatrol extends Routine {
	@Override
	/*
	 * 
	 */
	public int act(Game game, Blackboard blackboard) {
		game.drawTextScreen(100, 200, "Patrol last building");
		//TODO: Send idle troops to patrol the last building
		return 1;
	}
}
