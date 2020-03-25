package routine;

import java.util.Enumeration;
import java.util.List;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;

public class DefendLastBuilding extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		game.drawTextScreen(100, 200, "Defend last building!");
		List<Unit> buildings = blackboard.getBuildings();
		if(!buildings.isEmpty()) {
	        Enumeration<String> e = blackboard.army.keys();
	        Position pos = buildings.get(0).getPosition();
	        while(e.hasMoreElements()) {
	            String k = e.nextElement();
	            List<Unit> troops = blackboard.army.get(k);
	        	for (Unit troop : troops) {
	        		troop.move(pos);
	    		}
	        }
		}

		return -1;
	}
}
