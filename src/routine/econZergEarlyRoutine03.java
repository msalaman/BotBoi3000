package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;

public class econZergEarlyRoutine03 extends Routine {
	public TilePosition getBuildTile(Game game, Unit builder, UnitType buildingType, TilePosition aroundTile) {
		int cyclesForSearching = 0;
		TilePosition ret = null;
		int maxDist = 3;
		int stopDist = 40;

		// Refinery, Assimilator, Extractor
		if (buildingType.isRefinery()) {
			for (Unit n : game.neutral().getUnits()) {
				cyclesForSearching++;
				if ((n.getType() == UnitType.Resource_Vespene_Geyser)
						&& (Math.abs(n.getTilePosition().getX() - aroundTile.getX()) < stopDist)
						&& (Math.abs(n.getTilePosition().getY() - aroundTile.getY()) < stopDist))
					return n.getTilePosition();
			}
		}

		while ((maxDist < stopDist) && (ret == null)) {
			for (int i = aroundTile.getX() - maxDist; i <= aroundTile.getX() + maxDist; i++) {
				for (int j = aroundTile.getY() - maxDist; j <= aroundTile.getY() + maxDist; j++) {
					if (game.canBuildHere(new TilePosition(i, j), buildingType, builder, false)) {
						// units that are blocking the tile
						boolean unitsInWay = false;
						for (Unit u : game.getAllUnits()) {
							cyclesForSearching++;
							if (u.getID() == builder.getID())
								continue;
							if ((Math.abs(u.getTilePosition().getX() - i) < 4)
									&& (Math.abs(u.getTilePosition().getY() - j) < 4))
								unitsInWay = true;
						}
						if (!unitsInWay) {
							cyclesForSearching++;
							return new TilePosition(i, j);
						}
						// creep for Zerg
						if (buildingType.requiresCreep()) {
							boolean creepMissing = false;
							for (int k = i; k <= i + buildingType.tileWidth(); k++) {
								for (int l = j; l <= j + buildingType.tileHeight(); l++) {
									cyclesForSearching++;
									if (!game.hasCreep(k, l))
										creepMissing = true;
									break;
								}
							}
							if (creepMissing)
								continue;
						}
					}
				}
			}
			maxDist += 2;
		}

		if (ret == null)
			game.printf("Unable to find suitable build position for " + buildingType.toString());
		return ret;
	}
	@Override
	public int act(Game game, Blackboard blackboard) {
		int supply = blackboard.getSupplyUsed();
		if(supply > 25 && supply < 39) {
			game.drawTextScreen(100,200, "Early stage 3: build up Barraks and Refinery");
			Unit bunkerBuilder = blackboard.workers.get(0);
			TilePosition buildTile = getBuildTile(game, bunkerBuilder, UnitType.Terran_Barracks,
					bunkerBuilder.getTilePosition());
			if (buildTile != null) {
				if (bunkerBuilder.exists()) {
					bunkerBuilder.build(UnitType.Terran_Bunker, buildTile);
				}
			}
			int i = 1;
			for (Unit worker : blackboard.workers) {
				if (worker.isGatheringMinerals()) {
					if (blackboard.minerals >= 150 * i && blackboard.barracks.size() < 6) {
						buildTile = getBuildTile(game, worker, UnitType.Terran_Barracks, game.self().getStartLocation());
						if (buildTile != null) {
							worker.build(UnitType.Terran_Barracks, buildTile);
						}
					}
				}

				i++;
			}
			return 0;
		} else {
			return -1;
		}

	}
}
