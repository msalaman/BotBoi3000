package Routine;

import Blackboard.Blackboard;
import bwapi.Unit;
import bwapi.UnitType;

public class EconZergLate extends Routine {
	@Override
	public void start() {
		super.start();
	}
	
	public void reset() { }
	
	public EconZergLate(Blackboard blackboard) {
		super();
	}
	
	public void act(Blackboard blackboard) {
		/*if(The ratio of troops is too low) {
			fail(); //TODO: change if not early
			//If ratio wrong in early, then it means that we shouldn't be in late stage
			return;
		} */
		//TODO: Logic and exec of building stuff and troops
		blackboard.game.drawTextScreen(100,200, "Late stage 1: Vultures and tanks");
		blackboard.game.drawTextScreen(100,200, "This is the last stage and where the econ will stay");
		for (Unit building : blackboard.buildings) {
			if (building.getType() == UnitType.Terran_Factory) {
				building.build(UnitType.AllUnits.Terran_Vulture);
				building.build(UnitType.AllUnits.Terran_Siege_Tank_Siege_Mode);
			}
		}
	}
}