package coursework;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class PathFinder {
	private Tree tree;

	public static void main(String[] args) {
		PathFinder p = new PathFinder();
		p.go();
	}

	private void go() {
		tree = new Tree(4, new char[] { 'A', '1', '2', '3' });
		countPath(breadthFirstSearch());
		// tree.refresh();
		// countPath(depthFirstSearch());
	}

	private ArrayList<Node> countPath(Node n) {
		ArrayList<Node> path = new ArrayList<Node>();
		if (n != null) {
			int height = 0;
			while (n.getParent() != null) {
				++height;
				path.add(n);
				n = n.getParent();
			}
			for (int i = path.size() - 1; i >= 0; --i) {
				System.out.println("Height: " + (path.size() - 1 - i) + " Estimate: "
						+ path.get(i).getState().getHeuristicEstimate());
				tree.printState(path.get(i).getState().getStateArray());
			}

			System.out.println("Final State");
			tree.printState(tree.getFinalState().getStateArray());
		}
		return path;
	}

	private boolean isFinalState(Node n) {
		State s = n.getState();
		char[][] stateArray = s.getStateArray();
		char[][] finalArray = tree.getFinalState().getStateArray();
		for (int y = 0; y < stateArray[0].length; ++y) {
			for (int x = 0; x < stateArray.length; ++x) {
				if (stateArray[x][y] != finalArray[x][y]) {
					if (stateArray[x][y] == 'A' && finalArray[x][y] == 'N') {
						continue;
					} else if (stateArray[x][y] == 'N' && finalArray[x][y] == 'A') {
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
