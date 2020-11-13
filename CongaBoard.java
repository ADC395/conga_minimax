/*
 * Conga Board Implementation.
 */
class CongaBoard {
    // Default rows, columns & pieces
    private final int rows;
    private final int columns;
    private final int pieces;
    private final Player[] players;
    private Tile[][] board;
    // boardValue is a unique identifier for every state of the board
    private String boardValue;

    /* Constructor */
    public CongaBoard(int rows, int columns, int pieces, Player[] players) {
        this.rows = rows;
        this.columns = columns;
        this.pieces = pieces;
        this.players = players;
        // initialize board
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
        // Put players on the board
        this.board[0][0].setPlayer(this.players[0]);
        this.board[this.rows-1][this.columns-1].setPlayer(this.players[1]);
        // Add player pieces
        this.board[0][0].setCount(10);
        this.board[this.rows-1][this.columns-1].setCount(10);
        // Add the tiles to player's occupied tiles list
        this.players[0].addTile(this.board[0][0]);
        this.players[1].addTile(this.board[this.rows-1][this.columns-1]);
    }

    /* Make a hard copy of the board */
    public CongaBoard cloneBoard() {
        CongaBoard newCongaBoard = new CongaBoard(this.rows, this.columns, this.pieces, this.players);
        for(int row = 0; row < this.rows; row++ ) {
            for(int column = 0; column < this.columns; column++) {
                newCongaBoard.board[row][column] = this.board[row][column].cloneTile();
            }
        }
        return newCongaBoard;
    }

    /*
     * Initialize move from startTile to goalTile.
     * Will be used if we have a human player.
     *
     * @param   startTile: Tile you are currently on
     * @param   goalTile: Tile you want to move to
     *
     * @return  Type of move made
     */
    public Move humanMove(Tile startTile, Tile goalTile) {
        Move moveType = this.checkMove(startTile, goalTile, null);
        if (moveType == Move.INVALID) {
            return Move.INVALID;
        } else {
            this.moveToGoal(startTile, goalTile, startTile, moveType, 0, startTile.getCount());
        }
        return moveType;

    }

    /*
     * Initialize move from startTile to goalTile if you already know move type
     * and know that the move is valid
     *
     * @param   startTile: Tile you are currently on
     * @param   goalTile: Tile you want to move to
     * @param   move: Type of move to be made
     */
    public void move(Tile startTile, Tile goalTile, Move move) {
        this.moveToGoal(startTile, goalTile, startTile, move, 0, startTile.getCount());
    }


    /*
     * Start moving towards goal tile from start tile
     *
     * @param   startTile: Tile you are starting to move from
     * @param   goalTile: Your goal tile
     * @param   currentTile: Tile you are currently on
     * @param   moveType: Type of move to be made from currentTile to goalTile
     * @param   moveCount: Step made so far, starts from 0
     */
    private void moveToGoal(Tile startTile, Tile goalTile, Tile currentTile, Move moveType, int moveCount, int remainingCounts) {
        // TODO: logic about the move: what if we have pieces already do we carry them along
        // TODO: set the board name when moving
        currentTile.setPlayer(startTile.getPlayer());
        if (currentTile == goalTile) {
            goalTile.setCount(goalTile.getCount() + remainingCounts);
            startTile.getPlayer().removeTile(startTile);
            startTile.removePlayer();
            return;
        }
        Tile nextTile = this.getNextTile(currentTile, goalTile, moveType);
//        nextTile.setPlayer(startTile.getPlayer());
//        nextTile.setCount(nextTile.getCount() + currentTile.getCount() - moveCount);
        currentTile.setCount(currentTile.getCount() + moveCount);
        startTile.getPlayer().addTile(nextTile);
        remainingCounts = remainingCounts - moveCount;
        moveCount++;

        this.moveToGoal(startTile, goalTile, nextTile, moveType, moveCount, remainingCounts);
    }

