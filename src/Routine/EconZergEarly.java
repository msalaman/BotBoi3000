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
		blackboard.game.drawTextScreen(10,140, "Supply Used: " + blackboard.getSupplyUsed());
		blackboard.game.drawTextScreen(10,150, "Supply Total: " + blackboard.getSupplyTotal());
		int supplyTotal = blackboard.getSupplyTotal()/2;
		int supplyUsed = blackboard.getSupplyUsed()/2;
		if(supplyTotal >= 39) {
			succeed();
			return;
		}
		if(supplyUsed<=10 && supplyTotal<11) {
			blackboard.game.drawTextScreen(10, 160,"Stage 1");
			if(blackboard.commandCenters.get(0).getTrainingQueue().isEmpty()) {
				blackboard.commandCenters.get(0).build(UnitType.Terran_SCV);
			}
			if(blackboard.supplyDepots.size() == 0 && blackboard.minerals >=100) {
				for (Unit worker : blackboard.workers) {
					TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Supply_Depot,
							blackboard.game.self().getStartLocation());
					if (buildTile != null) {
						worker.build(UnitType.Terran_Supply_Depot, buildTile);
						break;
					}
				}
			}
		} else if(supplyUsed<16){
			blackboard.game.drawTextScreen(10, 160,"Stage 2");
			if(blackboard.barracks.size() == 0 && blackboard.minerals >= 150) {
				blackboard.game.drawTextScreen(10, 170,"Stage 2.1");
				for (Unit worker : blackboard.workers) {
					TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Barracks,
							blackboard.game.self().getStartLocation());
					if (buildTile != null) {
						worker.build(UnitType.Terran_Barracks, buildTile);
						break;
					}
				}
			} else if(supplyUsed < 15 && blackboard.barracks.size()>0) {
				blackboard.game.drawTextScreen(10, 160,"Stage 3");
				if(blackboard.barracks.get(0).getTrainingQueue().isEmpty()) {
					blackboard.barracks.get(0).build(UnitType.Terran_Marine);
				}
			} else if(supplyUsed == 15 && supplyTotal < 19) {
				
				for (Unit worker : blackboard.workers) {
					TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Supply_Depot,
							blackboard.game.self().getStartLocation());
					if (buildTile != null) {
						worker.build(UnitType.Terran_Supply_Depot, buildTile);
						break;
					}
				}
				if(blackboard.barracks.get(0).getTrainingQueue().isEmpty()) {
					blackboard.barracks.get(0).build(UnitType.Terran_Marine);
				}
			} else {
				if(blackboard.barracks.get(0).getTrainingQueue().isEmpty()) {
					blackboard.barracks.get(0).build(UnitType.Terran_Marine);
				}
				return;
			}
			
			return;
		}
		/*
		blackboard.game.drawTextScreen(10,160, "Supply Total: " + blackboard.getSupplyTotal());
		if(blackboard.getSupplyTotal()/2 >= 39) {
			succeed(); //TODO: change if not early
			//If ratio wrong in early, then it means that early is completed
			return;
		}
		int ii=0;
		for(Unit u : blackboard.buildings) {
			if(u.getType() == UnitType.Terran_Supply_Depot) {
				ii++;
			}
		}
		if(ii>5) {
			succeed();
			return;
		}
		//TODO: Logic and exec of building stuff and troops

		int supply = blackboard.getSupplyTotal()/2;
		if(supply < 10) {

			blackboard.commandCenters.get(0).build(UnitType.Terran_SCV);
		}
		if(supply < 51 && supply > 19) {
			blackboard.game.drawTextScreen(100,200, "Early stage 2: build Barracks, Marines and Supply Depots");
			
			Unit bunkerBuilder = blackboard.workers.get(0);
			TilePosition buildTile = getBuildTile(blackboard.game, bunkerBuilder, UnitType.Terran_Barracks,
					bunkerBuilder.getTilePosition());
			if (buildTile != null) {
				if (bunkerBuilder.exists()) {
					bunkerBuilder.build(UnitType.Terran_Barracks, buildTile);
				}
			}
			
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
					buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Supply_Depot,
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
		if(supply > 50 && supply < 79) {
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
		*/
	}	
}
