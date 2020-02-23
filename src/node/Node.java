package node;

import java.util.List;

import blackboard.Blackboard;
import bwapi.Game;
import routine.Routine;

public class Node {
	public Blackboard blackboard;
	public Game game;
	public Routine action;
	private int state;
	private Node parent;
	private List<Node> children;
	
	public void setBlackboard(Blackboard blackboard) {
		this.blackboard = blackboard;
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
		getChildren().add(n);
	}

	public List<Node> getChildren() {
		return children;
	}
	
	public int execute() {
		return action.act(game, blackboard);
	}
	
}
