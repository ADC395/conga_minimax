import java.util.Arrays;

class CongaBoard {
    // Default rows, columns & pieces
    int rows = 4;
    int columns = 4;
    int pieces = 10;
    Tile[][] board;

    enum Move {
        INVALID,
        ROW,
        COLUMN,
        DIAGONAL
    }

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
                int[] id = {row, col};
                this.board[row][col] = new Tile(id);
            }
        }
        // Put players in the board
        player1 = new Player(Color.BLACK, this.board[0][0].getId());
        this.board[0][0].setCount(10);
        this.board[0][0].setPlayer(player1);
        player2 = new Player(Color.WHITE, this.board[this.rows-1][this.columns-1].getId());
        this.board[this.rows-1][this.columns-1].setCount(10);
        this.board[this.rows-1][this.columns-1].setPlayer(player2);
    }

    public Move move(Tile currentTile, Tile goalTile) {
        Move moveType = this.checkMove(currentTile, goalTile);
        if (moveType == Move.INVALID) {
            System.out.println("Cannot make move from " + Arrays.toString(currentTile.getId()) + " to " +
                    Arrays.toString(goalTile.getId()));
        } else {
            this.moveToGoal(currentTile, goalTile, moveType, 0);
        }
        return moveType;

    }

    private void moveToGoal(Tile currentTile, Tile goalTile, Move moveType, int moveCount) {
        Player currentPlayer = currentTile.getPlayer();
        if (currentTile == goalTile) {
            System.out.println("Moved from " + Arrays.toString(currentTile.getId()) + " to " +
                    Arrays.toString(goalTile.getId()));
        } else {
            Tile nextTile = this.moveToNextTile(currentTile, goalTile, moveType, moveCount);
            if (moveCount == 0) {
                currentTile.setCount(0);
                currentTile.setPlayer(null);
                currentPlayer.removeTile(currentTile.getId());
            }
            if (nextTile.getPlayer() == null) {
                nextTile.setPlayer(currentPlayer);
            }
            currentPlayer.addTile(nextTile.getId());
            moveCount++;
            this.moveToGoal(nextTile, goalTile, moveType, moveCount);
        }
    }


    private Tile moveToNextTile(Tile currentTile, Tile goalTile, Move moveType, int moveCount) {
        Tile nextTile = getNextTile(currentTile, goalTile, moveType);
        // TODO: logic about the move: what if we have pieces already do we carry them along
        nextTile.setCount(nextTile.getCount() + currentTile.getCount() - moveCount);
        currentTile.setCount(moveCount);

        return nextTile;
    }


    private Tile getNextTile(Tile currentTile, Tile goalTile, Move moveType) {
        Tile nextTile;
        int currentRow, currentCol, goalRow, goalCol;
        currentRow = currentTile.getId()[0];
        currentCol = currentTile.getId()[1];
        goalRow = goalTile.getId()[0];
        goalCol = goalTile.getId()[1];

        switch (moveType) {
            case ROW:
                // move along row
                if (currentCol < goalCol) {
                    nextTile = this.board[currentRow][currentCol+1];
                } else {
                    nextTile = this.board[currentRow][currentCol-1];
                }
                break;
            case COLUMN:
                // move along column
                if (currentRow < goalRow) {
                    nextTile = this.board[currentRow+1][currentCol];
                } else {
                    nextTile = this.board[currentRow-1][currentCol];
                }
                break;
            default:
                // move along diagonal
                if (currentCol < goalCol) {
                    if (currentRow < goalRow) {
                        nextTile = this.board[currentRow+1][currentCol+1];
                    } else {
                        nextTile = this.board[currentRow-1][currentCol+1];
                    }
                } else {
                    if (currentRow < goalRow) {
                        nextTile = this.board[currentRow+1][currentCol-1];
                    } else {
                        nextTile = this.board[currentRow-1][currentCol-1];
                    }
                }
                break;
        }
        return nextTile;
    }


    private Move checkMove(Tile currentTile, Tile goalTile) {
        int distance = -1;
        Move moveType = null;
        int currentRow = currentTile.getId()[0];
        int currentCol = currentTile.getId()[1];
        int goalRow = goalTile.getId()[0];
        int goalCol = goalTile.getId()[1];
        Player pC = currentTile.getPlayer();
        Player pG = goalTile.getPlayer();

        // Check if rows and columns are valid
        if (currentRow > this.rows || currentCol > this.columns || goalRow > this.rows || goalCol > this.columns ||
                currentRow < 0 || currentCol < 0 || goalRow < 0 || goalCol < 0) {
            return Move.INVALID;
        }
        // Check if player at current tile can move to goal tile
        if ( pC == null || (pG != null && pC.getColor() != pG.getColor())) {
            return Move.INVALID;
        }

        // Make moves
        // Row moves
        if (currentRow == goalRow) {
            // Check if you have enough pieces to make the move
            distance = Math.abs(currentCol - goalCol);
            moveType =  Move.ROW;
        // Column moves
        } else if (currentCol == goalCol) {
            distance = Math.abs(currentRow - goalRow);
            moveType = Move.COLUMN;
        // Diagonal moves
        } else if (Math.abs(currentRow - goalRow) == Math.abs(currentCol - goalCol)){
            distance = Math.abs(currentRow - goalRow);
            moveType = Move.DIAGONAL;
        }
        // invalid move or not enough pieces to move
        if (moveType == null ||currentTile.getCount() < this.getMinimumPieces(distance)) {
            return Move.INVALID;
        }

        // Check if there is another player in between current tile and goal tile
        Tile tempTile = currentTile;
        while (tempTile != goalTile) {
            tempTile = this.getNextTile(currentTile, goalTile, moveType);
            if (tempTile.getPlayer() != null) {
                if (tempTile.getPlayer().getColor() != currentTile.getPlayer().getColor()) {
                    return Move.INVALID;
                }
            }
        }
        return moveType;
    }


    private int getMinimumPieces(int distance) {
        if (distance == -1) {
            return Integer.MAX_VALUE;
        }
        // Algorithm to calculate minimum number of pieces required to move
        // Sum of n numbers = n(n+1) / 2
        // Sum of n-1 numbers = n(n-1) / 2
        // Minimum required number of pieces to make move = (n(n-1) / 2) + 1
        return ((distance * (distance-1) / 2) + 1);
    }
}