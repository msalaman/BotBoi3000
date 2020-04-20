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
		} else if (blackboard.getSupplyTotal() / 2 < 58) {
			fail();
			// This means that the supply depots were destroyed and econ must start again
		}
		// TODO: Logic and exec of building stuff and troops
		int supplyTotal = blackboard.getSupplyTotal()/2;
		int supplyUsed = blackboard.getSupplyUsed() / 2;
		if (supplyUsed < 58) {
			Unit bunkerBuilder = blackboard.workers.get(0);
			
			if (bunkerBuilder.exists() && blackboard.supplyDepots.size() < 7) {
				TilePosition buildTile = getBuildTile(blackboard.game, bunkerBuilder, UnitType.Terran_Supply_Depot,
						bunkerBuilder.getTilePosition());
				if (buildTile != null) {
					bunkerBuilder.build(UnitType.Terran_Supply_Depot, buildTile);
				}
			}
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
			} else if (bunkerBuilder.exists() && blackboard.barracks.size() < 6) {
				TilePosition buildTile = getBuildTile(blackboard.game, bunkerBuilder, UnitType.Terran_Barracks,
						bunkerBuilder.getTilePosition());
				if (buildTile != null) {
					bunkerBuilder.build(UnitType.Terran_Barracks, buildTile);
				}
			}
			
			for (Unit barrack : blackboard.barracks) {
				if (barrack.getTrainingQueue().isEmpty()) {
					if (blackboard.army.get("medics").size() < 7 && blackboard.academies.size() > 0) {
						barrack.build(UnitType.Terran_Medic);
					} else {
						barrack.build(UnitType.Terran_Marine);
					}

				}
			}
		}
		if (supplyUsed < 200 && supplyUsed > 57) {
			Unit bunkerBuilder = blackboard.workers.get(0);
			
			blackboard.game.drawTextScreen(100, 200, "Mid stage 2: Build turrets and another sim city");
			blackboard.game.drawTextScreen(100, 210, "Marines:" + blackboard.getArmyUnits("marines").size());
			if (bunkerBuilder.exists() && supplyTotal < 200) {
				TilePosition buildTile = getBuildTile(blackboard.game, bunkerBuilder, UnitType.Terran_Supply_Depot,
						bunkerBuilder.getTilePosition());
				if (buildTile != null) {
					bunkerBuilder.build(UnitType.Terran_Supply_Depot, buildTile);
				}
			}
			for (Unit barrack : blackboard.barracks) {
				if (barrack.getTrainingQueue().isEmpty()) {
					if (blackboard.army.get("medics").size() < 7 && blackboard.academies.size() > 0) {
						barrack.build(UnitType.Terran_Medic);
					} else {
						barrack.build(UnitType.Terran_Marine);
					}

				}
			}
		}
	}
}