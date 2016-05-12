import java.util.ArrayList;

public class TileMatches {
	ArrayList<TileSidePair>[] sideMatches;

	@SuppressWarnings("unchecked")
	public TileMatches(Tile[] tiles) {
		sideMatches = (ArrayList<TileSidePair>[]) new ArrayList[8];
		for (int i = 0; i < 8; i++) {
			sideMatches[i] = new ArrayList<TileSidePair>();
		}
		for (int t = 0; t < 9; t++) {
			for (int r = 0; r < 4; r++) {
				Tile.Side side = tiles[t].getSide(r);
				// maps to the opposite half type
				sideMatches[2 * side.getSideType() - 2 + ((side.getHalf()) ? 0 : 1)].add(new TileSidePair(tiles[t], r));
				// stores in order of sideType then in order of half(with true
				// being first(matches false)
			}
		}
	}

	public ArrayList<TileSidePair> getMatches(int index) {
		return sideMatches[index];
	}

	public class TileSidePair {
		private Tile tile; 
		private int sideNum;

		public TileSidePair(Tile tile, int sideNum) {
			this.tile = tile;
			this.sideNum = sideNum;
		}

		public Tile getTile() {
			return tile;
		}

		public int getSide() {
			return sideNum;
		}

		@Override
		public String toString() {
			return "" + (tile.getIndex() + 1) + ":" + sideNum;
		}
	}
}