    /*
     * Get next tile needed to move form startTile to goalTile
     *
     * @param   startTile: Tile you are currently on
     * @param   goalTile: Tile you want to move to
     * @param   moveType: Type of move you are making
     *
     * @return  next Tile
     */
    public Tile getNextTile(Tile startTile, Tile goalTile, Move moveType) {
        Tile nextTile;
        int startRow, startCol, goalRow, goalCol;
        startRow = startTile.getId()[0];
        startCol = startTile.getId()[1];
        goalRow = goalTile.getId()[0];
        goalCol = goalTile.getId()[1];

        // Next tile will be determined by the type of move we are making
        switch (moveType) {
            case ROW:
                // move along row
                if (startCol < goalCol) {
                    nextTile = this.board[startRow][startCol + 1];
                } else {
                    nextTile = this.board[startRow][startCol - 1];
                }
                break;
            case COLUMN:
                // move along column
                if (startRow < goalRow) {
                    nextTile = this.board[startRow + 1][startCol];
                } else {
                    nextTile = this.board[startRow - 1][startCol];
                }
                break;
            default:
                // move along diagonal
                if (startCol < goalCol) {
                    if (startRow < goalRow) {
                        nextTile = this.board[startRow + 1][startCol + 1];
                    } else {
                        nextTile = this.board[startRow - 1][startCol + 1];
                    }
                } else {
                    if (startRow < goalRow) {
                        nextTile = this.board[startRow + 1][startCol - 1];
                    } else {
                        nextTile = this.board[startRow - 1][startCol - 1];
                    }
                }
                break;
        }
        return nextTile;
    }

    /*
     * Check if a move can be made from startTile to goalTile
     *
     * @param   startTile: Tile you are currently on
     * @param   goalTile: Tile you want to move to
     * @param   move: type of move to be made, can be null
     *
     * @return  Type of move that can be made
     */
    public Move checkMove(Tile startTile, Tile goalTile, Move move) {
        int distance = -1;
        Move moveType = null;
        int startRow = startTile.getId()[0];
        int startCol = startTile.getId()[1];
        int goalRow = goalTile.getId()[0];
        int goalCol = goalTile.getId()[1];
        Player pC = startTile.getPlayer();
        Player pG = goalTile.getPlayer();

        // Check if player at current tile can move to goal tile
        if (pC == null || (pG != null && pC.getColor() != pG.getColor())) {
            return Move.INVALID;
        }

        // if move is null than it will try to determine valid move type that can be made
        if (move !=  null) {
            distance = switch (move) {
                case ROW, DIAGONAL -> Math.abs(startCol - goalCol);
                case COLUMN -> Math.abs(startRow - goalRow);
                default -> 0;
            };
            moveType = move;
        } else {
            // Check for row move
            if (startRow == goalRow) {
                distance = Math.abs(startCol - goalCol);
                moveType = Move.ROW;
                // Check for column move
            } else if (startCol == goalCol) {
                distance = Math.abs(startRow - goalRow);
                moveType = Move.COLUMN;
                // Check for diagonal move
            } else if (Math.abs(startRow - goalRow) == Math.abs(startCol - goalCol)) {
                distance = Math.abs(startRow - goalRow);
                moveType = Move.DIAGONAL;
            }
        }

        // Check if we have enough pieces to make the move
        if (moveType == null || startTile.getCount() < this.getMinimumPieces(distance)) {
            return Move.INVALID;
        }

        // Check if there is another player in between current tile and goal tile
        Tile currentTile = startTile;
        while (currentTile != goalTile) {
            currentTile = this.getNextTile(currentTile, goalTile, moveType);
            if (currentTile.getPlayer() != null) {
                if (currentTile.getPlayer().getColor() != startTile.getPlayer().getColor()) {
                    return Move.INVALID;
                }
            }
        }
        return moveType;
    }

    /*
     * Get minimum amount of pieces required to make a move in conga board
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
    public void printBoard() {
        for (int row = 0; row < this.rows; row++) {
            System.out.println("_________________________");
            for (int col = 0; col < this.columns; col++) {
                Tile currentTile =this.board[row][col];
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

    /* Set board value. Board value also depends on which player has the current state
     *
     * @param player: Player who has the current turn
     */
    public void setBoardValue(Player player) {
        // board value = currentPlayerColor + [playerOccupiedTile + tile's id.....]
        StringBuilder tempS = new StringBuilder();
        tempS.append(player.getColor().toString().charAt(0));
        this.boardValue = player.getColor().toString();
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.columns; col++) {
                if (this.board[row][col].getPlayer() != null) {
                    tempS.append(row);
                    tempS.append(col);
                    tempS.append(this.board[row][col].getPlayer().getColor().toString().charAt(0));
                }
            }
        }
        this.boardValue = tempS.toString();
    }

    /* Get board value */
    public String getBoardValue() {
        return this.boardValue;
    }

    /* Get number of rows in the board */
    public int getRows() {
        return rows;
    }

    /* Get number of columns in the board */
    public int getColumns() {
        return columns;
    }

    /* Get the board */
    public Tile[][] getBoard() {
        return board;
    }
}