package routine;

import blackboard.Blackboard;
import bwapi.Game;

public abstract class Routine {
	//private Game game;
	//private Blackboard blackboard;
	public abstract int act(Game game, Blackboard blackboard);	
}
