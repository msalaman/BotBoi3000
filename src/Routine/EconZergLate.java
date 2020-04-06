package Routine;

import Blackboard.Blackboard;
import bwapi.TilePosition;
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
		blackboard.game.drawTextScreen(100,200, "Late stage 1: Vultures and tanks");
		blackboard.game.drawTextScreen(100,200, "This is the last stage and where the econ will stay");
		if(blackboard.getSupplyTotal()/2 < 200) {
			fail(); //TODO: change if not early
			//If ratio wrong in early, then it means that we shouldn't be in late stage
			return;
		}
		//TODO: Logic and exec of building stuff and troops
		
		for (Unit building : blackboard.buildings) {
			
			//if (building.getType() == UnitType.Terran_Factory) {
				//building.build(UnitType.Terran_Vulture);
				//building.build(UnitType.Terran_Siege_Tank_Siege_Mode);
			//}
		}
		for (Unit barrack : blackboard.barracks) {
			if (barrack.getTrainingQueue().isEmpty()) {
				if(blackboard.army.get("medics").size() < 7) {
					barrack.build(UnitType.Terran_Medic);
				}
				else {
					barrack.build(UnitType.Terran_Marine);
				}
					
			}
		}
		for (Unit worker : blackboard.workers) {
			TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Supply_Depot,
					blackboard.game.self().getStartLocation());
			// and, if found, send the worker to build it (and leave
			// others
			// alone - break;)
			if (buildTile != null) {
				worker.build(UnitType.Terran_Supply_Depot, buildTile);
			}
		}
	}
}