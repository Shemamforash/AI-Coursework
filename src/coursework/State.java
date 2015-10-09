package coursework;

import java.util.Arrays;

public class State {
	private char[][]	stateArray;
	private int			agentX, agentY;

	public State(State prevState, char dir) {
		char[][] oldArray = prevState.getStateArray();
		stateArray = new char[oldArray.length][oldArray[0].length];
		for(int x = 0; x < oldArray[0].length; ++x){
			for(int y = 0; y < oldArray.length; ++y){
				stateArray[y][x] = oldArray[y][x];
			}
		}
		this.agentX = prevState.getAgentX();
		this.agentY = prevState.getAgentY();
		move(dir);
	}

	public State(char[][] stateArray, int agentX, int agentY) {
		this.stateArray = stateArray.clone();
		this.agentX = agentX;
		this.agentY = agentY;
	}

	public void move(char dir) {
		switch (dir) {
			case 'L':
				moveLeft();
				break;
			case 'D':
				moveDown();
				break;
			case 'R':
				moveRight();
				break;
			case 'U':
				moveUp();
				break;
			default:
				break;
		}
	}

	public boolean equals(Object state) {
		if (state == null || state.getClass() != getClass()) {
			return false;
		}
		State otherState = (State) state;

		for (int x = 0; x < stateArray[0].length; ++x) {
			for (int y = 0; y < stateArray.length; ++y) {
				if (stateArray[y][x] != otherState.getStateArray()[y][x]) {		
					return false;
				}
			}
		}		
		return true;
	}

	public int getAgentX() {
		return agentX;
	}

	public int getAgentY() {
		return agentY;
	}

	public char[][] getStateArray() {
		return stateArray;
	}

	private boolean withinBounds() {
		if (agentX >=  stateArray.length || agentX < 0) {
			return false;
		} else if (agentY >= stateArray[0].length || agentY < 0) {
			return false;
		}
		return true;
	}

	private void moveRight() {
		++agentX;
		if (withinBounds()) {
			char prevChar = stateArray[agentX][agentY];
			stateArray[agentX][agentY] = 'A';
			stateArray[agentX - 1][agentY] = prevChar;
		} else {
			--agentX;
		}
	}

	private void moveLeft() {
		--agentX;
		if (withinBounds()) {
			char prevChar = stateArray[agentX][agentY];
			stateArray[agentX][agentY] = 'A';
			stateArray[agentX + 1][agentY] = prevChar;
		} else {
			++agentX;
		}
	}

	private void moveUp() {
		++agentY;
		if (withinBounds()) {
			char prevChar = stateArray[agentX][agentY];
			stateArray[agentX][agentY] = 'A';
			stateArray[agentX][agentY - 1] = prevChar;
		} else {
			--agentY;
		}
	}

	private void moveDown() {
		--agentY;
		if (withinBounds()) {
			char prevChar = stateArray[agentX][agentY];
			stateArray[agentX][agentY] = 'A';
			stateArray[agentX][agentY + 1] = prevChar;
		} else {
			++agentY;
		}
	}
}
