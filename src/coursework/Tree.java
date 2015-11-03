package coursework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Tree {
	private Node							startNode;
	private State							startState;
	private State							finalState;
	private int								originX, originY, agentX, agentY;
	private ArrayList<CharacterPosition>	finalCharacterPositions	= new ArrayList<CharacterPosition>();
	private HashMap<String, StateValue> 	stateMap = new HashMap<String, StateValue>();

	public ArrayList<CharacterPosition> getFinalCharacterPositions() {
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
						originX = x;
						originY = y;
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
	
	enum StateValue {
		VISITED, UNVISITED;
	}

	public void refresh() {
		stateMap.clear();
		stateExists(startState);
		startNode = new Node(startState, null, this);
		agentX = originX;
		agentY = originY;
	}

	public boolean stateExists(State state) {
		String stateString = null;
		char[][] stateArray = state.getStateArray();
		for (int y = 0; y < stateArray.length; ++y) {
			for (int x = 0; x < stateArray[0].length; ++x) {
				stateString = stateString + stateArray[x][y];
			}
		}
		if(stateMap.containsKey(stateString)){
			return true;
		}
		stateMap.put(stateString, StateValue.VISITED);
		return false;
	}

	public Node getInitialNode() {
		return new Node(startState, null, this);
	}

	public int getSize() {
		return stateMap.size();
	}
}