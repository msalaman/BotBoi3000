package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Unit;
import bwapi.UnitType;

public class econZergLateRoutine01 extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		game.drawTextScreen(100,200, "Late stage 1: Vultures and tanks");
		game.drawTextScreen(100,200, "This is the last stage and where the econ will stay");
		for (Unit building : blackboard.buildings) {
			if (building.getType() == UnitType.Terran_Factory) {
				building.build(UnitType.AllUnits.Terran_Vulture);
				building.build(UnitType.AllUnits.Terran_Siege_Tank_Siege_Mode);
			}
		}
		return -1;
	}
}
