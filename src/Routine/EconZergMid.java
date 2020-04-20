package Routine;

import Blackboard.Blackboard;
import bwapi.TechType;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;

public class EconZergMid extends Routine {
	@Override
	public void start() {
		super.start();
	}

	public void reset() {
	}

	public EconZergMid(Blackboard blackboard) {
		super();
	}

	public void act(Blackboard blackboard) {
		blackboard.game.drawTextScreen(100, 200, "Mid stage 1: Cut SVC production. Make Medics and Marines");
		if (blackboard.getSupplyTotal() / 2 >= 200) {
			succeed(); // TODO: change if not mid
			// If ratio wrong in mid, then it means that mid is completed
			return;
		} else if (blackboard.getSupplyTotal() / 2 < 50) {
			fail();
			// This means that the supply depots were destroyed and econ must start again
		}
		// TODO: Logic and exec of building stuff and troops
		int supplyTotal = blackboard.getSupplyTotal()/2;
		int supplyUsed = blackboard.getSupplyUsed() / 2;
		if (supplyUsed < 100) {
			blackboard.game.drawTextScreen(100, 210, "Mid stage 1: Cut SVC production. Make Medics and Marines");
			for (Unit barrack : blackboard.barracks) {
				if (barrack.getTrainingQueue().isEmpty()) {
					if (blackboard.army.get("marines").size() < 7) {
						barrack.build(UnitType.Terran_Marine);
					} else if (blackboard.army.get("medics").size() < 7 && blackboard.academies.size() > 0) {
						barrack.build(UnitType.Terran_Medic);
					} else {
						barrack.build(UnitType.Terran_Marine);
					}

				}
			}
			if(blackboard.academies.size() < 1 && blackboard.gas > 25) {
				for(Unit worker : blackboard.workers) {
					if(!worker.canBuild()) {
						continue;
					}
					TilePosition buildTile = getBuildTile(blackboard.game, worker, UnitType.Terran_Academy,
							worker.getTilePosition());
					if (buildTile != null) {
						worker.build(UnitType.Terran_Academy, buildTile);
					}
					break;
				}
			}
			if(blackboard.academies.size() > 0 && blackboard.gas >= 100) {
				blackboard.academies.get(0).research(TechType.Stim_Packs);
			}

			Unit bunkerBuilder = blackboard.workers.get(0);
			
			if (bunkerBuilder.exists() && blackboard.supplyDepots.size() < 9 && blackboard.barracks.size() >= 3) {
				TilePosition buildTile = getBuildTile(blackboard.game, bunkerBuilder, UnitType.Terran_Supply_Depot,
						bunkerBuilder.getTilePosition());
				if (buildTile != null) {
					bunkerBuilder.build(UnitType.Terran_Supply_Depot, buildTile);
				}
			} else if (bunkerBuilder.exists() && blackboard.barracks.size() < 6) {
				TilePosition buildTile = getBuildTile(blackboard.game, bunkerBuilder, UnitType.Terran_Barracks,
						bunkerBuilder.getTilePosition());
				if (buildTile != null) {
					bunkerBuilder.build(UnitType.Terran_Barracks, buildTile);
				}

			} else if (bunkerBuilder.exists() && blackboard.supplyDepots.size() < 13 && blackboard.barracks.size() >= 6) {
				TilePosition buildTile = getBuildTile(blackboard.game, bunkerBuilder, UnitType.Terran_Supply_Depot,
						bunkerBuilder.getTilePosition());
				if (buildTile != null) {
					bunkerBuilder.build(UnitType.Terran_Supply_Depot, buildTile);
				}
			}
			bunkerBuilder = blackboard.workers.get(1);
			if (bunkerBuilder.exists() && blackboard.refineries.size() < 1) {
				TilePosition buildTile = getBuildTile(blackboard.game, bunkerBuilder, UnitType.Terran_Refinery,
						bunkerBuilder.getTilePosition());
				if (buildTile != null) {
					bunkerBuilder.build(UnitType.Terran_Refinery, buildTile);
				}
			} else if (bunkerBuilder.exists() && blackboard.academies.size() < 1) {
				blackboard.workers.get(4).gather(blackboard.refineries.get(0), false);
				TilePosition buildTile = getBuildTile(blackboard.game, bunkerBuilder, UnitType.Terran_Academy,
						bunkerBuilder.getTilePosition());
				if (buildTile != null) {
					bunkerBuilder.build(UnitType.Terran_Academy, buildTile);
				}
			} 
			

		}
		if (supplyUsed < 200 && supplyUsed > 69) {
			Unit bunkerBuilder = blackboard.workers.get(0);
			for (Unit barrack : blackboard.barracks) {
				if (barrack.getTrainingQueue().isEmpty()) {
					if (blackboard.army.get("marines").size() < 7) {
						barrack.build(UnitType.Terran_Marine);
					} else if (blackboard.army.get("medics").size() < 7 && blackboard.academies.size() > 0) {
						barrack.build(UnitType.Terran_Medic);
					} else {
						barrack.build(UnitType.Terran_Marine);
					}

				}
			}
			blackboard.game.drawTextScreen(100, 200, "Mid stage 2: Build turrets and another sim city");
			blackboard.game.drawTextScreen(100, 210, "Marines:" + blackboard.getArmyUnits("marines").size());
			if (bunkerBuilder.exists() && supplyTotal < 200) {
				TilePosition buildTile = getBuildTile(blackboard.game, bunkerBuilder, UnitType.Terran_Supply_Depot,
						bunkerBuilder.getTilePosition());
				if (buildTile != null) {
					bunkerBuilder.build(UnitType.Terran_Supply_Depot, buildTile);
				}
			}
		}
	}
}