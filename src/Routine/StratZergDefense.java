package Routine;

import Blackboard.*;

import java.util.Enumeration;
import java.util.List;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;

public class StratZergDefense extends Routine{
	@Override
	public void start() {
		super.start();
	}
	
	public void reset() { }
	
	public StratZergDefense(Blackboard blackboard) {
		super();
	}
	
	public void act(Blackboard blackboard) {
		blackboard.game.drawTextScreen(10,200,"Defense Zerg");
		List<Unit> buildings = blackboard.getBuildings();
		if(buildings.size() == 1) { // Only one building
			blackboard.game.drawTextScreen(100,200, "There is only one building!");
		
			if(blackboard.getBuildings().get(0).isUnderAttack()) { // Our one building is under attack
				blackboard.game.drawTextScreen(100,200, "Our last building is underattack");
				// Send troops to defend last building
				if(!(buildings.isEmpty())) {
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
			}
			else { //Our one building is not under attack
				if(!buildings.isEmpty()) { //send idle troops to patrol last building
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
			}
		}
	}
}

