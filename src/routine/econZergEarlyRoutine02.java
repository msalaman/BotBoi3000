package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Unit;
import bwapi.UnitType;

public class econZergEarlyRoutine02 extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		int supply = blackboard.getSupplyUsed();
		if(supply < 26 && supply > 9) {
			game.drawTextScreen(100,200, "Early stage 2: focus on marines and SVCs");
			for (Unit barrack : blackboard.barracks) {
				if (barrack.getTrainingQueue().isEmpty()) {
					barrack.build(UnitType.AllUnits.Terran_Marine);
				}
			}
			return 0;
		} else {
			return -1;
		}
	}
}