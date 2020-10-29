public class CongaBoard {
    int rows = 4;
    int columns = 4;
    int pieces = 10;
    Tile[][] board;

    public CongaBoard() {
        this.initializeBoard();
    }

    public CongaBoard(int rows, int columns, int pieces) {
        this.rows = rows;
        this.columns = columns;
        this.pieces = pieces;

        this.initializeBoard();
    }

    private void initializeBoard() {
        Player player1;
        Player player2;
        this.board = new Tile[this.rows][this.columns];
        for (int row=0; row < this.rows; row++) {
            for (int col=0; col <this.columns; col++) {
                this.board[row][col] = new Tile(new int[]{row, col});
            }
        }
        player1 = new Player(Color.BLACK, this.board[0][0]);
        this.board[0][0].setCount(10);
        player2 = new Player(Color.WHITE, this.board[this.rows-1][this.columns-1]);
        this.board[this.rows-1][this.columns-1].setCount(10);

    }

}