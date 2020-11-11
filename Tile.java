/*
 * Tile implementation for Conga Board
 */
class Tile {
    private Player player;
    private int count;
    private final int[] id;

    /* Constructor */
    Tile(int[] id) {
        // id = [row, col]
        this.id = id;
        this.player = null;
        this.count = 0;
    }

    /* Make a hard copy of the tile */
    public Tile cloneTile () {
        Tile tile = new Tile(this.id);
        tile.player = this.player;
        tile.count = this.count;
        return tile;
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
        return this.id[0] == other.getId()[0] && this.id[1] == other.getId()[1];
    }

    /*
     * Remove the player from the board and reset count
     */
    public void removePlayer() {
        this.count = 0;
        this.player = null;
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

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}