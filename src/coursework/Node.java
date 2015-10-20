package coursework;

public class Node {
	private State	state;
	private Node	leftNode, rightNode, upNode, downNode, parent;
	private Boolean	visited	= false;
	private Tree	tree;

	public Node(State state, Node parent, Tree tree) {
		this.tree = tree;
		this.state = state;
		this.parent = parent;
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
		if (!tree.stateExists(leftState)) {
			leftNode = new Node(leftState, this, tree);
			return true;
		}
		return false;
	}

	public boolean generateRightNode() {
		State rightState = new State(state, 'R', tree);
		if (!tree.stateExists(rightState)) {
			rightNode = new Node(rightState, this, tree);
			return true;
		}
		return false;
	}

	public boolean generateUpNode() {
		State upState = new State(state, 'U', tree);
		if (!tree.stateExists(upState)) {
			upNode = new Node(upState, this, tree);
			return true;
		}
		return false;
	}

	public boolean generateDownNode() {
		State downState = new State(state, 'D', tree);
		if (!tree.stateExists(downState)) {
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
