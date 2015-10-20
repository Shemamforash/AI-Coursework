package coursework;

import java.util.ArrayList;
import java.util.Random;

public class Tree {
	private Node startNode;
	private ArrayList<State> existingStates;
	private State startState;
	private State finalState;
	private int agentX, agentY;
	private ArrayList<CharacterPosition> finalCharacterPositions = new ArrayList<CharacterPosition>();

	public ArrayList<CharacterPosition> getFinalCharacterPositions(){
		return finalCharacterPositions;
	}
	
	public Tree(int n, char[] chars) {
		char[][] startStateArray = new char[n][n];
		for (int y = 0; y < startStateArray[0].length; ++y) {
			for (int x = 0; x < startStateArray.length; ++x) {
				startStateArray[x][y] = 'N';
			}
		}
		for (int i = 0; i < chars.length; ++i) {
			placeChar(startStateArray, chars[i], n, true);
		}
		startState = new State(startStateArray, this);
		generateFinalState(n, chars);
		refresh();
	}

	public State getFinalState() {
		return finalState;
	}

	private void generateFinalState(int n, char[] chars) {
		char[][] finalStateArray = new char[n][n];
		while (true) {
			for (int y = 0; y < startState.getStateArray()[0].length; ++y) {
				for (int x = 0; x < startState.getStateArray().length; ++x) {
					finalStateArray[x][y] = 'N';
				}
			}
			for (int i = 0; i < chars.length; ++i) {
				finalCharacterPositions.add(placeChar(finalStateArray, chars[i], n, false));
			}
			if (!finalStateArray.equals(startState.getStateArray())) {
				break;
			}
		}
		finalState = new State(finalStateArray, this);
	}

	private CharacterPosition placeChar(char[][] arr, char c, int n, boolean placeAgent) {
		CharacterPosition cp = null;
		Random rand = new Random();
		boolean placeFound = false;
		while (!placeFound) {
			int y = rand.nextInt(n);
			int x = rand.nextInt(n);
			if (arr[x][y] == 'N') {
				arr[x][y] = c;
				if (c == 'A') {
					if (placeAgent) {
						agentX = x;
						agentY = y;
					} else {
						arr[x][y] = 'N';
					}
				}
				cp = new CharacterPosition(arr[x][y], x, y);
				placeFound = true;
			}
		}
		return cp;
	}

	public void refresh() {
		existingStates = new ArrayList<State>();
		addStateToExisting(startState);
		startNode = new Node(startState, null, this);
	}

	public void printState(char[][] s) {
		for (int y = 0; y < s[0].length; ++y) {
			for (int x = 0; x < s.length; ++x) {
				System.out.print(s[x][y] + ", ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public boolean stateExists(State state) {
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

	private void addStateToExisting(State state) {
		existingStates.add(state);
	}

	public int getSize() {
		return existingStates.size();
	}
}
