package routine;

import java.util.Enumeration;
import java.util.List;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Position;
import bwapi.Race;
import bwapi.Unit;

public class SingleBuildingPatrol extends Routine {
	@Override
	/*
	 * 
	 */
	public int act(Game game, Blackboard blackboard) {
		game.drawTextScreen(100, 200, "Patrol last building");
		// Send idle troops to patrol the last building
		List<Unit> buildings = blackboard.getBuildings();
		if(!buildings.isEmpty()) {
	        Enumeration<String> e = blackboard.army.keys();
	        Position pos = buildings.get(0).getPosition();
	        while(e.hasMoreElements()) {
	            String k = e.nextElement();
	            List<Unit> troops = blackboard.army.get(k);
	        	for (Unit troop : troops) {
	        		if(troop.isIdle())
	        		troop.move(pos);
	    		}
	        }
		}
		return -1;
	}
}
