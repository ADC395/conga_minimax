import java.util.Arrays;

/*
 * Tile implementation for Conga Board
 */
class Tile {
    private Player player;
    private int count;
    private final int[]  id;

    /* Constructor */
    Tile(int[] id) {
        // id = [row, col]
        this.id = id;
        this.player = null;
        this.count = 0;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getCount() {
        return  this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int[] getId() {
        return  this.id;
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
        return Arrays.equals(this.id, other.getId()) && this.player.equals(other.player);
    }

}