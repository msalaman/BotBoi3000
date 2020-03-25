package Routine;
import Blackboard.*;
import bwapi.Game;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
public abstract class Routine {
	 
    public enum RoutineState {
        Success,
        Failure,
        Running
    }
 
    protected RoutineState state;
 
    protected Routine() { }
 
    public void start() {
        this.state = RoutineState.Running;
    }
 
    public abstract void reset();
 
    public abstract void act(Blackboard blackboard);
 
    protected void succeed() {
        this.state = RoutineState.Success;
    }
 
    protected void fail() {
        this.state = RoutineState.Failure;
    }
 
    public boolean isSuccess() {
        return state.equals(RoutineState.Success);
    }
 
    public boolean isFailure() {
        return state.equals(RoutineState.Failure);
    }
 
    public boolean isRunning() {
        return state.equals(RoutineState.Running);
    }
 
    public RoutineState getState() {
        return state;
    }
    
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
}
