package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Race;

public class CheckBiggerMarineSize extends Routine {
	@Override
	/*
	 * 
	 */
	public int act(Game game, Blackboard blackboard) {
		int marineArmySize = blackboard.getArmyUnits("marines").size();
		if(marineArmySize < 5) { //No marines
			game.drawTextScreen(100,200, "Less than 5 Marines");
			return 0;
		}
	    else { //There are marines
			game.drawTextScreen(100, 200, "e have 5 or more marines");
			return 1;
		}
	}
}
