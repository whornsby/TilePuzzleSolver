public class Tile {
	private Side[] sides;
	private int rotation;
	private int tileNum;

	public Tile(int index, byte[] typeValues, boolean[] halfValues) {
		if (typeValues.length != 4) {
			throw new IllegalArgumentException("Type array is invalid");
		}
		if (halfValues.length != 4) {
			throw new IllegalArgumentException("Half array is invalid");
		}
		
		tileNum = index;
		rotation = 0;
		sides = new Side[4];
		for (int i = 0; i < 4; i++) {
			sides[i] = new Side(typeValues[i], halfValues[i]);
			// In-order: Up, Right, Down, Left
		}
	}

	public Side[] getSides() {
		if (rotation == 0) {
			return sides;
		} else {
			Side[] s = new Side[4];
			for (int i = 0; i < 4; i++) {
				s[i] = sides[(4 - rotation + i) % 4];
			}
			return s;
		}
	}

	public Side getSide(int sideNum) {
		return sides[(4 - rotation + sideNum) % 4];
	}

	public int getIndex(){
		return tileNum;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getRotation() {
		return rotation;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o instanceof Tile == false) {
			return false;
		}
		Tile t = (Tile) o;
		for (int i = 0; i < 4; i++) {
			Side s1 = this.sides[i];
			Side s2 = t.sides[i];
			if (s1.sideType != s2.sideType || s1.otherHalf != s2.otherHalf) {
				return false;
			}
		}
		return true;
		// this assumes there are no identical/duplicate tiles in the set
		// simply tests for different instances of the same tile
	}
	
	@Override
	public String toString(){
		return "" + (tileNum + 1);
	}

	public class Side {
		private byte sideType; // The pattern or icon (ex. type of dinosour_ that needs
						// to be matched on the side
		private boolean otherHalf; // which half of the type (ex. top or bottom) the
							// side represents

		public Side(byte type, boolean half) {
			this.sideType = type;
			this.otherHalf = half;
		}

		public int getSideType() {
			return sideType;
		}

		public boolean getHalf() {
			return otherHalf;
		}

		public boolean matches(Side s) {
			if (s == null) {
				return false;
			}
			return s.sideType == this.sideType && s.otherHalf != this.otherHalf;
			// halves can't be equal to match (top has to match to bottom)
		}
		
		@Override
		public String toString(){
			return "[" + sideType + ":" + ((otherHalf)?"Tail":"Head") + "]"; 
		}
	}
}
