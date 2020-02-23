package routine;

import blackboard.Blackboard;
import bwapi.Game;

public class PrintTest extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		game.drawTextScreen(100, 200, "We've implemented a Rountine");
		return 0;
	}
}
