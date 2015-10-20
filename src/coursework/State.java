package coursework;

import java.util.ArrayList;
import java.util.Arrays;

public class State {
	private char[][] stateArray;
	private ArrayList<CharacterPosition> characterPositions = new ArrayList<CharacterPosition>();
	private CharacterPosition agent;

	public ArrayList<CharacterPosition> getCharacterPositions() {
		return characterPositions;
	}

	public State(State prevState, char dir) {
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
	}

	public int getHeuristicEstimate() {
		ArrayList<CharacterPosition> finalChars = Tree.getFinalCharacterPositions();
		int heuristicEstimate = 0;
		for (int i = 0; i < finalChars.size(); ++i) {
			for (int j = 0; j < characterPositions.size(); ++j) {
				if (finalChars.get(i).getChar() == characterPositions.get(j).getChar()) {
					int estimatedDistance = getEstimatedDistance(characterPositions.get(j), finalChars.get(i));
					characterPositions.get(j).setEstimatedDistance(estimatedDistance);
					heuristicEstimate += estimatedDistance;
				}
			}
		}
		return heuristicEstimate;
	}

	private int getEstimatedDistance(CharacterPosition a, CharacterPosition b) {
		int xDiff = Math.abs(a.x() - b.x());
		int yDiff = Math.abs(a.y() - b.y());
		return xDiff + yDiff;
	}

	public State(char[][] stateArray) {
		for (int y = 0; y < stateArray.length; ++y) {
			for (int x = 0; x < stateArray[0].length; ++x) {
				this.stateArray = new char[stateArray.length][stateArray.length];
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
		Tree.printState(stateArray);
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

		for (int y = 0; y < stateArray.length; ++y) {
			for (int x = 0; x < stateArray[0].length; ++x) {
				if (stateArray[x][y] != otherState.getStateArray()[x][y]) {
					return false;
				}
			}
		}
		return true;
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

	private void moveRight() {
		agent.setX(getAgentX() + 1);
		if (withinBounds()) {
			CharacterPosition prevChar = findChar(stateArray[getAgentX()][getAgentY()]);
			stateArray[getAgentX()][getAgentY()] = 'A';
			if (prevChar != null) {
				prevChar.setX(prevChar.x() + 1);
				stateArray[getAgentX() - 1][getAgentY()] = prevChar.getChar();
			} else {
				stateArray[getAgentX() - 1][getAgentY()] = 'N';
			}
		} else {
			agent.setX(getAgentX() - 1);
		}
	}

	private void moveLeft() {
		agent.setX(getAgentX() - 1);
		if (withinBounds()) {
			CharacterPosition prevChar = findChar(stateArray[getAgentX()][getAgentY()]);
			stateArray[getAgentX()][getAgentY()] = 'A';
			if (prevChar != null) {
				prevChar.setX(prevChar.x() - 1);
				stateArray[getAgentX() + 1][getAgentY()] = prevChar.getChar();
			} else {
				stateArray[getAgentX() + 1][getAgentY()] = 'N';
			}
		} else {
			agent.setX(getAgentX() + 1);
		}
	}

	private void moveUp() {
		agent.setY(getAgentY() + 1);
		if (withinBounds()) {
			CharacterPosition prevChar = findChar(stateArray[getAgentX()][getAgentY()]);
			stateArray[getAgentX()][getAgentY()] = 'A';
			if (prevChar != null) {
				stateArray[getAgentX()][getAgentY() - 1] = prevChar.getChar();
				prevChar.setY(prevChar.y() + 1);
			} else {
				stateArray[getAgentX()][getAgentY() - 1] = 'N';
			}
		} else {
			agent.setY(getAgentY() - 1);
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

	private void moveDown() {
		agent.setY(getAgentY() - 1);
		if (withinBounds()) {
			CharacterPosition prevChar = findChar(stateArray[getAgentX()][getAgentY()]);
			stateArray[getAgentX()][getAgentY()] = 'A';
			if (prevChar != null) {
				stateArray[getAgentX()][getAgentY() + 1] = prevChar.getChar();
				prevChar.setY(prevChar.y() - 1);
			} else {
				stateArray[getAgentX()][getAgentY() + 1] = 'N';
			}
		} else {
			agent.setY(getAgentY() + 1);
		}
	}
}
