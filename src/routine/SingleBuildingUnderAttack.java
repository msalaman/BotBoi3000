package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Race;

public class SingleBuildingUnderAttack extends Routine {
	@Override
	/*
	 * 
	 */
	public int act(Game game, Blackboard blackboard) {
		int buildingsSize = blackboard.getBuildings().size();
		if(blackboard.getBuildings().get(0).isUnderAttack()) { // Our one building is under attack
			game.drawTextScreen(100,200, "Our last building is underattack");
			return 0;
		}
	    else { // Our one building is safe
			game.drawTextScreen(100, 200, "Our last building is safe...for now");
			return 1;
		}
	}
}
