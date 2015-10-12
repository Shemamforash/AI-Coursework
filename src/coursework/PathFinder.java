package coursework;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class PathFinder {
	private Tree	tree;

	public enum Algorithm {
		BREADTH_FIRST, DEPTH_FIRST, ITERATIVE_DEEPENING, ASTAR
	};

	private ArrayList<Node> getPath(Algorithm algorithmType, int n) {
		tree = new Tree(n, new char[] { 'A', '1', '2', '3', '4' });
		switch (algorithmType) {
			case BREADTH_FIRST:
				return countPath(breadthFirstSearch());
			case DEPTH_FIRST:
				return countPath(depthFirstSearch());
			case ITERATIVE_DEEPENING:
				break;
			case ASTAR:
				break;
			default:
				break;
		}
		return null;
	}

	private ArrayList<Node> countPath(Node n) {
		ArrayList<Node> path = new ArrayList<Node>();
		int height = 0;
		while (n.getParent() != null) {
			++height;
			path.add(n);
			n = n.getParent();
		}
		for (int i = path.size() - 1; i >= 0; --i) {
			// System.out.println("Height: " + (path.size() - 1 - i));
			// tree.printState(path.get(i).getState().getStateArray());
		}

		// System.out.println("Final State");
		// tree.printState(tree.getFinalStateArray());
		return path;
	}

	private boolean isFinalState(Node n) {
		State s = n.getState();
		char[][] stateArray = s.getStateArray();
		char[][] finalArray = tree.getFinalStateArray();
		for (int x = 0; x < stateArray[0].length; ++x) {
			for (int y = 0; y < stateArray.length; ++y) {
				if (stateArray[y][x] != finalArray[y][x]) {
					if (stateArray[y][x] == 'A' && finalArray[y][x] == 'N') {
						continue;
					} else if (stateArray[y][x] == 'N' && finalArray[y][x] == 'A') {
						continue;
					} else {
						return false;
					}
				}
			}
		}
		return true;
	}

	private Node depthFirstSearch() {
		float startTime = System.nanoTime();
		Stack<Node> nodeStack = new Stack<Node>();
		nodeStack.push(tree.getInitialNode());

		while (!nodeStack.isEmpty()) {
			Node node = nodeStack.pop();
			if (!node.getVisited()) {
				node.setVisited(true);
				ArrayList<Node> nodes = expandNodes(node);
				for (Node n : nodes) {
					if (isFinalState(n)) {
						countPath(n);
						System.out.println("DepthFS Time = " + ((float) System.nanoTime() - startTime) / 1000000000f);
						return n;
					}
					nodeStack.push(n);
				}
			}
		}
		return null;
	}

	private ArrayList<Node> expandNodes(Node node) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		if (node.generateDownNode()) {
			nodes.add(node.getDown());
		}
		if (node.generateUpNode()) {
			nodes.add(node.getUp());
		}
		if (node.generateRightNode()) {
			nodes.add(node.getRight());
		}
		if (node.generateLeftNode()) {
			nodes.add(node.getLeft());
		}
		return nodes;
	}

	private Node breadthFirstSearch() {
		float startTime = System.nanoTime();
		LinkedList<Node> nodeQueue = new LinkedList<Node>();
		nodeQueue.add(tree.getInitialNode());
		if (isFinalState(tree.getInitialNode())) {
			return tree.getInitialNode();
		}
		while (!nodeQueue.isEmpty()) {
			Node node = nodeQueue.remove();
			ArrayList<Node> nodes = expandNodes(node);
			for (Node n : nodes) {
				if (isFinalState(n)) {
					System.out.println("BreadthFS Time = " + ((float) System.nanoTime() - startTime) / 1000000000f);
					return n;
				}
				nodeQueue.add(n);
			}
		}
		return null;
	}
}
