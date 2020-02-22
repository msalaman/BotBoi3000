package routine;

import bwapi.Game;

public class PrintTest extends Routine {
	@Override
	public void act(Game game) {
		game.drawTextScreen(100, 200, "We've implemented a Rountine");
	}
}
