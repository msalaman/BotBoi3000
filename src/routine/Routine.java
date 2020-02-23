package routine;

import blackboard.Blackboard;
import bwapi.Game;

public abstract class Routine {
	public abstract int act(Game game, Blackboard blackboard);
}
