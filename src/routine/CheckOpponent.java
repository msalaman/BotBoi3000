package routine;

import blackboard.Blackboard;
import bwapi.Game;
import bwapi.Race;

public class CheckOpponent extends Routine {
	@Override
	/*
	 * For future development, make sure to have enemy race as a element on blackboard. This
	 * will allow us to check in the case that the opposing bot is a given race
	 * 
	 * TODO: MAKE SURE TO CHANGE IT SO IT ISN'T JUST CHOOSING ZERG!!!!
	 */
	public int act(Game game, Blackboard blackboard) {
		Race enemyRace = game.enemy().getRace();
		if(enemyRace == Race.Zerg) {
			game.drawTextScreen(100,200, "The enemy is a Zerg");
			return 0;
		} else if (enemyRace == Race.Protoss) {
			game.drawTextScreen(100, 200, "The enemy is a Protoss");
			//TODO: CHANGE THE RETURN VALUE!!!!
			return 0;
		} else if (enemyRace== Race.Terran) {
			game.drawTextScreen(100,200, "The enemy is a Terran");
			//TODO: CHANGE THE RETURN VALUE!!!!
			return 0;
		} else { //assume Protoss for now, however, this should change in the future.
			game.drawTextScreen(100, 200, "The enemy race is unknown. Assume Protoss");
			//TODO: CHANGE THE RETURN VALUE!!!!
			return 2;
		}
	}
}
