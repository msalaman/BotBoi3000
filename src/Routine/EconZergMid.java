package Routine;

import Blackboard.Blackboard;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;

public class EconZergMid extends Routine {
	@Override
	public void start() {
		super.start();
	}
	
	public void reset() { }
	
	public EconZergMid(Blackboard blackboard) {
		super();
	}
	
	public void act(Blackboard blackboard) {
		/*if(The ratio of troops is high) {
			succeed(); //TODO: change if not early
			//If ratio wrong in early, then it means that early is completed
			return;
		} else if(The ratio of troops is low){
			fail();
			//This means that the supply depots were destroyed and econ must start again
		}*/
		//TODO: Logic and exec of building stuff and troops
		int supply = blackboard.getSupplyUsed();
		if(supply < 44) {
			blackboard.game.drawTextScreen(100,200, "Mid stage 1: Cut SVC production. Make Medics");
			for (Unit barrack : blackboard.barracks) {
				if (barrack.getTrainingQueue().isEmpty()) {
					barrack.build(UnitType.AllUnits.Terran_Medic);
				}
			}
		}
		if(supply < 61 && supply > 43) {
			blackboard.game.drawTextScreen(100,200, "Mid stage 2: Build turrets and another sim city");
			int i = 1;
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
}