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
<<<<<<< HEAD
		char[] intchars = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		for (int difficulty = 3; difficulty < 4; ++difficulty) {
			char[] charArr = new char[difficulty + 1];
			charArr[0] = 'A';
			for (int i = 0; i < difficulty; ++i) {
				charArr[i + 1] = intchars[i];
			}
			int repetitions = 1;
			for (int i = 0; i < repetitions; ++i) {
				tree = new Tree(difficulty, charArr);
				System.out.println(tree.getSize());
				countPath(breadthFirstSearch());
				countPath(depthFirstSearch());
				countPath(aStar());
			}
			System.out.println("--- FINISHED ---");
			System.out.println("Difficulty: " + difficulty);
			System.out.println("Total repetitions: " + repetitions);
			System.out.println("-- Breadth First Search Averages --");
			System.out.println("Time: " + bfsTime / repetitions);
			System.out.println("Nodes: " + bfsAverageNodes / repetitions);
			System.out.println("-- Depth First Search Averages --");
			System.out.println("Time: " + dfsTime / repetitions);
			System.out.println("Nodes: " + dfsAverageNodes / repetitions);
			System.out.println("-- A* Search Averages --");
			System.out.println("Time: " + aStarTime / repetitions);
			System.out.println("Nodes: " + aStarAverageNodes / repetitions);
			bfsTime = 0;
			dfsTime = 0;
			aStarTime = 0;
			bfsAverageNodes = 0;
			dfsAverageNodes = 0;
			aStarAverageNodes = 0;
		}
=======
		tree = new Tree(4, new char[] { 'A', '1', '2', '3' });
		countPath(breadthFirstSearch());
		// tree.refresh();
		// countPath(depthFirstSearch());
>>>>>>> parent of 4662b1d... moving to tree creation beforehand
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
<<<<<<< HEAD
//				 System.out.println("Height: " + (path.size() - 1 - i) + " Estimate: " + path.get(i).getHeuristicEstimate());
//				 tree.printState(path.get(i).getState().getStateArray());
			}

//			System.out.println("Final State");
//			tree.printState(tree.getFinalState().getStateArray());
=======
				System.out.println("Height: " + (path.size() - 1 - i) + " Estimate: "
						+ path.get(i).getState().getHeuristicEstimate());
				tree.printState(path.get(i).getState().getStateArray());
			}

			System.out.println("Final State");
			tree.printState(tree.getFinalState().getStateArray());
>>>>>>> parent of 4662b1d... moving to tree creation beforehand
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
<<<<<<< HEAD
			Node currentNode = nodeStack.pop();
			if (!currentNode.getVisited()) {
				currentNode.setVisited(true);
				++dfsAverageNodes;
				for (Node n : currentNode.getChildren()) {
					if (isFinalState(n)) {
						dfsTime += ((float) System.nanoTime() - startTime) / 1000000000f;
=======
			Node node = nodeStack.pop();
			if (!node.getVisited()) {
				node.setVisited(true);
				ArrayList<Node> nodes = expandNodes(node);
				for (Node n : nodes) {
					if (isFinalState(n)) {
						countPath(n);
						System.out.println("DepthFS Time = " + ((float) System.nanoTime() - startTime) / 1000000000f);
>>>>>>> parent of 4662b1d... moving to tree creation beforehand
						return n;
					}
					nodeStack.push(n);
				}
			}
		}
		return null;
	}

<<<<<<< HEAD
	private class HeuristicEstimateComparator implements Comparator<Node> {
		public int compare(Node n1, Node n2) {
			int n1Estimate = n1.getHeuristicEstimate();
			int n2Estimate = n2.getHeuristicEstimate();
			if (n1Estimate < n2Estimate) {
				return -1;
			} else if (n1Estimate > n2Estimate) {
				return 1;
			}
			return 0;
		}
	}

	private Node aStar() {
		float startTime = System.nanoTime();
		ArrayList<Node> visitedNodes = new ArrayList<Node>();
		PriorityQueue<Node> unvisitedNodes = new PriorityQueue<Node>(new HeuristicEstimateComparator());
		unvisitedNodes.add(tree.getInitialNode());

		while (!unvisitedNodes.isEmpty()) {
			Node currentNode = unvisitedNodes.remove();
			if (isFinalState(currentNode)) {
				aStarTime += ((float) System.nanoTime() - startTime) / 1000000000f;
				return currentNode;
			}

			visitedNodes.add(currentNode);
			++aStarAverageNodes;
			for (Node n : currentNode.getChildren()) {
				int gVal = currentNode.getHeuristicEstimate() + Math.abs(currentNode.getHeuristicEstimate() - n.getHeuristicEstimate());
				unvisitedNodes.add(n);
				if (gVal >= n.getHeuristicEstimate()) {
					continue;
				}

				n.setHeuristicEstimate(gVal);
			}
		}
		return null;
=======
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
>>>>>>> parent of 4662b1d... moving to tree creation beforehand
	}

	private Node breadthFirstSearch() {
		float startTime = System.nanoTime();
		LinkedList<Node> nodeQueue = new LinkedList<Node>();
		nodeQueue.add(tree.getInitialNode());
		if (isFinalState(tree.getInitialNode())) {
			return tree.getInitialNode();
		}
		while (!nodeQueue.isEmpty()) {
<<<<<<< HEAD
			Node currentNode = nodeQueue.remove();
			++bfsAverageNodes;
			for (Node n : currentNode.getChildren()) {
				if (isFinalState(n)) {
					bfsTime += ((float) System.nanoTime() - startTime) / 1000000000f;
=======
			Node node = nodeQueue.remove();
			ArrayList<Node> nodes = expandNodes(node);
			for (Node n : nodes) {
				if (isFinalState(n)) {
					System.out.println("BreadthFS Time = " + ((float) System.nanoTime() - startTime) / 1000000000f);
>>>>>>> parent of 4662b1d... moving to tree creation beforehand
					return n;
				}
				nodeQueue.add(n);
			}
		}
		return null;
	}
}
