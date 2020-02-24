package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Race;

public class SendSCVScout extends Routine {
	@Override
	/*
	 * 
	 */
	public int act(Game game, Blackboard blackboard) {
		game.drawTextScreen(100, 200, "Send SCV to scout");
		//TODO: Send SCV to scout
		return 1;
	}
}
