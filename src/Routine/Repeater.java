package Routine;

import Blackboard.*;

public class Repeater extends Routine {
	 
    public Routine routine;
    //TODO: could be unnecessary
    //public Selector selector;
    private int times;
    private int originalTimes;
 
    public Repeater(Routine routine) {
        super();
        this.routine = routine;
        //this.selector = null;
        this.times = -1; // infinite
        this.originalTimes = times;
    }
 
    /*
    public Repeater(Selector selector) {
    	super();
    	this.routine = routine;
    	this.selector = null;
    	this.originalTimes = -1;
    	this.originalTimes = times;
    }
    */
    
    public Repeater(Routine routine, int times) {
        super();
        if (times < 1) {
            throw new RuntimeException("Can't repeat negative times.");
        }
        this.routine = routine;
        //this.selector = null;
        this.times = times;
        this.originalTimes = times;
    }
 
    /*
    public Repeater(Selector selector, int times) {
        super();
        if (times < 1) {
            throw new RuntimeException("Can't repeat negative times.");
        }
        this.routine = routine;
        this.selector = selector;
        this.times = times;
        this.originalTimes = times;
    }
    */
    
    @Override
    public void start() {
        super.start();
        
        //TODO: consider just using this
        //this.routine.start();
    }
 
    public void reset() {
        // reset counters
        this.times = originalTimes;
    }
 
    @Override
    public void act(Blackboard blackboard) {
    	if(routine == null) {
    		blackboard.game.drawTextScreen(10, 110, "The Selector doesn't exists and we're beat");
    	}
    	else {
    		blackboard.game.drawTextScreen(10, 110, "Please be a bit more Relieved");
    	}
    	if(routine.getState() == null) {
    		blackboard.game.drawTextScreen(10, 120, "finna call start on selector");
    		routine.start();
    		return;
    	}
        if (routine.isFailure()) {
        	blackboard.game.drawTextScreen(10, 140, "The Selector responded with failure and returned here.");
            fail();
        } else if (routine.isSuccess()) {
            if (times == 0) {
                succeed();
                return;
            }
            if (times > 0 || times <= -1) {
                times--;
                routine.reset();
                routine.start();
            }
        }
        if (routine.isRunning()) {
        	blackboard.game.drawTextScreen(10, 130, "Finna call .act on selector");
        	routine.act(blackboard);
            blackboard.game.drawTextScreen(150, 130, "Got back from .act on selector");
        }
    }
}
