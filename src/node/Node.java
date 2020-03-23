package node;

import java.util.ArrayList;
import java.util.List;

import blackboard.Blackboard;
import bwapi.Game;
import routine.Routine;

public class Node {
	public Blackboard blackboard;
	public Game game;
	public Routine action;
	protected int state=0; // 0 - Start, 1 - Done
	protected Node parent;
	protected List<Node> children = new ArrayList<Node>();
	
	public void setUp(Game game, Blackboard blackboard) {
		this.game = game;
		this.blackboard = blackboard;
	}
	
	public Node select(){
		System.out.println("ERROR: Node.select(). This should never show. overridden parent function of node");
		return null;
	}
	public void executeAll() {
		System.out.println("ERROR: Node.executeAll"
				+ "(). This should never show. overridden parent function of node");
		return;
	}
	
	public void setBlackboard(Blackboard blackboard) {
		this.blackboard = blackboard;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	public void setState(int s) {
		state = s;
	}
	
	public int getState() {
		return state;
	}
	
	public void setParent(Node p) {
		parent = p;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setChildren(List<Node> c) {
		children = c;
	}
	
	public void addChild(Node n) {
		children.add(n);
	}

	public List<Node> getChildren() {
		return children;
	}
	
	public int execute() {
		return action.act(game, blackboard);
	}
	
}
