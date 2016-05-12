import java.util.LinkedList;

public class TileSolver {
	private static Tile[] grid;
	private static Tile[] tiles;
	private static TileMatches matchGlossary;
	private static int comparisons;

	public static void main(String[] args) {
		comparisons = 0;
		grid = new Tile[9];
		tiles = new Tile[9];
		System.out.println("start");
		addTiles();
		matchGlossary = new TileMatches(tiles);
		System.out.println("SETUP COMPLETE.");

		// if(true)return;

		for (int i = 0; i < 9; i++) {
			for (int r = 0; r < 4; r++) {
				clearGrid();
				grid[0] = tiles[i];// i
				grid[0].setRotation(r);
				addToGrid(1);
			}
		}
		System.out.println(comparisons);
		System.out.println("PROGRAM COMPLETE");

	}

	private static void addToGrid(int location) {
		if (location >= 9) {
			printGrid();
			System.out.println(comparisons);
			System.out.println("SOLVED!");
			// System.exit(0); //optional to find only one solution or all
		}
		LinkedList<TileMatches.TileSidePair> queue = new LinkedList<TileMatches.TileSidePair>();
		// could be any list but if I'm calling it a queue it might as well be a
		// LinkedList

		if (location % 3 != 0) {
			// add to side
			Tile.Side matchSide = grid[location - 1].getSide(1);
			queue.addAll(matchGlossary.getMatches(2 * matchSide.getSideType() - 2 + ((matchSide.getHalf()) ? 1 : 0)));
			for (TileMatches.TileSidePair tsp : queue) {

				comparisons++;
				if (!gridContains(tsp.getTile())) {
					tsp.getTile().setRotation((3 - tsp.getSide()));
					if (location <= 2 || tsp.getTile().getSide(0).matches(grid[location - 3].getSide(2))) {
						grid[location] = tsp.getTile();
						addToGrid(location + 1);
						grid[location] = null;
					}

				}

			}
		} else {
			// add below
			Tile.Side matchSide = grid[location - 3].getSide(2);
			queue.addAll(matchGlossary.getMatches(2 * matchSide.getSideType() - 2 + ((matchSide.getHalf()) ? 1 : 0)));
			for (TileMatches.TileSidePair tsp : queue) {

				if (!gridContains(tsp.getTile())) {
					tsp.getTile().setRotation((4 - tsp.getSide()) % 4);
					grid[location] = tsp.getTile();
					addToGrid(location + 1);
					grid[location] = null;
				}

			}
		}
	}

	private static void clearGrid() {
		grid = new Tile[9];
		for (Tile t : tiles) {
			t.setRotation(0);
		}
	}

	private static boolean gridContains(Tile tile) {
		for (Tile t : grid) {
			if (tile.equals(t)) {
				return true;
			}
		}
		return false;
	}

	private static void addTiles() {
		// For dinosaur puzzle:
		// 1: Triceritops; 2: Parasaurolophus; 3: T-Rex; 4: Apatasaurus
		// true = bottom; false = head

		tiles[0] = new Tile(0, new byte[] { 1, 2, 3, 4 }, new boolean[] { false, false, false, false });
		tiles[1] = new Tile(1, new byte[] { 3, 4, 2, 2 }, new boolean[] { true, true, false, false });
		tiles[2] = new Tile(2, new byte[] { 2, 3, 1, 4 }, new boolean[] { true, false, true, false });
		tiles[3] = new Tile(3, new byte[] { 4, 3, 3, 1 }, new boolean[] { false, true, false, true });
		tiles[4] = new Tile(4, new byte[] { 2, 1, 4, 1 }, new boolean[] { false, false, false, false });
		tiles[5] = new Tile(5, new byte[] { 2, 4, 1, 3 }, new boolean[] { true, false, true, true });
		tiles[6] = new Tile(6, new byte[] { 3, 4, 2, 1 }, new boolean[] { false, false, true, true });
		tiles[7] = new Tile(7, new byte[] { 2, 3, 1, 4 }, new boolean[] { false, false, true, true });
		tiles[8] = new Tile(8, new byte[] { 2, 4, 3, 1 }, new boolean[] { false, true, false, false });

	}

	private static void printGrid() {
		System.out.println("\n============================");
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 5; k++) {
				for (int j = 0; j < 3; j++) {
					Tile t = grid[3 * i + j];
					if (t == null) {
						System.out.print((k % 4 == 0) ? "XXXXXXX" : "X     X");
					} else {
						String rotChar = "*";
						Tile.Side[] sides = t.getSides();

						if (k == 0) {// first row
							System.out.print("   " + sides[0].getSideType() + "   ");
						} else if (k == 1) {// rotation
							if (t.getRotation() == 0) {
								System.out.print("   " + rotChar + "   ");
							} else {
								System.out.print("       ");
							}
						} else if (k == 2) {// second row
							System.out.print(sides[3].getSideType() + " " + ((t.getRotation() == 3) ? rotChar : " ")
									+ (grid[3 * i + j].getIndex() + 1) + ((t.getRotation() == 1) ? rotChar : " ") + " "
									+ sides[1].getSideType());
						} else if (k == 3) {// rotation
							if (t.getRotation() == 2) {
								System.out.print("   " + rotChar + "   ");
							} else {
								System.out.print("       ");
							}
						} else if (k == 4) {// third row
							System.out.print("   " + sides[2].getSideType() + "   ");
						}
					}
				}
				System.out.println();
			}
		}
		System.out.println("============================");
	}

	@SuppressWarnings("unused")
	private static void printGrid2() {
		System.out.println("\n============================");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int gridIndex = 3 * i + j;
				if (grid[gridIndex] == null) {
					System.out.print(" [NULL] ");
				} else {
					System.out.print(" [Tile: " + (grid[gridIndex].getIndex() + 1) + ". Rotation: "
							+ grid[gridIndex].getRotation() + ".] ");
				}
			}
			System.out.println();
		}
		// return " " + ((r==0)?" ":"@") + " \n" + ((r==3)?" ":"@") +;
		System.out.println("============================");
	}

}
