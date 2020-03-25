package routine;

import java.util.ArrayList;
import java.util.List;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Race;
import bwta.BWTA;
import bwta.BaseLocation;

public class SendSCVScout extends Routine {
	@Override
	/*
	 * 
	 */
	public int act(Game game, Blackboard blackboard) {
		List<BaseLocation> baseLocations = BWTA.getBaseLocations();
		game.drawTextScreen(100, 200, "Send SCV to scout");
		
		blackboard.workers.get(0).move(baseLocations.get(baseLocations.size()).getPosition());
		return -1;
	}
}
