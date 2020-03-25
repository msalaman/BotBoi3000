package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Unit;
import bwapi.UnitType;

public class CreateMarine extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		game.drawTextScreen(100, 200, "Create marine");
		for (Unit barrack : blackboard.barracks) {
			if (barrack.getTrainingQueue().isEmpty()) {
				barrack.build(UnitType.AllUnits.Terran_Marine);
			}
		}
		return -1;
	}
}
