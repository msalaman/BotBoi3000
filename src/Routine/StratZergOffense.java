package Routine;

import Blackboard.*;

import java.util.Enumeration;
import java.util.List;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import bwta.BWTA;

public class StratZergOffense extends Routine{
	@Override
	public void start() {
		super.start();
	}
	
	public void reset() { }
	
	public StratZergOffense() {
		super();
	}
	
	public void act(Blackboard blackboard) {
		blackboard.game.drawTextScreen(10,210,"Offense against Zerg");
		List<Unit> marines = blackboard.getArmyUnits("marines");
		int k = 0;
		if(marines.size() < 40) {
			fail();
		}
		for (Unit marine : marines) {
			if (marine.isAttacking() == false && marine.isMoving() == false) {
				if (marines.size() > 50 || blackboard.selectedStrategy == blackboard.getStrategy("AttackAtAllCost")) {
					if (marines.size() > 40) {
						blackboard.selectedStrategy = blackboard.getStrategy("AttackAtAllCost");
					} else {
						blackboard.selectedStrategy = blackboard.getStrategy("WaitFor50");
					}
					if (blackboard.enemyBuildingMemory.isEmpty()) {
						marine.attack(blackboard.allLocations.get(k % blackboard.allLocations.size()).getPosition());
					} else {
						for (Position p : blackboard.enemyBuildingMemory) {
							marine.attack(p);
						}
					}

					if (marines.size() > 70) {
						if (k < blackboard.allLocations.size()) {
							marine.attack(blackboard.allLocations.get(k).getPosition());
						}
					}
				} else {
					Position newPos;

					if (blackboard.bunker != null) {
						List<TilePosition> path = BWTA.getShortestPath(blackboard.bunker.getTilePosition(),
								BWTA.getStartLocation(blackboard.game.self()).getTilePosition());

						if (path.size() > 1) {
							newPos = path.get(1).toPosition();
						} else {
							newPos = BWTA.getNearestChokepoint(marine.getPosition()).getCenter();
						}
					} else {
						newPos = BWTA.getNearestChokepoint(marine.getPosition()).getCenter();
					}

					marine.attack(newPos);
				}
			}
			k++;

			if (blackboard.bunker != null && blackboard.bunker.getLoadedUnits().size() < 4 && k < 5) {
				marine.load(blackboard.bunker);
			}
			
			if (blackboard.workerAttacked != null){
				marine.attack(blackboard.workerAttacked);
			}
		}

	}
}

