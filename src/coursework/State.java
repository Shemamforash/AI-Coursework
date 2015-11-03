package coursework;

import java.util.ArrayList;
import java.util.Arrays;

public class State {
	private char[][]						stateArray;
	private ArrayList<CharacterPosition>	characterPositions	= new ArrayList<CharacterPosition>();
	private CharacterPosition				agent;
	private String							stateString = new String();

	public ArrayList<CharacterPosition> getCharacterPositions() {
		return characterPositions;
	}

	public State(State prevState, char dir, Tree tree) {
		char[][] oldArray = prevState.getStateArray();
		stateArray = new char[oldArray.length][oldArray[0].length];
		for (int y = 0; y < oldArray.length; ++y) {
			for (int x = 0; x < oldArray[0].length; ++x) {
				stateArray[x][y] = oldArray[x][y];
			}
		}
		ArrayList<CharacterPosition> prevCharacterPositions = prevState.getCharacterPositions();
		for (int i = 0; i < prevCharacterPositions.size(); ++i) {
			CharacterPosition prevPos = prevCharacterPositions.get(i);
			CharacterPosition newPos = new CharacterPosition(prevPos.getChar(), prevPos.x(), prevPos.y());
			characterPositions.add(newPos);
		}
		agent = new CharacterPosition('A', prevState.getAgentX(), prevState.getAgentY());
		this.move(dir);
		setStateString();
	}
	
	private void setStateString(){
		for (int y = 0; y < stateArray.length; ++y) {
			for (int x = 0; x < stateArray[0].length; ++x) {
				stateString = stateString + String.valueOf(stateArray[x][y]);
			}
		}
	}
	
	public String getStateString(){
		return stateString;
	}

	public State(char[][] stateArray, Tree tree) {
		this.stateArray = new char[stateArray.length][stateArray.length];
		for (int y = 0; y < stateArray.length; ++y) {
			for (int x = 0; x < stateArray[0].length; ++x) {
				this.stateArray[x][y] = stateArray[x][y];
				if (stateArray[x][y] != 'N') {
					CharacterPosition cp = new CharacterPosition(stateArray[x][y], x, y);
					if (cp.getChar() == 'A') {
						agent = cp;
					} else {
						characterPositions.add(cp);
					}
				}
			}
		}
		setStateString();
	}

	public void move(char dir) {
		switch (dir) {
			case 'L':
				move(-1, 0);
				break;
			case 'D':
				move(0, 1);
				break;
			case 'R':
				move(1, 0);
				break;
			case 'U':
				move(0, -1);
				break;
			default:
				break;
		}
	}

	public int getAgentX() {
		return agent.x();
	}

	public int getAgentY() {
		return agent.y();
	}

	public char[][] getStateArray() {
		return stateArray;
	}

	private boolean withinBounds() {
		if (getAgentX() >= stateArray.length || getAgentX() < 0) {
			return false;
		} else if (getAgentY() >= stateArray[0].length || getAgentY() < 0) {
			return false;
		}
		return true;
	}
	
	private void move(int xDir, int yDir){
		agent.setX(getAgentX() + xDir);
		agent.setY(getAgentY() + yDir);
		if (withinBounds()) {
			CharacterPosition prevChar = findChar(stateArray[getAgentX()][getAgentY()]);
			stateArray[getAgentX()][getAgentY()] = 'A';
			if (prevChar != null) {
				prevChar.setX(prevChar.x() - xDir);
				prevChar.setY(prevChar.y() - yDir);
				stateArray[getAgentX() - xDir][getAgentY() - yDir] = prevChar.getChar();
			} else {
				stateArray[getAgentX() - xDir][getAgentY() - yDir] = 'N';
			}
		} else {
			agent.setX(getAgentX() - xDir);
			agent.setY(getAgentY() - yDir);
		}
	}

	private CharacterPosition findChar(char c) {
		for (int i = 0; i < characterPositions.size(); ++i) {
			if (characterPositions.get(i).getChar() == c) {
				return characterPositions.get(i);
			}
		}
		return null;
	}
}