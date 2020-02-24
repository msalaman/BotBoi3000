package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Race;

public class SingleBuildingCheck extends Routine {
	@Override
	/*
	 * 
	 */
	public int act(Game game, Blackboard blackboard) {
		int buildingsSize = blackboard.getBuildings().size();
		if(buildingsSize == 1) { // Only one building
			game.drawTextScreen(100,200, "There is only one building!");
			return 0;
		}
	    else { //There are many buildings
			game.drawTextScreen(100, 200, "There are mulitple buildings");
			return 1;
		}
	}
}
