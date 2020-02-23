package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Race;

public class CheckMarineSize extends Routine {
	@Override
	/*
	 * 
	 */
	public int act(Game game, Blackboard blackboard) {
		int marineArmySize = blackboard.getArmyUnits("marines").size();
		if(marineArmySize == 0) { //No marines
			game.drawTextScreen(100,200, "There are no marines!");
			return 0;
		}
	    else { //There are marines
			game.drawTextScreen(100, 200, "We have marines!");
			return 1;
		}
	}
}
