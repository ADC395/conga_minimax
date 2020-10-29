class Tile {
    private Player player;
    private int count;
    private int[] id;

    Tile(int[] id) {
        // id = [row, col]
        this.id = id;
    }

    public void setCount(int count) {
        this.count = count;
    }
}