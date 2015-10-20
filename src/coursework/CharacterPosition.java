package coursework;

class CharacterPosition {
	private int x, y;
	private char c;
	
	public CharacterPosition(char c, int x, int y) {
		this.c = c;
		setX(x);
		setY(y);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public char getChar() {
		return c;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}
}