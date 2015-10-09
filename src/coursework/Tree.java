package coursework;

import java.util.ArrayList;
import java.util.Random;

public class Tree {
	private Node startNode;
	private static ArrayList<State> existingStates;
	private char[][] startStateArray;
	private char[][] finalStateArray;
	private int agentX, agentY;
	
	public Tree(int n, char[] chars){
		startStateArray = new char[n][n];
		for(int x = 0; x < startStateArray[0].length; ++x){
			for(int y = 0; y < startStateArray.length; ++y){
				startStateArray[y][x] = 'N';
				finalStateArray[y][x] = 'N';
			}
		}
		for(int i = 0; i < chars.length; ++i){
			placeChar(startStateArray, chars[i], n);
			placeChar(finalStateArray, chars[i], n);
		}
		refresh();
	}
	
	public char[][] getFinalStateArray(){
		return finalStateArray;
	}
	
	private void placeChar(char[][] arr, char c, int n){
		Random rand = new Random();
		boolean placeFound = false;
		while(!placeFound){
			int x = rand.nextInt(n);
			int y = rand.nextInt(n);
			if(arr[y][x] == 'N'){
				arr[y][x] = c;
				if(c == 'A'){
					agentX = y;
					agentY = x;
				}
				placeFound = true;
			}
		}
	}
	
	public void refresh(){
		existingStates = new ArrayList<State>();
		State startState = new State(startStateArray, agentX, agentY);
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
	
	public static boolean stateExists(State state){
		for(State s: existingStates){
			if(s.equals(state)){
				return true;
			}
		}
		addStateToExisting(state);
		return false;
	}
	
	public Node getInitialNode(){
		return startNode;
	}
	
	private static void addStateToExisting(State state){
		existingStates.add(state);
	}
	
	public int getSize(){
		return existingStates.size();
	}
}
