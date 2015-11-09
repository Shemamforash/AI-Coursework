package coursework;

import java.util.ArrayList;

public class Node {
	private State	state;
	private Node	leftNode, rightNode, upNode, downNode, parent;
	private Boolean	visited	= false;
	private Tree	tree;
	private int heuristicEstimate = 0, height;
	
	public int getHeuristicEstimate(){
		return heuristicEstimate;
	}
	
	public void setHeuristicEstimate(int heuristicEstimate){
		this.heuristicEstimate = heuristicEstimate;
	}

	public Node(State state, Node parent, Tree tree) {
		this.tree = tree;
		this.state = state;
		this.parent = parent;
		heuristicEstimate = calculateHeuristic(state, tree.getFinalState());
		Node n = this;
		if (n != null) {
			while (n.getParent() != null) {
				++height;
				n = n.getParent();
			}
		}
	}

	public int getHeight(){
		return height;
	}
	
	private int calculateHeuristic(State aState, State bState) {
		ArrayList<CharacterPosition> aList = aState.getCharacterPositions();
		ArrayList<CharacterPosition> bList = bState.getCharacterPositions();
		int heuristicEstimate = 0;
		for (int i = 0; i < aList.size(); ++i) {
			for (int j = 0; j < bList.size(); ++j) {
				if (aList.get(i).getChar() == bList.get(j).getChar()) {
					int estimatedDistance = getEstimatedDistance(bList.get(j), aList.get(i));
					heuristicEstimate += estimatedDistance;
				}
			}
		}
		return heuristicEstimate;
	}

	private int getEstimatedDistance(CharacterPosition a, CharacterPosition b) {
		int xDiff = Math.abs(a.x() - b.x());
		int yDiff = Math.abs(a.y() - b.y());
		return xDiff + yDiff;
	}

	
	public State getState() {
		return state;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}

	public Boolean getVisited() {
		return visited;
	}

	public boolean generateLeftNode() {
		State leftState = new State(state, 'L', tree);
		if (!tree.checkState() || !tree.stateExists(leftState)) {
			leftNode = new Node(leftState, this, tree);
			return true;
		}
		return false;
	}

	public boolean generateRightNode() {
		State rightState = new State(state, 'R', tree);
		if (!tree.checkState() || !tree.stateExists(rightState)) {
			rightNode = new Node(rightState, this, tree);
			return true;
		}
		return false;
	}

	public boolean generateUpNode() {
		State upState = new State(state, 'U', tree);
		if (!tree.checkState() || !tree.stateExists(upState)) {
			upNode = new Node(upState, this, tree);
			return true;
		}
		return false;
	}

	public boolean generateDownNode() {
		State downState = new State(state, 'D', tree);
		if (!tree.checkState() || !tree.stateExists(downState)) {
			downNode = new Node(downState, this, tree);
			return true;
		}
		return false;
	}

	public Node getLeft() {
		return leftNode;
	}

	public Node getRight() {
		return rightNode;
	}

	public Node getUp() {
		return upNode;
	}

	public Node getDown() {
		return downNode;
	}

	public Node getParent() {
		return parent;
	}
}