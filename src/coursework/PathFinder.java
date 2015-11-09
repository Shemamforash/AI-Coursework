package coursework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class PathFinder {
	private Tree	tree;
	private Long	bfsTime	= 0L, dfsTime = 0L, aStarTime = 0L;
	private float	bfsAverageNodes	= 0, dfsAverageNodes = 0, aStarAverageNodes = 0, bfsAverageHeight = 0f, dfsAverageHeight = 0f, aStarAverageHeight = 0f, idAverageNodes = 0f, idAverageHeight = 0f, idTime = 0f;

	public static void main(String[] args) {
		PathFinder p = new PathFinder();
		p.go();
	}

	private void go() {
		char[] intchars = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		for (int difficulty = 3; difficulty < 8; ++difficulty) {
			char[] charArr = new char[3 + 1];
			charArr[0] = 'A';
			for (int i = 0; i < charArr.length - 1; ++i) {
				charArr[i + 1] = intchars[i];
			}
			float repetitions = 5f;
			for (int i = 0; i < repetitions; ++i) {
				tree = new Tree(3, charArr, difficulty);
//				breadthFirstSearch();
//				tree.refresh();
//				depthFirstSearch();
//				tree.refresh();
				aStarAverageHeight += aStar().getHeight();
//				tree.refresh();
//				iterativeDeepening();
			}
			float timeDivisor = repetitions;
			System.out.println("--- FINISHED ---");
			System.out.println("Difficulty: " + difficulty);
			System.out.println("Total repetitions: " + repetitions);
			System.out.println("-----------------------------------------------------");
			System.out.println("-- Breadth First Search Averages --");
//			System.out.println("Time: " + (float) (bfsTime / timeDivisor));
			System.out.println("Nodes: " + bfsAverageNodes / repetitions);
//			System.out.println("Height: " + bfsAverageHeight / repetitions);
			System.out.println("-----------------------------------------------------");
			System.out.println("-- Depth First Search Averages --");
//			System.out.println("Time: " + (float) (dfsTime / timeDivisor));
			System.out.println("Nodes: " + dfsAverageNodes / repetitions);
//			System.out.println("Height: " + dfsAverageHeight / repetitions);
			System.out.println("-----------------------------------------------------");
			System.out.println("-- A* Search Averages --");
//			System.out.println("Time: " + (float) (aStarTime / timeDivisor));
			System.out.println("Nodes: " + aStarAverageNodes / repetitions);
			System.out.println("Height: " + aStarAverageHeight / repetitions);
			System.out.println("-----------------------------------------------------");
			System.out.println("-- Iterative Deepening Search Averages --");
//			System.out.println("Time: " + (float) (idTime / timeDivisor));
			System.out.println("Nodes: " + idAverageNodes / repetitions);
//			System.out.println("Height: " + idAverageHeight / repetitions);
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

			bfsAverageNodes = 0;
			dfsAverageNodes = 0;
			aStarAverageNodes = 0;
			bfsTime = 0L;
			dfsTime = 0L;
			aStarTime = 0L;
			bfsAverageHeight = 0f;
			dfsAverageHeight = 0f;
			aStarAverageHeight = 0f;
			idAverageNodes = 0f;
			idAverageHeight = 0f;
			idTime = 0f;
		}
	}

	private boolean isFinalState(Node n) {
		String stateString = n.getState().getStateString();
		stateString = stateString.replace('A', 'N');
		if (stateString.equals(tree.getFinalState().getStateString())) {
			return true;
		}
		return false;
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
		Collections.shuffle(nodes);
		return nodes;
	}

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
		Long startTime = System.nanoTime();
		ArrayList<Node> visitedNodes = new ArrayList<Node>();
		PriorityQueue<Node> unvisitedNodes = new PriorityQueue<Node>(new HeuristicEstimateComparator());
		unvisitedNodes.add(tree.getInitialNode());

		while (!unvisitedNodes.isEmpty()) {
			Node currentNode = unvisitedNodes.remove();
			++aStarAverageNodes;
			if (isFinalState(currentNode)) {
				aStarTime += System.nanoTime() - startTime;
				return currentNode;
			}
			visitedNodes.add(currentNode);
			for (Node n : expandNodes(currentNode)) {
				int gVal = currentNode.getHeuristicEstimate() + Math.abs(currentNode.getHeuristicEstimate() - n.getHeuristicEstimate());
				unvisitedNodes.add(n);
				if (gVal >= n.getHeuristicEstimate()) {
					continue;
				}
				n.setHeuristicEstimate(gVal);
			}
		}
		return null;
	}

	private Node iterativeDeepening() {
		Long startTime = System.nanoTime();
		Stack<Node> nodeStack = new Stack<Node>();
		int depth = 0;
		while (true) {
			tree.refresh();
			nodeStack.clear();
			nodeStack.push(tree.getInitialNode());
			while (!nodeStack.isEmpty()) {
				Node node = nodeStack.pop();
				++idAverageNodes;
				if (!node.getVisited()) {
					node.setVisited(true);
					ArrayList<Node> nodes = expandNodes(node);
					for (Node n : nodes) {
						if (isFinalState(n)) {
							idTime += System.nanoTime() - startTime;
							return n;
						} else if (n.getHeight() <= depth) {
							nodeStack.push(n);
						}
					}
				}
			}
			++depth;
		}
	}

	private Node depthFirstSearch() {
		Stack<Node> nodeStack = new Stack<Node>();
		nodeStack.push(tree.getInitialNode());
		Long startTime = System.nanoTime();
		int nodesExplored = 0;
		while (!nodeStack.isEmpty()) {
			Node node = nodeStack.pop();
			++dfsAverageNodes;
			if(nodesExplored > 10000000){
				break;
			}
			if (!node.getVisited()) {
				node.setVisited(true);
				ArrayList<Node> nodes = expandNodes(node);
				for (Node n : nodes) {
					if (isFinalState(n)) {
						dfsTime += System.nanoTime() - startTime;
						return n;
					}
					nodeStack.push(n);
				}
			}
		}
		return null;
	}

	private Node breadthFirstSearch() {
		Long startTime = System.nanoTime();
		LinkedList<Node> nodeQueue = new LinkedList<Node>();
		nodeQueue.add(tree.getInitialNode());
		while (!nodeQueue.isEmpty()) {
			Node node = nodeQueue.remove();
			++bfsAverageNodes;
			if (isFinalState(node)) {
				bfsTime += System.nanoTime() - startTime;
				return node;
			}
			ArrayList<Node> nodes = expandNodes(node);
			for (Node n : nodes) {
				nodeQueue.add(n);
			}
		}
		return null;
	}
}