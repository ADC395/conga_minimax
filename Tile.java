import java.util.Arrays;

/*
 * Tile implementation for Conga Board
 */
class Tile extends Object implements Cloneable {
    private Player player;
    private int count;
    private final int[]  id;
    private String tileName;

    /* Constructor */
    Tile(int[] id) {
        // id = [row, col]
        this.id = id;
        this.player = null;
        this.count = 0;
        // Tile name = Tile + "Player's color initial" + tile id
        // Tile name will act as key if we store Tile in a dictionary
        this.tileName = "tile";
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        if (player == null) {
            this.tileName = "tile";
        } else {
            this.tileName = "tile" + player.getColor().toString().charAt(0) + id[0] + id[1];
        }
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int[] getId() {
        return this.id;
    }

    public String getTileName() {
        return this.tileName;
    }

    private void setTileName(String newTileName) {
        this.tileName = newTileName;
    }
    @Override
    /* Check if two tiles are equal */
    public boolean equals(Object obj) {
        // Check for null
        if (obj == null) {
            return false;
        }
        // Check class
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Tile other = (Tile) obj;
        return this.tileName.equals(other.getTileName());
    }

    /* Overriding clone() method of Object class */
    public Object clone () throws CloneNotSupportedException {
        Tile tile = new Tile(this.id);
        tile.player = this.player;
        tile.count = this.count;
        tile.tileName = this.tileName;

        return tile;
    }
}