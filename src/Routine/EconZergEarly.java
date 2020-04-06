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
		if(supplyUsed >= 39) {
			succeed();
			return;
		}
		if(supplyUsed<=10) {
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
		} else if(supplyUsed<16 && supplyUsed >10){
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
				blackboard.game.drawTextScreen(10, 170,"Stage 2.2");
				for(Unit barrack : blackboard.barracks) {
					if(barrack.getTrainingQueue().isEmpty() && blackboard.minerals >= 50) {
						barrack.build(UnitType.Terran_Marine);
					}
				}
			} else if(supplyUsed >= 15 && supplyTotal < 26) {
				blackboard.game.drawTextScreen(10, 160,"Stage 2.3");
				for (Unit worker : blackboard.workers) {
					TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Supply_Depot,
							blackboard.game.self().getStartLocation());
					if (buildTile != null) {
						worker.build(UnitType.Terran_Supply_Depot, buildTile);
						break;
					}
				}
				for(Unit barrack : blackboard.barracks) {
					if(barrack.getTrainingQueue().isEmpty() && blackboard.minerals >= 50) {
						barrack.build(UnitType.Terran_Marine);
					}
				}
				for(Unit commandCenter : blackboard.commandCenters) {
					if(commandCenter.getTrainingQueue().isEmpty() && blackboard.minerals >= 50) {
						commandCenter.build(UnitType.Terran_SCV);
					}
				}
				blackboard.game.drawTextScreen(10, 170,"Stage 3 end");
			}
		} else if(supplyUsed >= 16 && supplyTotal <34) {
			blackboard.game.drawTextScreen(10, 160,"Stage 4");
			for (Unit worker : blackboard.workers) {
				TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Supply_Depot,
						blackboard.game.self().getStartLocation());
				if (buildTile != null) {
					worker.build(UnitType.Terran_Supply_Depot, buildTile);
					break;
				}
			}

			for(Unit barrack : blackboard.barracks) {
				if(barrack.getTrainingQueue().isEmpty() && blackboard.minerals >= 50) {
					barrack.build(UnitType.Terran_Marine);
				}
			}
			for(Unit commandCenter : blackboard.commandCenters) {
				if(commandCenter.getTrainingQueue().isEmpty() && blackboard.minerals >= 50) {
					commandCenter.build(UnitType.Terran_SCV);
				}
			}

			blackboard.game.drawTextScreen(10, 170,"Stage 4 end");
		} else if(supplyUsed < 34){
			blackboard.game.drawTextScreen(10, 160,"Stage 5");
			for(Unit barrack : blackboard.barracks) {
				if(barrack.getTrainingQueue().isEmpty() && blackboard.minerals >= 50) {
					barrack.build(UnitType.Terran_Marine);
				}
			}

			for(Unit commandCenter : blackboard.commandCenters) {
				if(commandCenter.getTrainingQueue().isEmpty() && blackboard.minerals >= 50) {
					commandCenter.build(UnitType.Terran_SCV);
				}
			}
		} else {
			succeed();
		}	
		return;
	}	
}
