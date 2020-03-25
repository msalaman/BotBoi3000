package Routine;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import Blackboard.*;

public class Sequence extends Routine {
	
	Routine routine_curr;
	Queue<Routine> routine_q = new LinkedList<Routine>();
	List<Routine> routine_list = new LinkedList<Routine>();
 
    public Sequence() {
        super();
        routine_curr = null;
    }
    
    public void addRoutine(Routine routine) {
    	routine_list.add(routine);
    }
    
    @Override
    public void start() {
        super.start();
        if(routine_list == null) {
        	//TODO: this means that there is no list and the Sequence fails to do anything
        	fail();
        	return;
        }
        routine_q.clear();
        for(Routine r : routine_list) {
        	routine_q.add(r);
        }
        routine_curr = routine_q.poll();
        routine_curr.start();
    }
 
    public void reset() {
    	routine_q.clear();
        for(Routine r : routine_list) {
        	r.reset();
        	routine_q.add(r);
        }
        routine_curr = null;
    }
 
    @Override
    public void act(Blackboard blackboard) {
    	if(routine_curr == null) {
    		start();
    		return;
    	}
    	if(routine_curr.getState() == null) {
    		routine_curr.start();
    		return;
    	}
        if (routine_curr.isFailure()) {
            fail();
        } else if (routine_curr.isSuccess()) {
        	if(routine_q.peek() == null) {
        		succeed();
        	} else {
        		routine_curr = routine_q.poll();
        		routine_curr.start();
        	}
        } else if (routine_curr.isRunning()) {
            routine_curr.act(blackboard);
        }
    }
}
