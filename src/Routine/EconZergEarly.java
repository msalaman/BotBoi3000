package Routine;

import Blackboard.*;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;

public class EconZergEarly extends Routine{
	@Override
	public void start() {
		super.start();
	}
	
	public void reset() { }
	
	public EconZergEarly(Blackboard blackboard) {
		super();
	}
	
	public void act(Blackboard blackboard) {
		blackboard.game.drawTextScreen(10,200,"Early Zerg");
		if(blackboard.getSupplyUsed() >= 39) {
			succeed(); //TODO: change if not early
			//If ratio wrong in early, then it means that early is completed
			return;
		}
		//TODO: Logic and exec of building stuff and troops
		int supply = blackboard.getSupplyUsed();
		if(supply < 10) {
			blackboard.commandCenters.get(0).build(UnitType.Terran_SCV);
		}
		if(supply < 26 && supply > 9) {
			for (Unit barrack : blackboard.barracks) {
				if (barrack.getTrainingQueue().isEmpty()) {
					barrack.build(UnitType.Terran_Marine);
				}
			}
			int i=0;
			for(Unit u : blackboard.buildings) {
				if(u.getType() == UnitType.Terran_Supply_Depot) {
					i++;
				}
			}
			if(i<5) {
				for (Unit worker : blackboard.workers) {
					TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Supply_Depot,
							blackboard.game.self().getStartLocation());
					// and, if found, send the worker to build it (and leave
					// others
					// alone - break;)
					if (buildTile != null) {
						worker.build(UnitType.Terran_Supply_Depot, buildTile);
						break;
					}
				}
			}
		}
		if(supply > 25 && supply < 39) {
			blackboard.game.drawTextScreen(100,200, "Early stage 3: build up Barraks and Refinery");
			Unit bunkerBuilder = blackboard.workers.get(0);
			TilePosition buildTile = getBuildTile(blackboard.game, bunkerBuilder, UnitType.Terran_Barracks,
					bunkerBuilder.getTilePosition());
			if (buildTile != null) {
				if (bunkerBuilder.exists()) {
					bunkerBuilder.build(UnitType.Terran_Barracks, buildTile);
				}
			}
			int i = 1;
			for (Unit worker : blackboard.workers) {
				if (worker.isGatheringMinerals()) {
					if (blackboard.minerals >= 150 * i && blackboard.barracks.size() < 6) {
						buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Barracks, blackboard.game.self().getStartLocation());
						if (buildTile != null) {
							worker.build(UnitType.Terran_Barracks, buildTile);
						}
					}
				}

				i++;
			}

		}
	}	
}
