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
		if(supplyTotal >= 50) {
			succeed();
			return;
		}
		if(supplyUsed<=10) {
			blackboard.game.drawTextScreen(10, 160,"Stage 1");
			if(blackboard.commandCenters.get(0).getTrainingQueue().isEmpty()) {
				blackboard.commandCenters.get(0).build(UnitType.Terran_SCV);
			}
			if(blackboard.supplyDepots.size() < 3 && blackboard.minerals >=100) {
				for (Unit worker : blackboard.workers) {
					TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Supply_Depot,
							blackboard.game.self().getStartLocation());
					if (buildTile != null) {
						worker.build(UnitType.Terran_Supply_Depot, buildTile);
						break;
					}
				}
			}
		} else if(supplyTotal < 50){
			blackboard.game.drawTextScreen(10, 160,"Stage 2");
			if(blackboard.barracks.size() < 3 && blackboard.minerals >= 150) {
				blackboard.game.drawTextScreen(10, 170,"Stage 2.1");
				for (Unit worker : blackboard.workers) {
					TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Barracks,
							blackboard.game.self().getStartLocation());
					if (buildTile != null) {
						worker.build(UnitType.Terran_Barracks, buildTile);
						break;
					}
				}
			} 
			if(blackboard.barracks.size()>0) {
				blackboard.game.drawTextScreen(10, 170,"Stage 2.2");
				for(Unit barrack : blackboard.barracks) {
					if(barrack.getTrainingQueue().isEmpty() && blackboard.minerals >= 50) {
						barrack.build(UnitType.Terran_Marine);
					}
				}
			}
			if(supplyUsed >= 15 && supplyTotal < 50) {
				blackboard.game.drawTextScreen(10, 160,"Stage 2.3");
				if(blackboard.barracks.size() < 5 && blackboard.minerals >= 150) {
					blackboard.game.drawTextScreen(10, 170,"Stage 2.1");
					Unit worker = blackboard.workers.get(0);
					if(blackboard.barracks.size()<3) {
						TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Barracks,
								blackboard.game.self().getStartLocation());
						if (buildTile != null) {
							worker.build(UnitType.Terran_Barracks, buildTile);
						}
					} else {
						TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Supply_Depot,
								blackboard.game.self().getStartLocation());
						if (buildTile != null) {
							worker.build(UnitType.Terran_Supply_Depot, buildTile);
						}
					}
				}
				if(blackboard.barracks.size() >=3) {
					for(Unit barrack : blackboard.barracks) {
						if(barrack.getTrainingQueue().isEmpty() && blackboard.minerals >= 50) {
							barrack.build(UnitType.Terran_Marine);
						}
					}
				}
				for(Unit commandCenter : blackboard.commandCenters) {
					if(commandCenter.getTrainingQueue().isEmpty() && blackboard.minerals >= 50) {
						commandCenter.build(UnitType.Terran_SCV);
					}
				}
				blackboard.game.drawTextScreen(10, 170,"Stage 3 end");
			}
		} /*else if(supplyUsed >= 16 && supplyTotal <34) {
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
		}*/ else if(supplyUsed < 39){
			if(blackboard.barracks.size() < 6 && blackboard.minerals >= 150) {
				blackboard.game.drawTextScreen(10, 170,"Stage 2.1");
				for (Unit worker : blackboard.workers) {
					if(!worker.canBuild()) {
						continue;
					}
					TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Barracks,
							blackboard.game.self().getStartLocation());
					if (buildTile != null) {
						worker.build(UnitType.Terran_Barracks, buildTile);
						break;
					}
				}
			} 
			blackboard.game.drawTextScreen(10, 160,"Stage 5");
			if(blackboard.barracks.size() >= 2) {
				for(Unit barrack : blackboard.barracks) {
					if(barrack.getTrainingQueue().isEmpty() && blackboard.minerals >= 50) {
						barrack.build(UnitType.Terran_Marine);
					}
				}
			}

			for(Unit commandCenter : blackboard.commandCenters) {
				if(commandCenter.getTrainingQueue().isEmpty() && blackboard.minerals >= 50 && blackboard.workers.size() < 12) {
					commandCenter.build(UnitType.Terran_SCV);
				}
			}
		} else {
			succeed();
		}	
		return;
	}	
}
