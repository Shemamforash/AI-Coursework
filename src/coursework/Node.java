package coursework;

public class Node {
	private State	state;
	private Node leftNode, rightNode, upNode, downNode, parent;
	private Boolean visited = false;

	public Node(State state, Node parent) {
		this.state = state;
		this.parent = parent;
	}

	public State getState() {
		return state;
	}
	
	public void setVisited(Boolean visited){
		this.visited = visited;
	}
	
	public Boolean getVisited(){
		return visited;
	}
	
	public boolean generateLeftNode(){
		State leftState = new State(state, 'L');
		if(!Tree.stateExists(leftState)){
			leftNode = new Node(leftState, this);
			return true;
		}
		return false;
	}
	
	public boolean generateRightNode(){
		State rightState = new State(state, 'R');
		if(!Tree.stateExists(rightState)){
			rightNode = new Node(rightState, this);
			return true;
		}
		return false;
	}
	
	public boolean generateUpNode(){
		State upState = new State(state, 'U');
		if(!Tree.stateExists(upState)){
			upNode = new Node(upState, this);
			return true;
		}
		return false;
	}
	
	public boolean generateDownNode(){
		State downState = new State(state, 'D');
		if(!Tree.stateExists(downState)){
			downNode = new Node(downState, this);
			return true;
		}
		return false;
	}
	
	public Node getLeft(){
		return leftNode;
	}
	public Node getRight(){
		return rightNode;
	}
	public Node getUp(){
		return upNode;
	}
	public Node getDown(){
		return downNode;
	}
	public Node getParent(){
		return parent;
	}
}
