package coursework;

public class Node {
	private State	state;
	private Node	parent, upNode, downNode, leftNode, rightNode;
	private Boolean	visited	= false;
	private Tree	tree;
<<<<<<< HEAD
	private int heuristicEstimate = 0;
	private ArrayList<Node> children = new ArrayList<Node>();
	
	public Node(State state, Node parent, Tree tree) {
		this.tree = tree;
		this.state = state;
		this.parent = parent;
		heuristicEstimate = calculateHeuristic(state, tree.getFinalState());
		if(parent == null || parent.getParent() == null){
			expandNodes();
//			tree.printState(state.getStateArray());
			System.out.println("Children " + children.size());
		}
	}
	
	public int getHeuristicEstimate(){
		return heuristicEstimate;
	}
	
	public void setHeuristicEstimate(int heuristicEstimate){
		this.heuristicEstimate = heuristicEstimate;
	}

	public ArrayList<Node> getChildren(){
		return children;
	}
	
	private void expandNodes() {
		if (generateDownNode()) {
			children.add(downNode);
		}
		if (generateUpNode()) {
			children.add(upNode);
		}
		if (generateRightNode()) {
			children.add(rightNode);
		}
		if (generateLeftNode()) {
			children.add(leftNode);
		}
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
=======

	public Node(State state, Node parent, Tree tree) {
		this.tree = tree;
		this.state = state;
		this.parent = parent;
>>>>>>> parent of 4662b1d... moving to tree creation beforehand
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
		System.out.println("Left");
		tree.printState(leftState.getStateArray());
		if (!tree.addState(leftState)) {
			leftNode = new Node(leftState, this, tree);
			return true;
		}
		return false;
	}

	public boolean generateRightNode() {
		State rightState = new State(state, 'R', tree);
		System.out.println("Right");
		tree.printState(rightState.getStateArray());
		if (!tree.addState(rightState)) {
			rightNode = new Node(rightState, this, tree);
			return true;
		}
		return false;
	}

	public boolean generateUpNode() {
		State upState = new State(state, 'U', tree);
		System.out.println("Up");
		tree.printState(upState.getStateArray());
		if (!tree.addState(upState)) {
			upNode = new Node(upState, this, tree);
			return true;
		}
		return false;
	}

	public boolean generateDownNode() {
		State downState = new State(state, 'D', tree);
		System.out.println("Down");
		tree.printState(downState.getStateArray());
		if (!tree.addState(downState)) {
			downNode = new Node(downState, this, tree);
			return true;
		}
		return false;
	}
	
	public Node getParent() {
		return parent;
	}
}
