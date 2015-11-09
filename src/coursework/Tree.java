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
	private HashMap<String, StateValue>		stateMap				= new HashMap<String, StateValue>();
	private boolean							checkState				= true;

	public ArrayList<CharacterPosition> getFinalCharacterPositions() {
		return finalCharacterPositions;
	}

	public boolean checkState() {
		return checkState;
	}

	public Tree(int n, char[] chars, int difficulty) {
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
		generateFinalState(n, chars, difficulty);
		printState(startState.getStateArray());
		printState(finalState.getStateArray());
		refresh();
	}

	public State getFinalState() {
		return finalState;
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
	
	private void generateFinalState(int n, char[] chars, int difficulty) {		
		char[][] finalStateArray = new char[n][n];

		for (int y = 0; y < finalStateArray[0].length; ++y) {
			for (int x = 0; x < finalStateArray.length; ++x) {
				finalStateArray[x][y] = startState.getStateArray()[x][y];
				if(finalStateArray[x][y] == 'A'){
					finalStateArray[x][y] = 'N';
				}
			}
		}
//		
//		finalState = new State(finalStateArray, this);
//		
//		for(int i = 0; i < difficulty; ++i){
//			int switchVal = new Random().nextInt(4);
//			switch(switchVal){
//				case 0:
//					finalState.move('L');
//					break;
//				case 1:
//					finalState.move('R');
//					break;
//				case 2:
//					finalState.move('D');
//					break;
//				case 3:
//					finalState.move('U');
//					break;
//			}
//		}
//		
//		finalStateArray = finalState.getStateArray();
//		
//		for (int y = 0; y < finalStateArray[0].length; ++y) {
//			for (int x = 0; x < finalStateArray.length; ++x) {
//				finalStateArray[x][y] = startState.getStateArray()[x][y];
//				if(finalStateArray[x][y] == 'A'){
//					finalStateArray[x][y] = 'N';
//				}
//			}
//		}
//		
//		finalState = new State(finalStateArray, this);
		
		for(int i = 0; i < difficulty; ++i){
			int val = new Random().nextInt(3);
			boolean moveRow = new Random().nextBoolean();
			boolean flip = new Random().nextBoolean();
			if(moveRow){
				if(flip){
					char store = finalStateArray[0][val];
					finalStateArray[0][val] = finalStateArray[1][val];
					finalStateArray[1][val] = store;
				} else{
					char store = finalStateArray[1][val];
					finalStateArray[1][val] = finalStateArray[2][val];
					finalStateArray[2][val] = store;
				}
			}else{
				if(flip){
					char store = finalStateArray[val][0];
					finalStateArray[val][0] = finalStateArray[val][1];
					finalStateArray[val][1] = store;
				} else{
					char store = finalStateArray[val][1];
					finalStateArray[val][1] = finalStateArray[val][2];
					finalStateArray[val][2] = store;
				}
			}
		}
		
		finalState = new State(finalStateArray, this);
		
//		char[][] finalStateArray = new char[n][n];
//		while (true) {
//			for (int y = 0; y < startState.getStateArray()[0].length; ++y) {
//				for (int x = 0; x < startState.getStateArray().length; ++x) {
//					finalStateArray[x][y] = 'N';
//				}
//			}
//			for (int i = 0; i < chars.length; ++i) {
//				finalCharacterPositions.add(placeChar(finalStateArray, chars[i], n, false));
//			}
//			if (!finalStateArray.equals(startState.getStateArray())) {
//				break;
//			}
//		}
//		finalState = new State(finalStateArray, this);
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