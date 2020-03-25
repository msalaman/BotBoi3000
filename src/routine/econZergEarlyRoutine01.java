package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.UnitType;

public class econZergEarlyRoutine01 extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		int supply = blackboard.getSupplyUsed();
		if(supply < 10) {
			game.drawTextScreen(50,300, "Early stage 1: focus on SCVs");
			blackboard.commandCenters.get(0).build(UnitType.AllUnits.Terran_SCV);
			return 0;
		} else {
			return -1;
		}
	}
}
