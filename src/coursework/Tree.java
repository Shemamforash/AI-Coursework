package coursework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Tree {
	private Node							startNode;
	private State							startState;
	private int								originX, originY, agentX, agentY;
	private ArrayList<CharacterPosition>	finalCharacterPositions	= new ArrayList<CharacterPosition>();
	private HashMap<String, StateValue>		stateMap				= new HashMap<String, StateValue>();
	private boolean							checkState				= true;

	public ArrayList<CharacterPosition> getFinalCharacterPositions() {
		return finalCharacterPositions;
	}

	public boolean checkState() {
		return checkState;
	}

	public Tree(int n, char[] chars, int difficulty) {
		generateStartState(n, chars, difficulty);
		printState(startState.getStateArray());
		refresh();
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
	
	private void generateStartState(int n, char[] chars, int difficulty) {		
		char[][] startStateArray = new char[n][n];

		for (int y = 0; y < startStateArray[0].length; ++y) {
			for (int x = 0; x < startStateArray.length; ++x) {
				if(y == 0){
					startStateArray[x][y] = chars[x + 1];
				} else {
					startStateArray[x][y] = 'N';
				}
			}
		}
		
		for(int i = 0; i < difficulty; ++i){
			int val = new Random().nextInt(3);
			boolean moveRow = new Random().nextBoolean();
			boolean flip = new Random().nextBoolean();
			if(moveRow){
				if(flip){
					char store = startStateArray[0][val];
					startStateArray[0][val] = startStateArray[1][val];
					startStateArray[1][val] = store;
				} else{
					char store = startStateArray[1][val];
					startStateArray[1][val] = startStateArray[2][val];
					startStateArray[2][val] = store;
				}
			}else{
				if(flip){
					char store = startStateArray[val][0];
					startStateArray[val][0] = startStateArray[val][1];
					startStateArray[val][1] = store;
				} else{
					char store = startStateArray[val][1];
					startStateArray[val][1] = startStateArray[val][2];
					startStateArray[val][2] = store;
				}
			}
		}
		
		startState = new State(startStateArray, this);
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
		if (stateMap.containsKey(stateString)) {
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