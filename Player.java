import java.util.*;
import java.lang.*;

/* A player is an agent who can perform a move on the board
 * Each player is designated a color and has a list of occupied tiles
 *
 * @author	Adam Carrol
 * @version	1.0
 */
class Player {

	private final ArrayList<int[]> occupiedTiles;
	private final Color color;


	//Constructor
	Player(Color color, int[] tileId) {
		this.occupiedTiles = new ArrayList<>();
		this.color = color;
		this.occupiedTiles.add(tileId);
	}

	public Color getColor() {
		return this.color;
	}

	public ArrayList<int[]> getOccupiedTiles() {
		return this.occupiedTiles;
	}

	public void removeTile(int[] tile){
		// TODO: make this check better
		// Don't add duplicates
		for (int[] id : this.occupiedTiles) {
			if (Arrays.equals(id, tile)) {
				return;
			}
		}
		this.occupiedTiles.remove(tile);
	}

	public void addTile(int[] tileId) {

		this.occupiedTiles.add(tileId);
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
		// TODO: implement check for occupiedTiles
		return this.color == other.getColor();
	}
}







