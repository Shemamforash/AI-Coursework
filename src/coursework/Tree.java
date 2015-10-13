package coursework;

import java.util.ArrayList;
import java.util.Random;

public class Tree {
	private Node startNode;
	private static ArrayList<State> existingStates;
	private State startState;
	private static State finalState;
	private int agentX, agentY;
	private static ArrayList<CharacterPosition> finalCharacterPositions = new ArrayList<CharacterPosition>();

	public static ArrayList<CharacterPosition> getFinalCharacterPositions(){
		return finalCharacterPositions;
	}
	
	public Tree(int n, char[] chars) {
		char[][] startStateArray = new char[n][n];
		for (int x = 0; x < startStateArray[0].length; ++x) {
			for (int y = 0; y < startStateArray.length; ++y) {
				startStateArray[y][x] = 'N';
			}
		}
		for (int i = 0; i < chars.length; ++i) {
			placeChar(startStateArray, chars[i], n, true);
		}
		startState = new State(startStateArray);
		generateFinalState(n, chars);
		refresh();
	}

	public static State getFinalState() {
		return finalState;
	}

	private void generateFinalState(int n, char[] chars) {
		char[][] finalStateArray = new char[n][n];
		while (true) {
			for (int x = 0; x < startState.getStateArray()[0].length; ++x) {
				for (int y = 0; y < startState.getStateArray().length; ++y) {
					finalStateArray[y][x] = 'N';
				}
			}
			for (int i = 0; i < chars.length; ++i) {
				placeChar(finalStateArray, chars[i], n, false);
			}
			if (!finalStateArray.equals(startState.getStateArray())) {
				break;
			}
		}
		finalState = new State(finalStateArray);
		for (int x = 0; x < finalStateArray[0].length; ++x) {
			for (int y = 0; y < finalStateArray.length; ++y) {
				if (finalStateArray[y][x] != 'N') {
					CharacterPosition cp = new CharacterPosition(finalStateArray[y][x], x, y);
					finalCharacterPositions.add(cp);
				}
			}
		}
	}

	private void placeChar(char[][] arr, char c, int n, boolean placeAgent) {
		Random rand = new Random();
		boolean placeFound = false;
		while (!placeFound) {
			int x = rand.nextInt(n);
			int y = rand.nextInt(n);
			if (arr[y][x] == 'N') {
				arr[y][x] = c;
				if (c == 'A') {
					if (placeAgent) {
						agentX = y;
						agentY = x;
					} else {
						arr[y][x] = 'N';
					}
				}
				placeFound = true;
			}
		}
	}

	public void refresh() {
		existingStates = new ArrayList<State>();
		addStateToExisting(startState);
		startNode = new Node(startState, null);
	}

	public static void printState(char[][] s) {
		for (int x = 0; x < s[0].length; ++x) {
			for (int y = 0; y < s.length; ++y) {
				System.out.print(s[y][x] + ", ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static boolean stateExists(State state) {
		for (State s : existingStates) {
			if (s.equals(state)) {
				return true;
			}
		}
		addStateToExisting(state);
		return false;
	}

	public Node getInitialNode() {
		return startNode;
	}

	private static void addStateToExisting(State state) {
		existingStates.add(state);
	}

	public int getSize() {
		return existingStates.size();
	}
}
