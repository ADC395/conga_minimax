import java.util.Arrays;

/*
 * Conga Board Implementation.
 */
class CongaBoard {
    // Default rows, columns & pieces
    int rows = 4;
    int columns = 4;
    int pieces = 10;
    Tile temp;
    Tile[][] board;

    /* Default CongaBoard constructor */
    public CongaBoard() {
        this.initializeBoard();
    }

    /* Constructor override */
    public CongaBoard(int rows, int columns, int pieces) {
        this.rows = rows;
        this.columns = columns;
        this.pieces = pieces;

        this.initializeBoard();
    }

    /* Initialize board for the game. It generates first state of the game. */
    private void initializeBoard() {
        this.board = new Tile[this.rows][this.columns];
        // Initialize board
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.columns; col++) {
                int[] id = {row, col};
                this.board[row][col] = new Tile(id);
            }
        }
    }

    /*
     * move pieces from currentTile to goalTile if move is valid / possible.
     *
     * @param   currentTile (Tile): Tile you are currently on
     * @param   goalTile (Tile): Tile you want to move to
     *
     * @return  Move: Type of move made
     */
    public Move move(Tile currentTile, Tile goalTile) {
        Move moveType = this.checkMove(currentTile, goalTile);
        if (moveType == Move.INVALID) {
            System.out.println("Cannot make move from " + Arrays.toString(currentTile.getId()) + " to " +
                    Arrays.toString(goalTile.getId()));
        } else {
            this.moveToGoal(currentTile, goalTile, currentTile, moveType, 0);
        }
        return moveType;

    }

    /*
     * move pieces from currentTile to goalTile if you already know move type
     * and know that the move is valid
     *
     * @param   currentTile (Tile): Tile you are currently on
     * @param   goalTile (Tile): Tile you want to move to
     * @param   (Move)
     * @return  Move: Type of move made
     */
    public void move(Tile currentTile, Tile goalTile, Move move) {
        this.moveToGoal(currentTile, goalTile, currentTile, move, 0);

    }


    /*
     * Make move from currentTile to goalTile
     *
     * @param   currentTile: Tile you are currently on
     * @param   goalTile: Your goal tile
     * @param   moveType: Type of move to be made from currentTile to goalTile
     * @param   moveCount: Step made so far, starts from 0
     * @param   startingTile: Tile you are started the move from
     */
    private void moveToGoal(Tile currentTile, Tile goalTile, Tile startingTile, Move moveType, int moveCount) {
        Player currentPlayer = currentTile.getPlayer();

        if (currentTile == goalTile) {
            System.out.println("Moved from " + Arrays.toString(startingTile.getId()) + " to " +
                    Arrays.toString(goalTile.getId()));
            this.temp = null;
        } else {
            Tile nextTile = this.moveToNextTile(currentTile, goalTile, moveType, moveCount);
            if (moveCount == 0) {
                this.temp = currentTile;
                currentTile.setCount(0);
                currentTile.setPlayer(null);
                currentPlayer.removeTile(currentTile.getId());
            }
            if (nextTile.getPlayer() == null) {
                nextTile.setPlayer(currentPlayer);
            }
            currentPlayer.addTile(nextTile.getId());
            moveCount++;
            this.moveToGoal(nextTile, goalTile, startingTile, moveType, moveCount);
        }
    }

    /*
     * Make the move to next tile
     *
     * @param   currentTile: Tile you are currently on
     * @param   goalTile: Goal Tile
     * @param   moveCount: move made so far in current turn
     *
     * @return  Next tile
     */
    private Tile moveToNextTile(Tile currentTile, Tile goalTile, Move moveType, int moveCount) {
        Tile nextTile = getNextTile(currentTile, goalTile, moveType);
        // TODO: logic about the move: what if we have pieces already do we carry them along
        nextTile.setCount(nextTile.getCount() + currentTile.getCount() - moveCount);
        currentTile.setCount(moveCount);

        return nextTile;
    }

    /*
     * Get next tile needed to move form currentTile to goalTile
     *
     * @param   currentTile: Tile you are currently on
     * @param   goalTile: Tile you want to move to
     * @param   moveType: Type of move you are making
     *
     * @return  next Tile
     */
    public Tile getNextTile(Tile currentTile, Tile goalTile, Move moveType) {
        Tile nextTile;
        int currentRow, currentCol, goalRow, goalCol;
        currentRow = currentTile.getId()[0];
        currentCol = currentTile.getId()[1];
        goalRow = goalTile.getId()[0];
        goalCol = goalTile.getId()[1];

        // Next tile will be determined by the type of move we are making
        switch (moveType) {
            case ROW:
                // move along row
                if (currentCol < goalCol) {
                    nextTile = this.board[currentRow][currentCol + 1];
                } else {
                    nextTile = this.board[currentRow][currentCol - 1];
                }
                break;
            case COLUMN:
                // move along column
                if (currentRow < goalRow) {
                    nextTile = this.board[currentRow + 1][currentCol];
                } else {
                    nextTile = this.board[currentRow - 1][currentCol];
                }
                break;
            default:
                // move along diagonal
                if (currentCol < goalCol) {
                    if (currentRow < goalRow) {
                        nextTile = this.board[currentRow + 1][currentCol + 1];
                    } else {
                        nextTile = this.board[currentRow - 1][currentCol + 1];
                    }
                } else {
                    if (currentRow < goalRow) {
                        nextTile = this.board[currentRow + 1][currentCol - 1];
                    } else {
                        nextTile = this.board[currentRow - 1][currentCol - 1];
                    }
                }
                break;
        }
        return nextTile;
    }

    /*
     * Check if a move can be made from currentTile to goalTile
     *
     * @param   currentTile (Tile): Tile you are currently on
     * @param   goalTile (Tile): Tile you want to move to
     *
     * @return  Type of move that can be made
     */
    public Move checkMove(Tile currentTile, Tile goalTile) {
        int distance = -1;
        Move moveType = null;
        int currentRow = currentTile.getId()[0];
        int currentCol = currentTile.getId()[1];
        int goalRow = goalTile.getId()[0];
        int goalCol = goalTile.getId()[1];
        Player pC = currentTile.getPlayer();
        Player pG = goalTile.getPlayer();

        // check if rows and columns are valid for currentTile and goalTile
        if (currentRow > this.rows || currentCol > this.columns || goalRow > this.rows || goalCol > this.columns ||
                currentRow < 0 || currentCol < 0 || goalRow < 0 || goalCol < 0) {
            return Move.INVALID;
        }
        // Check if player at current tile can move to goal tile
        if (pC == null || (pG != null && pC.getColor() != pG.getColor())) {
            return Move.INVALID;
        }

        // Check for row move
        if (currentRow == goalRow) {
            distance = Math.abs(currentCol - goalCol);
            moveType = Move.ROW;
            // Check for column move
        } else if (currentCol == goalCol) {
            distance = Math.abs(currentRow - goalRow);
            moveType = Move.COLUMN;
            // Check for diagonal move
        } else if (Math.abs(currentRow - goalRow) == Math.abs(currentCol - goalCol)) {
            distance = Math.abs(currentRow - goalRow);
            moveType = Move.DIAGONAL;
        }
        // Check if valid move can be made
        if (moveType == null || currentTile.getCount() < this.getMinimumPieces(distance)) {
            return Move.INVALID;
        }

        // Check if there is another player in between current tile and goal tile
        Tile tempTile = currentTile;
        while (tempTile != goalTile) {
            tempTile = this.getNextTile(tempTile, goalTile, moveType);
            if (tempTile.getPlayer() != null) {
                if (tempTile.getPlayer().getColor() != currentTile.getPlayer().getColor()) {
                    return Move.INVALID;
                }
            }
        }
        return moveType;
    }

    /*
     * Get minimum amount of pieces required to make move
     *
     * @param   distance: Distance need to move
     *
     * @return  number of pieces required to make move
     */
    private int getMinimumPieces(int distance) {
        if (distance == -1) {
            return Integer.MAX_VALUE;
        }
        /* Algorithm to calculate minimum number of pieces required to move
         * Sum of n numbers = n(n+1) / 2
         * Sum of n-1 numbers = n(n-1) / 2
         * Minimum required number of pieces to make move = (n(n-1) / 2) + 1
         */
        return ((distance * (distance - 1) / 2) + 1);
    }

    /*
     * Print current board
     */
    public static void printBoard(CongaBoard congaBoard) {
        //System.out.println();
        for (int row = 0; row < congaBoard.rows; row++) {
            System.out.println("_________________________");
            for (int col = 0; col < congaBoard.columns; col++) {
                Tile currentTile = congaBoard.board[row][col];
                // Width of tile is 6
                if (col == 0) {
                    System.out.print("|");
                }
                if (currentTile.getPlayer() == null) {
                    System.out.printf("%6s", "|");
                } else if (currentTile.getPlayer().getColor() == Color.BLACK) {
                    System.out.print(String.format("%4d", currentTile.getCount()) + "B|");
                } else {
                    System.out.print(String.format("%4d", currentTile.getCount()) + "W|");
                }
            }
            System.out.println();
        }
        System.out.println("_________________________");
    }
}