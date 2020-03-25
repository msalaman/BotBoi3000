package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Unit;
import bwapi.UnitType;

public class econZergMidRoutine01 extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		int supply = blackboard.getSupplyUsed();
		if(supply < 44) {
			game.drawTextScreen(100,200, "Mid stage 1: Cut SVC production. Make Medics");
			for (Unit barrack : blackboard.barracks) {
				if (barrack.getTrainingQueue().isEmpty()) {
					barrack.build(UnitType.AllUnits.Terran_Medic);
				}
			}
			return 0;
		} else {
			return -1;
		}
	}
}
