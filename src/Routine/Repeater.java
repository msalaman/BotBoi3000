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
    	if(routine.getState() == null) {
    		routine.start();
    		return;
    	}
        if (routine.isFailure()) {
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
            routine.act(blackboard);
        }
    }
}
