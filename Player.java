import java.util.ArrayList;

/* A player is an agent who can perform a move on the board
 * Each player is designated a color and has a list of occupied tiles
 *
 * @author	Adam Carrol
 * @version	1.0
 */
class Player {

	private final ArrayList<Tile> occupiedTiles;
	private final Color color;

	// Constructor
	Player(Color color) {
		this.occupiedTiles = new ArrayList<>();
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	/*
	 * Add tile to the list of occupied tiles
	 *
	 * @param	tile: tile to be added to the occupied tile list
	 */
	public void addTile(Tile tile) {
		// Replace the tile if it is already in the list
		for(int i = 0; i < this.occupiedTiles.size(); i++) {
			if (this.occupiedTiles.get(i).equals(tile)) {
				this.occupiedTiles.set(i, tile);
				return;
			}
		}
		this.occupiedTiles.add(tile);
	}

	/*
	 * Remove a tile from the occupied tiles list
	 *
	 * @param	tile: tile to be removed from the list
	 */
	public void removeTile(Tile tile){
		this.occupiedTiles.removeIf(t -> t.equals(tile));
	}

	@Override
	public boolean equals(Object obj) {
		// Check for null
		if (obj == null) {
			return false;
		}
		// Check class
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		final Player other = (Player) obj;
		return this.color == other.getColor();
	}

	/*
	 * Get the list of occupied tiles
	 */
	public ArrayList<Tile> getOccupiedTiles() {
		return this.occupiedTiles;
	}
}







