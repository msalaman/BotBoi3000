package Routine;

import Blackboard.*;

import java.util.Enumeration;
import java.util.List;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;

public class StratZergScout extends Routine{
	@Override
	public void start() {
		super.start();
	}
	
	public void reset() { }
	
	public StratZergScout(Blackboard blackboard) {
		super();
	}
	
	public void act(Blackboard blackboard) {
		blackboard.game.drawTextScreen(10,210,"Scouting");
		List<Unit> workers = blackboard.workers;
		if (workers.size() > 7 && blackboard.searcher == null) {
			blackboard.searcher = workers.get(7);
		}

		if (blackboard.searcher != null && blackboard.searcher.isGatheringMinerals() && blackboard.searchingScv < blackboard.baseLocations.size()
				&& blackboard.searchingTimeout % 10 == 0) {
			blackboard.searcher.move(blackboard.baseLocations.get(blackboard.searchingScv).getPosition());
			blackboard.searchingScv++;
		}
	}
}

