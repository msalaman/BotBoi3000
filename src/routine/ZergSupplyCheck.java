package routine;

import blackboard.Blackboard;
import bwapi.Game;

public class ZergSupplyCheck extends Routine {
	@Override
	public int act(Game game, Blackboard blackboard) {
		int supply = blackboard.getSupplyTotal();
		if(supply<= 34) {
			game.drawTextScreen(100,200, "Early stages:");
			game.drawTextScreen(100,200, "Early stage 1: focus on SVCs");
			game.drawTextScreen(100,200, "Early stage 2: focus on marines and SVCs");
			game.drawTextScreen(100,200, "Early stage 3: build up Barraks and Refinery");
			return 0;
		} else if(supply <= 60) {
			game.drawTextScreen(100,200, "Mid stages:");
			game.drawTextScreen(100,200, "Mid stage 1: Cut SVC production. Make Medics");
			game.drawTextScreen(100,200, "Mid stage 2: Build turrets and another sim city");
			return 1;
		} else {
			game.drawTextScreen(100,200, "Late stage:");
			game.drawTextScreen(100,200, "Late stage 1: Vultures and tanks");
			return 2;
		}
	}
}
